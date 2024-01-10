package searchengine;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

import java.util.*;


/**
 * The PageLoader class loads and processes pages from a given file.
 * It is responsible for:
 * - Categorizing and assigning the lines of the data file to each page as url, title and content.
 * - Checking whether a page is valid.
 * - Keeping track of the total number of pages.
 * - Handling the logic of the inverted index and the document frequencies.
 * 
 */
public class PageLoader {

    private static Map<String, List<Page>> invertedIndex = new HashMap<>();
    private static Map<String, Integer> documentFrequencies = new HashMap<>();
    private static int totalDocuments = 0;

    /**
     * Constructs a PageLoader object with a given filename. Then it tries to read the file
     * and if succesfull it assigns all lines of the file to the the list of lines.
     * then it calls the processLines method on the lines.
     * @param filename the name of the file to be read.
     */
    public PageLoader(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            List<String> lines = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            processLines(lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Processes the  list of lines read from the file that is given to the constructor.
     *
     * @param lines the list containing the lines to be processed from the file.
     */
    private void processLines(List<String> lines) {
        int lastIndex = lines.size();
        for (int i = lastIndex - 1; i >= 0; i--) {
            if (lines.get(i).startsWith("*PAGE")) {
                lastIndex = processPage(lines, i, lastIndex);
            }
        }
    }

    /**
     * Processes a page by first calling the isPageValid method.
     * If a given Page is valid it assigns its url, title and List<String> content and from this creates a Page object.
     * Additionally it increments the total number of documents and calls the indexPageContent method.
     *
     * @param  lines      the list of lines containing all properties of the page
     * @param  startIndex the start index of the page in the listof lines in the dataset
     * @param  endIndex   the end index of the page in the list of lines in the dataset
     * @return            the start index of the next page in the list of lines from the dataset
     */
    private int processPage(List<String> lines, int startIndex, int endIndex) {
        if (isPageValid(lines, startIndex, endIndex)) {
            String url = lines.get(startIndex).substring(6);
            String title = lines.get(startIndex + 1);
            List<String> content = new ArrayList<>(lines.subList(startIndex + 2, endIndex));
            Page page = new Page(url, title, content);
            indexPageContent(page, content);
            totalDocuments++;
        }
        return startIndex;
    }

    /**
     * This method indexes the words in the content of a page if the page is valid.
     *
     * @param  page   the page of which the content will be indexed.
     * @param  content  the List of String of words in the content of a page.
     */
    private void indexPageContent(Page page, List<String> content) {
        Set<String> uniqueWords = new HashSet<>();
        if(isPageValid(page)){
        for (String word : content) {
            uniqueWords.add(word);
            invertedIndex.computeIfAbsent(word, k -> new ArrayList<>()).add(page);
        }
        updateDocumentFrequencies(uniqueWords);
        }
    }

    /**
     * Updates the number of times each unique word appears.
     *
     * @param  uniqueWords  a set of the unique words in the page.
     */
    private void updateDocumentFrequencies(Set<String> uniqueWords) {
        for (String word : uniqueWords) {
            documentFrequencies.put(word, documentFrequencies.getOrDefault(word, 0) + 1);
        }
    }

    /**
     * This boolean method checks whether a page is valid before constructing it as a Page.
     * It is valid if and only if it has a title and at least one word in its content.
     *
     * @param  lines      the list of lines to examine
     * @param  startIndex the index of the first line to examine (inclusive)
     * @param  endIndex   the index of the last line to examine (exclusive)
     * @return            true if the page is valid, false otherwise.
     */
    private boolean isPageValid(List<String> lines, int startIndex, int endIndex) {
        boolean hasTitle = false;
        boolean hasWords = false;

        for (int i = startIndex + 1; i < endIndex; i++) {
            String line = lines.get(i).trim();
            if (!line.isEmpty()) {
                if (!hasTitle) {
                    hasTitle = true; 
                } else {
                    hasWords = true;
                    break;
                }
            }
        }
        return hasTitle && hasWords;
    }
    /**
     * This boolean method checks whether an existing page is valid.
     * It is valid if and only if it has a title and at least one word.
     *
     * @param  page       the page to examine.
     * @return            true if page has title and content is not empty, false otherwise.
     */
    private boolean isPageValid(Page page) {
        return page.getTitle() != null && !page.getContent().isEmpty();
    }

    /**
     * Returns the inverted index as a Map of String to List of Page.
     *
     * @return  the inverted index as a map where the keys are strings of words
     * and the values are lists of pages that contain the given word.
     */
    public static Map<String, List<Page>> getInvertedIndex() {
        return invertedIndex;
    }

    /**
     * Retrieves the document frequencies map.
     *
     * @return  the map containing the document frequencies
     */
    public static Map<String, Integer> getDocumentFrequencies() {
        return documentFrequencies;
    }

    /**
     * Retrieves the total number of documents loaded in the Inverted Index.
     *
     * @return  the total number of documents loaded of type int.
     */
    public static int getTotalDocuments() {
        return totalDocuments;
    }

    /**
     * Process the content of the given list of lines, created for testing purposes.
     *
     * @param  lines  the list of lines to be processed
     */
    public void processContent(List<String> lines){
            processLines(lines);   
    }
    
    /**
     * Method is for testing purposes.
     * It resets the PageLoader Object between each test case.
     */
    public static void resetState() {
        invertedIndex.clear();
        documentFrequencies.clear();
        totalDocuments = 0;
    }

}
