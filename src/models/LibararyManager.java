package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LibararyManager {
    private static LibararyManager instance;

    private List<Book> books;
    private Map<Integer, Member> members;

    private LibararyManager() {
        books = new ArrayList<>();
        members = new HashMap<>();
    }

    public static LibararyManager getInstance() {
        if (instance == null) {
            instance = new LibararyManager();
        }
        return instance;

    }

    public void addBook(Book book) {
        books.add(book);
    }
    public List<Book> getBooks() {
        return books;
    }
    public Map<Integer, Member> getMembers() {
        return members;
    }

    public Book findBook(int id) {
        for (Book book : books) {
            if (book.getId() == id) {
                return book;
            }

        }
        return null;
    }

    public void addMember(Member member) {
        members.put(member.getMemberId(), member);
    }
    public Member getMemberById(int memberId) {
        return members.get(memberId);
    }



}
