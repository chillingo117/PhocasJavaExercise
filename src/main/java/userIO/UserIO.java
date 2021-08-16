package userIO;

import data.Db;
import generation.Person;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import query.*;

/**
 * UserIO class
 * Fully static
 *
 * Prompts and validates user input,
 * Prints user-requested output
 */
public class UserIO {
    private static final Scanner sc = new Scanner(System.in);
    private static final Query query = Query.getQuery();

    private static final String mainMenu = "What action would you like to take? \n" +
            "1: Generate new dataset\n" +
            "2: Make a query\n" +
            "3: Exit";

    private static final String queryMenu = "What query would you like to make? \n" +
            "1: Find the oldest person/people\n" +
            "2: Count the number of people per country\n" +
            "3: Group people by age range given a country\n" +
            "4: Find the person/people with the longest full name";

    private static final String countryPrompt = "Please enter the country of interest.";

    /**
     * Prompts user to type input for the main menu
     * @return validated user input as integer
     */
    public static int mainMenuInput(){
        System.out.println(mainMenu);
        String input = sc.nextLine();
        String[] valid = {"1","2","3"};
        if(Arrays.asList(valid).contains(input)){
            return Integer.parseInt(input);
        } else {
            return -1;
        }
    }

    /**
     * Prompts user to type input for the query menu
     * @return validated user input as integer
     */
    public static int queryMenuInput(){
        System.out.println(queryMenu);
        String input = sc.nextLine();
        String[] valid = {"1","2","3","4"};
        if(Arrays.asList(valid).contains(input)){
            return Integer.parseInt(input);
        } else {
            return -1;
        }
    }

    /**
     * Prints the oldest person/people
     */
    public static void printOldestPeople(){
        // Get the oldest people
        ArrayList<Person> oldests = query.getOldest();
        if (oldests.size() == 1) {
            System.out.println("The oldest person in the database is:");
        } else {
            System.out.println("The oldest people in the database are:");
        }
        for (Person old : oldests) {
            System.out.println(old);
        }
    }

    /**
     * Prints the number of people in each country
     */
    public static void printGroupByCountryCount(){
        for (CountryGroup group : query.groupByCountry()) {
            System.out.println("There are " + group.people.size() + " people in " + group.country);
        }
    }

    /**
     * Prompts user to type in a country, and only accepts valid country inputs.
     * Also provides the option to cancel.
     * @return a string that is a valid country, or null if this query was cancelled
     */
    private static String getCountryInput(){
        System.out.println(countryPrompt);
        String s = sc.nextLine();
        while(!query.countryExists(s) && !s.equals("x")){
            System.out.println("That country is not available. Please ensure correct spelling and capitalization");
            System.out.println(countryPrompt);
            System.out.println("Enter 'x' to cancel this query");
            s = sc.nextLine();
        }
        if(s.equals("x")){
            return null;
        } else {
            return s;
        }

    }

    /**
     * Prints people, filtered by country, in group brackets of 10 years
     */
    public static void printFilterByCountryGroupByAge() {
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
        }
    }

    /**
     * Prints the people with the longest name
     */
    public static void printLongestNamedPeople(){
        ArrayList<Person> longests = query.getLongestNamed();
        if (longests.size() == 1) {
            System.out.println("The person with the longest name is: ");
        } else {
            System.out.println("The people with the longest names are: ");
        }
        for (Person p : longests) {
            System.out.println(p);
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
    public static void resample(){
        System.out.println("Type in how many random people you want in the db");
        String s = sc.nextLine();
        int sampleSize = getValidSampleCount(s);
        Db.getDb().resampleDb(sampleSize);
    }
}
