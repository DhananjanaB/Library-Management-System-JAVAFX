package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import service.MemberService;

import java.sql.SQLException;

public class AddMemberPageController {

    @FXML
    private Button btnclear;

    @FXML
    private Button btnregistermember;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtFullName;

    @FXML
    private TextField txtMemberId;

    @FXML
    private TextField txtPhone;

    private final MemberService memberService = new MemberService();

    @FXML
    void btnregistermemberOnAction(ActionEvent event) {
        try {
            memberService.registerMember(txtMemberId.getText(), txtFullName.getText(),
                    txtEmail.getText(), txtPhone.getText(), txtAddress.getText());
            new Alert(Alert.AlertType.INFORMATION, "Member registered").show();
            clearForm();
        } catch (IllegalArgumentException e) {
            new Alert(Alert.AlertType.WARNING, e.getMessage()).show();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Couldn't save the member").show();
            e.printStackTrace();
        }
    }

    @FXML
    void btnclearOnAction(ActionEvent event) {
        clearForm();
    }

    private void clearForm() {
        txtMemberId.clear();
        txtFullName.clear();
        txtEmail.clear();
        txtPhone.clear();
        txtAddress.clear();
        txtMemberId.requestFocus();
    }
}