package main;

import controller.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Room;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;

public class MainApp extends Application {
    private Stage primaryStage;
    private AnchorPane rootLayout;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Booking hotel");

        showLoginPage();
    }


    public void showLoginPage() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("view/login.fxml"));
            rootLayout = loader.load();

            LoginController controller = loader.getController();
            controller.setMainApp(this);
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Login");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showRegisterPage() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("view/register.fxml"));
            AnchorPane registerPane = loader.load();

            RegisterController controller = loader.getController();
            controller.setMainApp(this);

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Registration");
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(registerPane);
            dialogStage.setScene(scene);

            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showCustomerPage(String name) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("view/main-customer.fxml"));
            AnchorPane mainPane = loader.load();

            CustomerController controller = loader.getController();
            controller.setMainApp(this);
            controller.setCustomerName(name);

            Scene scene = new Scene(mainPane);
            primaryStage.setTitle("Booking hotel");
            primaryStage.setResizable(false);
            primaryStage.setScene(scene);

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showRoomPage(String hotelName, ArrayList<Room> rooms, Date dateIn, Date dateOut, String customer) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("view/room-view.fxml"));
            AnchorPane roomPane = loader.load();

            RoomController controller = loader.getController();
            controller.setMainApp(this);
            controller.setHotelLabel(hotelName);
            controller.updateTable(rooms);
            controller.setDates(dateIn, dateOut);
            controller.setCustomerName(customer);

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Choose a room");
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(roomPane);
            dialogStage.setScene(scene);

            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showOwnerPage(String name) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("view/main-owner.fxml"));
            AnchorPane mainOwnerPage = loader.load();

            OwnerController controller = loader.getController();
            controller.setMainApp(this);
            controller.setOwnerNameLabel(name);

            Scene scene = new Scene(mainOwnerPage);
            primaryStage.setTitle("Booking hotel");
            primaryStage.setResizable(false);
            primaryStage.setScene(scene);

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showAddHotelPage(String ownerName) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("view/add-hotel.fxml"));
            AnchorPane addHotelPane = loader.load();

            AddHotelController controller = loader.getController();
            controller.setMainApp(this);
            controller.setOwnerName(ownerName);


            Stage dialogStage = new Stage();
            dialogStage.setTitle("Add a hotel");
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(addHotelPane);
            dialogStage.setScene(scene);

            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showAddRoomPage(String ownerName, String hotelName) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("view/add-room.fxml"));
            AnchorPane addRoomPane = loader.load();

            AddRoomController controller = loader.getController();
            controller.setMainApp(this);
            controller.setOwnerName(ownerName);
            controller.setHotelName(hotelName);

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Add a room");
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(addRoomPane);
            dialogStage.setScene(scene);

            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() throws Exception {
        // stop the planet
    }
}
