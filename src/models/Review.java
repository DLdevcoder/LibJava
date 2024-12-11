package models;

import java.util.Date;

public class Review  {
    protected Book book;
    protected String ReviewText;
    protected Date ReviewDate;
    protected double rating;
    protected Member member;


    public Review( Book book,String reviewText, Date reviewDate, double rating, Member member ) {
        this.book = book;
        this.ReviewText = reviewText;
        this.ReviewDate = reviewDate;
        this.rating = rating;
        this.member = member;


    }
    public Review(Book book,String reviewText, Date reviewDate,Member member) {
        this.book = book;
        this.ReviewText = reviewText;
        this.ReviewDate = reviewDate;
        this.member = member;
    }

    public Review(Book book, String comment, java.sql.Date reviewDate, Member member, String rating) {
        this.book = book;
        this.ReviewText = comment;
        this.ReviewDate = reviewDate;
        this.member = member;
        this.rating = Double.parseDouble(rating);

    }


    public String getReviewText() {
        return ReviewText;
    }


    public void setReviewText(String reviewText) {
        ReviewText = reviewText;
    }
    public java.sql.Date getReviewDate() {
        return (java.sql.Date) ReviewDate;
    }
    public void setReviewDate(Date reviewDate) {
        ReviewDate = reviewDate;
    }
    public double getRating() {
        return rating;
    }
    public void setRating(double rating) {
        this.rating = rating;
    }

    public Member getMember() {
        return member;
    }
    public void setMember(Member member) {
        this.member = member;
    }
    public Book getBook() {
        return book;
    }
    public void setBook(Book book) {
        this.book = book;
    }
    public int getBookId() {
        return book != null ? book.getId() : null; // Trả về id từ đối tượng Book
    }
    public String getBookTitle() {
        return book != null ? book.getTitle() : null;
    }
    public String getReviewerName() {
        return member != null ? member.getName() : null;
    }


    }




