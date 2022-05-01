package major_project.model;

import java.util.ArrayList;

public class CurrencyModelOffline implements CurrencyModel {
    public CurrencyModelOffline() {
        return;
    }

    public ArrayList<String> getViewingCurrencies() {
        ArrayList<String> list = new ArrayList();
        list.add("$$$");
        return list;
    }

    public String apiCommunicator(String uri) {
        return "$$$";
    }

    public void getSupportedCurrencies() {
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

    public String calcConversionRate(String inp, String out){
        return "1";
    }
}
