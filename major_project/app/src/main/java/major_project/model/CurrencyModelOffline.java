package major_project.model;

import java.util.ArrayList;

/**
 * Offline model for input API, mostly does nothing
 */
public class CurrencyModelOffline implements CurrencyModel {
    public CurrencyModelOffline() {
        return;
    }

    /**
     * Do nothing
     */
    public void getSupportedCurrencies() {
        return;
    }

    /**
     * return default currency
     */
    public ArrayList<String[]> supportedCurrencies(String country) {
        String[] curr = {"$$$",country};
        ArrayList<String[]> currencies = new ArrayList<String[]>();
        currencies.add(curr);
        return currencies;
    }

    /**
     * Do nothing
     */
    public String currConversion(String fromCurrCode, String toCurrCode,
        String amount){
            return amount;
    }

    /**
     * Do nothing
     */
    public void updateRate(double newRate, String from_curr_code,
        String to_curr_code) {
        return;
    }

    /**
     * Do nothing
     */
    public void clearCache() {
        return;
    }

    /**
     * Do nothing
     */
    public String getExchangeRateCache(String fromCurrCode, String toCurrCode) {
        return null;
    }

    /**
     * Do nothing
     */
    public String calcExchangeRate(String inp, String out) {
        return "1.000";
    }

    /**
     * Do nothing
     */
    public String getExchangeRate(String fromCurrCode, String toCurrCode) {
        return "1.000";
    }

    /**
     * Do nothing
     */
    public boolean signUp(String username, String pwdHash) {
        return true;
    }

    /**
     * Do nothing
     */
    public String login(String username, String pwdHash) {
        return "white";
    }

    /**
     * Do nothing
     */
    public String getUserColour(String username) {
        return "0x888888";
    }

    /**
     * Do nothing
     */
    public void updateColour(String colour, String username) {
        return;
    }

    /**
     * Do nothing
     */
    public void updateTheme(String theme, String username) {
        return;
    }

    /**
     * Do nothing
     */
    public void insertViewCurrency(String currCode, String currName,
        String username) {
        return;
    }

    /**
     * Do nothing
     */
    public void removeViewCurrency(String currCode, String username) {
        return;
    }

    /**
     * Do nothing
     */
    public void clearViewingTable(String username) {
        return;
    }

    /**
     * Do nothing
     */
    public ArrayList<String[]> getViewingCurrencies(String username) {
        return null;
    }
}
