package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class DashboardPageController {

    @FXML
    private StackPane contentArea;

    @FXML
    private Button btnHome, btnAddBook, btnViewBooks, btnAddMember, btnManageMembers,
            btnIssueBook, btnReturnBook, btnHistory, btnLogout;

    @FXML
    public void initialize() {
        showPage("home_page", btnHome);
    }

    @FXML
    void btnHomeOnAction(ActionEvent event) {
        showPage("home_page", btnHome);
    }

    @FXML
    void btnAddBookOnAction(ActionEvent event) {
        showPage("addbook_page", btnAddBook);
    }

    @FXML
    void btnViewBooksOnAction(ActionEvent event) {
        showPage("viewbook_page", btnViewBooks);
    }

    @FXML
    void btnAddMemberOnAction(ActionEvent event) {
        showPage("addmember_page", btnAddMember);
    }

    @FXML
    void btnManageMembersOnAction(ActionEvent event) {
        showPage("managemember_page", btnManageMembers);
    }

    @FXML
    void btnIssueBookOnAction(ActionEvent event) {
        showPage("issuebook_page", btnIssueBook);
    }

    @FXML
    void btnReturnBookOnAction(ActionEvent event) {
        showPage("returnbook_page", btnReturnBook);
    }

    @FXML
    void btnHistoryOnAction(ActionEvent event) {
        showPage("history_page", btnHistory);
    }

    @FXML
    void btnLogoutOnAction(ActionEvent event) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to log out?",
                ButtonType.YES, ButtonType.NO);
        confirm.setHeaderText(null);
        if (confirm.showAndWait().orElse(ButtonType.NO) != ButtonType.YES) {
            return;
        }
        try {
            Stage stage = (Stage) btnLogout.getScene().getWindow();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/view/login_page.fxml")));
            scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
            stage.setScene(scene);
            stage.centerOnScreen();
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Couldn't open the login page").show();
            e.printStackTrace();
        }
    }

    /** Loads a page into the centre and highlights its sidebar button. */
    private void showPage(String fxmlName, Button activeButton) {
        try {
            Parent page = FXMLLoader.load(getClass().getResource("/view/" + fxmlName + ".fxml"));
            contentArea.getChildren().setAll(page);

            List.of(btnHome, btnAddBook, btnViewBooks, btnAddMember, btnManageMembers,
                            btnIssueBook, btnReturnBook, btnHistory)
                    .forEach(b -> b.getStyleClass().remove("nav-active"));
            activeButton.getStyleClass().add("nav-active");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}