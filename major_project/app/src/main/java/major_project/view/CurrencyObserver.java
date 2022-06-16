package major_project.view;
import javafx.scene.control.*;
import javafx.collections.ObservableList;

/**
 * Observer class for extension,
 * updates the button text and holds the modifying amount (1.1)
 */
public class CurrencyObserver {
    private TableView mainTable;
    private String specialCurr;
    private final Double modifyingAmount = 1.1;

    public CurrencyObserver(TableView mainTable) {
        this.mainTable = mainTable;
        this.specialCurr = null;
    }

    /**
     * Search the mainTable and set the ticks so that only one currency can be
     * the special deal currency. In offline mode, there is only 1 currency,
     * so all of them get selected.
     */
    public void setSpecialButton() {
        ObservableList<CurrencyDisplay> rows = this.mainTable.getItems();
        for(int i=0; i<rows.size(); i++) {
            if(rows.get(i).getCurrencyCode().equals(specialCurr)) {
                rows.get(i).getSpecialBtn().setText("✓");
            } else {
                rows.get(i).getSpecialBtn().setText("  ");
            }
        }
    }

    /**
     * Set the special deal currency
     */
    public void setSpecial(String currCode) {
        if(specialCurr == null) {
            specialCurr = currCode;
        } else if(specialCurr.equals(currCode)) {
            specialCurr = null;
        } else {
            specialCurr = currCode;
        }
        setSpecialButton();
    }

    /**
     * Check if the currCode is the special deal currency and return the
     * amount to multiply the rate by (1.0 if it isn't)
     */
    public double checkSpecial(String currCode) {
        if(specialCurr == null) {
            return 1.0;
        } else if(specialCurr.equals(currCode)) {
            return modifyingAmount;
        }
        return 1.0;
    }
}
