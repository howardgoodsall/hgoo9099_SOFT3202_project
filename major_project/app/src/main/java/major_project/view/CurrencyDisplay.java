package major_project.view;
import javafx.scene.control.Button;

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
