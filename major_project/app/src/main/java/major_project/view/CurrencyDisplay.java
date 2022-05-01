package major_project.view;
import javafx.scene.control.Button;

/**
 * Essentially a POJO class for creating rows in the table
 * Includes the button to remove row
 */
public class CurrencyDisplay {
    private String currencyCode;
    private String name;
    private Button removeBtn;

    public CurrencyDisplay(String code, String name, Button removeBtn) {
        this.currencyCode = code;
        this.name = name;
        this.removeBtn = removeBtn;
    }

    public String getCurrencyCode() {
        return this.currencyCode;
    }

    public String getName() {
        return this.name;
    }

    public Button getRemoveBtn() {
        return this.removeBtn;
    }
}
