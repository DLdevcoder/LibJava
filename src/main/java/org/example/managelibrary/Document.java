package org.example.managelibrary;

public class Document {
    protected int bookId;
    protected String title;
    protected int authorId;
    protected String publisher;
    protected int year;
    protected String isbn;
    protected int quantity;
    protected int categoryId;
    protected String googleId;
    protected String description;
    protected String thumbnail;
    protected String language;

    public Document() {

    }
    public Document(int bookId, String title, int authorId, String publisher, int year, String isbn, int quantity, int categoryId, String googleId, String description, String thumbnail, String language) {
        this.bookId = bookId;
        this.title = title;
        this.authorId = authorId;
        this.publisher = publisher;
        this.year = year;
        this.isbn = isbn;
        this.quantity = quantity;
        this.categoryId = categoryId;
        this.googleId = googleId;
        this.description = description;
        this.thumbnail = thumbnail;
        this.language = language;
    }

    public int getBookIdId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

}
