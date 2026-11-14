package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import service.DashboardService;

import java.sql.SQLException;

public class HomePageController {

    @FXML
    private Label lblBorrowed;

    @FXML
    private Label lblOverdue;

    @FXML
    private Label lblTotalBooks;

    @FXML
    private Label lblTotalMembers;

    private final DashboardService dashboardService = new DashboardService();

    @FXML
    public void initialize() {
        try {
            lblTotalBooks.setText(String.valueOf(dashboardService.getTotalBooks()));
            lblTotalMembers.setText(String.valueOf(dashboardService.getTotalMembers()));
            lblBorrowed.setText(String.valueOf(dashboardService.getBorrowed()));
            lblOverdue.setText(String.valueOf(dashboardService.getOverdue()));
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Couldn't load the dashboard numbers").show();
            e.printStackTrace();
        }
    }
}