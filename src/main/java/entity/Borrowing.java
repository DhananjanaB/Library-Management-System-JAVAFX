package entity;

import java.time.LocalDate;

public class Borrowing {
    private int borrowId;
    private String memberId;
    private String memberName;
    private String bookId;
    private String bookTitle;
    private LocalDate issueDate;
    private LocalDate dueDate;
    private LocalDate returnDate;

    public Borrowing(int borrowId, String memberId, String memberName, String bookId, String bookTitle,
                     LocalDate issueDate, LocalDate dueDate, LocalDate returnDate) {
        this.borrowId = borrowId;
        this.memberId = memberId;
        this.memberName = memberName;
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
    }

    public int getBorrowId() { return borrowId; }
    public String getMemberId() { return memberId; }
    public String getMemberName() { return memberName; }
    public String getBookId() { return bookId; }
    public String getBookTitle() { return bookTitle; }
    public LocalDate getIssueDate() { return issueDate; }
    public LocalDate getDueDate() { return dueDate; }
    public LocalDate getReturnDate() { return returnDate; }

    /** Shown in the History table: the date, or "-" when the book is still out. */
    public String getReturnDateText() {
        return returnDate == null ? "-" : returnDate.toString();
    }

    /** Returned / Overdue / Borrowed. Calculated from the dates, not stored in the database. */
    public String getStatus() {
        if (returnDate != null) {
            return "Returned";
        }
        return dueDate.isBefore(LocalDate.now()) ? "Overdue" : "Borrowed";
    }

    /** Shown in the ComboBox. */
    @Override
    public String toString() {
        return "#" + borrowId + " - " + memberName + " - " + bookTitle;
    }
}