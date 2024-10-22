package controllers;

import models.Author;

import java.util.ArrayList;
import java.util.List;

public class AuthorController{
    protected List<Author> authors;

    public AuthorController(){
        this.authors = new ArrayList<Author>();
    }

    public void displayAuthors(int authorID){
        for(Author author : authors){
            if(author.getAuthorID() == authorID){
                System.out.println("ID: "+author.getAuthorID());
                System.out.println("Name: "+author.getAuthorName());
                System.out.println("Biography: "+author.getBiography());


            }
        }

    }







}

