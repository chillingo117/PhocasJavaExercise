import data.Db;
import generation.Person;

public class TestUtils {
    private static final Db db = Db.getDb();

    public static void writeDummyDb() {
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
}
