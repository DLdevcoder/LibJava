package models;
public class Author {
    private int authorID;
    private String authorName;
    private String biography;

    public Author(int authorID, String authorName, String biography) {
        this.authorID = authorID;
        this.authorName = authorName;
        this.biography = biography;

    }

    public int getAuthorID() {
        return authorID;

    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
    public String getAuthorName() {
        return authorName;
    }
    public void setBiography(String biography) {
        this.biography = biography;
    }
    public String getBiography() {
        return biography;
    }


}

