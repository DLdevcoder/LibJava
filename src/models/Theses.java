package models;

public class Theses extends Document{
    private String degree;
    private String institution;

    public Theses(String title, String author) {
        this.title = title;
        this.author = author;
    }

    public Theses(String title, String author, int quantity) {
        this.title = title;
        this.author = author;
        this.quantity = quantity;
    }

    public Theses(String title, String author, int quantity, String degree, String institution, String year) {
        this.title = title;
        this.author = author;
        this.quantity = quantity;
        this.degree = degree;
        this.institution = institution;
        this.publicationYear = year;

    }

    public Theses(int id, String title, String author, String year, String degree, String institution, String language) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publicationYear = year;
        this.degree = degree;
        this.institution = institution;
        this.language = language;

    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public Theses(int id, String title, String author, String language, String publicationYear,
                  String description, int quantity, String degree, String institution) {
        super(id, title, author, language, publicationYear, description, quantity);
        this.degree = degree;
        this.institution = institution;
    }

    public Theses(String title, String author, String language, String publicationYear,
                  String description, int quantity, String degree, String institution) {
        super(title, author, language, publicationYear, description, quantity);
        this.degree = degree;
        this.institution = institution;
    }



}
