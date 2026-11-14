package repository;

import db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DashboardRepository {

    public long countBooks() throws SQLException {
        return count("SELECT COUNT(*) FROM book");
    }

    public long countMembers() throws SQLException {
        return count("SELECT COUNT(*) FROM member");
    }

    /** Books that are out right now. */
    public long countBorrowed() throws SQLException {
        return count("SELECT COUNT(*) FROM borrowing WHERE return_date IS NULL");
    }

    /** Books that are out and past their due date. */
    public long countOverdue() throws SQLException {
        return count("SELECT COUNT(*) FROM borrowing WHERE return_date IS NULL AND due_date < CURDATE()");
    }

    private long count(String sql) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        return rs.next() ? rs.getLong(1) : 0;
    }
}