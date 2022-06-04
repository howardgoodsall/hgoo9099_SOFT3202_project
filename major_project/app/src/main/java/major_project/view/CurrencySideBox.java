package major_project.view;

import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import java.util.ArrayList;
import javafx.scene.paint.Color;
import javafx.scene.control.ColorPicker;
import javafx.event.EventHandler;

/**
 * Class for initialising the side box of the window
 */
public class CurrencySideBox {
    /**
     * initialise the side box, with world map, clear table and clear cache buttons
     */
    public static VBox sideBoxInit(CurrencyView view, Color userColour,
        String fontStyle) {
        Button worldMapButton = new Button();
        worldMapButton.setOnAction((event) -> view.mapPopUp());
        worldMapButton.setText("Open World Map");
        worldMapButton.setStyle(fontStyle);
        worldMapButton.setMinWidth(160);

        Button clearBtn = new Button("Clear Table");
        clearBtn.setMinWidth(160);
        clearBtn.setOnAction((event) -> view.clearMainTable());
        clearBtn.setStyle(fontStyle);

        Button clearCacheBtn = new Button("Clear Cache");
        clearCacheBtn.setMinWidth(160);
        clearCacheBtn.setOnAction((event) -> view.clearCache());
        clearCacheBtn.setStyle(fontStyle);

        Label colourPickerLabel = new Label("""
        World Map Background Colour:
        """);
        colourPickerLabel.setStyle(fontStyle);

        final ColorPicker colorPicker = new ColorPicker(userColour);
        colorPicker.setOnAction((event) -> view.setUserColour(colorPicker));
        colorPicker.setStyle(fontStyle);

        VBox sidePanel = new VBox();
        sidePanel.getChildren().addAll(worldMapButton, clearBtn, clearCacheBtn,
            colourPickerLabel, colorPicker);

        sidePanel.setSpacing(10);

        return sidePanel;
    }
}
