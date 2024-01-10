package searchengine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * The Page class represents each page found in the dataset.
 * Each page can have a url, a title and a list of content.
 */ 
public class Page {
    private String url;
    private String title;
    private List<String> content;
    /**
     * Constructs a Page object with the specified URL, title, and content.
     *
     * @param url     the URL of the page
     * @param title   the title of the page
     * @param content the content of the page
     */
    public Page(String url, String title, List<String> content) {
        this.url = url;
        this.title = title;
        this.content = new ArrayList<>(content);
    }
    /**
     * This method creates a map with key of type String and value of type Integer.
     * The key represents the word in the content of the page.
     * The value is the number of times the word appears in the content.
     * @return a map of term frequencies
     */
    public Map<String, Integer> getTermFrequencies() {
        Map<String, Integer> termFrequencies = new HashMap<>();
        for (String word : content) {
                termFrequencies.put(word, termFrequencies.getOrDefault(word, 0) + 1);
        }
        return termFrequencies;
    }
    /**
    * Gets the size of the content of the page.
    * @return the total number of terms in a page
    */
    public int getTotalTerms() {
        return content.size();
    }
    /**
     * Gets the url of the page.
     * @return the URL of the page.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Gets the title of the page.
     * @return the title of the page.
     */
    public String getTitle() {
        return title;
    }
    
    /**
     * Gets the content of the page.
     * @return the content of the page.
     */
    public List<String> getContent() {
        return content;
    }
}
