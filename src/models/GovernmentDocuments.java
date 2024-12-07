package models;

public class GovernmentDocuments extends Document{
    private String documentType;
    private String descriptions;

    public GovernmentDocuments(int id, String title, String author, String language, String publicationYear, String description, int quantity, String documentType) {
        super(id, title, author, language, publicationYear, description, quantity);
        this.documentType = documentType;
    }

    public GovernmentDocuments(String title, String author, String language, String publicationYear, String description, int quantity, String documentType) {
        super(title, author, language, publicationYear, description,quantity);
        this.documentType = documentType;
    }

    public GovernmentDocuments(String title, String author) {
        this.title = title;
        this.author = author;
    }

    public GovernmentDocuments(String title, String author, int quantity) {
        this.title = title;
        this.author = author;
        this.quantity = quantity;
    }

    public GovernmentDocuments(String title, String author, int quantity, String type, String year, String description) {
        this.title = title;
        this.author = author;
        this.quantity = quantity;
        this.documentType = type;
        this.publicationYear = year;
        this.descriptions = description;


    }

    public GovernmentDocuments(int id, String title, String author, String year, String documentType, String descriptions) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publicationYear = year;
        this.documentType = documentType;
        this.descriptions = descriptions;

    }

    public GovernmentDocuments(int id, String title, String author, String year, String type, String descriptions, String language) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.language = language;
        this.publicationYear = year;
        this.descriptions = descriptions;
        this.documentType = type;

    }

    public  String getDescriptions(){
        return descriptions;
    }


    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }


}
