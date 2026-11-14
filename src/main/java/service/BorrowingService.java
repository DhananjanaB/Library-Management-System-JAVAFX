package service;

import entity.Book;
import entity.Member;
import repository.BorrowingRepository;

import java.sql.SQLException;
import java.time.LocalDate;

public class BorrowingService {

    private final BorrowingRepository borrowingRepository = new BorrowingRepository();

    public void issueBook(Member member, Book book, LocalDate issueDate, LocalDate dueDate)
            throws SQLException {
        if (member == null) {
            throw new IllegalArgumentException("Select a member");
        }
        if (book == null) {
            throw new IllegalArgumentException("Select a book");
        }
        if (issueDate == null || dueDate == null) {
            throw new IllegalArgumentException("Pick the issue date and the due date");
        }
        if (dueDate.isBefore(issueDate)) {
            throw new IllegalArgumentException("The due date can't be before the issue date");
        }
        if (borrowingRepository.hasActiveBorrowing(member.getId(), book.getId())) {
            throw new IllegalArgumentException(member.getFullName() + " already has this book");
        }
        if (!borrowingRepository.issue(member.getId(), book.getId(), issueDate, dueDate)) {
            throw new IllegalArgumentException("No copies of this book are left");
        }
    }
}