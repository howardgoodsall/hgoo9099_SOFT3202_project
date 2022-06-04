package major_project.view;

import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;
import javafx.scene.control.ColorPicker;
import javafx.event.EventHandler;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;

public class CurrencyConversionBox {
    private CurrencyView view;

    public CurrencyConversionBox(CurrencyView view) {
        this.view = view;
    }

    /**
     * retrieve currency name from currency Code
     * Searchs from data in the main table so no model-view leak
     */
    public String getCurrNameFromCode(String currCode) {
        ObservableList<CurrencyDisplay> rows = view.mainTable.getItems();
        for(int i=0; i<rows.size(); i++) {
            if(rows.get(i).getCurrencyCode().equals(currCode)) {
                return rows.get(i).getName();
            }
        }
        return null;
    }

    /**
     * initialise the conversion field at the bottom of the pane
     */
    public HBox conversionBoxInit(String fontStyle) {
        HBox hbox = new HBox();
        ComboBox<String> fromDropDown = new ComboBox<String>();
        fromDropDown.setStyle(fontStyle);
        ComboBox<String> toDropDown = new ComboBox<String>();
        toDropDown.setStyle(fontStyle);
        ObservableList<CurrencyDisplay> rows = view.mainTable.getItems();
        view.mainTable.getItems().addListener(new ListChangeListener<Object>() {
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
        calculateBtn.setOnAction((event) -> view.doConversion(
            (String)fromDropDown.getValue(), (String)toDropDown.getValue(),
            fromTextField.getText(), toLabel, exRate));

        Button sendReportButton = new Button("Send Report");
        sendReportButton.setStyle(fontStyle);
        sendReportButton.setOnAction((event) -> view.sendReport(sendReportButton,
            getCurrNameFromCode((String)fromDropDown.getValue()),
            (String)fromDropDown.getValue(),
            getCurrNameFromCode((String)toDropDown.getValue()),
            (String)toDropDown.getValue(),
            fromTextField.getText(), exRate.getText()));

        hbox.getChildren().addAll(fromLabel, fromDropDown, fromTextField,
            middleLabel, toDropDown, toLabel, exRate, calculateBtn,
            sendReportButton);
        hbox.setSpacing(10);
        return hbox;
    }
}
