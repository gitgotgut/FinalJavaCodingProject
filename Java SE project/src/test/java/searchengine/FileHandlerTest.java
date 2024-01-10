package searchengine;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class FileHandlerTest {


/**
 * Test case to verify that the readString method correctly reads a string from a file. 
 * It takes a valid file path as input and returns the content of the file as a string.
 * It verrifies that the readString method returns the expected result.
 */
@Test
void readString_ValidFilePath_ReadContentSuccesfully(){

    String result = FileHandler.readString("data/Testfiles/file-reader-test.txt");
    assertTrue(result.equals("House"));

}
/**
 * Test case to verify that the getFile method correctly reads a byte array from a file.
 * It takes a valid file path as input and returns the content of the file as a byte array.
 * It asserts that the first five bytes of the file's content match the expected values.
 */
@Test
void getFile_ValidFilePath_RetriveByteContentCorrectly(){
    byte[] data = FileHandler.getFile("data/Testfiles/file-reader-test.txt");

    assertTrue(data[0]==72);
    assertTrue(data[1]==111);
    assertTrue(data[2]==117);
    assertTrue(data[3]==115);
    assertTrue(data[4]==101);
}
/**
 * Test case to test the behavior of the getFile method in the FileHandler class when it is called with a non-existing file path.
 * It verifies that the getFile method returns an empty byte array when the file is not found.
 * If the file is not found, the getFile method should return an empty byte array.
 */

@Test
void getFile_NonExistingFilePath_ReturnEmptyByteArray(){

    byte[] data = FileHandler.getFile("non-existing-file.txt");
    assertTrue(data.length==0);

}
/**
 * Test case to test if the readString method of the FileHandler class in the FileHandler class returns an empty string when given a non-existing file path.
 * When the file is not found the readString method returns an empty String.
 */
@Test
void readString_NonExistingFilePath_ReturnEmptyString(){

    String result = FileHandler.readString("non-existing-file.txt");
    assertTrue(result.equals(""));


}
}    
