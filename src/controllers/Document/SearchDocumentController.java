package controllers.Document;


import models.Book;

import java.util.List;
import java.util.stream.Collectors;

public class SearchDocumentController {

    private SearchStrategy searchStrategy;

    public void setSearchStrategy(SearchStrategy searchStrategy) {
        this.searchStrategy = searchStrategy;
    }

    public List<Book> executeSearch(List<Book> books, String keyword) {
        if (searchStrategy == null) {
            throw new IllegalStateException("Search strategy is not set.");
        }
        return searchStrategy.search(books, keyword);
    }
}


class SearchByTitleStrategy implements SearchStrategy {
    @Override
    public List<Book> search(List<Book> books, String keyword) {
        return books.stream()
                .filter(book -> book.getTitle().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }
}

