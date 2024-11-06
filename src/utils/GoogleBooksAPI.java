    package utils;

    import java.io.BufferedReader;
    import java.io.InputStreamReader;
    import java.net.HttpURLConnection;
    import java.net.MalformedURLException;
    import java.net.URL;
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
                URL url = new URL(urlWithISBN);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                if(connection.getResponseCode() == 200) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();
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

    }