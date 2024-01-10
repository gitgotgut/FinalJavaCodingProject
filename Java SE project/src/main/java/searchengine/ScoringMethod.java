package searchengine;
 /**
  * The ScoringMethod interface defines a method for scoring a Page in relation to a Query. 
  */
public interface ScoringMethod {
    /**
     * A method that is meant to be overwritten by classes that implement ScoringMethod.
     * This is supposed to compare a Page against a Query and return the result in type double.
     * @param page The Page that is to be scored against the Query.
     * @param query The given Query to evaluate a given Page against.
     * @return Returns a double value representation of the score of a given Page compared to a given Query.
     */
    double score(Page page, Query query);
}
