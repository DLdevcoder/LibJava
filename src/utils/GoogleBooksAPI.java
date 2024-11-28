    package utils;

    import java.io.BufferedReader;
    import java.io.InputStreamReader;
    import java.net.HttpURLConnection;
    import java.net.MalformedURLException;
    import java.net.URL;
    import java.util.ArrayList;
    import java.util.List;
    import java.util.Scanner;

    import javafx.scene.image.Image;
    import javafx.scene.image.ImageView;
    import models.Book;
    import org.json.JSONArray;
    import org.json.JSONObject;

    public class GoogleBooksAPI {

        private static final String API_KEY = "AIzaSyBStXWe0XlatZoNMyqVSY2xgZOWPDH4Ieo"; // Thay YOUR_API_KEY bằng API Key của bạn
        private static final String API_URL = "https://www.googleapis.com/books/v1/volumes?q=";

        public JSONObject fetchBookInfoByISBN(String isbn) {
            String urlWithISBN = API_URL + isbn;
            try {
                //Thiết lập kết nối với http
                URL url = new URL(urlWithISBN);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
               // Kiểm tra phản hồi từ server
                if(connection.getResponseCode() == 200) {
                    //Đọc dữ liệu từ phản hồi
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();
                    //Chuyển chuỗi phản hồi sang đối tượng Json
                    JSONObject json = new JSONObject(response.toString());
                    return getBookDetails(json);
                } else{
                    System.out.println("Error: ");
                }


            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return null;
        }
        public JSONArray fetchBooksByQuery(String query) {
            String urlWithQuery = API_URL + query + "&key=" + API_KEY;
            try {
                //Thiết lập kết nối với http
                URL url = new URL(urlWithQuery);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                if (connection.getResponseCode() == 200) {
                    //Đọc dữ liệu từ sever
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();
                    //Chuyển chuỗi phản hồi sang đối tượng Json
                    JSONObject json = new JSONObject(response.toString());
                    return json.optJSONArray("items");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return null;
        }
        public List<Book> getBooksByQuery(String query) {
            String urlWithQuery = API_URL + query + "&maxResults=40&key="  + API_KEY; // URL với query tìm kiếm
            List<Book> bookList = new ArrayList<>();
            int startIndex = 0;// Danh sách sách sẽ được trả về
            try {
                while (true) {
                    // Xây dựng URL với startIndex
                    String paginatedUrl = urlWithQuery + "&startIndex=" + startIndex;

                    // Gọi API để lấy dữ liệu sách
                    JSONArray booksArray = fetchBooksByQuery(paginatedUrl);

                    if (booksArray == null || booksArray.length() == 0) {
                        break; // Không còn sách để lấy
                    }

                    // Duyệt qua kết quả và thêm vào danh sách
                    for (int i = 0; i < booksArray.length(); i++) {
                        JSONObject volumeInfo = booksArray.getJSONObject(i).getJSONObject("volumeInfo");

                        String isbn = getIsbn(volumeInfo);
                        String title = volumeInfo.getString("title");
                        String author = volumeInfo.optString("authors", "No author detected");
                        String publicationYear = volumeInfo.optString("publishedDate", "No Publication Year");

                        Book book = new Book(isbn, title, author, publicationYear);
                        bookList.add(book);
                    }

                    // Tăng startIndex để lấy trang tiếp theo
                    startIndex += 40;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bookList;  // Trả về danh sách tất cả các cuốn sách
        }

        private JSONObject getBookDetails(JSONObject response) {
            if(!response.getJSONArray("items").isEmpty()) {
                return response.getJSONArray("items").getJSONObject(0).getJSONObject("volumeInfo");
            }
            return null;
        }

        public Book getBookByISBN(String isbn, String imageLink) {
            JSONObject volumeInfo = fetchBookInfoByISBN(isbn);
            if(volumeInfo != null) {
                String title = volumeInfo.getString("title");
                String author = volumeInfo.optString("author", "No author detected");
                String publicationYear = volumeInfo.optString("publishedDate", "No Publication Year");
                String publisher = volumeInfo.optString("publisher", "No Publisher");
                String language = volumeInfo.optString("language", "No Language");


                if (publicationYear.length() > 4) {
                    publicationYear = publicationYear.substring(0, 4);
                }


                Image image = new Image(imageLink);
                ImageView imageView = new ImageView(image);
                return new Book(title, author,publicationYear, publisher, language, imageView);


            }
            return null;

        }

        private String getIsbn(JSONObject volumeInfo) {
            String isbn = "No ISBN detected";
            if (volumeInfo.has("industryIdentifiers")) {
                JSONArray identifiers = volumeInfo.getJSONArray("industryIdentifiers");
                for (int i = 0; i < identifiers.length(); i++) {
                    JSONObject identifier = identifiers.getJSONObject(i);
                    if ("ISBN_13".equals(identifier.getString("type"))) {
                        isbn = identifier.getString("identifier");
                        break;
                    }
                }
            }
            return isbn;
        }


    }