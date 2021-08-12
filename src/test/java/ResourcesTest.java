import data.Resources;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Tests for the Resources class
 */
class ResourcesTest {
    Resources res = Resources.getResources();

    @Test
    void firstNamesTest(){
        ArrayList<String> firstNames = new ArrayList<>(Arrays.asList("Will", "Jeff", "Claire", "Irma", "Lacey", "Abigail", "Alphonse", "Ivory", "Len", "Jennifer", "Anne"));
        Assertions.assertEquals(firstNames, res.getFirstNameList());
    }

    @Test
    void lastNamesTest() {
        ArrayList<String> lastNames = new ArrayList<>(Arrays.asList("Lang", "Duarte", "Sullivan", "Gill", "Farmer", "Henderson", "Chen", "Davila", "Reed", "Perkins"));
        Assertions.assertEquals(lastNames, res.getLastNameList());
    }

    @Test
    void countriesTest() {
        ArrayList<String> countries = new ArrayList<>(Arrays.asList("Australia", "New Zealand", "China", "United States of America", "Germany"));
        Assertions.assertEquals(countries, res.getCountryList());
    }
}
