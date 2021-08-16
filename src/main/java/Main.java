
import userIO.UserIO;

/**
 * Main Class
 * Fully static
 *
 * Serves to run the app
 */
public class Main {
    private static boolean quit = false;

    /**
     * Processes input for the main menu.
     * @param input validated user input as int
     */
    private static void mainMenuSelect(int input){
        switch (input) {
            case 1:
                UserIO.resample();
                break;
            case 2:
                queryMenuSelect(UserIO.queryMenuInput());
                break;
            case 3:
                quit = true;
                break;
            default:
                System.out.println("mainMenuSelection error: validated input not recognized");
                System.exit(-1);
        }
    }

    /**
     * Processes input for the query menu.
     * @param input validated user input as int
     */
    private static void queryMenuSelect(int input){
        switch (input) {
            case 1:
                UserIO.printOldestPeople();
                break;
            case 2:
                UserIO.printGroupByCountryCount();
                break;
            case 3:
                UserIO.printFilterByCountryGroupByAge();
                break;
            case 4:
                UserIO.printLongestNamedPeople();
                break;
            default:
                System.out.println("queryMenuSelection error: validated input not recognized");
                System.exit(-1);
        }
    }

    /**
     * Main class. Runs the app.
     * @param args None
     */
    public static void main(String[] args) {
        System.out.println("Creating initial sample...");
        UserIO.resample();
        while(!quit){
            mainMenuSelect(UserIO.mainMenuInput());
        }
    }
}
