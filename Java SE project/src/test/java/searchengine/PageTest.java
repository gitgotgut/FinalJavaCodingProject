package searchengine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class PageTest {

    /**
     * Test case to verify that all fields of the Page object are set correctly and the getters return the expected values.
     */
    @Test
    void Page_allFieldsSetCorrectly_GettersReturnCorrectly() {
        String testUrl = "http://title1.com";
        String testTitle = "Title1";
        List<String> testContent = Arrays.asList("word1", "word2", "word3");
        Page page = new Page(testUrl, testTitle, testContent);
        assertEquals(testUrl, page.getUrl());
        assertEquals(testTitle, page.getTitle());

    }
    /**
     * A test to verify that when the page title is not set,
     * the getTitle method returns null.
     */
    @Test
    void Page_titleNotSet_getTitleReturnsNull() {
        String testUrl = "http://title1.com";
        List<String> testContent = Arrays.asList("word1", "word2", "word3");
        Page page = new Page(testUrl, null, testContent);
        assertEquals(testUrl, page.getUrl());
        assertNull(page.getTitle());
        assertEquals(testContent, page.getContent());
    }

    /**
     * Test case to verify that when the page URL is not set,
     * the getUrl() method returns null.
     */
    @Test
    void Page_urlNotSet_getUrlReturnsNull() {
        String testTitle = "Title1";
        List<String> testContent = Arrays.asList("word1", "word2", "word3");
        Page page = new Page(null, testTitle, testContent);
        assertNull(page.getUrl());
        assertEquals(testTitle, page.getTitle());
        assertEquals(testContent, page.getContent());
    }

    /**
     * Test case to verify that when the page content is not set, the getContent method returns an empty list.
     */
    @Test
    void Page_contentNotSet_getContentReturnsEmptyList() {
        String testUrl = "http://title1.com";
        String testTitle = "Title1";
        List<String> content = Arrays.asList();
        Page page = new Page(testUrl, testTitle, content);
        assertEquals(testUrl, page.getUrl());
        assertEquals(testTitle, page.getTitle());
        assertTrue(page.getContent().isEmpty());
    }

    /**
     * Tests the "getTermFrequencies" method when two valid pages are provided.
     * The method should assign the correct term frequencies to the pages, and index repeated words correctly.
     */
    @Test
    void getTermFrequencies_twoValidPages_assignsTermFrequenciesCorrectly() {
        String testUrl = "http://title1.com";
        String testTitle = "Title1";
        List<String> content = Arrays.asList("word1", "word2", "word3", "word3", "word3");
        Page page = new Page(testUrl, testTitle, content);
        String testUrl2 = "http://title2.com";
        String testTitle2 = "Title2";
        List<String> content2 = Arrays.asList("word3", "word4", "word4");
        Page page2 = new Page(testUrl2, testTitle2, content2);
        assertEquals(3, page.getTermFrequencies().size());
        assertEquals(2, page2.getTermFrequencies().size());

        assertEquals(3, page.getTermFrequencies().get("word3"));
        assertEquals(1, page2.getTermFrequencies().get("word3"));
    }

}
