/*
Resources Class
SINGLETON

Reads and contains the name and country lists for easy access by the rest of the project.
 */
package data;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * Resources class
 * Singleton
 *
 * Reads name and country data for easier access by the rest of this app.
 */
public class Resources {
    private static Resources resources = null;
    private static final ArrayList<String> firstNameList = new ArrayList<>();
    private static final ArrayList<String> lastNameList = new ArrayList<>();
    private static final ArrayList<String> countryList = new ArrayList<>();

    /**
     * Upon instantiation, this class reads all the information and stores it as attributes.
     */
    private Resources(){
        String path = System.getProperty("user.dir") + "/src/main/resources/";
        try{
            BufferedReader reader = new BufferedReader(new FileReader(path+ "firstNames.txt"));
            String row;
            while ((row = reader.readLine()) != null) {
                firstNameList.add(row);
            }
            reader.close();

            reader = new BufferedReader(new FileReader(path+ "countries.txt"));
            while ((row = reader.readLine()) != null) {
                countryList.add(row);
            }
            reader.close();

            reader = new BufferedReader(new FileReader(path+ "lastNames.txt"));
            while ((row = reader.readLine()) != null) {
                lastNameList.add(row);
            }
            reader.close();

        } catch (Exception err) {
            if (err instanceof FileNotFoundException) {
                System.out.println("data not found");
            } else {
                System.out.println("Unknown Error");
            }
            System.exit(1);
        }
    }

    public static Resources getResources() {
        if(resources == null){
            resources = new Resources();
        }
        return resources;
    }

    public ArrayList<String> getFirstNameList(){
        return new ArrayList<>(firstNameList);
    }
    public ArrayList<String> getLastNameList(){
        return new ArrayList<>(lastNameList);
    }
    public ArrayList<String> getCountryList(){
        return new ArrayList<>(countryList);
    }
}
