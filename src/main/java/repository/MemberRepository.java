package repository;

import db.DBConnection;
import entity.Member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MemberRepository {

    public void save(Member member) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement ps = con.prepareStatement(
                "INSERT INTO member (member_id, full_name, email, phone, address) VALUES (?,?,?,?,?)");
        ps.setString(1, member.getId());
        ps.setString(2, member.getFullName());
        ps.setString(3, member.getEmail());
        ps.setString(4, member.getPhone());
        ps.setString(5, member.getAddress());
        ps.executeUpdate();
    }

    public boolean exists(String id) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement ps = con.prepareStatement("SELECT 1 FROM member WHERE member_id = ?");
        ps.setString(1, id);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }

    public List<Member> findAll() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement ps = con.prepareStatement(
                "SELECT member_id, full_name, email, phone, address FROM member ORDER BY member_id");
        ResultSet rs = ps.executeQuery();
        List<Member> members = new ArrayList<>();
        while (rs.next()) {
            members.add(new Member(rs.getString(1), rs.getString(2), rs.getString(3),
                    rs.getString(4), rs.getString(5)));
        }
        return members;
    }

    public void update(Member member) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement ps = con.prepareStatement(
                "UPDATE member SET full_name = ?, email = ?, phone = ?, address = ? WHERE member_id = ?");
        ps.setString(1, member.getFullName());
        ps.setString(2, member.getEmail());
        ps.setString(3, member.getPhone());
        ps.setString(4, member.getAddress());
        ps.setString(5, member.getId());
        ps.executeUpdate();
    }

    public void delete(String id) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement ps = con.prepareStatement("DELETE FROM member WHERE member_id = ?");
        ps.setString(1, id);
        ps.executeUpdate();
    }
}