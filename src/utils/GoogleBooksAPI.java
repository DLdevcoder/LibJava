    package utils;

    import java.io.BufferedReader;
    import java.io.InputStreamReader;
    import java.net.HttpURLConnection;
    import java.net.MalformedURLException;
    import java.net.URL;
    import java.util.ArrayList;
    import java.util.List;
    import java.util.Scanner;

    import javafx.application.Platform;
    import javafx.scene.control.Alert;
    import javafx.scene.image.Image;
    import javafx.scene.image.ImageView;
    import models.Book;
    import org.json.JSONArray;
    import org.json.JSONException;
    import org.json.JSONObject;

    public class GoogleBooksAPI {

        private static final String API_KEY = "&key=AIzaSyBhMm6LPXdhV-GHY4zzBmd9ZbCCoxQRbsc";
        private static final String API_URL = "https://www.googleapis.com/books/v1/volumes?q=";
        private static final String API_URLISBN ="https://www.googleapis.com/books/v1/volumes?q=isbn:";

        private GoogleBooksAPI() {}

        /**
         * Fetches detailed book information by its ISBN from the Google Books API.
         *
         * @param isbn The ISBN of the book to fetch information for.
         * @return A JSONObject containing the book details, or null if not found.
         */
        public static JSONObject fetchBookInfoByISBN(String isbn) {
            String urlWithISBN = API_URLISBN + isbn + API_KEY;
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

        /**
         * Fetches a list of books matching the search query from the Google Books API.
         *
         * @param query The search query (e.g., book title, author).
         * @return A JSONArray of books matching the query, or null if no results are found.
         */
        public static JSONArray fetchBooksByQuery(String query) {
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

        /**
         * Retrieves a list of books based on the search query, with pagination support.
         * Fetches books in batches of 40 until all results are retrieved.
         *
         * @param query The search query (e.g., book title, author).
         * @return A List of Book objects that match the search query.
         */
        public static List<Book> getBooksByQuery(String query) {
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

        /**
         * Extracts book details (title, author, etc.) from the given response.
         *
         * @param response The JSONObject containing the API response.
         * @return A JSONObject containing the book's details (e.g., title, author).
         */
        private static JSONObject getBookDetails(JSONObject response) {
            try {
                // Kiểm tra nếu không có trường "items" hoặc mảng "items" rỗng
                if (!response.has("items") || response.getJSONArray("items").isEmpty()) {
                    throw new JSONException("No books found for the given ISBN.");
                }

                // Trả về thông tin sách đầu tiên trong danh sách
                return response.getJSONArray("items").getJSONObject(0).getJSONObject("volumeInfo");
            } catch (JSONException e) {
                // In thông báo lỗi và ném ngoại lệ để xử lý ở nơi gọi phương thức
                System.err.println("Error retrieving book details: " + e.getMessage());
                throw e;
            }
        }


        /**
         * Retrieves book details by ISBN, including title, author, and cover image.
         *
         * @param isbn The ISBN of the book.
         * @param imageLink The URL to the book's cover image.
         * @param quantity The quantity of the book available in inventory.
         * @return A Book object with the book's details, or null if not found.
         */
        public static Book getBookByISBN(String isbn, String imageLink, int quantity) {
            JSONObject volumeInfo = fetchBookInfoByISBN(isbn);
            if(volumeInfo != null) {
                String title = volumeInfo.getString("title");
                String author = volumeInfo.optString("author", "No author detected");
                String publicationYear = volumeInfo.optString("publishedDate", "No Publication Year");
                String publisher = volumeInfo.optString("publisher", "No Publisher");
                String language = volumeInfo.optString("language", "No Language");
                String category = "No Category";



                if (publicationYear.length() > 4) {
                    publicationYear = publicationYear.substring(0, 4);
                }


                Image image = new Image(imageLink);
                ImageView imageView = new ImageView(image);
                return new Book(title, author,publicationYear, publisher, language, imageView,quantity);


            }
            return null;

        }

        /**
         * Extracts the ISBN number from the volume info of a book.
         *
         * @param volumeInfo The JSONObject containing the book's volume information.
         * @return The ISBN number, or "No ISBN detected" if not found.
         */
        private static String getIsbn(JSONObject volumeInfo) {
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