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

    public String getExchangeRate(String fromCurrCode, String toCurrCode){
        return "1.000";
    }
}
