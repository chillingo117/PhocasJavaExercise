
package generation;
import data.Resources;

import java.util.ArrayList;
import java.util.Random;

/**
 * Person Generator class
 * Singleton
 *
 * Generates instances of Person with random attributes.
 */
public class PersonGenerator {
    private static final int maxAge = 99;
    private static PersonGenerator personGenerator = null;
    private static final Resources resources = Resources.getResources();
    private final Random random = new Random();

    private static int currentId = 0;

    private PersonGenerator() {}

    public static PersonGenerator getPersonGenerator() {
        if(personGenerator == null){
            personGenerator = new PersonGenerator();
        }
        return personGenerator;
    }

    /**
     * Returns a random string from an Arraylist of strings.
     * @param array array of strings
     * @return a random string from given array
     */
    private String getRandom(ArrayList<String> array) {
        int index = random.nextInt(array.size());
        return array.get(index);
    }

    /**
     * Generates a new random person. The incrementation of currentId ensure no two rows have the same id.
     * @return new random person
     */
    public Person generatePerson(){
        Person newPerson = new Person();
        newPerson.setId(Integer.toString(currentId));
        newPerson.setFirstName(getRandom(resources.getFirstNameList()));
        newPerson.setLastName(getRandom(resources.getLastNameList()));
        newPerson.setAge(random.nextInt(maxAge+1));
        newPerson.setCountry(getRandom(resources.getCountryList()));
        currentId++;
        return newPerson;
    }

    /**
     * Upon database resample, this function resets the id count to start again from 0.
     */
    public void resetGeneration(){
        currentId = 0;
    }

    /**
     * For testing purposes.
     * @return currentId
     */
    public int getCurrentId(){ return currentId; }
}
