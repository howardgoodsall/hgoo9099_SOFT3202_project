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

public class WindowHandler {
    private VBox vBox;
    private APIComm apiComm;
    private HBox loginHBox;
    private HBox signUpHBox;

    public WindowHandler(APIComm apiComm) {
        this.vBox = new VBox();
        this.vBox.setSpacing(10);
        this.apiComm = apiComm;
        return;
    }

    public VBox getVBox() {
        return this.vBox;
    }

    public void serverStatusButton() {
        Button serverStatusButton = new Button();
        Label serverStatusLabel = new Label("...\n");
        serverStatusLabel.setLabelFor(serverStatusButton);
        serverStatusButton.setText("Server Status");
        serverStatusButton.setOnAction((event) -> {
            if(this.apiComm.serverStatus()) {
                serverStatusLabel.setText("Online\n");
            } else {
                serverStatusLabel.setText("Offline\n");
            }
        });
        //this.hBox.getChildren().add(serverStatusButton);
        HBox hBox = new HBox();
        hBox.getChildren().add(serverStatusButton);
        hBox.getChildren().add(serverStatusLabel);
        this.vBox.getChildren().add(hBox);
    }

    public void loginSuccess() {
        this.vBox.getChildren().remove(this.loginHBox);
        this.vBox.getChildren().remove(this.signUpHBox);
        HBox hBox = new HBox();
        User user = this.apiComm.getUser();
        String info = String.format("Token: %s", this.apiComm.getToken());
        Label infoLabel = new Label(info);
        hBox.getChildren().add(infoLabel);
        this.vBox.getChildren().add(hBox);
        viewInfo();
    }

    public void login() {
        TextField login = new TextField();
        Button loginButton = new Button();
        Label loginUpLabel = new Label("");
        this.loginHBox = new HBox();
        this.loginHBox.getChildren().add(login);
        this.loginHBox.getChildren().add(loginButton);
        loginButton.setText("Login");
        loginButton.setOnAction((event) -> {
            if(this.apiComm.login(login.getText())){
                loginSuccess();
            }
        });
        this.vBox.getChildren().add(this.loginHBox);
    }

    public void signUp() {
        TextField signUp = new TextField();
        Button signUpButton = new Button();
        Label signUpLabel = new Label("");
        this.signUpHBox = new HBox();
        this.signUpHBox.getChildren().add(signUp);
        this.signUpHBox.getChildren().add(signUpButton);
        this.signUpHBox.getChildren().add(signUpLabel);
        signUpButton.setText("Sign Up");
        signUpButton.setOnAction((event) -> {
            if(this.apiComm.signUp(signUp.getText())){
                loginSuccess();
            } else {
                signUpLabel.setText(" Username already taken");
            }
        });
        this.vBox.getChildren().add(this.signUpHBox);
    }

    public void viewInfo() {
        Button infoButton = new Button();
        HBox hBox= new HBox();
        Label infoLabel = new Label("");
        hBox.getChildren().add(infoButton);
        hBox.getChildren().add(infoLabel);
        infoButton.setText("View User Information");
        infoButton.setOnAction((event) -> {
            User user = this.apiComm.getUser();
            String userInfo = String.format("\nUsername: %s\nCredits: %d\nJoined At: %s\nShip Count: %d\nStructure Count: %d\n",
                user.getUsername(), user.getCredits(), user.getJoinedAt(),
                user.getShipCount(), user.getStructureCount());
            infoLabel.setText(userInfo);
        });
        this.vBox.getChildren().add(hBox);
    }


}
