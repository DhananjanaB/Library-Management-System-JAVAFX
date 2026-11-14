package controllers;

import entity.Book;
import entity.Member;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import service.BookService;
import service.BorrowingService;
import service.MemberService;

import java.sql.SQLException;
import java.time.LocalDate;

public class IssueBookPageController {

    @FXML
    private Button btnclear;

    @FXML
    private Button btnissuebook;

    @FXML
    private ComboBox<Book> cmbBook;

    @FXML
    private ComboBox<Member> cmbMember;

    @FXML
    private DatePicker dpDueDate;

    @FXML
    private DatePicker dpIssueDate;

    private final MemberService memberService = new MemberService();
    private final BookService bookService = new BookService();
    private final BorrowingService borrowingService = new BorrowingService();

    @FXML
    public void initialize() {
        dpIssueDate.setValue(LocalDate.now());
        dpDueDate.setValue(LocalDate.now().plusDays(14));
        loadChoices();
    }

    @FXML
    void btnissuebookOnAction(ActionEvent event) {
        try {
            borrowingService.issueBook(cmbMember.getValue(), cmbBook.getValue(),
                    dpIssueDate.getValue(), dpDueDate.getValue());
            new Alert(Alert.AlertType.INFORMATION, "Book issued").show();
            clearForm();
            loadChoices();
        } catch (IllegalArgumentException e) {
            new Alert(Alert.AlertType.WARNING, e.getMessage()).show();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Couldn't issue the book").show();
            e.printStackTrace();
        }
    }

    @FXML
    void btnclearOnAction(ActionEvent event) {
        clearForm();
    }

    private void clearForm() {
        cmbMember.setValue(null);
        cmbBook.setValue(null);
        dpIssueDate.setValue(LocalDate.now());
        dpDueDate.setValue(LocalDate.now().plusDays(14));
    }

    /** Fills both drop-downs. Only books that still have copies are listed. */
    private void loadChoices() {
        try {
            cmbMember.getItems().setAll(memberService.getAllMembers());
            cmbBook.getItems().setAll(bookService.getAllBooks().stream()
                    .filter(b -> b.getQty() > 0)
                    .toList());
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Couldn't load members and books").show();
            e.printStackTrace();
        }
    }
}