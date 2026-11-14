package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import service.BookService;

import java.sql.SQLException;

public class AddBookPageController {

    @FXML
    private Button btnaddbook;

    @FXML
    private Button btnclear;

    @FXML
    private TextField txtAuthor;

    @FXML
    private TextField txtBookId;

    @FXML
    private TextField txtCategory;

    @FXML
    private TextField txtQuantity;

    @FXML
    private TextField txtTitle;

    @FXML
    private TextField txtYear;

    private final BookService bookService = new BookService();

    @FXML
    void btnaddbookOnAction(ActionEvent event) {
        try {
            bookService.addBook(txtBookId.getText(), txtTitle.getText(), txtAuthor.getText(),
                    txtCategory.getText(), txtYear.getText(), txtQuantity.getText());
            new Alert(Alert.AlertType.INFORMATION, "Book added").show();
            clearForm();
        } catch (IllegalArgumentException e) {
            new Alert(Alert.AlertType.WARNING, e.getMessage()).show();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Couldn't save the book").show();
            e.printStackTrace();
        }
    }

    @FXML
    void btnclearOnAction(ActionEvent event) {
        clearForm();
    }

    private void clearForm() {
        txtBookId.clear();
        txtTitle.clear();
        txtAuthor.clear();
        txtCategory.clear();
        txtYear.clear();
        txtQuantity.clear();
        txtBookId.requestFocus();
    }
}