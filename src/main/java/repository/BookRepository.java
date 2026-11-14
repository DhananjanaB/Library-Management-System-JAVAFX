package repository;

import db.DBConnection;
import entity.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public List<Book> findAll() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement ps = con.prepareStatement(
                "SELECT book_id, title, author, category, published_year, quantity FROM book ORDER BY book_id");
        ResultSet rs = ps.executeQuery();
        List<Book> books = new ArrayList<>();
        while (rs.next()) {
            books.add(new Book(rs.getString(1), rs.getString(2), rs.getString(3),
                    rs.getString(4), rs.getInt(5), rs.getInt(6)));
        }
        return books;
    }

    public void update(Book book) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement ps = con.prepareStatement(
                "UPDATE book SET title = ?, author = ?, category = ?, published_year = ?, quantity = ? WHERE book_id = ?");
        ps.setString(1, book.getTitle());
        ps.setString(2, book.getAuthor());
        ps.setString(3, book.getCategory());
        ps.setInt(4, book.getYear());
        ps.setInt(5, book.getQty());
        ps.setString(6, book.getId());
        ps.executeUpdate();
    }

    public void delete(String id) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement ps = con.prepareStatement("DELETE FROM book WHERE book_id = ?");
        ps.setString(1, id);
        ps.executeUpdate();
    }
}