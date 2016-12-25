package controller;

import dao.UserDao;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.MainApp;

public class RegisterController {
    @FXML
    private AnchorPane anchorPane;

    @FXML
    private TextField loginField;

    @FXML
    private TextField nameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private CheckBox ownerCheck;

    @FXML
    private CheckBox agreeCheck;

    private Stage dialogStage;
    private MainApp mainApp;
    private UserDao userDao;

    @FXML
    public void initialize() {
        userDao = new UserDao();
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @FXML
    private void handleCancelButton() {
        dialogStage = (Stage) anchorPane.getScene().getWindow();
        dialogStage.close();
    }

    @FXML
    private void handleSubmitButton() {
        if (isInputValid()) {
            if (userDao.registerUser(loginField.getText(), nameField.getText(),
                    passwordField.getText(), ownerCheck.isSelected())) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.initOwner(dialogStage);
                alert.setTitle("Register");
                alert.setHeaderText("Registration was successful!");
                alert.setContentText("You can sign in now.");

                alert.showAndWait();

                dialogStage = (Stage) anchorPane.getScene().getWindow();
                dialogStage.close();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initOwner(dialogStage);
                alert.setTitle("Error!");
                alert.setHeaderText("The e-mail is already exist");
                alert.setContentText("Please login with your e-mail or choose another one");

                alert.showAndWait();
            }
        }
    }

    /**
     * Validates the user input in the text fields.
     *
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (loginField.getText() == null || loginField.getText().length() < 5 || !loginField.getText().contains("@")) {
            errorMessage += "Login is not valid!\n";
        }

        if (nameField.getText() == null || nameField.getText().length() < 2) {
            errorMessage += "Name is not valid!\n";
        }

        if (passwordField.getText() == null || passwordField.getText().length() < 5) {
            errorMessage += "Password is not valid!\n";
        }

        if (confirmPasswordField.getText() == null || confirmPasswordField.getText().length() < 5
                || !confirmPasswordField.getText().equals(passwordField.getText())) {
            errorMessage += "Password confirmation is not valid!\n";
        }

        if (!agreeCheck.isSelected()) {
            errorMessage += "Agreement wasn't accept!!\n";
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
