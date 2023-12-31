package major_project.view;

import javafx.scene.layout.VBox;
import java.util.ArrayList;
import javafx.scene.paint.Color;
import javafx.scene.control.ColorPicker;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import org.apache.commons.codec.digest.DigestUtils;

public class CurrencyLogin {
    private CurrencyView view;
    private String fontStyle;

    public CurrencyLogin(CurrencyView view, String fontStyle) {
        this.view = view;
        this.fontStyle = fontStyle;
    }

    /**
     * Perform login, alert if can't login, otherwise it closes login screen
     * and opens main screen
     */
    public void loginAction(String username, String password,
        Button loginButton) {
        String pwdHash = DigestUtils.sha1Hex(password);//Hash pwd as soon as it recieves it
        String result = view.model.login(username, pwdHash);
        if(result == null) {
            Alert alertError = new Alert(Alert.AlertType.ERROR);
            alertError.setHeaderText("Wrong username or password");
            alertError.setTitle("Wrong username or password");
            alertError.showAndWait();
        } else {
            /*this.theme = result;
            this.loggedIn = true;
            this.user = username;*/
            view.setLogin(username, result);
            ((Stage)loginButton.getScene().getWindow()).close();
        }
    }

    /**
     * Action for sign up button, alert if username already exists
     */
    public void signUpAction(String username, String password,
        Button loginButton) {
        String pwdHash = DigestUtils.sha1Hex(password);
        if(view.model.signUp(username, pwdHash)) {
            loginAction(username, password, loginButton);
        } else {
            Alert alertError = new Alert(Alert.AlertType.ERROR);
            alertError.setHeaderText("Username already exists");
            alertError.setTitle("Username already exists");
            alertError.showAndWait();
        }
    }

    /**
     * Display login screen before main screen
     */
    public void loginScreen() {
        TextField username = new TextField();
        TextField password = new TextField();
        Button loginButton = new Button();
        Label usernameLabel = new Label("Username");
        usernameLabel.setStyle(fontStyle);
        Label passwordLabel = new Label("Password");
        passwordLabel.setStyle(fontStyle);
        loginButton.setText("Login");
        loginButton.setStyle(fontStyle);
        loginButton.setOnAction((event) -> loginAction(username.getText(),
            password.getText(), loginButton));
        Button signUpButton = new Button();
        signUpButton.setText("Sign up");
        signUpButton.setStyle(fontStyle);
        signUpButton.setOnAction((event) -> signUpAction(username.getText(),
            password.getText(), loginButton));
        VBox loginBox = new VBox();
        loginBox.setPadding(new Insets(20, 20, 20, 20));
        loginBox.getChildren().addAll(usernameLabel, username, passwordLabel,
            password, loginButton, signUpButton);
        loginBox.setSpacing(10);
        BorderPane secondaryPane = new BorderPane();
        secondaryPane.setPadding(new Insets(20, 20, 20, 20));
        secondaryPane.setCenter(loginBox);
        Scene secondaryScene = new Scene(secondaryPane);
        Stage secondaryStage = new Stage();
        secondaryStage.setWidth(500);
        secondaryStage.setHeight(300);
        secondaryStage.setScene(secondaryScene);
        secondaryStage.setTitle("Login");
        secondaryStage.showAndWait();
    }
}
