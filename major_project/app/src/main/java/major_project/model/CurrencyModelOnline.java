package major_project.model;

import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONObject;
import org.json.JSONArray;
import javafx.scene.control.*;

/**
 * Online model for input API
 */
public class CurrencyModelOnline implements CurrencyModel {
    private ArrayList<String> viewingCurrencies;
    private ArrayList<CurrencyData> supportCurrencies;
    private String apiKey;
    private APICaller apiComm;

    public CurrencyModelOnline(String apiKey, APICaller apiComm) {
        this.apiKey = apiKey;
        this.apiComm = apiComm;
    }

    /**
     * Get list of supported currencies
     */
    public void getSupportedCurrencies() {
        this.supportCurrencies = new ArrayList<CurrencyData>();
        String uri = String.format(
            "https://api.currencyscoop.com/v1/currencies?type=fiat&api_key=%s"
            ,apiKey);
        String response = this.apiComm.apiCommGET(uri);
        JSONObject jsonObj = new JSONObject(response);
        try {
            JSONObject jsonObjData = (JSONObject)((JSONObject)jsonObj
                .get("response")).get("fiats");
            for(int i=0; i<jsonObjData.names().length(); i++){
                CurrencyData currencyData = new Gson().fromJson(jsonObjData.get(
                    jsonObjData.names().getString(i)).toString(), CurrencyData.class);
                if(!currencyData.getCurrencyName().contains("(funds code)")) {
                    supportCurrencies.add(currencyData);
                }
            }
        } catch(Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("API Error");
            alert.setHeaderText("An error occured, (check that INPUT_API_KEY is set correctly)");
            alert.showAndWait();
            return;
        }
    }

    /**
     * Get the support currencies for a country, calls getSupportedCurrencies
     */
    public ArrayList<String[]> supportedCurrencies(String country) {
        if(this.supportCurrencies == null) {
            getSupportedCurrencies();
        }
        ArrayList<String[]> currOfSelectedCountry = new ArrayList<String[]>();
        for(int i=0; i<this.supportCurrencies.size(); i++) {
            for(int j=0; j<this.supportCurrencies.get(i).getCountries().length;
                j++) {
                if(this.supportCurrencies.get(i).getCountries()[j]
                    .equals(country)) {
                    String[] currencyDataPair = {this.supportCurrencies.get(i)
                            .getCurrencyCode(), this.supportCurrencies.get(i)
                            .getCurrencyName()};
                    currOfSelectedCountry.add(currencyDataPair);
                }
            }
        }
        if(currOfSelectedCountry.size() != 0) {
            return currOfSelectedCountry;
        } else {
            return null;
        }
    }

    /**
     * Handle currency conversion call to API
     */
    public String currConversion(String fromCurrCode, String toCurrCode,
        String amount){
            String uri = String.format(
            "https://api.currencyscoop.com/v1/convert?api_key=%s&from=%s&to=%s&amount=%s"
            ,apiKey, fromCurrCode, toCurrCode, amount);
            JSONObject jsonObj = new JSONObject(this.apiComm.apiCommGET(uri));
            String result = String.format("%.03f",
                ((JSONObject)jsonObj.get("response"))
                .getDouble("value"));
            return result;
        }

    /**
     * Calculate exchange rate
     */
    public String calcConversionRate(String inp, String out) {
        try {
            if(inp == null || inp.equals("")) {
                return "Incorrect Formatting";
            } else if (out == null || out.equals("")) {
                return "Incorrect Formatting";
            }
            double inputVal = Double.parseDouble(inp);
            double outputVal = Double.parseDouble(out);
            double exchangeRate = inputVal / outputVal;
            String result = String.format("%.03f", exchangeRate);
            return result;
        } catch (NumberFormatException e) {
            return "Incorrect Formatting";
        }
    }

    /**
     * Get exchange rate between selected currencies
     */
    public String getExchangeRate(String fromCurrCode, String toCurrCode) {
        String uri = String.format(
        "https://api.currencyscoop.com/v1/latest?api_key=%s&base=%s&symbols=%s"
        ,apiKey, fromCurrCode, toCurrCode);
        JSONObject jsonObj = new JSONObject(this.apiComm.apiCommGET(uri));
        String result = String.format("%.03f",
            ((JSONObject)(((JSONObject)jsonObj.get("response"))
            .get("rates"))).getDouble(toCurrCode));
        return result;
    }
}
