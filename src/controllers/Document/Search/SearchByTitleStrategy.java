package controllers.Document.Search;

import java.util.List;
import java.util.stream.Collectors;

public class SearchByTitleStrategy<T> implements SearchStrategy<T> {
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
