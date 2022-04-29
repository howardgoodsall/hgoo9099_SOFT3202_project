package major_project.model;

import java.util.ArrayList;

public interface CurrencyModel {
    //public void showTable();
    public ArrayList<String> getViewingCurrencies();
    public String apiCommunicator(String uri);
    public void getSupportedCurrencies();
    public String[] supportedCurrencies(String country);
    
}
