package controllers;

import entity.Borrowing;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import service.BorrowingService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class ReturnBookPageController {

    @FXML
    private Button btnreturnbook;

    @FXML
    private ComboBox<Borrowing> cmbBorrowing;

    @FXML
    private DatePicker dpReturnDate;

    @FXML
    private Label lblBook;

    @FXML
    private Label lblBorrowDate;

    @FXML
    private Label lblDueDate;

    @FXML
    private Label lblMember;

    @FXML
    private Label lblStatus;

    private final BorrowingService borrowingService = new BorrowingService();

    @FXML
    public void initialize() {
        dpReturnDate.setValue(LocalDate.now());
        cmbBorrowing.valueProperty().addListener((obs, oldValue, b) -> showDetails(b));
        dpReturnDate.valueProperty().addListener((obs, oldValue, d) -> showDetails(cmbBorrowing.getValue()));
        loadBorrowings();
    }

    @FXML
    void btnreturnbookOnAction(ActionEvent event) {
        try {
            borrowingService.returnBook(cmbBorrowing.getValue(), dpReturnDate.getValue());
            new Alert(Alert.AlertType.INFORMATION, "Book returned").show();
            cmbBorrowing.setValue(null);
            dpReturnDate.setValue(LocalDate.now());
            loadBorrowings();
        } catch (IllegalArgumentException e) {
            new Alert(Alert.AlertType.WARNING, e.getMessage()).show();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Couldn't return the book").show();
            e.printStackTrace();
        }
    }

    private void showDetails(Borrowing b) {
        if (b == null) {
            lblMember.setText("-");
            lblBook.setText("-");
            lblBorrowDate.setText("-");
            lblDueDate.setText("-");
            lblStatus.setText("-");
            lblStatus.setStyle("");
            return;
        }
        lblMember.setText(b.getMemberName() + " (" + b.getMemberId() + ")");
        lblBook.setText(b.getBookTitle());
        lblBorrowDate.setText(b.getIssueDate().toString());
        lblDueDate.setText(b.getDueDate().toString());

        LocalDate returnDate = dpReturnDate.getValue() == null ? LocalDate.now() : dpReturnDate.getValue();
        long lateDays = ChronoUnit.DAYS.between(b.getDueDate(), returnDate);
        if (lateDays > 0) {
            lblStatus.setText("Overdue by " + lateDays + (lateDays == 1 ? " day" : " days"));
            lblStatus.setStyle("-fx-text-fill: #c62828; -fx-font-weight: bold;");
        } else {
            lblStatus.setText("On time");
            lblStatus.setStyle("-fx-text-fill: #2e7d32; -fx-font-weight: bold;");
        }
    }

    private void loadBorrowings() {
        try {
            cmbBorrowing.getItems().setAll(borrowingService.getActiveBorrowings());
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Couldn't load the borrowed books").show();
            e.printStackTrace();
        }
    }
}