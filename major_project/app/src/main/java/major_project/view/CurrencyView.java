package major_project.view;

import major_project.controller.CurrencyController;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.geometry.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.GridPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import java.util.Optional;
import java.util.ArrayList;
import javafx.geometry.Insets;
import org.controlsfx.control.WorldMapView;
import org.controlsfx.control.WorldMapView.CountryView;
import org.apache.commons.codec.digest.DigestUtils;
import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.control.ColorPicker;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

/**
 * View handler class
 */
public class CurrencyView {
    private final CurrencyController controller;
    private final Scene scene;
    private final BorderPane pane;
    private TableView mainTable;
    private final String fontStyle = "-fx-font: 16 arial;";
    private String theme = "white";
    private boolean loggedIn = false;
    private String user = null;
    private String userColour = "0x111111";

    public CurrencyView(CurrencyController controller) {
        this.controller = controller;
        loginScreen();
        this.pane = new BorderPane();
        this.scene = new Scene(this.pane);
        if(this.loggedIn) {
            this.scene.getRoot().setStyle("-fx-base:"+this.theme);
            initialise();
        } else {
            System.exit(0);//If login screen is closed before logging in, exit
        }
    }

    /**
     * Perform login, alert if can't login, otherwise it closes login screen
     * and opens main screen
     */
    public void loginAction(String username, String password,
        Button loginButton) {
        String pwdHash = DigestUtils.sha1Hex(password);//Hash pwd as soon as it recieves it
        String result = this.controller.login(username, pwdHash);
        if(result == null) {
            Alert alertError = new Alert(Alert.AlertType.ERROR);
            alertError.setHeaderText("Wrong username or password");
            alertError.setTitle("Wrong username or password");
            alertError.showAndWait();
        } else {
            this.theme = result;
            this.loggedIn = true;
            this.user = username;
            ((Stage)loginButton.getScene().getWindow()).close();
        }
    }

