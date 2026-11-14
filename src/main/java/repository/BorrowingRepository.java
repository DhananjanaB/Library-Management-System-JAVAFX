package repository;

import db.DBConnection;
import entity.Borrowing;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BorrowingRepository {

    private static final String SELECT_JOINED =
            "SELECT b.borrow_id, b.member_id, m.full_name, b.book_id, k.title, " +
                    "b.issue_date, b.due_date, b.return_date " +
                    "FROM borrowing b " +
                    "JOIN member m ON m.member_id = b.member_id " +
                    "JOIN book k ON k.book_id = b.book_id ";

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

    /** Sets the return date and puts the copy back in stock together. Returns false if already returned. */
    public boolean returnBook(int borrowId, LocalDate returnDate) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        con.setAutoCommit(false);
        try {
            PreparedStatement find = con.prepareStatement(
                    "SELECT book_id FROM borrowing WHERE borrow_id = ? AND return_date IS NULL");
            find.setInt(1, borrowId);
            ResultSet rs = find.executeQuery();
            if (!rs.next()) {
                con.rollback();
                return false;
            }
            String bookId = rs.getString(1);

            PreparedStatement update = con.prepareStatement(
                    "UPDATE borrowing SET return_date = ? WHERE borrow_id = ?");
            update.setDate(1, Date.valueOf(returnDate));
            update.setInt(2, borrowId);
            update.executeUpdate();

            PreparedStatement stock = con.prepareStatement(
                    "UPDATE book SET quantity = quantity + 1 WHERE book_id = ?");
            stock.setString(1, bookId);
            stock.executeUpdate();

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

    /** Books that are borrowed right now (not returned yet). */
    public List<Borrowing> findActive() throws SQLException {
        return query(SELECT_JOINED + "WHERE b.return_date IS NULL ORDER BY b.borrow_id");
    }

    /** Every borrowing, newest first. Used by the History page. */
    public List<Borrowing> findAll() throws SQLException {
        return query(SELECT_JOINED + "ORDER BY b.borrow_id DESC");
    }

    private List<Borrowing> query(String sql) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        List<Borrowing> list = new ArrayList<>();
        while (rs.next()) {
            Date returned = rs.getDate(8);
            list.add(new Borrowing(rs.getInt(1), rs.getString(2), rs.getString(3),
                    rs.getString(4), rs.getString(5),
                    rs.getDate(6).toLocalDate(), rs.getDate(7).toLocalDate(),
                    returned == null ? null : returned.toLocalDate()));
        }
        return list;
    }
}