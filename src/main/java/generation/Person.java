
package generation;

import io.jsondb.annotation.Document;
import io.jsondb.annotation.Id;

/**
 * Person Class
 * Using jsondb, the Person class itself is used to identify the 'collection' of people (aka the table),
 * while each person occupies individual rows.
 * Each row has an id, first name, last name, age, and country.
 */
@Document(collection = "people", schemaVersion = "1.0")
public class Person{
    @Id
    private String id;

    private String firstName;
    private String lastName;
    private int age;
    private String country;

    //GETS and SETS
    public String getId() { return id; }
    public void setId(String id){ this.id = id; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    @Override
    public String toString() {
        return age + " years old " + firstName + " " + lastName + " from " + country;
    }
}
