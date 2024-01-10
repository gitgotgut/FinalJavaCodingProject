package searchengine;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Set;
import java.util.HashSet;


class SearchHandlerTest {

    ScoringMethod scoringMethod;
    SearchHandler searchHandler;
    PageLoader pageLoader;

    @BeforeEach
    void setUp() {
       scoringMethod =new TermFrequencyScore();
       searchHandler = new SearchHandler(scoringMethod);
       pageLoader = new PageLoader("data/Testfiles/test-searchHandler.txt");
    }

    @AfterEach
    void tearDown() {
        PageLoader.resetState();
    }
        
    /**
     * Test case to verify that the search method correctly searches for a query in a list of pages.
     * It checks if the urls of the pages in the result list are in the expected order.
     */
    @Test
    void search_ValidQuerry_ReturnsResultInExpectedOrder(){
        List<Page> validResult = searchHandler.search("example");
        assertTrue(validResult.get(0).getUrl().equals("http://page2.com"));
        assertTrue(validResult.get(1).getUrl().equals("http://page1.com"));


    }
   
    /**
     * Test case to verify that the pageContainsAllParts method correctly checks if a page contains all the parts of a query.
     * It takes a valid page and query as input. 
     * - If the page contains all the parts of the query, it returns true.
     * - If the page does not contain all the parts of the query, it returns false.
     * - If the page or the query is null, it returns false.
     */
    
    @Test

    void pageContainsAllParts_MatchingPartsForValidPage_AllPartsFoundAndNotAllPartsFound(){
        List<Page> result = searchHandler.search("example");
        Set<String>set1 = new HashSet<String>();
        set1.add("this");
        set1.add("sample");
        assertTrue(SearchHandler.pageContainsAllParts(result.get(1), set1));
        assertTrue(!SearchHandler.pageContainsAllParts(result.get(0), set1));
        assertFalse(SearchHandler.pageContainsAllParts(null, set1));


    }

    /**
     * Test case to verify that the printSearchResultsWithScores method prints the search results in the expected format.
     * It takes a valid query as input and prints the search results with scores.
     */

    @Test 
    void printSearchResultsWithScores_searchResultsForValidQuery_CorrectPrintList(){

        StorePrint print = new StorePrint(System.out);

        System.setOut(print);

        searchHandler.printSearchResultsWithScores("example");

        assertTrue(StorePrint.printList.get(0).equals("Search Results for: \"example\""));
        System.out.println(StorePrint.printList.get(1));
        assertEquals(StorePrint.printList.get(1),("URL: http://page2.com" + ", Title: "  + "title2" + ", Score: " + "3.0"));
        assertEquals(StorePrint.printList.get(2),("URL: http://page1.com" + ", Title: "  + "title1" + ", Score: " + "1.0"));

    }

}