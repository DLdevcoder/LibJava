package controllers.Document.Search;

import java.util.List;

/**
 * Generic search controller for searching items based on different criteria.
 */
public class SearchDocumentController<T> {

    private SearchStrategy<T> searchStrategy;

    /**
     * Sets the search strategy to be used for searching items.
     *
     * @param searchStrategy the SearchStrategy to be used
     */
    public void setSearchStrategy(SearchStrategy<T> searchStrategy) {
        this.searchStrategy = searchStrategy;
    }

    /**
     * Executes the search using the current search strategy.
     *
     * @param items the list of items (e.g., books, theses, government documents) to search through
     * @param keyword the keyword to search for
     * @return the list of items that match the search criteria
     * @throws IllegalStateException if the search strategy is not set
     */
    public List<T> executeSearch(List<T> items, String keyword) {
        if (searchStrategy == null) {
            throw new IllegalStateException("Search strategy is not set.");
        }
        return searchStrategy.search(items, keyword);
    }
}


