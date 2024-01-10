package searchengine;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class QueryTest {
    /**
     * Test case to verify that a single word search term is correctly indexed.
     */
    @Test
    void Query_singleWordsSearchTerm_isAccepted() {
        Query query = new Query("word1");
        List<Set<String>> expected = Arrays.asList(new HashSet<>(Arrays.asList("word1")));
        assertEquals(expected, query.getQueryParts());
    }
    /**
     * A test case to verify that the Query constructor
     * correctly handles multiple words in the search term.
     */
    @Test
    void Query_multipleWordsSearchTerm_areAccepted() {
        Query query = new Query("word1 word2");
        List<Set<String>> expected = Arrays.asList(new HashSet<>(Arrays.asList("word1", "word2")));
        assertEquals(expected, query.getQueryParts());
    }
    /**
     * A test case to verify that the Query constructor
     * correctly handles 1 word for each part (split by " OR ").
     */
    @Test
    void Query_testORKeywordHandlingWithOneWordPerPart_isAccepted() {
        Query query = new Query("word1 OR word2");
        List<Set<String>> expected = Arrays.asList(
            new HashSet<>(Arrays.asList("word1")),
            new HashSet<>(Arrays.asList("word2"))
        );
        assertEquals(expected, query.getQueryParts());
    }
    /**
     * A test case to verify that the Query constructor
     * correctly handles multiple words for each part (split by " OR ").
     */
        @Test
    void Query_testORKeywordHandlingWithmultipleWordsPerPart_isAccepted() {
        Query query = new Query("word1 word3 OR word2 word4");
        List<Set<String>> expected = Arrays.asList(
            new HashSet<>(Arrays.asList("word1", "word3")),
            new HashSet<>(Arrays.asList("word2", "word4"))
        );
        assertEquals(expected, query.getQueryParts());
    }
    /**
     * A test to check if an empty query string is not added to the queryParts.
     *
     * @param  emptyQuery	an empty searchTerm
     */
    @Test
    void getQueryParts_emptyQuery_queryPartsReturnsNull() {
        String emptyQuery = "";
        Query query = new Query(emptyQuery);
        assertNull(query.getQueryParts());
    }

    /**
     * A test to check if a query string only containing "OR", without spaces
     *  is not added to the queryParts.
     */
    @Test
    void Query_queryWithOnlyOR_returnsEmptyList() {
        Query query = new Query("OR");
        assertTrue(query.getQueryParts().isEmpty());
    }

    /**
     * Tests if duplicate words in a query are merged.
     */
    @Test
    void Query_duplicateWords_areMerged() {
        Query query = new Query("word word");
        List<Set<String>> expected = Arrays.asList(new HashSet<>(Arrays.asList("word")));
        assertEquals(expected, query.getQueryParts());
    }

    /**
     * A test case to verify that the query accepts both lower case and upper case words.
     */
    @Test
    void Query_lowerCaseAndUpperCase_isAccepted() {
        Query query = new Query("Word word");
        List<Set<String>> expected = Arrays.asList(new HashSet<>(Arrays.asList("Word", "word")));
        assertEquals(expected, query.getQueryParts());
    }
}
