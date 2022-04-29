package major_project.model;

import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONObject;
import org.json.JSONArray;

public class CurrencyModelOnline implements CurrencyModel {
    private ArrayList<String> viewingCurrencies;
    private ArrayList<CurrencyData> supportCurrencies;
    private String api_key = "902d7e1a3ee13cbb1eef28146c2f968d";

    public CurrencyModelOnline() {
        return;
    }

    public ArrayList<String> getViewingCurrencies() {
        return new ArrayList<String>();
    }

    public String apiCommunicator(String uri) {
        try {
            HttpRequest request = HttpRequest.newBuilder(new URI(uri))
                .GET().build();
            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (IOException | InterruptedException e) {
            System.out.println("An error occured");
            System.out.println(e.getMessage());
            return null;
        } catch (URISyntaxException ignored) {
            return null;
        }
    }

    public void getSupportedCurrencies() {
        this.supportCurrencies = new ArrayList<CurrencyData>();
        JSONObject jsonObj = new JSONObject(apiCommunicator(String.format(
            "https://api.currencyscoop.com/v1/currencies?type=fiat&api_key=%s"
            ,api_key)));
        JSONObject jsonObjData = (JSONObject)((JSONObject)jsonObj
            .get("response")).get("fiats");
        for(int i=0; i<jsonObjData.names().length(); i++){
            CurrencyData currencyData = new Gson().fromJson(jsonObjData.get(
                jsonObjData.names().getString(i)).toString(), CurrencyData.class);
            supportCurrencies.add(currencyData);
        }
    }

    public String[] supportedCurrencies(String country) {
        if(this.supportCurrencies == null) {
            getSupportedCurrencies();
        }
        ArrayList<String[]> currOfSelectedCountry = new ArrayList<String[]>();
        for(int i=0; i<this.supportCurrencies.size(); i++) {
            for(int j=0; j<this.supportCurrencies.get(i).getCountries().length;
                j++) {
                if(this.supportCurrencies.get(i).getCountries()[j].equals(country)) {
                    String[] currencyDataPair = {this.supportCurrencies.get(i)
                            .getCurrencyCode(), this.supportCurrencies.get(i).getCurrencyName()};
                    currOfSelectedCountry.add(currencyDataPair);
                }
            }
        }
        if(currOfSelectedCountry.size() != 0) {
            return currOfSelectedCountry.get(0);
        } else {
            return null;
        }
    }
}
