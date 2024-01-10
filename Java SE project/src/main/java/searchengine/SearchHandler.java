package searchengine;

import com.sun.net.httpserver.HttpExchange;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * This class handles the the applications search functionality.
 * It provides methods for searching and displaying search results.
 */
public class SearchHandler {
    static final Charset CHARSET = StandardCharsets.UTF_8;
    private ScoringMethod scoringMethod;

    /**
     * Creates a new SearchHandler object.
     *
     * @param  scoringMethod  the scoring method to be used in the SearchHandler object
     */

    public SearchHandler(ScoringMethod scoringMethod) {
        this.scoringMethod = scoringMethod;
    }

    /**
     * Performs a search and return the results as JSON.
     *
     * @param  io              the HttpExchange object representing the request and response of the HTTP
     * @param  pageLoader      the PageLoader object used to load web pages
     * @param  scoringMethod   the ScoringMethod object used to calculate the score of search results
     * @throws UnsupportedEncodingException incase the URL decoding fails
     */
    public static void searchResults(HttpExchange io, PageLoader pageLoader, ScoringMethod scoringMethod)
            throws UnsupportedEncodingException {
        SearchHandler searchHandler = new SearchHandler(scoringMethod);

        String query = io.getRequestURI().getRawQuery();
        String decodeQuery = URLDecoder.decode(query, StandardCharsets.UTF_8.name());
        String searchTerm = decodeQuery.split("=")[1];
        List<Page> searchResults = searchHandler.search(searchTerm);

        var response = new ArrayList<String>();
        if (searchResults.isEmpty()) {
            response.add("{\"message\": \"No web page contains the query word.\"}");
        } else {
            for (var page : searchResults) {
                response.add(String.format("{\"url\": \"%s\", \"title\": \"%s\"}",
                        page.getUrl(), page.getTitle()));
            }
        }

        var bytes = response.toString().getBytes(CHARSET);
        WebServer.respond(io, 200, "application/json", bytes);
    }

    /**
     * Generates a list of pages matching the given search term.
     *
     * @param  searchTerm  the term searched for
     * @return             a list of pages matching the search term
     */
    public List<Page> search(String searchTerm) {
        Query query = new Query(searchTerm);
        Set<Page> matchingPages = findMatchingPages(query);
        return sortPagesByScore(matchingPages, query);
    }

    /**
     * Finds the set of pages that match a given query.
     *
     * @param  query  the query object containing the query parts
     * @return        the set of pages matching the query
     */

    private Set<Page> findMatchingPages(Query query) {
        Set<Page> matchingPages = new HashSet<>();
        for (Set<String> part : query.getQueryParts()) {
            Set<Page> pagesForPart = new HashSet<>();
            for (String word : part) {
            List<Page> pagesForWord = PageLoader.getInvertedIndex().getOrDefault(word, new ArrayList<>());
            if (pagesForPart.isEmpty()) {
                pagesForPart.addAll(pagesForWord);
            } else {
                pagesForPart.retainAll(pagesForWord);
            }
        }
            matchingPages.addAll(pagesForPart);
        }
        return matchingPages;
    }

   /**
     * Sorts the given pages by score based on the provided query.
     *
     * @param  pages  the set of pages that willbe sorted
     * @param  query  the query used to calculate the page scores
     * @return        the sorted list of pages
     */
    private List<Page> sortPagesByScore(Set<Page> pages, Query query) {
        Map<Page, Double> pageScores = calculatePageScores(pages, query);
        List<Page> sortedPages = new ArrayList<>(pages);
        sortedPages.sort((p1, p2) -> Double.compare(pageScores.get(p2), pageScores.get(p1)));
        return sortedPages;
    }

    /**
     * Calculates the scores for each page based on the given set of pages and query.
     *
     * @param  pages  the set of pages to calculate scores for
     * @param  query  the query used to calculate scores
     * @return        a map of pages and their corresponding scores
     */
    private Map<Page, Double> calculatePageScores(Set<Page> pages, Query query) {
        Map<Page, Double> pageScores = new HashMap<>();
        for (Page page : pages) {
            double score = scoringMethod.score(page, query);
            pageScores.put(page, pageScores.getOrDefault(page, 0.0) + score);
        }
        return pageScores;
    }

    /**
     * Checks wether a given page contains all the specified parts.
     *
     * @param  page   the page to check
     * @param  parts  the set of parts to check in the page
     * @return        true if the page contains all the parts and returns false otherwise
     */
    public static boolean pageContainsAllParts(Page page, Set<String> parts) {
        return parts.stream()
                .allMatch(part -> PageLoader.getInvertedIndex().getOrDefault(part, new ArrayList<>()).contains(page));
    }

    /**
     * Prints the search results with scores for a given search term.
     *
     * @param  searchTerm  the search term to be used for searching
     */
    public void printSearchResultsWithScores(String searchTerm) {
        Query query = new Query(searchTerm);
        Set<Page> matchingPages = findMatchingPages(query);
        List<Page> sortedPages = sortPagesByScore(matchingPages, query);

        Map<Page, Double> pageScores = calculatePageScores(matchingPages, query);

        System.out.println("Search Results for: \"" + searchTerm + "\"");
        for (Page page : sortedPages) {
            double score = pageScores.get(page);
            System.out.println("URL: " + page.getUrl() + ", Title: " + page.getTitle() + ", Score: " + score);
        }
    }

}