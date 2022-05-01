package major_project.model;

import java.util.ArrayList;

public interface CurrencyModel {
    //public void showTable();
    public void getSupportedCurrencies();
    public ArrayList<String[]> supportedCurrencies(String country);
    public String currConversion(String fromCurrCode, String toCurrCode,
        String amount);
    public String calcConversionRate(String inp, String out);
}
