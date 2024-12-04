package models;

import utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Member extends Person {
    private int memberId; // id tự tăng nên k cần setter
    private String membershipDate; // tu dong lay ngay hien tai nen k can setter



    public Member() {
    }

    public Member( int memberId,String name, String address, String phone, String email, String membershipDate, String password) {
        super(name, address, phone, email, password);
        this.memberId = memberId;
        this.membershipDate = membershipDate;

    }

    public Member(String name, String email, String phone, String address, String password) {
        super(name, email, phone, address, password);
    }

    public Member(int id, String name) {
        this.memberId = id;
        this.name = name;
    }

    public Member(int i) {
        this.memberId = i;
    }

    public Member(String name) {
        this.name = name;

    }


    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public int getMemberId() {
        return memberId;
    }
    public String getMembershipDate() {
        return this.membershipDate;
    }

    /**
     * lưu bình luận của member vào database
     * @param review member cần lấy thông tin
     */
    public void saveReviewToDatabase(Review review) {
        String sql = "INSERT INTO reviews(book_id, review_date, comment, rating, member_id) VALUES(?,?,?,?,?)";

        try(Connection connection = DatabaseConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, review.getBook().getId()); // book_id
            preparedStatement.setDate(2, review.getReviewDate()); // review_date (java.sql.Date)
            preparedStatement.setString(3, review.getReviewText()); // comment
            preparedStatement.setDouble(4, review.getRating()); // rating
            preparedStatement.setInt(5, review.getMember().getMemberId()); // member_id


            preparedStatement.executeUpdate();

        } catch(SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * lấy danh sách bình luận của member
     * @return danh sách bình luận
     */
    public List<Review> getReviews() {
        String sql = """
                SELECT d.*,m.*, r.rating, r.comment, r.review_date
                FROM reviews r
                JOIN members m ON m.member_id = r.member_id
                JOIN documents d ON d.id = r.document_id
                WHERE m.member_id = ?""";
        List<Review> reviews = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, this.memberId);
            ResultSet rs  = preparedStatement.executeQuery();
            while (rs.next()) {
                Book document = new Book(rs.getInt("id"), rs.getString("title"));
                Member member = new Member(rs.getInt("member_id"), rs.getString("name"));
                Review review = new Review(document, rs.getString("comment"), rs.getDate("review_date"), rs.getDouble("rating"), member);
                reviews.add(review);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviews;

    }
}
