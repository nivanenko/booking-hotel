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
import model.Booking;
import model.Hotel;
import org.apache.commons.lang3.text.WordUtils;
import util.CommonUtil;

import java.sql.Date;
import java.util.ArrayList;

public class CustomerController {
    // Common items
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Label customerName;

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
    // Hotel/room table
    private TableView<Hotel> hotelTable;
    @FXML
    private TableColumn<Hotel, String> nameHotelColumn;
    @FXML
    private TableColumn<Hotel, String> descColumn;
    @FXML
    private TableColumn<Hotel, Integer> priceHotelColumn;
    private ObservableList<Hotel> hotelData;

    // Booking table
    @FXML
    private TableView<Booking> bookingTable;
    @FXML
    private TableColumn<Booking, String> hotelBookNameColumn;
    @FXML
    private TableColumn<Booking, Date> dateInColumn;
    @FXML
    private TableColumn<Booking, Date> dateOutColumn;
    @FXML
    private TableColumn<Booking, Integer> priceBookingColumn;
    private ObservableList<Booking> bookingData;

    private Date dateIn;
    private Date dateOut;
    private ArrayList<Booking> bookings;

    private Stage dialogStage;
    private MainApp mainApp;
    private HotelDao hotelDao;

    @FXML
    public void initialize() {
        hotelDao = new HotelDao();

        hotelData = FXCollections.observableArrayList();
        nameHotelColumn.setCellValueFactory(new PropertyValueFactory<>("hotelName"));
        descColumn.setCellValueFactory(new PropertyValueFactory<>("hotelDescription"));
        priceHotelColumn.setCellValueFactory(new PropertyValueFactory<>("minPrice"));

        hotelTable.setRowFactory(tv -> {
            TableRow<Hotel> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    Hotel rowData = row.getItem();
                    String name = rowData.getHotelName();

                    mainApp.showRoomPage(name, hotelDao.retrieveRooms(name, dateIn, dateOut),
                            dateIn, dateOut, customerName.getText());
                }
            });
            return row;
        });

        bookingData = FXCollections.observableArrayList();
        hotelBookNameColumn.setCellValueFactory(new PropertyValueFactory<>("hotelName"));
        dateInColumn.setCellValueFactory(new PropertyValueFactory<>("dateIn"));
        dateOutColumn.setCellValueFactory(new PropertyValueFactory<>("dateOut"));
        priceBookingColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public void setCustomerName(String name) {
        customerName.setText(name);
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
            hotelData.clear();
            hotelTable.setItems(hotelData);

            dateIn = Date.valueOf(fromDate.getValue());
            dateOut = Date.valueOf(toDate.getValue());
            ArrayList<Hotel> hotels = hotelDao.searchHotel(WordUtils.capitalize(cityTextField.getText()),
                    Integer.parseInt(guestField.getText()));

            if (hotels != null) {
                for (Hotel hotel : hotels) {
                    hotelData.add(hotel);
                }
                hotelTable.setItems(hotelData);
            }
        }
    }

    @FXML
    private void handleRefreshBookingButton() {
        bookingData.clear();
        bookingTable.setItems(bookingData);
        bookings = hotelDao.retrieveBooking(customerName.getText());

        if (bookings != null) {
            for (Booking booking : bookings) {
                bookingData.add(booking);
            }
            bookingTable.setItems(bookingData);
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initOwner(dialogStage);
            alert.setTitle("Bookings");
            alert.setHeaderText("You do not have any booking yet");
            alert.setContentText("Please, make a booking.");

            alert.showAndWait();
        }
    }

    @FXML
    private void handleDiscardBookingButton() {

    }

    @FXML
    private void handleCustomButton() {
        ArrayList<Hotel> hotels = hotelDao.getAllHotels();
        String hotelString = "";

        for (Hotel hotel : hotels) {
            hotelString += hotel.getHotelAddress() + "\n\n";
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initOwner(dialogStage);
        alert.setTitle("Hotels");
        alert.setHeaderText("SELECT HOTEL_ADDRESS FROM Hotels;");
        alert.setContentText(hotelString);

        alert.showAndWait();
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
        if (toDate.getValue() == null || fromDate.getValue() == null) {
            errorMessage += "Dates are not valid!\n";
        } else if (Date.valueOf(toDate.getValue()).before(Date.valueOf(fromDate.getValue()))
                && !Date.valueOf(fromDate.getValue()).before(new java.util.Date())) {
            errorMessage += "Dates are not valid!\n";
        }

        if (guestField.getText() == null || guestField.getText().length() < 1 || !CommonUtil.isNumeric(guestField.getText())) {
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
