/**INFO
 * SID: 490477658
 * Input: https://currencyscoop.com/api-documentation
 * Output: https://www.twilio.com/
 * Level of completion: Distinction
 * Optional features:
 * - About (C)
 * - Light/Dark mode (C)
 * - Personalisation and user accounts (D)
 */
package major_project;

import major_project.model.*;
import major_project.view.CurrencyView;
import javafx.application.Application;
import javafx.stage.Stage;
import java.util.List;

public class Main extends Application {
    private CurrencyModel model;
    private CurrencyOutput outputModel;
    private CurrencyView view;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        CurrencyDataStore database = new CurrencyDataStore();
        List<String> params = getParameters().getRaw();
        if (params.size() != 2) {
            System.out.println("Usage: gradle run --args=<on/off>line <on/off>line");
            System.exit(0);
        }
        if (params.get(0).equalsIgnoreCase("online")) {//Params for input model
            model = new CurrencyModelOnline(System.getenv("INPUT_API_KEY"),
                new APICaller(), database);
        } else if (params.get(0).equalsIgnoreCase("offline")) {
            model = new CurrencyModelOffline();
        } else {
            System.out.println("Usage: gradle run --args=<on/off>line <on/off>line");
            System.exit(0);
        }
        if (params.get(1).equalsIgnoreCase("online")) {//Params for output model
            outputModel = new CurrencyOutputOnline(
                System.getenv("TWILIO_API_KEY"),
                System.getenv("TWILIO_API_SID"),
                System.getenv("TWILIO_API_FROM"),
                System.getenv("TWILIO_API_TO"),
                new APICaller());
        } else if (params.get(1).equalsIgnoreCase("offline")) {
            outputModel = new CurrencyOutputOffline();
        } else {
            System.out.println("Usage: gradle run --args=<on/off>line <on/off>line");
            System.exit(0);
        }
        view = new CurrencyView(model, outputModel);

        primaryStage.setWidth(1300);
        primaryStage.setHeight(650);
        primaryStage.setScene(view.getScene());
        primaryStage.setTitle("Currency Conversion App");
        primaryStage.show();

    }
}
