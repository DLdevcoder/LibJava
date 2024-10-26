package utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

public class GoogleBooksAPI {

    private static final String API_KEY = "AIzaSyBStXWe0XlatZoNMyqVSY2xgZOWPDH4Ieo"; // Thay YOUR_API_KEY bằng API Key của bạn
    private static final String API_URL = "https://www.googleapis.com/books/v1/volumes?q=";

    public static String searchBooks(String query) {
        try {
            String urlString = "https://www.googleapis.com/books/v1/volumes?q=" +
                java.net.URLEncoder.encode(query, "UTF-8") +
                "&key=" + API_KEY;
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }

            // Đóng kết nối
            in.close();
            conn.disconnect();

            // Phân tích kết quả JSON
            return parseBookResults(content.toString());

        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    // Hàm xử lý JSON trả về từ API
    // test
    private static String parseBookResults(String jsonResponse) {
        StringBuilder result = new StringBuilder();
        JSONObject jsonObject = new JSONObject(jsonResponse);

        if (jsonObject.has("items")) {
            JSONArray items = jsonObject.getJSONArray("items");

            for (int i = 0; i < items.length(); i++) {
                JSONObject volumeInfo = items.getJSONObject(i).getJSONObject("volumeInfo");
                String title = volumeInfo.getString("title");
                String authors = volumeInfo.has("authors") ? volumeInfo.getJSONArray("authors").join(", ") : "Unknown author";

                result.append("Title: ").append(title).append("\n");
                result.append("Author(s): ").append(authors).append("\n");
                result.append("Description: ").append(volumeInfo.has("description") ? volumeInfo.getString("description") : "No description available").append("\n");
                result.append("Publisher: ").append(volumeInfo.has("publisher") ? volumeInfo.getString("publisher") : "Unknown publisher").append("\n");
                result.append("Published Date: ").append(volumeInfo.has("publishedDate") ? volumeInfo.getString("publishedDate") : "Unknown date").append("\n");
                result.append("Page Count: ").append(volumeInfo.has("pageCount") ? volumeInfo.getInt("pageCount") : "Unknown").append("\n");
                result.append("Categories: ").append(volumeInfo.has("categories") ? volumeInfo.getJSONArray("categories").join(", ") : "Unknown").append("\n\n");
                // can them nhieu thong tin khac
            }
        } else {
            result.append("No books found.");
        }

        return result.toString();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a query: ");
        String query = sc.nextLine();
        String result = searchBooks(query);
        System.out.println(result);
    }
}
