//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package models;

public class Book {
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

    public Book() {
    }

    public Book(int bookId, String title, int authorId, String publisher, int year, String isbn, int quantity, int categoryId, String googleId, String description, String thumbnail, String language) {
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

    public Book(String title, String publisher, int year, String isbn, int quantity, String description, String thumbnail, String language) {
        this.title = title;
        this.publisher = publisher;
        this.year = year;
        this.isbn = isbn;
        this.quantity = quantity;
        this.description = description;
        this.thumbnail = thumbnail;
        this.language = language;

    }

    public Book(int bookId, String title, String publisher, int year, String isbn, int quantity, String description, String thumbnail, String language) {
        this.bookId = bookId;
        this.title = title;
        this.publisher = publisher;
        this.year = year;
        this.isbn = isbn;
        this.quantity = quantity;
        this.description = description;
        this.thumbnail = thumbnail;
        this.language = language;

    }

    public int getBookId() {
        return this.bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAuthorId() {
        return this.authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getPublisher() {
        return this.publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getYear() {
        return this.year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getIsbn() {
        return this.isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getGoogleId() {
        return this.googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbnail() {
        return this.thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getLanguage() {
        return this.language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}

