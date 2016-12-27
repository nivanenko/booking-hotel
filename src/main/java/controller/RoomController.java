package controller;

import dao.HotelDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.MainApp;
import model.Hotel;
import model.Room;

import java.sql.Date;
import java.util.ArrayList;

public class RoomController {
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Label hotelLabel;
    @FXML
    private TableView<Room> roomTable;
    @FXML
    private TableColumn<Room, Integer> numberColumn;
    @FXML
    private TableColumn<Room, String> descColumn;
    @FXML
    private TableColumn<Hotel, Integer> priceColumn;
    @FXML
    private TableColumn<Hotel, Integer> capacityColumn;
    private ObservableList<Room> roomData;
    private Date dateIn;
    private Date dateOut;
    private String customerName;

    private Stage dialogStage;
    private MainApp mainApp;
    private HotelDao hotelDao;

    @FXML
    public void initialize() {
        hotelDao = new HotelDao();

        roomData = FXCollections.observableArrayList();
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        descColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        capacityColumn.setCellValueFactory(new PropertyValueFactory<>("capacity"));
    }

    @FXML
    private void handleSubmitButton() {
        Room roomToBook = roomTable.getSelectionModel().getSelectedItem();

        if (hotelDao.createBooking(roomToBook, dateIn, dateOut, customerName)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initOwner(dialogStage);
            alert.setTitle("Booking");
            alert.setHeaderText("Booking was successful!");
            alert.setContentText("You can check it out in booking section.");

            alert.showAndWait();

            dialogStage = (Stage) anchorPane.getScene().getWindow();
            dialogStage.close();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Error!");
            alert.setHeaderText("Something went wrong");
            alert.setContentText("Please, just relax");

            alert.showAndWait();
        }
    }

    @FXML
    public void handleCancelButton() {
        dialogStage = (Stage) anchorPane.getScene().getWindow();
        dialogStage.close();
    }

    public void updateTable(ArrayList<Room> rooms) {
        for (Room room : rooms) {
            roomData.add(room);
        }
        roomTable.setItems(roomData);
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public void setHotelLabel(String hotelName) {
        hotelLabel.setText(hotelName);
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setDates(Date dateIn, Date dateOut) {
        this.dateIn = dateIn;
        this.dateOut = dateOut;
    }
}
