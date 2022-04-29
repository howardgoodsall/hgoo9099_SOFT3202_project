package major_project.model;

import java.util.ArrayList;

public class CurrencyModelOffline implements CurrencyModel {
    public CurrencyModelOffline() {
        return;
    }

    public ArrayList<String> getViewingCurrencies() {
        ArrayList<String> list = new ArrayList();
        list.add("??D");
        return list;
    }

    public String apiCommunicator(String uri) {
        return "??D";
    }

    public void getSupportedCurrencies() {
    }

    public String[] supportedCurrencies(String country) {
        String[] curr = {"??D",country};
        return curr;
    }
}
