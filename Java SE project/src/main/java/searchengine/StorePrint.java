package searchengine;
import java.util.List;
import java.util.LinkedList;
import java.io.PrintStream;

/**
 * Extends {@link PrintStream} to capture and store printed lines.
 * StorePrint class is used as a helper class for test cases in FileHandlerTest.
 */
public class StorePrint extends PrintStream{
    
    /**
     * A static list that stores the printed lines.
     */
    public static List<String> printList = new LinkedList<String>();

        /**
         * Constructor of a StorePrint instance.
         * @param org The original PrintStream that the StorePrint instance will extend.
         */
        public StorePrint(PrintStream org) {
            super(org);
        }

        /**
         * Stores the given line in the static printList and prints it.
         * @param line The line of type String to be stored and printed.
         */
        @Override
        public void println(String line) {
            printList.add(line);
            super.println(line);
        }
    
        /**
         * Takes the given integer line and converts it to a String and then stores it in the static printList and prints it.
         * @param line The line of type int to be stored and printed.
         */
         public void println(int line) {
             this.println(String.valueOf(line));
         }
    }
