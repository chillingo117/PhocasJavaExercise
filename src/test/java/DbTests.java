import data.Db;
import generation.Person;
import org.junit.jupiter.api.*;

import java.io.*;
import java.util.List;

/**
 * Tests for the DB Class
 */
class DbTests {
    private static final String path = System.getProperty("user.dir") + "/src/main/resources/";
    Db db = Db.getDb();

    /**
     * Returns the number of rows in the database file
     * @return number of rows in the database file. Note: this is the number of people in the database +1 for the schema version row.
     */
    int countDbRows() throws IOException {
        int rows = 0;
        BufferedReader reader = new BufferedReader(new FileReader(path+ "people.json"));
        while (reader.readLine() != null) {
            rows++;
        }
        reader.close();
        return rows;
    }

    @BeforeEach
    @AfterEach
    void writeDummyDb() {
        db.wipeDb();
        Person newPerson = new Person();
        newPerson.setFirstName("Jennifer");
        newPerson.setLastName("Farmer");
        newPerson.setAge(82);
        newPerson.setCountry("China");
        newPerson.setId("0");
        db.addPerson(newPerson);
        newPerson = new Person();
        newPerson.setFirstName("Abigail");
        newPerson.setLastName("Wat");
        newPerson.setAge(41);
        newPerson.setCountry("Germany");
        newPerson.setId("1");
        db.addPerson(newPerson);
        newPerson = new Person();
        newPerson.setFirstName("Irma");
        newPerson.setLastName("Chen");
        newPerson.setAge(43);
        newPerson.setCountry("Germany");
        newPerson.setId("2");
        db.addPerson(newPerson);
        newPerson = new Person();
        newPerson.setFirstName("Irma");
        newPerson.setLastName("Chad");
        newPerson.setAge(90);
        newPerson.setCountry("Australia");
        newPerson.setId("3");
        db.addPerson(newPerson);
        newPerson = new Person();
        newPerson.setFirstName("Will");
        newPerson.setLastName("Duarte");
        newPerson.setAge(10);
        newPerson.setCountry("China");
        newPerson.setId("4");
        db.addPerson(newPerson);
    }

    /**
     * Tests that, when resampling, the wipeDb function actually wipes the db.
     */
    @Test
    void wipeTest() throws IOException {
        db.wipeDb();
        Assertions.assertEquals(1, countDbRows());
    }

    /**
     * Test that resample works, and also correctly changes the number of people in the database.
     * Note that there is actually one extra row which is occupied by the schema version line.
     */
    @Test
    void resampleTest() throws IOException {
        db.resampleDb(10);
        Assertions.assertEquals(11, countDbRows());
        db.resampleDb(30);
        Assertions.assertEquals(31, countDbRows());
    }

    /**
     * Test that getting the db contents while filtering by country works.
     * Tries with blue-sky test of China, then an edge case with 0 people from NZ.
     */
    @Test
    void getDbContentsByCountryTest() {
        List<Person> response = db.getDbContents("China");

        Assertions.assertEquals(2, response.size());
        Assertions.assertTrue(response.stream().anyMatch(p -> (p.getId().equals("0"))));
        Assertions.assertTrue(response.stream().anyMatch(p -> (p.getId().equals("4"))));
        response = db.getDbContents("New Zealand");
        Assertions.assertEquals(response.size(), 0);
    }

    /**
     * Tests that getting the whole of the db contents is working as intended.
     * It checks if the response is of correct size, and contains an instance of correct names.
     */
    @Test
    void getWholeDbTest() {
        List<Person> response = db.getDbContents();
        Assertions.assertEquals(5, response.size());
        String[] lastNames = {"Farmer", "Wat", "Chen", "Chad", "Duarte"};
        for(String lastName : lastNames){
            Assertions.assertTrue(response.stream().anyMatch(p -> (p.getLastName().equals(lastName))));
        }
    }
}
