package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class ViewBookPageController {

    @FXML
    private TableView<Person> table;

    @FXML
    private TableColumn<Person, String> id;

    @FXML
    private TableColumn<Person, String> title;

    @FXML
    private TableColumn<Person, String> author;

    @FXML
    private TableColumn<Person, String> category;

    @FXML
    private TableColumn<Person, Integer> year;

    @FXML
    private TableColumn<Person, Integer> qty;

    @FXML
    private TextField txtSearch;

    private final ObservableList<Person> list = FXCollections.observableArrayList(
            new Person(5, 2008, "B001", "Clean Code", "R.Martin", "Programming"),
            new Person(3, 1965, "B002", "Dune", "F.Herbert", "Fiction"),
            new Person(4, 2011, "B003", "Sapiens", "Y.Harari", "History")
    );

    @FXML
    public void initialize() {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        author.setCellValueFactory(new PropertyValueFactory<>("author"));
        category.setCellValueFactory(new PropertyValueFactory<>("category"));
        year.setCellValueFactory(new PropertyValueFactory<>("year"));
        qty.setCellValueFactory(new PropertyValueFactory<>("qty"));

        FilteredList<Person> filtered = new FilteredList<>(list, p -> true);
        table.setItems(filtered);

        txtSearch.textProperty().addListener((obs, oldVal, newVal) -> {
            String key = newVal.toLowerCase().trim();
            filtered.setPredicate(p ->
                    key.isEmpty()
                            || p.getId().toLowerCase().contains(key)
                            || p.getTitle().toLowerCase().contains(key)
                            || p.getAuthor().toLowerCase().contains(key));
        });
    }
}