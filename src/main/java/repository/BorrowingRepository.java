package repository;

import db.DBConnection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class BorrowingRepository {

    /** Adds the borrowing row and reduces the stock together. Returns false if no copy is left. */
    public boolean issue(String memberId, String bookId, LocalDate issueDate, LocalDate dueDate)
            throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        con.setAutoCommit(false);
        try {
            PreparedStatement stock = con.prepareStatement(
                    "UPDATE book SET quantity = quantity - 1 WHERE book_id = ? AND quantity > 0");
            stock.setString(1, bookId);
            if (stock.executeUpdate() == 0) {
                con.rollback();
                return false;
            }

            PreparedStatement insert = con.prepareStatement(
                    "INSERT INTO borrowing (member_id, book_id, issue_date, due_date) VALUES (?,?,?,?)");
            insert.setString(1, memberId);
            insert.setString(2, bookId);
            insert.setDate(3, Date.valueOf(issueDate));
            insert.setDate(4, Date.valueOf(dueDate));
            insert.executeUpdate();

            con.commit();
            return true;
        } catch (SQLException e) {
            con.rollback();
            throw e;
        } finally {
            con.setAutoCommit(true);
        }
    }

    public boolean hasActiveBorrowing(String memberId, String bookId) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement ps = con.prepareStatement(
                "SELECT 1 FROM borrowing WHERE member_id = ? AND book_id = ? AND return_date IS NULL");
        ps.setString(1, memberId);
        ps.setString(2, bookId);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }
}