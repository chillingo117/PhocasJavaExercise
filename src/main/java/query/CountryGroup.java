package query;

import generation.Person;

import java.util.ArrayList;

/**
 * Country group class
 *
 * Custom variable for grouping Person instances with their countries.
 */
public class CountryGroup {
    public String country;
    public ArrayList<Person> people = new ArrayList<>();

    public CountryGroup(String country){
        this.country = country;
    }
}
