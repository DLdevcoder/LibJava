package utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import models.Book;
import org.json.JSONArray;
import org.json.JSONObject;

public class GoogleBooksAPI {

    private static final String API_KEY = "AIzaSyBStXWe0XlatZoNMyqVSY2xgZOWPDH4Ieo"; // Thay YOUR_API_KEY bằng API Key của bạn
    private static final String API_URL = "https://www.googleapis.com/books/v1/volumes?q=";

}