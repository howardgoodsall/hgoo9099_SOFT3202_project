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

/**
 * Online model for input API
 */
public class CurrencyModelOnline implements CurrencyModel {
    private ArrayList<String> viewingCurrencies;
    private ArrayList<CurrencyData> supportCurrencies;
    private String apiKey;
    private APICaller apiComm;
    private CurrencyDataStore database;

    public CurrencyModelOnline(String apiKey, APICaller apiComm,
        CurrencyDataStore database) {
        this.apiKey = apiKey;
        this.apiComm = apiComm;
        this.database = database;
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
        if(response == null){//If the internet/api call doesn't work
            return;
        }
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
            System.out.println("An error occured, (check that INPUT_API_KEY is set correctly)");
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


    public String getExchangeRateCache(String fromCurrCode, String toCurrCode) {
        Double cacheResult = this.database.getCacheRate(fromCurrCode, toCurrCode);
        if(cacheResult != null) {
            String resultCache = String.format("%.03f", cacheResult);
            return resultCache;
        }
        return null;
    }

    public void updateRate(double newRate, String from_curr_code,
        String to_curr_code) {
        this.database.updateRate(newRate, from_curr_code, to_curr_code);
    }

    public void clearCache() {
        this.database.dropRatesTable();
    }

    /**
     * Calculate exchange rate
     */
    public String calcExchangeRate(String inp, String out) {
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

    public boolean signUp(String username, String pwdHash) {
        if(this.database.searchUsers(username)) {
            return false;
        } else {
            this.database.insertUser(username, pwdHash);
            return true;
        }
    }

    public String login(String username, String pwdHash) {
        return this.database.login(username, pwdHash);
    }

    public void updateTheme(String theme, String username) {
        this.database.updateTheme(theme, username);
    }

    public void insertViewCurrency(String currCode, String currName,
        String username) {
        ArrayList<String[]> viewing_currs =
            this.database.getViewingCurrencies(username);
        for(int i=0; i<viewing_currs.size(); i++) {
            if(viewing_currs.get(i)[0].equals(currCode)) {
                return;
            }
        }
        this.database.insertViewCurrency(currCode, currName, username);
    }

    public void removeViewCurrency(String currCode, String username) {
        this.database.deleteViewCurrency(currCode, username);
    }

    public ArrayList<String[]> getViewingCurrencies(String username) {
        return this.database.getViewingCurrencies(username);
    }
}
