package major_project.view;

import major_project.model.CurrencyModel;
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
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import java.util.Optional;
import java.util.ArrayList;
import javafx.geometry.Insets;
import org.controlsfx.control.WorldMapView;

public class CurrencyView {
    private final CurrencyModel model;
    private final Scene scene;
    private final BorderPane pane;
    private TableView mainTable;
    //private ArrayList<String> currencyCodes;

    public CurrencyView(CurrencyModel model) {
        this.model = model;
        this.pane = new BorderPane();
        this.scene = new Scene(this.pane);
        //this.currencyCodes =

        initialise();
    }

    public void initialise() {
        initCurrencyList();
        this.pane.setCenter(this.mainTable);
        Button worldMapButton = new Button();
        worldMapButton.setOnAction((event) -> mapPopUp());
        worldMapButton.setText("Open World Map");
        this.pane.setTop(worldMapButton);
        this.pane.setAlignment(worldMapButton, Pos.CENTER);
        this.pane.setPadding(new Insets(20, 20, 20, 20));
        Button clearBtn = new Button("Clear");
        clearBtn.setOnAction((event) -> clearMainTable());
        this.pane.setRight(clearBtn);
        HBox conversionBox = conversionBoxInit();
        this.pane.setBottom(conversionBox);
        this.pane.setAlignment(conversionBox, Pos.CENTER);
    }

    public Scene getScene() {
        return this.scene;
    }

    public void initCurrencyList() {
        //Create the main table
        ArrayList<String> viewingCurrencies = model.getViewingCurrencies();
        this.mainTable = new TableView();
        TableColumn currencyCodeCol = new TableColumn("Currency Code");
        currencyCodeCol.setCellValueFactory(new PropertyValueFactory<>(
            "currencyCode"));
        currencyCodeCol.setMinWidth(400);
        TableColumn nameCol = new TableColumn("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setMinWidth(400);
        TableColumn removeButtonCol = new TableColumn("");
        removeButtonCol.setMinWidth(110);
        removeButtonCol.setCellValueFactory(new PropertyValueFactory<>(
            "removeBtn"));
        this.mainTable.getColumns().addAll(currencyCodeCol, nameCol,
            removeButtonCol);
    }

    public void clearMainTable() {
        this.mainTable.getItems().clear();
    }

    public HBox conversionBoxInit() {
        HBox hbox = new HBox();
        ComboBox<String> fromDropDown = new ComboBox<String>();
        ComboBox<String> toDropDown = new ComboBox<String>();
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
        Label middleLabel = new Label();
        middleLabel.setText(" -> ");
        Label toLabel = new Label();
        toLabel.setText("??");

        Button calculateBtn = new Button("Calculate");

        hbox.getChildren().addAll(fromDropDown, fromTextField, middleLabel,
            toDropDown, toLabel, calculateBtn);
        return hbox;
    }

    public int doConversion(String fromCurrCode, int amount, String toCurrCode) {
        return 0;
    }

    public void removeItem(String currencyName) {
        ObservableList<CurrencyDisplay> rows = this.mainTable.getItems();
        for(int i=0; i<rows.size(); i++) {
            if(rows.get(i).getCurrencyCode().equals(currencyName)) {
                this.mainTable.getItems().remove(rows.get(i));
                return;
            }
        }
    }

    public void getCurrencyFromCountries(ObservableList<WorldMapView.Country>
        selectedCountries) {
        //Check if each country has supported currencies and add them to the list
        for(int i=0; i<selectedCountries.size(); i++) {
            String[] currData = model.supportedCurrencies(selectedCountries.get(i)
                .getLocale().getDisplayCountry());
            if(currData != null) {
                Button removeBtn = new Button();
                removeBtn.setText("X");
                removeBtn.setOnAction((event) -> removeItem(currData[0]));
                CurrencyDisplay newRow = new CurrencyDisplay(currData[0],
                    currData[1], removeBtn);
                this.mainTable.getItems().add(newRow);
            } else {
                Alert alertError = new Alert(Alert.AlertType.ERROR);
                alertError.setHeaderText(String.format("No supported currencies found for %s"
                    ,selectedCountries.get(i).getLocale().getDisplayCountry()));
                alertError.setTitle("No currencies found");
                alertError.showAndWait();
            }
        }
    }

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
}
