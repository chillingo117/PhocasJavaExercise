
import query.*;
import generation.Person;
import data.Db;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Main Class
 *
 * Serves to run the app, prompts user input, processes user input, and prints out information.
 */
public class Main {
    private static final Scanner sc = new Scanner(System.in);
    private static final Query query = Query.getQuery();

    private static final String menu = "What action would you like to take? \n" +
            "1: Generate new dataset\n" +
            "2: Make a query\n" +
            "3: Exit";

    private static final String queryMenu = "What query would you like to make? \n" +
            "1: Find the oldest person\n" +
            "2: Count the number of people per country\n" +
            "3: Group people by age range given a country\n" +
            "4: Find the person with the longest full name";

    private static final String countryPrompt = "Please enter the country of interest.";

    /**
     * Processes input for the main menu.
     * @param input string input from user for the main menu
     */
    private static void mainMenuInput(String input){
        if(input.equals("1")){
            resample();
        } else if (input.equals("2")){
            System.out.println(queryMenu);
            String in = sc.nextLine();
            queryInput(in);
        } else {
            System.out.println("Invalid input");
            // start over from main menu
        }
    }

    /**
     * Processes input for the query menu.
     * @param input string input from user for the query menu
     */
    private static void queryInput(String input){
        switch (input) {
            case "1":
                // Get the oldest people
                ArrayList<Person> oldests = query.getOldest();
                if (oldests.size() == 1) {
                    System.out.println("The oldest person in the database is: ");
                } else {
                    System.out.println("The oldest people in the database are: ");
                }
                for (Person old : oldests) {
                    System.out.println(old);
                }
                break;
            case "2":
                // get the count of everyone grouped by country
                for (CountryGroup group : query.groupByCountry()) {
                    System.out.println("There are " + group.people.size() + " people in " + group.country);
                }
                break;
            case "3":
                // filter by country, then group by age brackets of 10
                String country = getCountryInput();
                if (country != null) {
                    System.out.println("In " + country + " :");
                    for (AgeGroup group : query.groupByAgeRangeGivenCountry(country)) {
                        System.out.println("Aged between " + (int) (group.floor * 10) + " and " + (int) (((group.floor * 10) + 9)) + ":");
                        for (Person p : group.people) {
                            System.out.println(p.getFirstName() + " " + p.getLastName() + "");
                        }
                    }
                } else {
                    // if the country is null, then the query was cancelled
                    System.out.println("Query cancelled");
                    // start over from main menu
                }
                break;
            case "4":
                ArrayList<Person> longests = query.getLongestNamed();
                if (longests.size() == 1) {
                    System.out.println("The person with the longest name is: ");
                } else {
                    System.out.println("The people with the longest names are: ");
                }
                for (Person p : longests) {
                    System.out.println(p);
                }
                break;
            default:
                System.out.println("Invalid input");
                // start over from main menu
                break;
        }
    }

    /**
     * Prompts user to type in a country, and only accepts valid country inputs.
     * Also provides the option to give up.
     * @return a string that is a valid country, or null if this query was cancelled
     */
    private static String getCountryInput(){
        System.out.println(countryPrompt);
        String s = sc.nextLine();
        while(!query.countryExists(s) && !s.equals("x")){
            System.out.println("That country is not available.");
            System.out.println(countryPrompt);
            System.out.println("Enter 'x' to cancel");
            s = sc.nextLine();
        }
        if(s.equals("x")){
            return null;
        } else {
            return s;
        }

    }

    /**
     * Takes in an input for a sample count. If the input is valid, it is returned.
     * Otherwise, this function repeatedly asks for input until a valid one is given.
     * @param in a user's attempt at a valid number
     * @return The user's input converted to a positive number
     */
    private static int getValidSampleCount(String in){
        try{
            int n = Integer.parseInt(in);
            if(n <= 0 ){
                System.out.println("Invalid input. Please enter a positive number.");
                String s = sc.nextLine();
                return getValidSampleCount(s);
            } else {
                return n;
            }
        } catch (Exception err){
            System.out.println("Invalid input. Please enter a positive number.");
            String s = sc.nextLine();
            return getValidSampleCount(s);
        }
    }

    /**
     * Asks for the value to use as a sample size for resampling the database.
     * Then prompts the database to perform the resample.
     */
    private static void resample(){
        System.out.println("Type in how many random people you want in the db");
        String s = sc.nextLine();
        int sampleSize = getValidSampleCount(s);
        Db.getDb().resampleDb(sampleSize);
    }

    /**
     * Main class. Runs the app.
     * @param args None
     */
    public static void main(String[] args) {
        System.out.println("Creating initial sample...");
        resample();
        System.out.println(menu);
        String in = sc.nextLine();
        while(!in.equals("3")){ //"3" is the input for quitting.
            mainMenuInput(in);
            // once input has been processed, go back to main menu
            System.out.println(menu);
            in = sc.nextLine();
        }
    }
}
