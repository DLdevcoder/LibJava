package controllers.Document.Search;

import java.util.List;

public interface SearchStrategy<T> {
    List<T> search(List<T> items, String keyword);
}
