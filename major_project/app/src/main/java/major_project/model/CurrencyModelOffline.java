package major_project.model;

import java.util.ArrayList;

/**
 * Offline model for input API
 */
public class CurrencyModelOffline implements CurrencyModel {
    public CurrencyModelOffline() {
        return;
    }

    public void getSupportedCurrencies() {
        return;
    }

    public ArrayList<String[]> supportedCurrencies(String country) {
        String[] curr = {"$$$",country};
        ArrayList<String[]> currencies = new ArrayList<String[]>();
        currencies.add(curr);
        return currencies;
    }

    public String currConversion(String fromCurrCode, String toCurrCode,
        String amount){
            return amount;
    }

    public void updateRate(double newRate, String from_curr_code,
        String to_curr_code) {
        return;
    }

    public void clearCache() {
        return;
    }

    public String getExchangeRateCache(String fromCurrCode, String toCurrCode) {
        return null;
    }

    public String calcExchangeRate(String inp, String out) {
        return "1.000";
    }

    public String getExchangeRate(String fromCurrCode, String toCurrCode) {
        return "1.000";
    }

    public boolean signUp(String username, String pwdHash) {
        return true;
    }

    public String login(String username, String pwdHash) {
        return "white";
    }

    public String getUserColour(String username) {
        return "0x888888";
    }

    public void updateColour(String colour, String username) {
        return;
    }

    public void updateTheme(String theme, String username) {
        return;
    }

    public void insertViewCurrency(String currCode, String currName,
        String username) {
        return;
    }

    public void removeViewCurrency(String currCode, String username) {
        return;
    }

    public void clearViewingTable(String username) {
        return;
    }

    public ArrayList<String[]> getViewingCurrencies(String username) {
        return null;
    }
}
