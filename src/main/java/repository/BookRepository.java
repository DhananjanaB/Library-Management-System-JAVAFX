package repository;

import db.DBConnection;
import entity.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookRepository {

    public void save(Book book) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement ps = con.prepareStatement(
                "INSERT INTO book (book_id, title, author, category, published_year, quantity) VALUES (?,?,?,?,?,?)");
        ps.setString(1, book.getId());
        ps.setString(2, book.getTitle());
        ps.setString(3, book.getAuthor());
        ps.setString(4, book.getCategory());
        ps.setInt(5, book.getYear());
        ps.setInt(6, book.getQty());
        ps.executeUpdate();
    }

    public boolean exists(String id) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement ps = con.prepareStatement("SELECT 1 FROM book WHERE book_id = ?");
        ps.setString(1, id);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }
}