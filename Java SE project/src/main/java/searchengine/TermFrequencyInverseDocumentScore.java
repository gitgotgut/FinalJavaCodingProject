package searchengine;

import java.util.Map;
import java.util.Set;

/**
 * Implements the {@link ScoringMethod} interface to calculate the relevance of a web page
 * to a given query using the Term Frequency-Inverse Document Frequency (TF-IDF) method.
 * This scoring method considers both the frequency of each term in the page (term frequency)
 * and the importance of the term in the entire document collection (inverse document frequency).
 */
public class TermFrequencyInverseDocumentScore implements ScoringMethod {

/**
     * Calculates the TF-IDF score of a given page for a specified query.
     * The score is determined by iterating over each term in the query and computing its term frequency (TF)
     * and inverse document frequency (IDF). The TF is the number of times a term appears in the page,
     * normalized by the total number of terms in the page. The IDF is calculated as the logarithm of
     * the ratio of the total number of documents to the number of documents containing the term.
     * 
     * @param page  The {@code Page} object representing the web page to be scored.
     * @param query The {@code Query} object representing the search query.
     * @return      The calculated TF-IDF score as a double. Higher scores indicate greater relevance
     *              of the page to the given query. If a term from the query does not appear in the page,
     *              or is not present in the document frequencies, its contribution to the score is zero.
     */
    @Override
    public double score(Page page, Query query) {
        Map<String, Integer> termFrequencies = page.getTermFrequencies();
        Map<String, Integer> documentFrequencies = PageLoader.getDocumentFrequencies();
        int totalDocuments = PageLoader.getTotalDocuments();
        int totalTermsInDocument = page.getTotalTerms();

        double score = 0;
        
        for (Set<String> queryPart : query.getQueryParts()) {
            double partScore = 0;
            for (String term : queryPart) {
                int tf = termFrequencies.getOrDefault(term, 0);
                int df = documentFrequencies.getOrDefault(term, 0);
                if (df > 0 && tf > 0) {
                    double normalizedTf = (double) tf / totalTermsInDocument;
                    partScore += (normalizedTf * Math.log((double) totalDocuments / df));

                }
            }
            score = Math.max(score, partScore);
        }
        return score;
    }
}
