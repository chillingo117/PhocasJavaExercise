package Query;

import generation.Person;

import java.util.ArrayList;

/**
 * Age group class
 *
 * A custom variable for grouping Person instances to a measure of which age range they are in.
 * "floor" stands for their age after it has been rounded down to the nearest low 10.
 */
public class AgeGroup {
    public double floor;
    public ArrayList<Person> people = new ArrayList<>();

    public AgeGroup(double floor){
        this.floor = floor;
    }
}
