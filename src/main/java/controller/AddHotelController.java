package controller;

import dao.OwnerDao;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.MainApp;

public class AddHotelController {
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TextField nameField;
    @FXML
    private TextField cityField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField descField;

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
            if (ownerDao.addHotel(ownerName, nameField.getText(), cityField.getText(),
                    addressField.getText(), descField.getText())) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.initOwner(dialogStage);
                alert.setTitle("Adding hotel");
                alert.setHeaderText("Adding a hotel was successful!");
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

        if (nameField.getText() == null || nameField.getText().length() < 2) {
            errorMessage += "Name is not valid!\n";
        }
        if (cityField.getText() == null || cityField.getText().length() < 2) {
            errorMessage += "City is not valid!\n";
        }
        if (addressField.getText() == null || addressField.getText().length() < 2) {
            errorMessage += "Address is not valid!\n";
        }
        if (descField.getText() == null || descField.getText().length() < 2) {
            errorMessage += "Description is not valid!\n";
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
