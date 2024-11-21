//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package models;
import javafx.scene.image.ImageView;


public class Book extends Document {
    protected String publisher;
    protected String isbn;


    protected String googleId;
    protected String thumbnail;

    public Book(String title, String author, String year, String publisher, String language, ImageView ImageLink) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.publicationYear = year;
        this.language = language;
        this.ImageLink = ImageLink;

    }



    public Book(String title, String publisher, String year, String isbn, int quantity, String description, String thumbnail, String language) {
        this.setTitle(title);
        this.publisher = publisher;
        this.setPublicationYear(year);
        this.isbn = isbn;
        this.quantity = quantity;
        this.setDescription(description);
        this.thumbnail = thumbnail;
        this.setLanguage(language);
    }

    public Book(int bookId, String title, String publisher, String year, String isbn, int quantity, String description, String thumbnail, String language) {
        this.setId(bookId);
        this.title = title;
        this.publisher = publisher;
        this.setPublicationYear(year);
        this.isbn = isbn;
        this.setDescription(description);
        this.quantity = quantity;
        this.setPublicationYear(publicationYear);
        this.thumbnail = thumbnail;
        this.setLanguage(language);

    }


    public Book(String title, String author, String publicationYear, String publisher, String language) {
        this.setTitle(title);
        this.setAuthor(author);
        this.publisher = publisher;
        this.setLanguage(language);
        this.publicationYear = publicationYear;
    }

    public Book(int id, String title, String author, String publicationYear, String publisher, String language, ImageView ImageLink) {
        this.setId(id);
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.publisher = publisher;
        this.language = language;
        this.ImageLink = ImageLink;

    }

    public Book(int id, String title) {
       super(id,title);

    }


    public String getPublisher() {
        return this.publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getIsbn() {
        return this.isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }




    public String getGoogleId() {
        return this.googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public String getThumbnail() {
        return this.thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

}

