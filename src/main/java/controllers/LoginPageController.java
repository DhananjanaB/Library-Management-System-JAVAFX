package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import service.AuthService;

import java.io.IOException;
import java.sql.SQLException;

public class LoginPageController {

    @FXML
    private Button btnClear;

    @FXML
    private Button btnLogin;

    @FXML
    private Label lblError;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUsername;

    private final AuthService authService = new AuthService();

    @FXML
    void btnclearonAction(ActionEvent event) {
        txtUsername.clear();
        txtPassword.clear();
        lblError.setText("");
        txtUsername.requestFocus();
    }

    @FXML
    void btnloginonAction(ActionEvent event) {
        try {
            if (authService.login(txtUsername.getText(), txtPassword.getText())) {
                Stage stage = (Stage) btnLogin.getScene().getWindow();
                stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/dashboard_page.fxml"))));
                stage.centerOnScreen();
            } else {
                lblError.setText("Invalid username or password");
            }
        } catch (IllegalArgumentException e) {
            lblError.setText(e.getMessage());
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Cannot connect to the database").show();
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Cannot open the dashboard").show();
        }
    }
}