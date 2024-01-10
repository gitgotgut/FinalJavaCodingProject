package searchengine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

/**
 * This test class is designed to test the functionality of the {@code TermFrequencyInverseDocumentScore} class.
 * It specifically tests the scoring logic implemented for calculating the Term Frequency-Inverse Document Frequency (TF-IDF) score
 * of a given web page in relation to a search query.
 */
class TermFrequencyInverseDocumentScoreTest {

    private TermFrequencyInverseDocumentScore scorer;
    private Page mockPage;
    private Query mockQuery;

    /**
     * Sets up the test environment before each test method is executed.
     * This setup includes initializing the {@code TermFrequencyInverseDocumentScore} scorer, 
     * and creating mock {@code Page} and {@code Query} objects with predefined data for testing.
     */
    @BeforeEach
    void setUp() {
        scorer = new TermFrequencyInverseDocumentScore();
        mockPage = new Page("http://example.com", "Test Page", List.of("test", "java", "java", "example"));
        mockQuery = new Query("test java");
    }

    /**
     * Tests the score calculation when there are no matching terms between the page content and the query.
     * This method changes the query to have a term that does not exist in the page content and then
     * asserts that the calculated score is zero, indicating no relevance.
     */
    @Test
    void testScoreWithNoMatchingTerms() {
        mockQuery = new Query("nonexistent");
        double actualScore = scorer.score(mockPage, mockQuery);
        assertEquals(0, actualScore, "Score should be zero when no terms match.");
    }

}