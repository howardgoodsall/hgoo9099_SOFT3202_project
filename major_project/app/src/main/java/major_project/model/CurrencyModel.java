package major_project.model;

import java.util.ArrayList;

/**
 * Interface for input API
 */
public interface CurrencyModel {
    public void getSupportedCurrencies();
    public ArrayList<String[]> supportedCurrencies(String country);
    public String currConversion(String fromCurrCode, String toCurrCode,
        String amount);
    public void updateRate(double newRate, String from_curr_code,
        String to_curr_code);
    public void clearCache();
    public String getExchangeRateCache(String fromCurrCode, String toCurrCode);
    public String calcExchangeRate(String inp, String out);
    public String getExchangeRate(String fromCurrCode, String toCurrCode);
    public boolean signUp(String username, String pwdHash);
    public String login(String username, String pwdHash);
    public String getUserColour(String username);
    public void updateColour(String colour, String username);
    public void updateTheme(String theme, String username);
    public void insertViewCurrency(String currCode, String currName,
        String username);
    public void removeViewCurrency(String currCode, String username);
    public void clearViewingTable(String username);
    public ArrayList<String[]> getViewingCurrencies(String username);
}
