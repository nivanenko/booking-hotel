package controller;

import dao.OwnerDao;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.MainApp;

public class OwnerController {
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Label nameLabel;

    private Stage dialogStage;
    private MainApp mainApp;
    private OwnerDao ownerDao;

    @FXML
    public void initialize() {
        ownerDao = new OwnerDao();

    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public void setNameLabel(String name) {
        nameLabel.setText(name);
    }

    @FXML
    private void handleHotelRefreshButton() {

    }

    @FXML
    private void handleAddHotelButton() {

    }

    @FXML
    private void handleAddRoomButton() {

    }

    @FXML
    private void handleSignOutButton() {
        dialogStage = (Stage) anchorPane.getScene().getWindow();
        dialogStage.close();

        mainApp.showLoginPage();
    }
}
