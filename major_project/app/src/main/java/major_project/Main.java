//INFO
//SID: 490477658
//Input: https://currencyscoop.com/api-documentation
//Output: https://www.twilio.com/
package major_project;

import major_project.model.*;
import major_project.view.CurrencyView;
import javafx.application.Application;
import javafx.stage.Stage;
import java.util.List;

public class Main extends Application {
    private CurrencyModel model;
    private CurrencyView view;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        List<String> params = getParameters().getRaw();
        if (params.size() != 2) {
            System.out.println("Usage: gradle run --args=<on/off>line <on/off>line");
            System.exit(0);
        }
        if (params.get(0).equalsIgnoreCase("online")) {
            model = new CurrencyModelOnline();
        } else if (params.get(0).equalsIgnoreCase("offline")) {
            model = new CurrencyModelOffline();
        } else {
            System.out.println("Usage: gradle run --args=<on/off>line <on/off>line");
            System.exit(0);
        }
        view = new CurrencyView(model);
        primaryStage.setWidth(1000);
        primaryStage.setHeight(650);
        primaryStage.setScene(view.getScene());
        primaryStage.setTitle("Currency Scoop");
        primaryStage.show();
    }
}
