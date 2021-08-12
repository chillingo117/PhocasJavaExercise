
import generation.PersonGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests for the PersonGenerator class
 */
class GeneratePersonTests {
    PersonGenerator gen = PersonGenerator.getPersonGenerator();

    /**
     * Checks that the unique id is incrementing as people are generated.
     * And that it still does so after a reset.
     * Also tests if a reset does reset the current id to 0.
     */
    @Test
    void generationTest(){
        for(int i=0; i<10; i++){
            gen.generatePerson();
            Assertions.assertEquals(gen.getCurrentId(), i+1);
        }
        gen.resetGeneration();
        Assertions.assertEquals(gen.getCurrentId(), 0);
        for(int i=0; i<10; i++){
            gen.generatePerson();
            Assertions.assertEquals(gen.getCurrentId(), i+1);
        }
    }
}
