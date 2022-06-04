package major_project.view;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import org.controlsfx.control.WorldMapView;
import org.controlsfx.control.WorldMapView.CountryView;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import javafx.scene.control.ColorPicker;
import javafx.event.EventHandler;
import javafx.event.Event;
import javafx.scene.input.MouseEvent;

/**
 * Class for world map pop up
 * Split into different class from CurrencyView mainly for future extensibility
 * (Also single responsibility)
 */
public class CurrencyWorldMap {
    private WorldMapView worldMap;
    private String theme;
    private final Color colour;
    private boolean clickFlag = false;
    private String fontStyle;

    public CurrencyWorldMap(String theme, String userColour, String fontStyle) {
        worldMap = new WorldMapView();
        this.theme = theme;
        this.colour = Color.web(userColour);
        this.fontStyle = fontStyle;
    }

    /**
     * Style and produce the whole map popup
     * The code that implements colours for the countries rather than just the
     * background has been left in but commented out.
     */
    public Stage mapPopUp() {
        //Create world map and display
        worldMap.setStyle("-fx-base:"+this.theme);
        worldMap.setBackground(new Background(new BackgroundFill(
            this.colour, null, null)));
        BorderPane secondaryPane = new BorderPane();
        secondaryPane.setCenter(worldMap);
        Label infoLabel = new Label();
        infoLabel.setText("Press shift to select multiple countries, then close the window.");
        infoLabel.setStyle(fontStyle);
        secondaryPane.setBottom(infoLabel);
        secondaryPane.setStyle("-fx-base:"+this.theme);
        Scene secondaryScene = new Scene(secondaryPane);
        Stage secondaryStage = new Stage();
        secondaryStage.setWidth(1000);
        secondaryStage.setHeight(600);
        secondaryStage.setScene(secondaryScene);
        secondaryStage.setTitle("Currency World Map");
        return secondaryStage;
    }

    /**
     * Return selected countries (called after window close)
     */
    public ObservableList<WorldMapView.Country> getSelectedCountries() {
        return worldMap.getSelectedCountries();
    }
}
