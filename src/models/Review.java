package models;

import java.util.Date;

public class Review  {
    protected Book book;
    protected String ReviewText;
    protected Date ReviewDate;
    protected double rating;
    protected Member member;


    public Review( int book_id,String reviewText, Date reviewDate, double rating, int memberid ) {
        this.book.setId(book_id);
        this.ReviewText = reviewText;
        this.ReviewDate = reviewDate;
        this.rating = rating;
        this.member.setMemberId(memberid);


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
    public int getMemberid() {
        return member.getMemberId();
    }
    public void setMemberid(int memberid) {
        member.setMemberId(memberid);
    }
    public int getBookid() {
        return book.getId();
    }
    public void setBookid(int bookid) {
        book.setId(bookid);
    }



    }




