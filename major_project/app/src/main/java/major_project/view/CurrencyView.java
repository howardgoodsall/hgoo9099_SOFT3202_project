package major_project.view;

import major_project.model.CurrencyModel;
import major_project.model.CurrencyOutput;

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
    //public final CurrencyController controller;
    private final Scene scene;
    private final BorderPane pane;
    public TableView mainTable;
    private final String fontStyle =
        "-fx-font: 16 arial; -fx-background-radius: 15px;";
    private String theme = "white";
    private boolean loggedIn = false;
    private String user = null;
    private String userColour = "0x111111";//Default colour
    public final CurrencyModel model;
    private final CurrencyOutput outputModel;

    public CurrencyView(CurrencyModel model, CurrencyOutput outputModel) {
        this.model = model;
        this.outputModel = outputModel;
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
     * Set values after login
     */
    public void setLogin(String username, String theme) {
        this.theme = theme;
        this.loggedIn = true;
        this.user = username;
    }

    /**
     * Display login screen before main screen
     */
    public void loginScreen() {
        CurrencyLogin login = new CurrencyLogin(this, fontStyle);
        login.loginScreen();
    }
    /**
     * Set up layout and buttons
     */
    public void initialise() {
        this.mainTable = new TableView<TableColumn>();
        Insets spacer = new Insets(10, 10, 10, 20);
        HBox conversionBox = conversionBoxInit();
        this.pane.setBottom(conversionBox);
        this.pane.setAlignment(conversionBox, Pos.CENTER);
        this.pane.setMargin(conversionBox, spacer);
        initCurrencyList();
        this.pane.setCenter(this.mainTable);
        this.pane.setPadding(spacer);
        VBox sideBox = sideBoxInit();
        this.pane.setRight(sideBox);
        this.pane.setMargin(sideBox, spacer);
        Menu m = new Menu("Menu");
        MenuItem m1 = new MenuItem("About");
        m1.setOnAction((event) -> aboutButton());
        m.getItems().add(m1);
        MenuBar mb = new MenuBar();
        mb.getMenus().add(m);
        HBox topPanel = new HBox();
        ToggleButton themebtn = new ToggleButton("Change theme");
        themebtn.setStyle(fontStyle);
        themebtn.setOnAction((event) -> changeThemeButton());
        ToggleGroup group = new ToggleGroup();
        themebtn.setToggleGroup(group);
        topPanel.getChildren().addAll(mb, themebtn);
        topPanel.setSpacing(10);
        this.pane.setTop(topPanel);
        this.pane.setMargin(topPanel, spacer);
    }

    /**
     * About button pop up
     */
    public void aboutButton() {
        Label aboutLabelTop = new Label("Program name: Currency Conversion App");
        Label aboutLabelMiddle = new Label("Student name: Howard Goodsall");
        Label aboutLabelBottom = new Label(
        """
        References:
            https://stackoverflow.com/questions/13784333/platform-runlater-and-task-in-javafx
        """);
        BorderPane popUpPane = new BorderPane();
        popUpPane.setTop(aboutLabelTop);
        popUpPane.setLeft(aboutLabelMiddle);
        popUpPane.setBottom(aboutLabelBottom);
        popUpPane.setPadding(new Insets(20, 20, 20, 20));
        Scene secondaryScene = new Scene(popUpPane);
        secondaryScene.getRoot().setStyle(("-fx-base:"+this.theme));
        Stage secondaryStage = new Stage();
        secondaryStage.setWidth(650);
        secondaryStage.setHeight(200);
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
        this.model.updateTheme(this.theme, this.user);
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
        this.userColour = this.model.getUserColour(this.user);
        //Populate table with data from database
        ArrayList<String[]> userViewList =
            this.model.getViewingCurrencies(this.user);
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

    /**
     * Set the user's colour preference for map background
     */
    public void setUserColour(ColorPicker colorPicker) {
        this.userColour = colorPicker.getValue().toString();
        this.model.updateColour(this.userColour, this.user);
    }

    /**
     * initialise the side box, with world map, clear table and clear cache tables
     */
    public VBox sideBoxInit() {
        return CurrencySideBox.sideBoxInit(this, Color.web(this.userColour),
            fontStyle);
    }

    /**
     * initialise the conversion field at the bottom of the pane
     */
    public HBox conversionBoxInit() {
        CurrencyConversionBox convBox = new CurrencyConversionBox(this);
        return convBox.conversionBoxInit(fontStyle);
    }

    /**
     * Button function to clear main table
     */
    public void clearMainTable() {
        this.mainTable.getItems().clear();
        this.model.clearViewingTable(this.user);
    }

    /**
     * Button function to clear database cache
     */
    public void clearCache() {
        this.model.clearCache();
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
     * Call api and update thread (runs in separate thread)
     */
    public void updateToLabel(String fromCurrCode, String toCurrCode,
        String amount, Label toLabel) {
        String value = this.model.currConversion(fromCurrCode, toCurrCode,
            amount);

        //Following has been adapted from https://stackoverflow.com/questions/13784333/platform-runlater-and-task-in-javafx
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                toLabel.setText(String.format(" ->   %s %s ",
                value, toCurrCode));
            }
        });
    }

    /**
     * Update the label for the exchange rate (runs in separate thread)
     */
    public void updateExRateLabel(String fromCurrCode, String toCurrCode,
        String amount, Label exRate, String exRateResult) {
        String rate = exRateResult;
        if(exRateResult == null) {
            rate = this.model.getExchangeRate(fromCurrCode,
                toCurrCode);
            this.model.updateRate(Double.parseDouble(rate),
                fromCurrCode, toCurrCode);
        }
        final String newLabel = String.format(" Rate: %s ", rate);
        //Following has been adapted from https://stackoverflow.com/questions/13784333/platform-runlater-and-task-in-javafx
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                exRate.setText(newLabel);
            }
        });
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

        Thread t1 = new Thread(() -> updateToLabel(fromCurrCode, toCurrCode,
            amount, toLabel));
        t1.start();

        String exRateResult = this.model.getExchangeRateCache(fromCurrCode,
            toCurrCode);
        if(exRateResult != null) {
            ButtonType cacheFetchBtn =
                new ButtonType("Use local data from cache",
                ButtonBar.ButtonData.CANCEL_CLOSE);
            ButtonType apiFetchBtn =
                new ButtonType("Fetch new data from CurrencyScoop",
                ButtonBar.ButtonData.OK_DONE);
            Alert alertRefresh = new Alert(Alert.AlertType.WARNING,
                "",
                apiFetchBtn, cacheFetchBtn);
            alertRefresh.setHeaderText("Cache found for this exchange rate. Refresh exchange rate?");
            alertRefresh.setTitle("Cache Hit");
            alertRefresh.getDialogPane().setStyle("-fx-base:"+this.theme);
            Optional<ButtonType> result = alertRefresh.showAndWait();
            if (result.isPresent() && result.get() == apiFetchBtn){
                exRateResult = null;
            } else {
                String newLabel = String.format(" Rate: %s ", exRateResult);
                exRate.setText(newLabel);
            }

        }
        final String rateHolder = exRateResult;
        if(exRateResult == null) {
            Thread t2 = new Thread(() -> updateExRateLabel(fromCurrCode, toCurrCode,
                amount, exRate, rateHolder));
            t2.start();
        }
    }

    /**
     * Remove item from main table
     */
    public void removeItem(String currencyName) {
        ObservableList<CurrencyDisplay> rows = this.mainTable.getItems();
        for(int i=0; i<rows.size(); i++) {
            if(rows.get(i).getCurrencyCode().equals(currencyName)) {
                this.model.removeViewCurrency(rows.get(i).getCurrencyCode(),
                    this.user);
                this.mainTable.getItems().remove(rows.get(i));
                return;
            }
        }
    }

    /**
     * Add row to mainTable
     */
    public void addRowToMainTable(CurrencyDisplay newRow) {
        this.mainTable.getItems().add(newRow);
    }

    /**
     * Display error for no currencies found
     */
    public void noSupportedCurrencies(String countryName) {
        Alert alertError = new Alert(Alert.AlertType.ERROR);
        alertError.setHeaderText(String.format("No supported currencies found for %s"
            ,countryName));
        alertError.setTitle("No currencies found");
        alertError.getDialogPane().setStyle("-fx-base:"+this.theme);
        alertError.showAndWait();
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
                this.model.supportedCurrencies(selectedCountries.get(i)
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
                        Platform.runLater(() -> addRowToMainTable(newRow));
                        this.model.insertViewCurrency(
                            newRow.getCurrencyCode(), newRow.getName(),
                            this.user);
                    }
                }
            }  else {
                String countryName = selectedCountries.get(i).getLocale()
                    .getDisplayCountry();
                Platform.runLater(() -> noSupportedCurrencies(countryName));
            }
        }
    }

    /**
     * Handle world map pop up
     */
    public void mapPopUp() {
        CurrencyWorldMap worldMap = new CurrencyWorldMap(theme, this.userColour,
            this.fontStyle);
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
        String report = this.outputModel.createReport(
            curr1Name, curr1Code, curr2Name, curr2Code,
            this.model.getExchangeRate(curr1Code, curr2Code), curr1Val,
            this.model.currConversion(curr1Code, curr2Code, curr1Val));
        this.outputModel.sendReport(report);
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
