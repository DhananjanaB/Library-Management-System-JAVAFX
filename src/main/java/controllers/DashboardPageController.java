package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class DashboardPageController {

    @FXML
    private StackPane contentArea;

    @FXML
    public void initialize() {
        loadPage("home_page");
    }

    @FXML
    void btnHomeOnAction(ActionEvent event) {
        loadPage("home_page");
    }

    @FXML
    void btnAddBookOnAction(ActionEvent event) {
        loadPage("addbook_page");
    }

    @FXML
    void btnViewBooksOnAction(ActionEvent event) {
        loadPage("viewbook_page");
    }

    @FXML
    void btnAddMemberOnAction(ActionEvent event) {
        loadPage("addmember_page");
    }

    @FXML
    void btnManageMembersOnAction(ActionEvent event) {
        loadPage("managemember_page");
    }

    @FXML
    void btnIssueBookOnAction(ActionEvent event) {
        loadPage("issuebook_page");
    }

    @FXML
    void btnReturnBookOnAction(ActionEvent event) {
        loadPage("returnbook_page");
    }

    private void loadPage(String fxmlName) {
        try {
            Parent page = FXMLLoader.load(getClass().getResource("/view/" + fxmlName + ".fxml"));
            contentArea.getChildren().setAll(page);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}