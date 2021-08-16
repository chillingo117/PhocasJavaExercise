import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import userIO.UserIO;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

class UserIOTests {
    private final InputStream sysInBackup = System.in; // backup System.in to restore it later
    private final PrintStream sysOutBackup = System.out;

    @AfterEach
    void resetSystemIn(){
        System.setIn(sysInBackup);
        System.setOut(sysOutBackup);
    }

    /**
     * Tests for main menu input receiving
     */
    @Test
    void mainMenuInputTest(){
        String[] toTest = {"1","2","3","4","a","-1","0","What"};
        StringBuilder fullInput = new StringBuilder();
        for(String test : toTest){
            fullInput.append(test);
            fullInput.append(System.lineSeparator());
        }

        System.out.println(fullInput);
        ByteArrayInputStream inStream = new ByteArrayInputStream(fullInput.toString().getBytes());
        System.setIn(inStream);
        Assertions.assertEquals(1, UserIO.mainMenuInput());
        Assertions.assertEquals(2, UserIO.mainMenuInput());
        Assertions.assertEquals(3, UserIO.mainMenuInput());
        Assertions.assertEquals(-1, UserIO.mainMenuInput()); //"4"
        Assertions.assertEquals(-1, UserIO.mainMenuInput()); //"a"
        Assertions.assertEquals(-1, UserIO.mainMenuInput()); //"-1"
        Assertions.assertEquals(-1, UserIO.mainMenuInput()); //"0"
        Assertions.assertEquals(-1, UserIO.mainMenuInput()); //"What"
    }

//    /**
//     * Tests for query input receiving
//     */
//    @Test
//    void queryInputTest(){
//        String[] toTest = {"1","2","3","4","a","-1","0","What"};
//        StringBuilder fullInput = new StringBuilder();
//        for(String test : toTest){
//            fullInput.append(test);
//            fullInput.append(System.lineSeparator());
//        }
//
//        System.out.println(fullInput);
//        ByteArrayInputStream inStream = new ByteArrayInputStream(fullInput.toString().getBytes());
//        System.setIn(inStream);
//        Assertions.assertEquals(1, UserIO.queryMenuInput());
//        Assertions.assertEquals(2, UserIO.queryMenuInput());
//        Assertions.assertEquals(3, UserIO.queryMenuInput());
//        Assertions.assertEquals(4, UserIO.queryMenuInput());
//        Assertions.assertEquals(-1, UserIO.queryMenuInput()); //"5"
//        Assertions.assertEquals(-1, UserIO.queryMenuInput()); //"a"
//        Assertions.assertEquals(-1, UserIO.queryMenuInput()); //"-1"
//        Assertions.assertEquals(-1, UserIO.queryMenuInput()); //"0"
//        Assertions.assertEquals(-1, UserIO.queryMenuInput()); //"What"
//    }

    /**
     * Tests to see if printing the oldest person prints the
     */
    @Test
    void oldestPrintTest(){
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        UserIO.printOldestPeople();
        Assertions.assertEquals("The oldest person in the database is:" + System.lineSeparator()+"90 years old Irma Chad from Australia", out.toString().trim());
    }
}
