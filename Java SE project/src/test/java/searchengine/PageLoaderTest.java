package searchengine;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;


class PageLoaderTest {
    private PageLoader pageLoader;

    @BeforeEach
    void setUp() {
        pageLoader = new PageLoader("");
    }

    @AfterEach
    void tearDown() {
        PageLoader.resetState();
    }

    /**
     * Test case for the processLines method when given an empty list of lines.
     * This test ensures that when an empty list of lines is passed to the processContent method, 
     * the inverted index and document frequencies are not updated, and the total number of documents remains 0.
     */
    @Test
    void processLines_emptyListOfLines_emptyInvIndexAndDocFreqs() {
        List<String> emptyLines = Arrays.asList();
        pageLoader.processContent(emptyLines);
        assertTrue(PageLoader.getInvertedIndex().isEmpty());
        assertTrue(PageLoader.getDocumentFrequencies().isEmpty());
        assertEquals(0, PageLoader.getTotalDocuments());
        }
    
    /**
     * A test case to verify that the processLines method correctly
     * updates the inverted index for a single valid page.
     * Also document frequencies, and total document count is updated accordingly.
     */
    @Test
    void processLines_oneValidPage_InvIndexAndDocFreqsAndTotalDocsAreUpdated() {
        PageLoader.resetState();
        List<String> validLines = Arrays.asList("*PAGE:http://page1.com", "Title 1", "word1", "word2");
        pageLoader.processContent(validLines);
        assertFalse(PageLoader.getInvertedIndex().isEmpty());
        assertEquals(1, PageLoader.getInvertedIndex().get("word1").size());
        assertEquals(1, PageLoader.getDocumentFrequencies().get("word1"));
        assertEquals(1, PageLoader.getInvertedIndex().get("word2").size());
        assertEquals(1, PageLoader.getDocumentFrequencies().get("word2"));
        assertEquals(1, PageLoader.getTotalDocuments());
    }
    /**
     * A test case to verify that the processContent method processes a list of valid lines,
     *  containing multiple pages, 
     *  and verifies that the Inv Index and Doc Frequencies are updated.
     */
    @Test
    void processLines_multipleValidPages_InvIndexAndDocFreqsAndTotalDocsAreUpdated() {
        List<String> validLines2 = Arrays.asList("*PAGE:http://page1.com", "Title 1", "word1", "word2",
        "*PAGE:http://page2.com", "Title2", "word3", "word1",
        "*PAGE:http://page3.com", "Title3", "word4", "word2");
        pageLoader.processContent(validLines2);
        assertFalse(PageLoader.getInvertedIndex().isEmpty());
        assertEquals(2, PageLoader.getInvertedIndex().get("word1").size());
        assertEquals(2, PageLoader.getDocumentFrequencies().get("word1"));
        assertEquals(2, PageLoader.getInvertedIndex().get("word2").size());
        assertEquals(1, PageLoader.getDocumentFrequencies().get("word3"));
        assertEquals(3, PageLoader.getTotalDocuments());
    }

    /**
     * A test case to verify that the processContent method processes a list of both valid and invalid lines,
     * and verifies that the Inv Index and Doc Frequencies are only updated for the valid lines.
     */
    @Test
    void processLines_oneValidTwoInvalidPages_onlyValidPageIsProcessed() {
        List<String> validLines3 = Arrays.asList("*PAGE:http://page1.com", "Title 1", "word1", "word2",
        "*PAGE:http://page2.com", "Title2",
        "*PAGE:http://page3.com", "word2");
        pageLoader.processContent(validLines3);
        assertFalse(PageLoader.getInvertedIndex().isEmpty());
        assertEquals(1, PageLoader.getInvertedIndex().get("word1").size());
        assertEquals(1, PageLoader.getDocumentFrequencies().get("word1"));
        assertEquals(1, PageLoader.getInvertedIndex().get("word2").size());
        assertEquals(1, PageLoader.getDocumentFrequencies().get("word2"));
        assertEquals(1, PageLoader.getTotalDocuments());
    }
    
    /**
     * Test case to verify that an invalid page, it is not processed.
     * The function takes a list of invalid lines that has no valid words
     * and calls the processContent method of the pageLoader object.
     * It then asserts that the Inverted Index and Document Frequencies
     * are both empty, and the total number of documents remains zero.
     */
    @Test
    void processLines_oneInvalidPage_isNotProcessed() {
        List<String> invalidLines = Arrays.asList("*PAGE:http://page1.com", "Title1", " ", "   ");
        pageLoader.processContent(invalidLines);
        assertTrue(PageLoader.getInvertedIndex().isEmpty());
        assertTrue( PageLoader.getDocumentFrequencies().isEmpty());
        assertEquals(0, PageLoader.getTotalDocuments());
    }

}
