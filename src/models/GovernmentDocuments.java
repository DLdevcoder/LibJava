package models;

public class GovernmentDocuments extends Document{
    private String documentType;

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

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }


}
