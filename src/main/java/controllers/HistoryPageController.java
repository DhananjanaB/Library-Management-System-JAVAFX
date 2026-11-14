package controllers;

import entity.Borrowing;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import service.BorrowingService;

import java.sql.SQLException;
import java.time.LocalDate;

public class HistoryPageController {

    @FXML
    private TableView<Borrowing> table;

    @FXML
    private TableColumn<Borrowing, String> colMember;

    @FXML
    private TableColumn<Borrowing, String> colBook;

    @FXML
    private TableColumn<Borrowing, LocalDate> colIssued;

    @FXML
    private TableColumn<Borrowing, LocalDate> colDue;

    @FXML
    private TableColumn<Borrowing, String> colReturned;

    @FXML
    private TableColumn<Borrowing, String> colStatus;

    private final BorrowingService borrowingService = new BorrowingService();

    @FXML
    public void initialize() {
        colMember.setCellValueFactory(new PropertyValueFactory<>("memberName"));
        colBook.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        colIssued.setCellValueFactory(new PropertyValueFactory<>("issueDate"));
        colDue.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        colReturned.setCellValueFactory(new PropertyValueFactory<>("returnDateText"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        try {
            table.getItems().setAll(borrowingService.getHistory());
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Couldn't load the borrowing history").show();
            e.printStackTrace();
        }
    }
}