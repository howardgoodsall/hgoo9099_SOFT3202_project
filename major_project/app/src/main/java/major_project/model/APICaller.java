package major_project.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Base64;
import java.util.Base64.Encoder;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.HttpEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.http.HttpHeaders;

public class APICaller {
    public String apiCommGET(String uri) {
        try {
            HttpClient httpclient = HttpClients.createDefault();
            HttpGet httpget = new HttpGet(uri);
            HttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            String responseString = EntityUtils.toString(entity, "UTF-8");
            return responseString;
        } catch(Exception e){
            System.out.println("An error occured");
            System.out.println(e.getMessage());
            return null;
        }
    }

    public String apiCommPOST(String uri, String report, String postAuth,
        String toNum, String fromNum) {
        try {
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
}
