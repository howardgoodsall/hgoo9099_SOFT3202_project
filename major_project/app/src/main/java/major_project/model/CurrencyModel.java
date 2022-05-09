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
    public String getExchangeRate(String fromCurrCode, String toCurrCode);
}
