package controller;

import dao.HotelDao;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.MainApp;
import model.Hotel;
import model.Room;

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
    private ObservableList<Hotel> hotelData;

    private Stage dialogStage;
    private MainApp mainApp;
    private HotelDao hotelDao;
    private ArrayList<Room> rooms;

    @FXML
    public void initialize() {
        hotelDao = new HotelDao();

//        roomTable.setItems();
    }

    @FXML
    public void handleCancelButton() {
        dialogStage = (Stage) anchorPane.getScene().getWindow();
        dialogStage.close();
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public void setHotelLabel(String hotelName) {
        hotelLabel.setText(hotelName);
    }

    public void setRooms(ArrayList<Room> rooms) {
        this.rooms = rooms;
    }
}
