package task2;

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

public class APICommOnline implements APIComm {
    private String username;
    private String token;
    private User user;
    public APICommOnline() {
        return;
    }

    public String getUsername() {
        return this.username;
    }

    public String getToken() {
        return this.token;
    }

    public User getUser() {
        return this.user;
    }

    public boolean serverStatus() {
        try {
            HttpRequest request = HttpRequest.newBuilder(new URI("https://api.spacetraders.io/game/status")).GET().build();
            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if(response.body().equals("{\"status\":\"spacetraders is currently online and available to play\"}")) {
                return true;
            } else {
                return false;
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("An error occured");
            System.out.println(e.getMessage());
            return false;
        } catch (URISyntaxException ignored) {
            return false;
        }
    }

    public boolean login(String token) {
        try {
            String uri = String.format("https://api.spacetraders.io/my/account?token=%s&class=MK-1",token);
            HttpRequest request = HttpRequest.newBuilder(new URI(uri)).GET().build();
            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if(response.statusCode() >= 400) {
                return false;
            } else {
                System.out.println(response.body());
                //Gson gson = new Gson();
                //JSONObject userJSON = new Gson().fromJson(response.body(), JSONObject.class);
                //System.out.println(gson.getJSONArray("user"));
                //JSONArray userArray = userJSON.getJSONArray("user");
                //this.user = gson.fromJson(userJSON.getJSONArray("user"), User.class);
                //JSONObject jsonObject = new Gson().fromJson(response.body(), JSONObject.class);
                //Gson gson = new GsonBuilder().registerTypeAdapter(User.class, new CustomDeserilizer()).create();
                //this.user = gson.fromJson(jsonObject.get("user"), User.class);

                //System.out.println(this.user.getUsername());
                this.token = token;
                return true;
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("An error occured");
            System.out.println(e.getMessage());
            return false;
        } catch (URISyntaxException ignored) {
            return false;
        }
    }

    public boolean signUp(String username) {
        try {
            String uri = String.format("https://api.spacetraders.io/users/:%s/claim",username);
            HttpRequest request = HttpRequest.newBuilder(new URI(uri)).POST(HttpRequest.BodyPublishers.ofString(username)).build();
            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if(response.statusCode() >= 400) {
                return false;
            } else {
                JSONObject jsonObject = new Gson().fromJson(response.body(), JSONObject.class);
                String token = jsonObject.get("token").toString();
                login(token);//Calls login as well so that a user object is created
                return true;
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("An error occured");
            System.out.println(e.getMessage());
            return false;
        } catch (URISyntaxException ignored) {
            return false;
        }
    }
}