    /**
     * Action for sign up button, alert if username already exists
     */
    public void signUpAction(String username, String password,
        Button loginButton) {
        String pwdHash = DigestUtils.sha1Hex(password);
        if(this.controller.signUp(username, pwdHash)) {
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
        Label usernameLabel = new Label("username");
        usernameLabel.setStyle(fontStyle);
        Label passwordLabel = new Label("password");
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

    /**
     * Set up layout and buttons
     */
    public void initialise() {
        this.mainTable = new TableView<TableColumn>();
        HBox conversionBox = conversionBoxInit();
        this.pane.setBottom(conversionBox);
        this.pane.setAlignment(conversionBox, Pos.CENTER);
        initCurrencyList();
        this.pane.setCenter(this.mainTable);
        this.pane.setPadding(new Insets(20, 20, 20, 20));
        this.pane.setRight(sideBoxInit());
        Menu m = new Menu("Menu");
        MenuItem m1 = new MenuItem("About");
        m1.setOnAction((event) -> aboutButton());
        m.getItems().add(m1);
        MenuBar mb = new MenuBar();
        mb.getMenus().add(m);
        HBox topPanel = new HBox();
        ToggleButton themebtn = new ToggleButton("Change theme");
        themebtn.setOnAction((event) -> changeThemeButton());
        ToggleGroup group = new ToggleGroup();
        themebtn.setToggleGroup(group);
        topPanel.getChildren().addAll(mb, themebtn);
        this.pane.setTop(topPanel);
    }

    /**
     * About button pop up
     */
    public void aboutButton() {
        Label aboutLabelTop = new Label("Program name: Currency Conversion Thing");
        Label aboutLabelMiddle = new Label("Student name: Howard Goodsall");
        Label aboutLabelBottom = new Label(
        "References: https://github.com/controlsfx/controlsfx/issues/1091 (colour on map)");
        BorderPane popUpPane = new BorderPane();
        popUpPane.setTop(aboutLabelTop);
        popUpPane.setLeft(aboutLabelMiddle);
        popUpPane.setBottom(aboutLabelBottom);
        popUpPane.setPadding(new Insets(20, 20, 20, 20));
        Scene secondaryScene = new Scene(popUpPane);
        secondaryScene.getRoot().setStyle(("-fx-base:"+this.theme));
        Stage secondaryStage = new Stage();
        secondaryStage.setWidth(400);
        secondaryStage.setHeight(110);
        secondaryStage.setScene(secondaryScene);
        secondaryStage.setTitle("About Page");
        secondaryStage.showAndWait();
    }

    /**
     * Toggle the theme
     */
    public void changeThemeButton() {
        if(this.theme.equals("black")) {
            this.theme = "white";
        } else {
            this.theme = "black";
        }
        this.controller.updateTheme(this.theme, this.user);
        this.scene.getRoot().setStyle(("-fx-base:"+this.theme));
    }

    /**
     * Return the scene (called in Main)
     */
    public Scene getScene() {
        return this.scene;
    }

    /**
     * Set up main table
     */
    public void initCurrencyList() {
        //Create the main table
        TableColumn<CurrencyView, String> currencyCodeCol =
            new TableColumn<CurrencyView, String>("Currency Code");
        currencyCodeCol.setCellValueFactory(new PropertyValueFactory<>(
            "currencyCode"));
        currencyCodeCol.setMinWidth(400);
        TableColumn<CurrencyView, String> nameCol =
            new TableColumn<CurrencyView, String>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setMinWidth(500);
        TableColumn<CurrencyView, Button> removeButtonCol =
            new TableColumn<CurrencyView, Button>("");
        removeButtonCol.setMinWidth(200);
        removeButtonCol.setCellValueFactory(new PropertyValueFactory<>(
            "removeBtn"));
        this.mainTable.getColumns().addAll(currencyCodeCol, nameCol,
            removeButtonCol);
        //Get user's colour preference
        this.userColour = this.controller.getUserColour(this.user);
        //Populate table with data from database
        ArrayList<String[]> userViewList =
            this.controller.getViewingCurrencies(this.user);
        if(userViewList != null) {
            for(int i=0; i<userViewList.size(); i++) {
                String[] item = userViewList.get(i);
                Button removeBtn = new Button();
                removeBtn.setText("X");
                removeBtn.setOnAction((event) -> removeItem(item[0]));
                CurrencyDisplay newRow = new CurrencyDisplay(item[0], item[1],
                    removeBtn);
                this.mainTable.getItems().add(newRow);
            }
        }
    }

    public void setUserColour(ColorPicker colorPicker) {
        this.userColour = colorPicker.getValue().toString();
        this.controller.updateColour(this.userColour, this.user);
    }

    /**
     * initialise the side box, with world map, clear table and clear cache tables
     */
    public VBox sideBoxInit() {
        Button worldMapButton = new Button();
        worldMapButton.setOnAction((event) -> mapPopUp());
        worldMapButton.setText("Open World Map");
        worldMapButton.setStyle(fontStyle);
        worldMapButton.setMinWidth(160);

        Button clearBtn = new Button("Clear Table");
        clearBtn.setMinWidth(160);
        clearBtn.setOnAction((event) -> clearMainTable());
        clearBtn.setStyle(fontStyle);

        Button clearCacheBtn = new Button("Clear Cache");
        clearCacheBtn.setMinWidth(160);
        clearCacheBtn.setOnAction((event) -> clearCache());
        clearCacheBtn.setStyle(fontStyle);

        final ColorPicker colorPicker = new ColorPicker(Color.web(this.userColour));
        colorPicker.setOnAction((event) ->setUserColour(colorPicker));

        VBox sidePanel = new VBox();
        sidePanel.getChildren().addAll(worldMapButton, clearBtn, clearCacheBtn,
            colorPicker);
        sidePanel.setPadding(new Insets(50, 20, 20, 20));
        return sidePanel;
    }

    /**
     * initialise the conversion field at the bottom of the pane
     */
    public HBox conversionBoxInit() {
        HBox hbox = new HBox();
        ComboBox<String> fromDropDown = new ComboBox<String>();
        fromDropDown.setStyle(fontStyle);
        ComboBox<String> toDropDown = new ComboBox<String>();
        toDropDown.setStyle(fontStyle);
        ObservableList<CurrencyDisplay> rows = this.mainTable.getItems();
        this.mainTable.getItems().addListener(new ListChangeListener<Object>() {
            @Override
            public void onChanged(Change<?> change) {
                fromDropDown.getItems().clear();
                toDropDown.getItems().clear();
                for(int i=0; i<rows.size(); i++) {
                    fromDropDown.getItems().add(rows.get(i).getCurrencyCode());
                    toDropDown.getItems().add(rows.get(i).getCurrencyCode());
                }
            }
        });

        TextField fromTextField = new TextField();
        fromTextField.setStyle(fontStyle);
        Label fromLabel = new Label();
        fromLabel.setText("             Convert ");
        fromLabel.setStyle(fontStyle);
        Label middleLabel = new Label();
        middleLabel.setText("   To   ");
        middleLabel.setStyle(fontStyle);
        Label toLabel = new Label();
        toLabel.setText("    ->              ");
        toLabel.setStyle(fontStyle);
        Label exRate = new Label();
        exRate.setText(" Rate:    ");
        exRate.setStyle(fontStyle);

        Button calculateBtn = new Button("Calculate");
        calculateBtn.setStyle(fontStyle);
        calculateBtn.setOnAction((event) -> doConversion(
            (String)fromDropDown.getValue(), (String)toDropDown.getValue(),
            fromTextField.getText(), toLabel, exRate));

        Button sendReportButton = new Button("Send Report");
        sendReportButton.setStyle(fontStyle);
        sendReportButton.setOnAction((event) -> sendReport(sendReportButton,
            getCurrNameFromCode((String)fromDropDown.getValue()),
            (String)fromDropDown.getValue(),
            getCurrNameFromCode((String)toDropDown.getValue()),
            (String)toDropDown.getValue(),
            fromTextField.getText(), exRate.getText()));

        hbox.getChildren().addAll(fromLabel, fromDropDown, fromTextField,
            middleLabel, toDropDown, toLabel, exRate, calculateBtn,
            sendReportButton);
        return hbox;
    }


    /**
     * Button function to clear main table
     */
    public void clearMainTable() {
        this.mainTable.getItems().clear();
        this.controller.clearViewingTable(this.user);
    }

    /**
     * Button function to clear database cache
     */
    public void clearCache() {
        this.controller.clearCache();
    }

    /**
     * retrieve currency name from currency Code
     * Searchs from data in the main table so no model-view leak
     */
    public String getCurrNameFromCode(String currCode) {
        ObservableList<CurrencyDisplay> rows = this.mainTable.getItems();
        for(int i=0; i<rows.size(); i++) {
            if(rows.get(i).getCurrencyCode().equals(currCode)) {
                return rows.get(i).getName();
            }
        }
        return null;
    }

    /**
     * Get params and perform conversion
     */
    public void doConversion(String fromCurrCode, String toCurrCode,
        String amount, Label toLabel, Label exRate) {
        if(fromCurrCode == null || toCurrCode == null || amount == null) {
            Alert alertError = new Alert(Alert.AlertType.ERROR);
            alertError.setHeaderText("Not enough inputs given");
            alertError.setTitle("Not enough inputs given");
            alertError.getDialogPane().setStyle("-fx-base:"+this.theme);
            alertError.showAndWait();
            return;
        }
        String value = this.controller.currConversion(fromCurrCode, toCurrCode,
            amount);
        toLabel.setText(String.format(" ->   %s %s ",
        value, toCurrCode));
        String exRateResult = this.controller.getExchangeRateCache(fromCurrCode,
            toCurrCode);
        boolean update = false;
        if(exRateResult != null) {
            Alert alertRefresh = new Alert(Alert.AlertType.CONFIRMATION);
            alertRefresh.setHeaderText("Cache found for this exchange rate. Refresh exchange rate?");
            alertRefresh.setTitle("Cache Hit");
            alertRefresh.getDialogPane().setStyle("-fx-base:"+this.theme);
            Optional<ButtonType> result = alertRefresh.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK){
                exRateResult = null;
                update = true;
            }
        }
        if(exRateResult == null) {
            exRateResult = this.controller.getExchangeRate(fromCurrCode,
                toCurrCode);
            //Double exRateDouble = this.controller.calcExchangeRate(amount, value);
            if(update) {
                this.controller.updateRate(Double.parseDouble(exRateResult),
                    fromCurrCode, toCurrCode);
            }
            //exRateResult = String.format("%.03f", exRateDouble);
        }
        exRate.setText(String.format(" Rate: %s ", exRateResult));
    }

    /**
     * Remove item from main table
     */
    public void removeItem(String currencyName) {
        ObservableList<CurrencyDisplay> rows = this.mainTable.getItems();
        for(int i=0; i<rows.size(); i++) {
            if(rows.get(i).getCurrencyCode().equals(currencyName)) {
                this.controller.removeViewCurrency(rows.get(i).getCurrencyCode(),
                    this.user);
                this.mainTable.getItems().remove(rows.get(i));
                return;
            }
        }
    }

    /**
     * After selecting countries on the world map, add their currencies to
     * main table. (Runs in separate thread)
     */
    public void getCurrencyFromCountries(ObservableList<WorldMapView.Country>
        selectedCountries) {
        //Check if each country has supported currencies and add them to the list
        for(int i=0; i<selectedCountries.size(); i++) {
            ArrayList<String[]> allCurrenciesForCountry =
                this.controller.supportedCurrencies(selectedCountries.get(i)
                .getLocale().getDisplayCountry());
            if(allCurrenciesForCountry != null) {
                for(int j=0; j<allCurrenciesForCountry.size(); j++) {
                    boolean duplicate = false;
                    String[] currData = allCurrenciesForCountry.get(j);
                    for(int k=0; k<this.mainTable.getItems().size(); k++) {//Check for duplicates
                        if(currData[1].equals((
                            (CurrencyDisplay)(this.mainTable.getItems().get(k)))
                            .getName())) {
                            duplicate = true;
                        }
                    }
                    if((currData != null) && (!duplicate)) {
                        Button removeBtn = new Button();
                        removeBtn.setText("X");
                        removeBtn.setOnAction((event) -> removeItem(currData[0]));
                        CurrencyDisplay newRow = new CurrencyDisplay(currData[0],
                            currData[1], removeBtn);
                        this.mainTable.getItems().add(newRow);//Add new row to table
                        this.controller.insertViewCurrency(newRow.getCurrencyCode(),
                            newRow.getName(), this.user);
                    }
                }
            }  else {
                String countryName = selectedCountries.get(i).getLocale()
                    .getDisplayCountry();
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        Alert alertError = new Alert(Alert.AlertType.ERROR);
                        alertError.setHeaderText(String.format("No supported currencies found for %s"
                            ,countryName));
                        alertError.setTitle("No currencies found");
                        //alertError.getDialogPane().setStyle("-fx-base:"+this.theme);
                        alertError.showAndWait();
                    }
                });
            }
        }
    }

    /**
     * Handle world map pop up
     */
    public void mapPopUp() {
        CurrencyWorldMap worldMap = new CurrencyWorldMap(theme, this.userColour);
        Stage mapStage = worldMap.mapPopUp();
        mapStage.showAndWait();
        ObservableList<WorldMapView.Country> selectedCountries = worldMap
            .getSelectedCountries();
        //Get selected countries and add their currencies to the list
        if(selectedCountries != null) {
            Thread t1 = new Thread(() ->
                getCurrencyFromCountries(selectedCountries));
            t1.start();
        }
    }

    /**
     * Handles call to controller for send report (runs in separate thread)
     */
    public void createReport(Button sendReportButton, String curr1Name,
        String curr1Code, String curr2Name, String curr2Code, String curr1Val) {
        this.controller.createAndSendReport(curr1Name, curr1Code,
            curr2Name, curr2Code, curr1Val);
    }

    /**
     * Get params from fields and send to output model via controller (creates new thread)
     */
    public void sendReport(Button sendReportButton, String curr1Name,
        String curr1Code, String curr2Name, String curr2Code, String curr1Val,
        String exRateLabel) {
        if(curr1Name == null || curr1Code == null || curr2Name == null
            || curr2Code == null || curr1Val == null || exRateLabel == null) {
            Alert alertError = new Alert(Alert.AlertType.ERROR);
            alertError.setHeaderText("Calculate Exchange rate first");
            alertError.setTitle("Calculate Exchange rate first");
            alertError.getDialogPane().setStyle("-fx-base:"+this.theme);
            alertError.showAndWait();
            return;
        }
        Thread t1 = new Thread(() -> createReport(sendReportButton,
            curr1Name, curr1Code, curr2Name, curr2Code, curr1Val));
        t1.start();
    }
}
