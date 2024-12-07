package controllers.Document;

import java.util.List;
import java.util.stream.Collectors;

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

class SearchByAuthorStrategy<T> implements SearchStrategy<T> {
    @Override
    public List<T> search(List<T> items, String keyword) {
        return items.stream()
                .filter(item -> {
                    try {
                        // Sử dụng phản chiếu để gọi phương thức getAuthor() trên các đối tượng
                        String author = (String) item.getClass().getMethod("getAuthor").invoke(item);
                        return author != null && author.toLowerCase().contains(keyword.toLowerCase());
                    } catch (Exception e) {
                        return false; // Nếu không có phương thức getAuthor, bỏ qua
                    }
                })
                .collect(Collectors.toList());
    }
}

class SearchByTitleStrategy<T> implements SearchStrategy<T> {
    @Override
    public List<T> search(List<T> items, String keyword) {
        return items.stream()
                .filter(item -> {
                    try {
                        // Sử dụng phản chiếu để gọi phương thức getTitle() trên các đối tượng
                        String title = (String) item.getClass().getMethod("getTitle").invoke(item);
                        return title != null && title.toLowerCase().contains(keyword.toLowerCase());
                    } catch (Exception e) {
                        return false; // Nếu không có phương thức getTitle, bỏ qua
                    }
                })
                .collect(Collectors.toList());
    }
}


