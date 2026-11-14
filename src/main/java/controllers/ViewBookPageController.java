package controllers;

import entity.Book;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import service.BookService;

import java.sql.SQLException;

public class ViewBookPageController {

    @FXML
    private TableView<Book> table;

    @FXML
    private TableColumn<Book, String> id;

    @FXML
    private TableColumn<Book, String> title;

    @FXML
    private TableColumn<Book, String> author;

    @FXML
    private TableColumn<Book, String> category;

    @FXML
    private TableColumn<Book, Integer> year;

    @FXML
    private TableColumn<Book, Integer> qty;

    @FXML
    private TextField txtSearch;

    @FXML
    private TextField txtBookId;

    @FXML
    private TextField txtTitle;

    @FXML
    private TextField txtAuthor;

    @FXML
    private TextField txtCategory;

    @FXML
    private TextField txtYear;

    @FXML
    private TextField txtQuantity;

    private final BookService bookService = new BookService();
    private final ObservableList<Book> books = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        author.setCellValueFactory(new PropertyValueFactory<>("author"));
        category.setCellValueFactory(new PropertyValueFactory<>("category"));
        year.setCellValueFactory(new PropertyValueFactory<>("year"));
        qty.setCellValueFactory(new PropertyValueFactory<>("qty"));

        FilteredList<Book> filtered = new FilteredList<>(books, b -> true);
        table.setItems(filtered);

        txtSearch.textProperty().addListener((obs, oldValue, newValue) -> {
            String key = newValue.toLowerCase().trim();
            filtered.setPredicate(b -> key.isEmpty()
                    || b.getId().toLowerCase().contains(key)
                    || b.getTitle().toLowerCase().contains(key)
                    || b.getAuthor().toLowerCase().contains(key));
        });

        table.getSelectionModel().selectedItemProperty().addListener((obs, oldBook, book) -> {
            if (book != null) {
                txtBookId.setText(book.getId());
                txtTitle.setText(book.getTitle());
                txtAuthor.setText(book.getAuthor());
                txtCategory.setText(book.getCategory());
                txtYear.setText(String.valueOf(book.getYear()));
                txtQuantity.setText(String.valueOf(book.getQty()));
            }
        });

        loadBooks();
    }

    @FXML
    void btnupdateOnAction(ActionEvent event) {
        if (table.getSelectionModel().getSelectedItem() == null) {
            new Alert(Alert.AlertType.WARNING, "Select a book from the table first").show();
            return;
        }
        try {
            bookService.updateBook(txtBookId.getText(), txtTitle.getText(), txtAuthor.getText(),
                    txtCategory.getText(), txtYear.getText(), txtQuantity.getText());
            new Alert(Alert.AlertType.INFORMATION, "Book updated").show();
            loadBooks();
        } catch (IllegalArgumentException e) {
            new Alert(Alert.AlertType.WARNING, e.getMessage()).show();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Couldn't update the book").show();
            e.printStackTrace();
        }
    }

    @FXML
    void btndeleteOnAction(ActionEvent event) {
        Book selected = table.getSelectionModel().getSelectedItem();
        if (selected == null) {
            new Alert(Alert.AlertType.WARNING, "Select a book from the table first").show();
            return;
        }
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                "Delete " + selected.getTitle() + "?", ButtonType.YES, ButtonType.NO);
        if (confirm.showAndWait().orElse(ButtonType.NO) != ButtonType.YES) {
            return;
        }
        try {
            bookService.deleteBook(selected.getId());
            clearForm();
            loadBooks();
        } catch (SQLException e) {
            if (e.getErrorCode() == 1451) {
                new Alert(Alert.AlertType.WARNING, "This book has borrowing records, so it can't be deleted").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Couldn't delete the book").show();
                e.printStackTrace();
            }
        }
    }

    @FXML
    void btnclearOnAction(ActionEvent event) {
        table.getSelectionModel().clearSelection();
        clearForm();
    }

    private void clearForm() {
        txtBookId.clear();
        txtTitle.clear();
        txtAuthor.clear();
        txtCategory.clear();
        txtYear.clear();
        txtQuantity.clear();
    }

    private void loadBooks() {
        try {
            books.setAll(bookService.getAllBooks());
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Couldn't load the books").show();
            e.printStackTrace();
        }
    }
}