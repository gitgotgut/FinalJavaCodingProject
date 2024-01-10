package searchengine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

/**
 * This test class is designed to verify the functionality of the {@code TermFrequencyScore} class.
 * It tests the term frequency scoring method's ability to correctly calculate the relevance score of a web page
 * based on the frequency of terms from a search query appearing in the page content.
 */
class TermFrequencyScoreTest {

    private TermFrequencyScore scorer;
    private Page mockPage;
    private Query mockQuery;

     /**
     * Sets up the test environment before each test method is executed.
     * This involves initializing the {@code TermFrequencyScore} scorer,
     * and creating mock {@code Page} and {@code Query} objects with predefined content and query terms.
     */
    @BeforeEach
    void setUp() {
        scorer = new TermFrequencyScore();
        mockPage = new Page("http://example.com", "Test Page", List.of("test", "java", "java", "example"));
        mockQuery = new Query("test java");
    }

    /**
     * Tests the score calculation for a scenario where the page contains terms matching the query.
     * The method asserts that the calculated score matches the expected value based on the term frequencies
     * in the page content.
     */
    @Test
    void testScoreWithValidTerms() {
        double expectedScore = 3; 
        double actualScore = scorer.score(mockPage, mockQuery);
        assertEquals(expectedScore, actualScore, "Score should be correctly calculated for valid terms.");
    }

    /**
     * Tests the score calculation when there are no matching terms between the page content and the query.
     * The method changes the query to a term that does not exist in the page content and then
     * asserts that the calculated score is zero, indicating no relevance.
     */
    @Test
    void testScoreWithNoMatchingTerms() {
        mockQuery = new Query("nonexistent");
        double actualScore = scorer.score(mockPage, mockQuery);
        assertEquals(0, actualScore, "Score should be zero when no terms match.");
    }
}