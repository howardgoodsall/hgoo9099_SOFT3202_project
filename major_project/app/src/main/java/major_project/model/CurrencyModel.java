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
    public String calcConversionRate(String inp, String out);
}
