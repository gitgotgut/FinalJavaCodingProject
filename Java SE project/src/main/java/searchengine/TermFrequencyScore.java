package searchengine;

import java.util.Map;
import java.util.Set;

/**
 * Implements the {@link ScoringMethod} interface to calculate the relevance of a web page
 * to a given query using the Term Frequency (TF) scoring method. This class focuses on 
 * evaluating the frequency of terms from the query within the web page, 
 * treating each term's presence as equally important.
 */
public class TermFrequencyScore implements ScoringMethod {

    /**
     * This calculates the Term Frequency (TF) score of a given page for a specified query.
     * The score is computed by summing up the occurrences of each term in the query
     * that appears in the page. The frequency of each term in the page contributes to the score.
     * The method supports handling of multiple terms in a query and computes a part score for each query part,
     * ultimately taking the maximum score from these parts as the final score.
     * 
     * @param page  The {@code Page} object representing the web page to be scored.
     * @param query The {@code Query} object representing the search query.
     * @return      The calculated term frequency score as a double. Higher scores
     *              indicate that the page contains more occurrences of the terms present in the query.
     *              A score of zero indicates no occurrence of query terms in the page.
     */
    @Override
    public double score(Page page, Query query) {
        double score = 0;
        Map<String, Integer> termFrequencies = page.getTermFrequencies();

        for (Set<String> queryPart : query.getQueryParts()) {
            double partScore = 0;
            for (String term : queryPart) {
                partScore += termFrequencies.getOrDefault(term, 0);
            }
            score = Math.max(score, partScore);
        }

        return score;
    }
}