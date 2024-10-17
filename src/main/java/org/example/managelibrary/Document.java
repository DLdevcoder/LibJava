package org.example.managelibrary;

public class Document {
    protected int id;
    protected String title;
    protected int authorId;
    protected String publisher;
    protected int year;
    protected String isbn;
    protected int quantity;
    protected int categoryId;

    public Document() {

    }
    public Document(int id, String title, int authorId, String publisher, int year, String isbn, int quantity,int categoryId ) {
        this.id = id;
        this.title = title;
        this.authorId = authorId;
        this.publisher = publisher;
        this.year = year;
        this.isbn = isbn;
        this.quantity = quantity;
        this.categoryId = categoryId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
