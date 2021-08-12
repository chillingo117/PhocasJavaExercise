package Query;

import data.Db;
import data.Resources;
import generation.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * Query class
 * Singleton
 *
 * Used to perform the various queries available.
 */
public class Query {
    private static final Db db = Db.getDb();
    private static Query query = null;

    private Query(){}

    public static Query getQuery() {
        if(query == null){
            query = new Query();
        }
        return query;
    }

    /**
     * Returns the oldest person in the database.
     * @return the oldest person in the database
     */
    public ArrayList<Person> getOldest(){
        List<Person> people = db.getDbContents();
        ArrayList<Person> tied = new ArrayList<>();
        Person oldest = null;
        for (Person contender : people) {
            if(oldest == null){ // if this is the first contender, make it the oldest
                oldest = contender;
            } else {
                if (contender.getAge() > oldest.getAge()) {
                    tied = new ArrayList<>();
                    oldest = contender;
                } else if (contender.getAge() == oldest.getAge()){
                    tied.add(contender);
                }
            }
        }

        tied.add(oldest);
        return tied;
    }

    /**
     * Groups everyone in the database by their country.
     * @return everyone in the database, grouped by country
     */
    public ArrayList<CountryGroup> groupByCountry() {
        ArrayList<CountryGroup> responseData = new ArrayList<>();
        List<Person> people = db.getDbContents();
        for(Person p : people){
            boolean added = false;
            for(CountryGroup group : responseData){
                if(group.country.equals(p.getCountry())){
                    group.people.add(p);
                    added = true;
                }
            }
            if(!added){
                CountryGroup newGroup = new CountryGroup(p.getCountry());
                newGroup.people.add(p);
                responseData.add(newGroup);
            }
        }
        return responseData;
    }

    /**
     * Gets all the people from a given country, then groups them in age brackets of 10 years.
     * @param country country to filter by
     * @return the people from given country, grouped by their age bracket
     */
    public ArrayList<AgeGroup> groupByAgeRangeGivenCountry(String country){
        ArrayList<AgeGroup> responseData = new ArrayList<>();
        List<Person> people = db.getDbContents(country);
        for(Person p : people){
            boolean added = false;
            double pFloor = Math.floor(p.getAge()/10.0); // this effectively round down to the nearest low 10.
            for(AgeGroup group : responseData){
                if(group.floor == pFloor){
                    group.people.add(p);
                    added = true;
                }
            }
            if(!added){
                AgeGroup newGroup = new AgeGroup(pFloor);
                newGroup.people.add(p);
                responseData.add(newGroup);
            }
        }
        return responseData;
    }

    private int getNameLength (Person person){
        return person.getFirstName().length() + person.getLastName().length();
    }

    public ArrayList<Person> getLongestNamed () {
        List<Person> people = db.getDbContents();
        ArrayList<Person> tied = new ArrayList<>();
        Person longest = null;
        for(Person contender : people){
            if(longest == null){ //if this is the first contender, make them the longest
                longest = contender;
            } else {
                if(getNameLength(contender) > getNameLength(longest)){
                    longest = contender;
                    tied = new ArrayList<>();
                } else if (getNameLength(contender) == getNameLength(longest)) {
                    tied.add(contender);
                }
            }
        }
        tied.add(longest);
        return tied;
    }

    /**
     * Returns true if the given country is a valid country.
     * Doesn't guarantee that anyone actually has that country though.
     * @param country country as a string
     * @return true if country is valid for the dataset, I.E, in countries.txt
     */
    public boolean countryExists(String country){
        return Resources.getResources().getCountryList().contains(country);
    }

}
