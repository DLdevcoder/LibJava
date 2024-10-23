package models;

public abstract class Document {
    protected int id;
    protected String title;
    protected String author;
    protected String language;
    protected int publicationYear;
    protected int quantity;
    protected String description;


    protected Document(int id, String title, String author, String language, int publicationYear, String description, int quantity) {
        this.quantity = quantity;
        this.id = id;
        this.title = title;
        this.author = author;
        this.language = language;
        this.publicationYear = publicationYear;
        this.description = description;
    }

    protected Document(String title, String author, String language, int publicationYear, String description, int quantity) {
        this.quantity = quantity;
        this.title = title;
        this.author = author;
        this.language = language;
        this.publicationYear = publicationYear;
        this.description = description;
    }

    protected Document() {
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

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public abstract String getInfo();

}
