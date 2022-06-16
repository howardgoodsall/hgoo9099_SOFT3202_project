package major_project.view;
import javafx.scene.control.Button;

/**
 * Essentially a POJO class for creating rows in the table.
 * Includes the button to remove row and button for setting this row as a
 * special deal currency.
 */
public class CurrencyDisplay {
    private String currencyCode;
    private String name;
    private Button specialBtn;
    private Button removeBtn;

    public CurrencyDisplay(String code, String name, Button specialBtn,
        Button removeBtn) {
        this.currencyCode = code;
        this.name = name;
        this.specialBtn = specialBtn;
        this.removeBtn = removeBtn;
    }

    public String getCurrencyCode() {
        return this.currencyCode;
    }

    public String getName() {
        return this.name;
    }

    public Button getSpecialBtn() {
        return this.specialBtn;
    }

    public Button getRemoveBtn() {
        return this.removeBtn;
    }
}
