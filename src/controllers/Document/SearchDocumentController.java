package controllers.Document;


import models.Book;

import java.util.List;
import java.util.stream.Collectors;

public class SearchDocumentController {

    private SearchStrategy searchStrategy;

    /**
     * Sets the search strategy to be used for searching books.
     *
     * @param searchStrategy the SearchStrategy to be used
     */
    public void setSearchStrategy(SearchStrategy searchStrategy) {
        this.searchStrategy = searchStrategy;
    }

    /**
     * Executes the search using the current search strategy.
     *
     * @param books the list of books to search through
     * @param keyword the keyword to search for
     * @return the list of books that match the search criteria
     * @throws IllegalStateException if the search strategy is not set
     */
    public List<Book> executeSearch(List<Book> books, String keyword) {
        if (searchStrategy == null) {
            throw new IllegalStateException("Search strategy is not set.");
        }
        return searchStrategy.search(books, keyword);
    }
}

/**
 * SearchStrategy implementation that searches books by title.
 * Filters the books based on whether the title contains the given keyword.
 */
class SearchByTitleStrategy implements SearchStrategy {
    @Override
    public List<Book> search(List<Book> books, String keyword) {
        return books.stream()
                .filter(book -> book.getTitle().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }
}

