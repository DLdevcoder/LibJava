package controllers.Document.Search;

import java.util.List;
import java.util.stream.Collectors;

public class SearchByAuthorStrategy<T> implements SearchStrategy<T> {
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
