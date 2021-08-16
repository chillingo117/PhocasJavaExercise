import query.*;
import data.Db;
import generation.Person;
import org.junit.jupiter.api.*;

import java.util.ArrayList;

/**
 * Tests for the Query class
 */
class QueryTests {
    Query query = Query.getQuery();
    Db db = Db.getDb();

    @BeforeEach
    @AfterEach
    void writeDummyDb() {
        TestUtils.writeDummyDb();
    }


    /**
     * Tests that the query to find the oldest people works.
     * Tests if it works with one distinct oldest person, and if there are multiple old people.
     */
    @Test
    void oldestTest() {
        ArrayList<Person> oldests = query.getOldest();
        Assertions.assertEquals(oldests.size(), 1);
        Assertions.assertEquals(oldests.get(0).getAge(), 90);

        Person tiedOldPerson = new Person();
        tiedOldPerson.setAge(90);
        db.addPerson(tiedOldPerson);

        oldests = query.getOldest();
        Assertions.assertEquals(oldests.size(), 2);
        Assertions.assertEquals(oldests.get(0).getAge(), 90);
        Assertions.assertEquals(oldests.get(1).getAge(), 90);
    }

    /**
     * Tests the group and count by country query.
     */
    @Test
    void groupByCountryTest() {
        ArrayList<CountryGroup> groups = query.groupByCountry();
        Assertions.assertEquals(3, groups.size());
        Assertions.assertTrue(groups.stream().anyMatch(g -> (g.country.equals("Germany") && g.people.size() == 2)));
        Assertions.assertTrue(groups.stream().anyMatch(g -> (g.country.equals("China") && g.people.size() == 2)));
        Assertions.assertTrue(groups.stream().anyMatch(g -> (g.country.equals("Australia") && g.people.size() == 1)));
    }

    /**
     * Tests that the group by age range, given a country, works.
     * Tests edge cases like 19 and 20, as well as 99 and 0.
     * Tests that the groups are in the correct order (youngest first)
     */
    @Test
    void groupByAgeRangeTest() {
        int[] ages = {1,10,11,12,15,19,20,22,25,30,50,46,79,99,63,82};
        for(int age : ages){
            Person newPerson = new Person();
            newPerson.setAge(age);
            newPerson.setFirstName(Integer.toString(age));
            newPerson.setCountry("New Zealand");
            newPerson.setId(Integer.toString(age+100));
            db.addPerson(newPerson);
        }
        ArrayList<AgeGroup> groups = query.groupByAgeRangeGivenCountry("New Zealand");
        Assertions.assertEquals(10, groups.size());
        int[] correctSizes = {1,5,3,1,1,1,1,1,1,1};
        for(int i=0; i<10; i++){
            Assertions.assertEquals(groups.get(i).floor, i);
            Assertions.assertEquals(groups.get(i).people.size(), correctSizes[i]);
        }
    }

    /**
     * Tests that the query to find the longest named people works.
     * Tests if it works with one distinct long named person, and if there are multiple long named people.
     */
    @Test
    void longestNameTest() {
        ArrayList<Person> longests = query.getLongestNamed();
        Assertions.assertEquals(longests.size(), 1);
        Assertions.assertEquals(longests.get(0).getFirstName(), "Jennifer");
        Assertions.assertEquals(longests.get(0).getLastName(), "Farmer");


        Person tiedName = new Person();
        tiedName.setFirstName("Jennifer");
        tiedName.setLastName("Farmer");
        db.addPerson(tiedName);

        longests = query.getLongestNamed();
        Assertions.assertEquals(longests.size(), 2);
        Assertions.assertEquals(longests.get(0).getFirstName(), "Jennifer");
        Assertions.assertEquals(longests.get(1).getFirstName(), "Jennifer");
        Assertions.assertEquals(longests.get(0).getLastName(), "Farmer");
        Assertions.assertEquals(longests.get(1).getLastName(), "Farmer");
    }
}
