package se.jensen.caw21.bjorn;

import java.util.Scanner;

public class MyProgram {
    private ApiClient myApiClient;

    // Vår konstruktor som skapar ett nytt ApiClient-objekt
    public MyProgram() {
        myApiClient = new ApiClient("http://127.0.0.1:8080/api/v1");
    }

    // Där vårt program startar
    public void start() {
        boolean programRunning = true;

        while (programRunning) {
            System.out.println();
            System.out.println("=========================================");
            System.out.println("Hello hacker. What would you like to do?");
            System.out.println("1. Get list of movies");
            System.out.println("2. Clear list of movies");
            System.out.println("3. Add a movie to the list");
            System.out.println("4. Exit program");
            System.out.println("=========================================");
            System.out.println();

            int userChoice = getUserInt();

            System.out.println("User picked: " + userChoice);

            switch (userChoice) {
                case 1:
                    printListOfMovies();
                    break;
                case 2:
                    clearListOfMovies();
                    break;
                case 3:
                    addMovie();
                    break;
                case 4:
                    System.out.println("Goodbye.");
                    programRunning = false;
            }

        }
    }

    // Använd ApiClient för att hämta listan av filmer från servern och presentera filmerna till användaren
    public void printListOfMovies() {
        Movie[] movies = myApiClient.getMovies();

        System.out.println("Movies");
        System.out.println("-----------------------------------------");

        if (movies.length > 0) {
            for (int i = 0; i < movies.length; i++) {
                String title = movies[i].title;
                int rating = movies[i].rating;

                System.out.printf("-> %s (%d/10)\n", title, rating);
            }
        } else {
            System.out.println("No movies in list :(");
        }
    }

    // Använd ApiClient för att rensa listan av filmer på servern
    // OBS: Fungerar inte om inte den metoden läggs till i servern.
    // Uppgift: Lägg till den funktionaliteten i serven, uppdatera den här klienten (i ApiClient.java) om det behövs.
    public void clearListOfMovies() {
        if (myApiClient.clearMovies()) {
            System.out.println("List of movies cleared!");
        } else {
            System.out.println("Issue clearing list of movies. :(");
        }
    }

    // Fråga användaren om titel och betyg för film och lägg till den här filmen på servern
    public void addMovie() {
        System.out.println("What's the movie called?");
        String title = getUserString();

        System.out.println("How'd ya like it?");
        int rating = getUserInt();

        Movie newMovie = new Movie(title, rating);

        boolean success = myApiClient.addMovie(newMovie);

        if (success) {
            System.out.println("Movie added!");
        } else {
            System.out.println("Issue adding movie. :(");
        }

    }

    public String getUserString() {
        Scanner myScanner = new Scanner(System.in);

        String myString;

        while (true) {
            try {
                System.out.print("> ");
                myString = myScanner.nextLine();
                break;
            } catch (Exception e) {
                //System.out.println("Exception: " + e);
                System.out.println("Felaktig indata");
            }
        }

        return myString;
    }

    public int getUserInt() {
        Scanner myScanner = new Scanner(System.in);

        int myInteger;

        while (true) {
            try {
                System.out.print("> ");
                myInteger = Integer.parseInt(myScanner.nextLine());
                break;
            } catch (Exception e) {
                //System.out.println("Exception: " + e);
                System.out.println("Felaktig indata");
            }
        }

        return myInteger;
    }
}
