package se.jensen.caw21.bjorn;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;

import java.util.ArrayList;

import java.net.URL;
import java.net.URLConnection;
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

      ArrayList<String> myArrayOfStrings = new ArrayList<String>();

      return myArrayOfStrings;
  }

  public Movie[] getMovies() {
    Movie[] movies = {};

    String target = "/movies";

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
			}
			else {
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
    String target = "/clear_movies";

    //System.out.println("Clearing movies from " + apiAddress + target);

    boolean success = false;

    try {
      URL url = new URL(apiAddress + target);
      connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("GET");

      int status = connection.getResponseCode();

      if (status >= 300) {
        success = false;
      } else {
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
    String target = "/add_movie";

    //System.out.println("Adding movie at " + apiAddress + target);

    boolean success = false;

    try {
      URL url = new URL(apiAddress + target);
      connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("POST");
      connection.setRequestProperty("Content-Type", "application/json; utf-8");
      connection.setDoOutput(true);

      try(OutputStream os = connection.getOutputStream()) {
        byte[] input = newMovie.toJson().getBytes("utf-8");
        os.write(input, 0, input.length);
      }

      int status = connection.getResponseCode();

      if (status >= 300) {
        success = false;
      } else {
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
