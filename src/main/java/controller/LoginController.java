package controller;

import dao.UserDao;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.MainApp;
import org.springframework.stereotype.Component;

@Component
public class LoginController {
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button submitButton;
    @FXML
    private Button exitButton;
    @FXML
    private Button registerButton;
    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;

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

    @FXML
    private void handleExitButton() {
        dialogStage = (Stage) anchorPane.getScene().getWindow();
        dialogStage.close();
    }

    @FXML
    private void handleRegisterButton() {
        mainApp.showRegisterPage();
    }

    @FXML
    private void handleSubmitButton() {
        String name = loginField.getText();
        if (isInputValid()) {
            if (userDao.checkUser(name, passwordField.getText())
                    && !userDao.isOwner(name)) {
                mainApp.showCustomerPage(name);
            } else if (userDao.isOwner(name)) {
                mainApp.showOwnerPage(name);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initOwner(dialogStage);
                alert.setTitle("Login failed");
                alert.setHeaderText("Please check your credentials");
                alert.setContentText("Your login or password are invalid! Please try again.");

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
        if (passwordField.getText() == null || passwordField.getText().length() < 5) {
            errorMessage += "Password is not valid!\n";
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
