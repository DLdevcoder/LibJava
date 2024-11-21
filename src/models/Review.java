package models;

public class Review  {
    protected int id;
    protected String title;
    protected String ReviewerName;
    protected String ReviewText;
    protected String ReviewDate;


    public Review(int id,String title, String reviewText, String reviewDate, String reviewName) {
        this.id = id;
        this.title = title;
        this.ReviewerName = reviewName;
        this.ReviewText = reviewText;
        this.ReviewDate = reviewDate;


    }

    public String getReviewerName() {
        return ReviewerName;
    }
    public String getReviewText() {
        return ReviewText;
    }

    public void setReviewerName(String reviewerName) {
        ReviewerName = reviewerName;
    }
    public void setReviewText(String reviewText) {
        ReviewText = reviewText;
    }
    public String getReviewDate() {
        return ReviewDate;
    }
    public void setReviewDate(String reviewDate) {
        ReviewDate = reviewDate;
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

}
