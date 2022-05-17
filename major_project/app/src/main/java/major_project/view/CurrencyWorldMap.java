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

public class CurrencyWorldMap {
    private WorldMapView worldMap;
    private String theme;
    private final Color colour;
    private final Color oppositeColour;
    private boolean clickFlag = false;

    public CurrencyWorldMap(String theme, String userColour) {
        worldMap = new WorldMapView();
        this.theme = theme;
        this.colour = Color.web(userColour);
        this.oppositeColour = Color.web(String.format("rgb(%d,%d,%d)",
            (int)(255-this.colour.getRed()),
            (int)(255-this.colour.getGreen()),
            (int)(255-this.colour.getBlue())));
    }

    public Color getColour() {
        return this.colour;
    }

    public Color getOppColour() {
        return this.oppositeColour;
    }


    public Stage mapPopUp() {
        //Create world map and display
        worldMap.setStyle("-fx-base:"+this.theme);
        worldMap.setBackground(new Background(new BackgroundFill(
            this.colour, null, null)));
        /*String backgroundColour = "0x111111";
        if(this.theme.equals("white") ||
            this.colour.toString().compareTo("0x444444") < 0) {//This changes the background colour so that it always contrasts with the chosen colour
            backgroundColour = "0xeeeeee";
        }
        worldMap.setBackground(new Background(new BackgroundFill(
            Color.web(backgroundColour), null, null)));
        worldMap.setCountrySelectionMode​(WorldMapView.SelectionMode.MULTIPLE);
        //Following lines adapted from https://github.com/controlsfx/controlsfx/issues/1091
        worldMap.setCountryViewFactory((country) -> {
            CountryView view = new CountryView(country);
            view.fillProperty().set(this.colour);
            EventHandler<MouseEvent> mouseEvents =
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        //view.fillProperty().set(Color.WHITE);
                        if(event.getEventType() == MouseEvent.MOUSE_EXITED) {
                            if(view.getFill().equals(getOppColour())) {
                                view.fillProperty().set(getColour());
                            }
                        } else if(event.getEventType() == MouseEvent.MOUSE_ENTERED) {
                            if(view.getFill().equals(getColour())) {
                                view.fillProperty().set(getOppColour());
                            }
                        } else if(event.getEventType() == MouseEvent.MOUSE_CLICKED) {
                            if(view.getFill().equals(Color.BLACK)) {
                                view.fillProperty().set(getColour());
                            } else {
                                view.fillProperty().set(Color.BLACK);
                            }
                        }
                    }
                };
            view.setOnMouseEntered​(mouseEvents);
            view.setOnMouseExited​(mouseEvents);
            view.setOnMouseClicked(mouseEvents);

            //view.strokeWidthProperty().set(1);
            return view;
        });
        */
        BorderPane secondaryPane = new BorderPane();
        secondaryPane.setCenter(worldMap);
        Label infoLabel = new Label();
        infoLabel.setText("Press shift to select multiple countries, then close the window.");
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

    public ObservableList<WorldMapView.Country> getSelectedCountries() {
        return worldMap.getSelectedCountries();
    }

    public String getColourStr() {
        return this.colour.toString();
    }
}
