package task2;

import java.util.List;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.beans.property.StringProperty;

public class SpaceTraderApp extends Application {
    APIComm apiComm;

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {
        List<String> params = getParameters().getRaw();
        if (params.size() != 1) {
            System.out.println("Usage: gradle run --args=<online or offline>");
            return;
        }
        if (params.get(0).equalsIgnoreCase("online")) {
            this.apiComm = new APICommOnline();
            System.out.println("Online");
        } else if (params.get(0).equalsIgnoreCase("offline")) {
            this.apiComm = new APICommOffline();
            System.out.println("Offline");
        } else {
            return;
        }
        Pane spacer = new Pane();
        spacer.setStyle("-fx-background-color: #e0e0e0;");
        HBox.setHgrow(spacer, Priority.ALWAYS);
        spacer.setMinSize(10, 1);
        WindowHandler windowHandle = new WindowHandler(this.apiComm);
        windowHandle.serverStatusButton();
        windowHandle.login();
        windowHandle.signUp();

        VBox vBox = windowHandle.getVBox();

        Scene scene = new Scene(vBox, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Space Traders");
        primaryStage.show();
    }
}
