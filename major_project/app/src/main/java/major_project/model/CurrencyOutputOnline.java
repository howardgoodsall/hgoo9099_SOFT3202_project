package major_project.model;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
//import java.net.http.HttpClient;
//import java.net.http.HttpRequest;
//import java.net.http.HttpResponse;
import org.json.JSONObject;
import org.json.JSONArray;
import java.math.BigDecimal;

import java.util.Base64;
import java.util.Base64.Encoder;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.HttpEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.http.HttpHeaders;

public class CurrencyOutputOnline implements CurrencyOutput {
    //Online model for output
    private final String apiKey;
    private final String apiSID;
    private final String fromNum;
    private final String toNum;

    public CurrencyOutputOnline(String apiKey, String apiSID, String fromNum,
        String toNum){
        this.apiKey = apiKey;
        this.apiSID = apiSID;
        this.fromNum = fromNum;
        this.toNum = toNum;
    }

    //Create Short report string
    public String createReport(String curr1Name, String curr1Code,
        String curr2Name, String curr2Code, String exRate, String curr1Val,
        String curr2Val) {
        String shortReport = String.format("%s:%s    Rate:%s    %s %s:%s %s",
            curr1Name, curr2Name, exRate, curr1Code, curr1Val, curr2Code,
            curr2Val);

        return shortReport;
    }

    //Send post request
    public String apiCommunicator(String uri, String report) {
        try {
            String postAuth = String.format("%s:%s",apiSID,
                apiKey);

            String authString = "Basic " + Base64.getEncoder().encodeToString(
                (postAuth).getBytes());

            HttpClient httpclient = HttpClients.createDefault();
            HttpPost httppost = new HttpPost(uri);
            httppost.setHeader(HttpHeaders.AUTHORIZATION, authString);
            List<NameValuePair> parameters = new ArrayList<NameValuePair>(2);
            parameters.add(new BasicNameValuePair("To", toNum));
            parameters.add(new BasicNameValuePair("From", fromNum));
            parameters.add(new BasicNameValuePair("Body", report));
            httppost.setEntity(new UrlEncodedFormEntity(parameters, "UTF-8"));

            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            String responseString = EntityUtils.toString(entity, "UTF-8");
            return responseString;
        } catch(Exception e){
            System.out.println("An error occured");
            System.out.println(e.getMessage());
            return null;
        }
    }

    //Give args to apiCommunicator
    public void sendReport(String report) {
        String uri = String.format(
            "https://api.twilio.com/2010-04-01/Accounts/%s/Messages.json",
            apiSID);
        if(apiCommunicator(uri, report) == null) {
            System.out.println(String.format(
            "Make sure environment variables are configured correctly, To: %s, From %s",
            toNum, fromNum));
        }
    }
}
