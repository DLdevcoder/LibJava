package controllers.Document;

import java.util.List;

public interface SearchStrategy<T> {
    List<T> search(List<T> items, String keyword);
}
