package se.jensen.caw21.bjorn;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;

import com.github.cliftonlabs.json_simple.JsonObject;

import com.fasterxml.jackson.databind.*;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import java.net.URL;
import java.net.HttpURLConnection;

public class ApiClient {
    // Typically https://127.0.0.1:8080/api (Important: Without the / at the end)
    private String apiAddress;
    HttpURLConnection connection;

    public ApiClient(String apiAddress) {
        this.apiAddress = apiAddress;
    }

    public ArrayList<String> getStringArray(String target) {
        JsonObject countryObj = new JsonObject();

        ArrayList<String> myArrayOfStrings = new ArrayList<>();

        return myArrayOfStrings;
    }

    public Movie[] getMovies() {
        Movie[] movies = {};

        String target = "/movies/list";

        //System.out.println("Getting movies from " + apiAddress + target);

        BufferedReader reader;
        String line;
        StringBuilder responseContent = new StringBuilder();

        try {
            URL url = new URL(apiAddress + target);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("accept", "application/json");

            int status = connection.getResponseCode();

            if (status >= 300) {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            } else {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            }

            //System.out.println(responseContent.toString());
            String jsonStr = responseContent.toString();

            ObjectMapper mapper = new ObjectMapper();
            movies = mapper.readValue(jsonStr, Movie[].class);

        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            connection.disconnect();
        }

        return movies;
    }

    public boolean clearMovies() {
        String target = "/movies/clear"; // http://127.0.0.1:8080/api/v1/movies/clear

        //System.out.println("Clearing movies from " + apiAddress + target);

        boolean success = false;

        try {
            URL url = new URL(apiAddress + target);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int status = connection.getResponseCode();

            if (status < 300) {
                success = true;
            }

            //System.out.println(responseContent.toString());
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            connection.disconnect();
        }

        return success;
    }

    public boolean addMovie(Movie newMovie) {
        String target = "/movies/create";

        //System.out.println("Adding movie at " + apiAddress + target);

        boolean success = false;

        try {
            // Skapa ett URL-objekt och säg vilken adress vi vill skicka information till
            URL url = new URL(apiAddress + target);

            // Öppna nätverksanslutningen
            connection = (HttpURLConnection) url.openConnection();

            // Ange metod
            connection.setRequestMethod("POST");

            // Lägg till header (säg att vi vill skicka JSON-data)
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setDoOutput(true);

            // Konvertera vårt Java-objekt (Movie) till JSON med hjälp av .toJSON-metoden i klassen Movie,
            // och skriv den JSON-datan till vår nätverksanslutning med hjälp av en OutputStream
            try (OutputStream os = connection.getOutputStream()) {
                // Skapa en byte-array som innehåller JSON-datan
                byte[] input = newMovie.toJson().getBytes(StandardCharsets.UTF_8);

                // Skriv byte-arrayen till nätverksanslutningen (vi måste också ange hur lång strängen är)
                os.write(input, 0, input.length);
            }

            // Vad fick vi för svar? Vad var HTTP-statuskoden vi fick tillbaka?
            int status = connection.getResponseCode();

            // Generellt om HTTP-koden är över 300 har något gått fel
            // Om den är 299 eller lägre har det gått bra
            // (Exempelvis är "200 OK" bra och "404 Not Found" inte bra)
            if (status < 300) {
                success = true;
            }

            //System.out.println(responseContent.toString());
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            connection.disconnect();
        }

        return success;
    }
}
