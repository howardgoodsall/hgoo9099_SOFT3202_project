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
import java.util.ArrayList;
import java.util.List;

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

    public UserWrapper getInfo(){
        try {
            String uri = String.format("https://api.spacetraders.io/my/account?token=%s",this.token);
            HttpRequest request = HttpRequest.newBuilder(new URI(uri)).GET().build();
            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if(response.statusCode() >= 400) {
                return null;
            } else {
                UserWrapper userWrap = new Gson().fromJson(response.body(), UserWrapper.class);
                this.user = userWrap.getUser();
                this.token = token;
                return userWrap;
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("An error occured");
            System.out.println(e.getMessage());
            return null;
        } catch (URISyntaxException ignored) {
            return null;
        }
    }

    public UserWrapper login(String token) {
        //Login performs get info as verification
        this.token = token;
        return getInfo();
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
                UserWrapper userWrap = new Gson().fromJson(response.body(), UserWrapper.class);
                //System.out.println(userWrap.getToken());
                login(userWrap.getToken());//Calls login as well so that a user object is created
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

    public LoansWrapper viewLoans() {
        try {
            String uri = String.format("https://api.spacetraders.io/types/loans?token=%s", this.token);
            HttpRequest request = HttpRequest.newBuilder(new URI(uri)).GET().build();
            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if(response.statusCode() >= 400) {
                return null;
            } else {
                LoansWrapper loansWrap = new Gson().fromJson(response.body(), LoansWrapper.class);
                return loansWrap;
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("An error occured");
            System.out.println(e.getMessage());
            return null;
        } catch (URISyntaxException ignored) {
            return null;
        }
    }

    public boolean takeLoan(String type) {
        try {
            LoanPost loanPost = new LoanPost(this.token, type);
            Gson gson = new Gson();
            String postJSON = gson.toJson(loanPost);
            String uri = String.format("https://api.spacetraders.io/my/loans?token=%s&type=%s", this.token, type);
            HttpRequest request = HttpRequest.newBuilder(new URI(uri))
                .POST(HttpRequest.BodyPublishers.ofString(postJSON))
                .build();
            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if(response.statusCode() >= 400) {
                return false;
            } else {
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

    public LoansWrapper viewActiveLoans() {
        try {
            String uri = String.format("https://api.spacetraders.io/my/loans?token=%s",this.token);
            HttpRequest request = HttpRequest.newBuilder(new URI(uri)).GET().build();
            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if(response.statusCode() >= 400) {
                return null;
            } else {
                LoansWrapper loansWrap = new Gson().fromJson(response.body(), LoansWrapper.class);
                return loansWrap;
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("An error occured");
            System.out.println(e.getMessage());
            return null;
        } catch (URISyntaxException ignored) {
            return null;
        }
    }

    public ShipsWrapper listAvailableShips() {
        try {
            //String uri = "https://api.spacetraders.io/types/ships";//Unsure of which one is correct
            String uri = String.format("https://api.spacetraders.io/systems/OE/ship-listings?token=%s",this.token);
            HttpRequest request = HttpRequest.newBuilder(new URI(uri))
                .GET().build();
            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if(response.statusCode() >= 400) {
                return null;
            } else {
                ShipsWrapper shipsWrap = new Gson().fromJson(response.body(), ShipsWrapper.class);
                return shipsWrap;
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("An error occured");
            System.out.println(e.getMessage());
            return null;
        } catch (URISyntaxException ignored) {
            return null;
        }
    }

    public boolean purchaseShip(String location, String type) {
        try {
            String uri = String.format("https://api.spacetraders.io/my/ships?token=%s&location=%s&type=%s",
                this.token, location, type);
            HttpRequest request = HttpRequest.newBuilder(new URI(uri))
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();
            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if(response.statusCode() >= 400) {
                return false;
            } else {
                UserWrapper userWrap = new Gson().fromJson(response.body(), UserWrapper.class);
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

    public ShipInventory viewShips() {
        try {
            String uri = String.format("https://api.spacetraders.io/my/ships?token=%s",this.token);
            HttpRequest request = HttpRequest.newBuilder(new URI(uri))
                .GET().build();
            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if(response.statusCode() >= 400) {
                return null;
            } else {
                ShipInventory shipsWrap = new Gson().fromJson(response.body(), ShipInventory.class);
                return shipsWrap;
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("An error occured");
            System.out.println(e.getMessage());
            return null;
        } catch (URISyntaxException ignored) {
            return null;
        }
    }
}
