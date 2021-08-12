package data;

import generation.Person;
import generation.PersonGenerator;
import io.jsondb.JsonDBTemplate;

import java.util.List;

/**
 * Db class
 * Singleton
 *
 * Responsible for managing the db through the use of the jsondb library.
 * Allows for resampling data and pulling data.
 */
public class Db {
    private final String dbFileLocation = System.getProperty("user.dir") + "/src/main/resources";
    private final String baseScanPackage = "generation";
    private final JsonDBTemplate jsonDBTemplate = new JsonDBTemplate(dbFileLocation, baseScanPackage);

    private static Db db = null;

    private Db() {
        if(!jsonDBTemplate.collectionExists("people")){
            jsonDBTemplate.createCollection(Person.class);
        }
    }

    public static Db getDb() {
        if(db == null){
            db = new Db();
        }
        return db;
    }

    /**
     * Wipes the db
     */
    public void wipeDb(){
        jsonDBTemplate.dropCollection(Person.class);
        PersonGenerator.getPersonGenerator().resetGeneration();
        jsonDBTemplate.createCollection(Person.class);
    }

    /**
     * Wipes the database and refills it with a random sample of size numPeople*
     * @param numPeople new sample size
     */
    public void resampleDb(int numPeople){
        wipeDb();
        for(int i=0; i<numPeople; i++){
            Person newPerson = PersonGenerator.getPersonGenerator().generatePerson();
            jsonDBTemplate.insert(newPerson);
        }
    }

    /**
     * For testing. Adds a person to the db.
     * @param person the person to add
     */
    public void addPerson(Person person){
        jsonDBTemplate.insert(person);
    }

    /**
     * Gets the people from the database that are affiliated with the given country.
     * @param country The selected country
     * @return the people from a given country
     */
    public List<Person> getDbContents(String country){
        String jxQuery = String.format("/.[country='%s']", country);
        return jsonDBTemplate.find(jxQuery, Person.class);
    }

    /**
     * Returns all people from the database.
     * @return all people in the database
     */
    public List<Person> getDbContents(){
        return jsonDBTemplate.findAll(Person.class);
    }

}
