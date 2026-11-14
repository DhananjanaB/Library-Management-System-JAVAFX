package service;

import entity.Member;
import repository.MemberRepository;

import java.sql.SQLException;
import java.util.List;

public class MemberService {

    private final MemberRepository memberRepository = new MemberRepository();

    public void registerMember(String id, String name, String email, String phone, String address)
            throws SQLException {
        Member member = buildMember(id, name, email, phone, address);
        if (memberRepository.exists(member.getId())) {
            throw new IllegalArgumentException("Member ID " + member.getId() + " already exists");
        }
        memberRepository.save(member);
    }

    public void updateMember(String id, String name, String email, String phone, String address)
            throws SQLException {
        memberRepository.update(buildMember(id, name, email, phone, address));
    }

    public void deleteMember(String id) throws SQLException {
        memberRepository.delete(id);
    }

    public List<Member> getAllMembers() throws SQLException {
        return memberRepository.findAll();
    }

    private Member buildMember(String id, String name, String email, String phone, String address) {
        if (id.trim().isEmpty() || name.trim().isEmpty() || email.trim().isEmpty()
                || phone.trim().isEmpty() || address.trim().isEmpty()) {
            throw new IllegalArgumentException("Fill in all the fields");
        }
        if (!email.trim().matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new IllegalArgumentException("Enter a valid email address, for example name@email.com");
        }
        if (!phone.trim().matches("^0\\d{9}$")) {
            throw new IllegalArgumentException("Phone number must be 10 digits starting with 0");
        }
        return new Member(id.trim(), name.trim(), email.trim(), phone.trim(), address.trim());
    }
}