package major_project.view;

import major_project.model.CurrencyModel;
import major_project.model.CurrencyOutput;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToggleButton;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import java.util.Optional;
import java.util.ArrayList;
import javafx.geometry.Insets;
import org.controlsfx.control.WorldMapView;

/**
 * View handler class
 */
public class CurrencyView {
    private final CurrencyModel model;
    private final CurrencyOutput outputModel;
    private final Scene scene;
    private final BorderPane pane;
    private TableView mainTable;
    private final String fontStyle = "-fx-font: 16 arial;";
    private String theme = "white";

    public CurrencyView(CurrencyModel model, CurrencyOutput outputModel) {
        this.model = model;
        this.outputModel = outputModel;
        this.pane = new BorderPane();
        this.scene = new Scene(this.pane);
        this.scene.getRoot().setStyle("-fx-base:"+this.theme);//"-fx-base:black"
        initialise();
    }

    /**
     * Set up layout and buttons
     */
    public void initialise() {
        initCurrencyList();
        this.pane.setCenter(this.mainTable);
        this.pane.setPadding(new Insets(20, 20, 20, 20));
        this.pane.setRight(sideBoxInit());
        HBox conversionBox = conversionBoxInit();
        this.pane.setBottom(conversionBox);
        this.pane.setAlignment(conversionBox, Pos.CENTER);
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
        Label aboutLabelBottom = new Label("References: None");
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

    public void changeThemeButton() {
        if(this.theme.equals("black")) {
            this.theme = "white";
        } else {
            this.theme = "black";
        }
        this.scene.getRoot().setStyle(("-fx-base:"+this.theme));
    }

    public Scene getScene() {
        return this.scene;
    }

    /**
     * Set up main table
     */
    public void initCurrencyList() {
        //Create the main table
        this.mainTable = new TableView<TableColumn>();
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
    }


    public VBox sideBoxInit() {
        Button worldMapButton = new Button();
        worldMapButton.setOnAction((event) -> mapPopUp());
        worldMapButton.setText("Open World Map");
        worldMapButton.setStyle(fontStyle);
        worldMapButton.setMinWidth(160);

        Button clearBtn = new Button("Clear");
        clearBtn.setMinWidth(160);
        clearBtn.setOnAction((event) -> clearMainTable());
        clearBtn.setStyle(fontStyle);

        Button clearCacheBtn = new Button("Clear Cache");
        clearCacheBtn.setMinWidth(160);
        clearCacheBtn.setOnAction((event) -> clearCache());
        clearCacheBtn.setStyle(fontStyle);

        VBox sidePanel = new VBox();
        sidePanel.getChildren().addAll(worldMapButton, clearBtn, clearCacheBtn);
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
     * Get params and perform conversion
     */
    public void doConversion(String fromCurrCode, String toCurrCode,
        String amount, Label toLabel, Label exRate) {
        if(fromCurrCode == null || toCurrCode == null || amount == null) {
            Alert alertError = new Alert(Alert.AlertType.ERROR);
            alertError.setHeaderText("Not enough inputs given");
            alertError.setTitle("Not enough inputs given");
            alertError.showAndWait();
            return;
        }
        String value = model.currConversion(fromCurrCode, toCurrCode, amount);
        toLabel.setText(String.format(" ->   %s %s ",
        value, toCurrCode));
        String exRateResult = model.getExchangeRateCache(fromCurrCode, toCurrCode);
        boolean update = false;
        if(exRateResult != null) {
            Alert alertRefresh = new Alert(Alert.AlertType.CONFIRMATION);
            alertRefresh.setHeaderText("Cache found for this exchange rate. Refresh exchange rate?");
            alertRefresh.setTitle("Cache Hit");
            Optional<ButtonType> result = alertRefresh.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK){
                exRateResult = null;
                update = true;
            }
        }
        if(exRateResult == null) {
            exRateResult = model.getExchangeRate(fromCurrCode, toCurrCode);
            if(update) {
                this.model.updateRate(Double.parseDouble(exRateResult),
                    fromCurrCode, toCurrCode);
            }
        }
        exRate.setText(String.format(" Rate: %s ", exRateResult ));
    }

    /**
     * Remove item from main table
     */
    public void removeItem(String currencyName) {
        ObservableList<CurrencyDisplay> rows = this.mainTable.getItems();
        for(int i=0; i<rows.size(); i++) {
            if(rows.get(i).getCurrencyCode().equals(currencyName)) {
                this.mainTable.getItems().remove(rows.get(i));
                return;
            }
        }
    }

    /**
     * After selecting countries on the world map, add their currencies to
     * main table
     */
    public void getCurrencyFromCountries(ObservableList<WorldMapView.Country>
        selectedCountries) {
        //Check if each country has supported currencies and add them to the list
        for(int i=0; i<selectedCountries.size(); i++) {
            ArrayList<String[]> allCurrenciesForCountry =
                model.supportedCurrencies(selectedCountries.get(i)
                .getLocale().getDisplayCountry());
            if(allCurrenciesForCountry != null) {
                for(int j=0; j<allCurrenciesForCountry.size(); j++) {
                    boolean duplicate = false;
                    String[] currData = allCurrenciesForCountry.get(j);
                    for(int k=0; k<this.mainTable.getItems().size(); k++) {//Check for duplicates
                        if(currData[1].equals(((CurrencyDisplay)(this.mainTable.getItems().get(k))
                            ).getName())) {
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
                    }
                }
            }  else {
                Alert alertError = new Alert(Alert.AlertType.ERROR);
                alertError.setHeaderText(String.format("No supported currencies found for %s"
                    ,selectedCountries.get(i).getLocale().getDisplayCountry()));
                alertError.setTitle("No currencies found");
                alertError.showAndWait();
            }
        }
    }

    /**
     * Handle world map pop up
     */
    public void mapPopUp() {
        //Create world map and display
        WorldMapView worldMap = new WorldMapView();
        worldMap.setCountrySelectionMode​(WorldMapView.SelectionMode.MULTIPLE);
        BorderPane secondaryPane = new BorderPane();
        secondaryPane.setCenter(worldMap);
        Label infoLabel = new Label();
        infoLabel.setText("Press shift to select multiple countries, then close the window.");
        secondaryPane.setBottom(infoLabel);
        Scene secondaryScene = new Scene(secondaryPane);
        Stage secondaryStage = new Stage();
        secondaryStage.setWidth(1000);
        secondaryStage.setHeight(600);
        secondaryStage.setScene(secondaryScene);
        secondaryStage.setTitle("Currency World Map");
        secondaryStage.showAndWait();
        ObservableList<WorldMapView.Country> selectedCountries = worldMap
            .getSelectedCountries();
        //Get selected countries and add their currencies to the list
        if(selectedCountries != null) {
            getCurrencyFromCountries(selectedCountries);
        }
    }

    /**
     * Get params from fields and send to output model
     */
    public void sendReport(Button sendReportButton, String curr1Name,
        String curr1Code, String curr2Name, String curr2Code, String curr1Val,
        String exRateLabel) {
        if(curr1Name == null || curr1Code == null || curr2Name == null
            || curr2Code == null || curr1Val == null || exRateLabel == null) {
            Alert alertError = new Alert(Alert.AlertType.ERROR);
            alertError.setHeaderText("Calculate Exchange rate first");
            alertError.setTitle("Calculate Exchange rate first");
            alertError.showAndWait();
            return;
        }
        sendReportButton.setText("Sending ...");
        String curr2Val = model.currConversion(curr1Code, curr2Code, curr1Val);
        String exRate = model.getExchangeRate(curr1Code, curr2Code);
        String report = outputModel.createReport(curr1Name, curr1Code, curr2Name
            , curr2Code, exRate, curr1Val, curr2Val);
        boolean result = outputModel.sendReport(report);
        if(result) {
            sendReportButton.setText("Send Report");
        } else {
            sendReportButton.setText("Failed to Send");
        }
    }
}
