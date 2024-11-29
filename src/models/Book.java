//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package models;
import javafx.scene.image.ImageView;

import java.io.Serializable;
import java.util.ArrayList;


public class Book extends Document  {
    protected String publisher;
    protected String isbn;

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
        this.setLanguage(language);
    }

    public Book(int bookId, String title, String publisher, String year, String isbn, int quantity, String description, String thumbnail, String language) {
        this.setId(bookId);
        this.title = title;
        this.publisher = publisher;
        this.setPublicationYear(year);
        this.isbn = isbn;

        this.setPublicationYear(publicationYear);

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

    public Book(int i) {
        this.setId(i);
    }

    public Book(String isbn, String title, String author, String publicationYear) {
        this.setIsbn(isbn);
        this.setTitle(title);
        this.setAuthor(author);
        this.setPublicationYear(publicationYear);
    }

    public Book(String title,String author,String publicationYear,String publisher,String language, ImageView imageView,int quantity){
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.language = language;
        this.publisher = publisher;
        this.ImageLink = imageView;
        this.quantity = quantity;

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






}

