package controller;

import dao.OwnerDao;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.MainApp;
import util.CommonUtil;

public class AddRoomController {
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TextField hotelNameField;
    @FXML
    private TextField descField;
    @FXML
    private TextField capacityField;
    @FXML
    private TextField priceField;

    private Stage dialogStage;
    private MainApp mainApp;
    private OwnerDao ownerDao;
    private String ownerName;

    @FXML
    public void initialize() {
        ownerDao = new OwnerDao();

    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    @FXML
    private void handleSubmitButton() {
        if (isInputValid()) {
            if (ownerDao.addRoom(ownerName, hotelNameField.getText(), descField.getText(),
                    Integer.parseInt(capacityField.getText()),
                    Integer.parseInt(priceField.getText()))) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.initOwner(dialogStage);
                alert.setTitle("Adding room");
                alert.setHeaderText("Adding a room was successful!");
                alert.setContentText("It's in system now.");

                alert.showAndWait();

                dialogStage = (Stage) anchorPane.getScene().getWindow();
                dialogStage.close();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initOwner(dialogStage);
                alert.setTitle("Error!");
                alert.setHeaderText("Error!");
                alert.setContentText("Please check your fields!");

                alert.showAndWait();
            }
        }
    }

    @FXML
    private void handleCancelButton() {
        dialogStage = (Stage) anchorPane.getScene().getWindow();
        dialogStage.close();
    }

    /**
     * Validates the user input in the text fields.
     *
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (hotelNameField.getText() == null || hotelNameField.getText().length() < 2) {
            errorMessage += "Hotel name is not valid!\n";
        }
        if (descField.getText() == null || descField.getText().length() < 2) {
            errorMessage += "Description is not valid!\n";
        }
        if (capacityField.getText() == null || capacityField.getText().length() < 1 ||
                !CommonUtil.isNumeric(capacityField.getText())) {
            errorMessage += "Capacity is not valid!\n";
        }
        if (priceField.getText() == null || priceField.getText().length() < 1 ||
                !CommonUtil.isNumeric(priceField.getText())) {
            errorMessage += "Price is not valid!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();
            return false;
        }
    }
}
