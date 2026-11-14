package controllers;

import entity.Member;
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
import service.MemberService;

import java.sql.SQLException;

public class ManageMemberPageController {

    @FXML
    private TableView<Member> table;

    @FXML
    private TableColumn<Member, String> id;

    @FXML
    private TableColumn<Member, String> name;

    @FXML
    private TableColumn<Member, String> email;

    @FXML
    private TableColumn<Member, String> phone;

    @FXML
    private TableColumn<Member, String> address;

    @FXML
    private TextField txtSearch;

    @FXML
    private TextField txtMemberId;

    @FXML
    private TextField txtFullName;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtPhone;

    @FXML
    private TextField txtAddress;

    private final MemberService memberService = new MemberService();
    private final ObservableList<Member> members = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        address.setCellValueFactory(new PropertyValueFactory<>("address"));

        FilteredList<Member> filtered = new FilteredList<>(members, m -> true);
        table.setItems(filtered);

        txtSearch.textProperty().addListener((obs, oldValue, newValue) -> {
            String key = newValue.toLowerCase().trim();
            filtered.setPredicate(m -> key.isEmpty()
                    || m.getId().toLowerCase().contains(key)
                    || m.getFullName().toLowerCase().contains(key)
                    || m.getEmail().toLowerCase().contains(key));
        });

        table.getSelectionModel().selectedItemProperty().addListener((obs, oldMember, m) -> {
            if (m != null) {
                txtMemberId.setText(m.getId());
                txtFullName.setText(m.getFullName());
                txtEmail.setText(m.getEmail());
                txtPhone.setText(m.getPhone());
                txtAddress.setText(m.getAddress());
            }
        });

        loadMembers();
    }

    @FXML
    void btnupdateOnAction(ActionEvent event) {
        if (table.getSelectionModel().getSelectedItem() == null) {
            new Alert(Alert.AlertType.WARNING, "Select a member from the table first").show();
            return;
        }
        try {
            memberService.updateMember(txtMemberId.getText(), txtFullName.getText(),
                    txtEmail.getText(), txtPhone.getText(), txtAddress.getText());
            new Alert(Alert.AlertType.INFORMATION, "Member updated").show();
            loadMembers();
        } catch (IllegalArgumentException e) {
            new Alert(Alert.AlertType.WARNING, e.getMessage()).show();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Couldn't update the member").show();
            e.printStackTrace();
        }
    }

    @FXML
    void btndeleteOnAction(ActionEvent event) {
        Member selected = table.getSelectionModel().getSelectedItem();
        if (selected == null) {
            new Alert(Alert.AlertType.WARNING, "Select a member from the table first").show();
            return;
        }
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                "Delete " + selected.getFullName() + "?", ButtonType.YES, ButtonType.NO);
        if (confirm.showAndWait().orElse(ButtonType.NO) != ButtonType.YES) {
            return;
        }
        try {
            memberService.deleteMember(selected.getId());
            clearForm();
            loadMembers();
        } catch (SQLException e) {
            if (e.getErrorCode() == 1451) {
                new Alert(Alert.AlertType.WARNING, "This member has borrowing records, so they can't be deleted").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Couldn't delete the member").show();
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
        txtMemberId.clear();
        txtFullName.clear();
        txtEmail.clear();
        txtPhone.clear();
        txtAddress.clear();
    }

    private void loadMembers() {
        try {
            members.setAll(memberService.getAllMembers());
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Couldn't load the members").show();
            e.printStackTrace();
        }
    }
}