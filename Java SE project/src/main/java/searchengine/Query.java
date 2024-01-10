package searchengine;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
/**
 * A class handling search queries from the given search term and storing them in a List of Set of String called queryParts.
 */
public class Query {
    private List<Set<String>> queryParts;

    /**
     * Constructs a new Query object with the provided search term.
     * The search term is split into query parts by the regular expression "\\s+OR\\s+"
     * Each part is then split into linked words by the regular expression "\\s+".
     *
     * @param searchTerm the search term is used to initialize the Query object.
     * 
     */
    public Query(String searchTerm) {
        if (!searchTerm.isEmpty()) {
            queryParts = new ArrayList<>();
            String[] parts = searchTerm.split("\\s+OR\\s+");
            for (String part : parts) {
                if(!part.equals("OR")){
                Set<String> words = new HashSet<>(Arrays.asList(part.split("\\s+")));
                queryParts.add(words);
                }
            }
        }
    }

    /**
     * Retrieves a list of sets of linked words representing the query parts.
     * The query parts are split in the constructor by the regular expression "\\s+OR\\s+".
     *
     * @return a list of sets of linked words representing the query parts.
     */
    public List<Set<String>> getQueryParts() {
        return queryParts;
    }
}
