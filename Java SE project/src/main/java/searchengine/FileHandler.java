package searchengine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * FileHandler.java provides utility methods for handling files.
 */
public class FileHandler {
    /**
     * Reads the contents of a file and returns them as a byte array.
     *
     * @param  filename   the path from which the name of the file is to be read
     * @return            A byte array containing the contents of the file at the given path
     */
    public static byte[] getFile(String filename) {
        try {
            return Files.readAllBytes(Paths.get(filename));
        } catch (IOException e) {
            e.printStackTrace();
            return new byte[0];
        }
    }

    /**
     * Reads the contents of a file and returns it as a string.
     *
     * @param  filename  the path from which the name of the file is to be read
     * @return           the contents of the file as a string
     */
    public static String readString(String filename) {
        try {
            return Files.readString(Paths.get(filename));
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}