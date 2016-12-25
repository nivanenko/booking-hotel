package controller;

import dao.HotelDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.MainApp;
import model.Hotel;
import model.Room;
import util.CommonUtil;

import java.sql.Date;
import java.util.ArrayList;

public class CustomerController {
    // Common items
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Label nameLabel;

    // Search tab
    @FXML
    private TextField cityTextField;
    @FXML
    private DatePicker fromDate;
    @FXML
    private DatePicker toDate;
    @FXML
    private TextField guestField;
    @FXML
    private TableView<Hotel> hotelTable;
    @FXML
    private TableColumn<Hotel, String> nameColumn;
    @FXML
    private TableColumn<Hotel, String> descColumn;
    @FXML
    private TableColumn<Hotel, Integer> priceColumn;
    private ObservableList<Hotel> hotelData;

    private Stage dialogStage;
    private MainApp mainApp;
    private HotelDao hotelDao;

    @FXML
    public void initialize() {
        hotelDao = new HotelDao();
        hotelData = FXCollections.observableArrayList();

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("hotelName"));
        descColumn.setCellValueFactory(new PropertyValueFactory<>("hotelDescription"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("minPrice"));

        hotelTable.setRowFactory(tv -> {
            TableRow<Hotel> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    Hotel rowData = row.getItem();
                    String name = rowData.getHotelName();
                    ArrayList<Room> rooms = hotelDao.retrieveRooms(name);

                    mainApp.showRoomPage(name, hotelDao.retrieveRooms(name));
                }
            });
            return row;
        });
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public void setNameLabel(String name) {
        nameLabel.setText(name);
    }

    @FXML
    private void handleSignOutButton() {
        dialogStage = (Stage) anchorPane.getScene().getWindow();
        dialogStage.close();

        mainApp.showLoginPage();
    }

    @FXML
    private void handleClearButton() {
        cityTextField.clear();
        fromDate.getEditor().clear();
        toDate.getEditor().clear();
        guestField.clear();
    }

    @FXML
    private void handleSearchButton() {
        if (isInputValid()) {
            Date fromDateSQL = Date.valueOf(fromDate.getValue());
            Date toDateSQL = Date.valueOf(toDate.getValue());
            ArrayList<Hotel> hotels = hotelDao.searchHotel(cityTextField.getText(),
                    fromDateSQL, toDateSQL, Integer.parseInt(guestField.getText()));

            for (Hotel hotel : hotels) {
                hotelData.add(hotel);
            }

            hotelTable.setItems(hotelData);
        }
    }

    /**
     * Validates the user input in the text fields.
     *
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (cityTextField.getText() == null || cityTextField.getText().length() < 2) {
            errorMessage += "City is not valid!\n";
        }
        if (fromDate.getValue() == null) {
            errorMessage += "Date from is not valid!\n";
        }
        if (toDate.getValue() == null) {
            errorMessage += "Date to is not valid!\n";
        }
        if (Date.valueOf(toDate.getValue()).before(Date.valueOf(fromDate.getValue()))) {
            errorMessage += "Dates are not valid!\n";
        }
        if (guestField.getText() == null || !CommonUtil.isNumeric(guestField.getText())) {
            errorMessage += "Guests is not valid!\n";
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
