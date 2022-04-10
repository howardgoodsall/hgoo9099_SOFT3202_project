package task2;

import java.util.List;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

public class WindowHandler {
    private VBox vBox;
    private APIComm apiComm;
    private HBox loginHBox;
    private HBox signUpHBox;
    private int shipCount;
    private int userShips;

    public WindowHandler(APIComm apiComm) {
        this.vBox = new VBox();
        this.vBox.setSpacing(10);
        this.apiComm = apiComm;
        this.shipCount = 0;
        this.userShips = 0;
        return;
    }

    public VBox getVBox() {
        return this.vBox;
    }

    public void serverStatusButton() {
        Button serverStatusButton = new Button();
        Label serverStatusLabel = new Label("...\n");
        serverStatusLabel.setLabelFor(serverStatusButton);
        serverStatusButton.setText("Server Status");
        serverStatusButton.setOnAction((event) -> {
            if(this.apiComm.serverStatus()) {
                serverStatusLabel.setText("Online\n");
            } else {
                serverStatusLabel.setText("Offline\n");
            }
        });
        //this.hBox.getChildren().add(serverStatusButton);
        HBox hBox = new HBox();
        hBox.getChildren().add(serverStatusButton);
        hBox.getChildren().add(serverStatusLabel);
        this.vBox.getChildren().add(hBox);
    }

    public void loginSuccess() {
        this.vBox.getChildren().remove(this.loginHBox);
        this.vBox.getChildren().remove(this.signUpHBox);
        HBox hBox = new HBox();
        User user = this.apiComm.getUser();
        String info = String.format("Token: %s   ", this.apiComm.getToken());
        Button copyButton = new Button();
        copyButton.setText("Copy");
        copyButton.setOnAction((event) -> {
            final Clipboard clipboard = Clipboard.getSystemClipboard();
            final ClipboardContent content = new ClipboardContent();
            content.putString(this.apiComm.getToken());
            clipboard.setContent(content);
        });
        Label infoLabel = new Label(info);
        hBox.getChildren().add(infoLabel);
        hBox.getChildren().add(copyButton);
        this.vBox.getChildren().add(hBox);
        viewInfo();
        viewLoans();
        viewActiveLoans();
        listAvailableShips();
        viewShips();
    }

    public void login() {
        TextField login = new TextField();
        Button loginButton = new Button();
        Label loginLabel = new Label("");
        this.loginHBox = new HBox();
        this.loginHBox.getChildren().add(login);
        this.loginHBox.getChildren().add(loginButton);
        this.loginHBox.getChildren().add(loginLabel);
        loginButton.setText("Login");
        loginButton.setOnAction((event) -> {
            if(this.apiComm.login(login.getText()) != null){
                loginSuccess();
            } else {
                loginLabel.setText("Incorrect Token");
            }
        });
        this.vBox.getChildren().add(this.loginHBox);
    }

    public void signUp() {
        TextField signUp = new TextField();
        Button signUpButton = new Button();
        Label signUpLabel = new Label("");
        this.signUpHBox = new HBox();
        this.signUpHBox.getChildren().add(signUp);
        this.signUpHBox.getChildren().add(signUpButton);
        this.signUpHBox.getChildren().add(signUpLabel);
        signUpButton.setText("Sign Up");
        signUpButton.setOnAction((event) -> {
            String username = signUp.getText();
            if(username!= "" && this.apiComm.signUp(username)){
                loginSuccess();
            } else {
                signUpLabel.setText(" Username already taken");
            }
        });
        this.vBox.getChildren().add(this.signUpHBox);
    }

    public void viewInfo() {
        Button infoButton = new Button();
        HBox hBox= new HBox();
        Label infoLabel = new Label("");
        hBox.getChildren().add(infoButton);
        hBox.getChildren().add(infoLabel);
        infoButton.setText("View User Information");
        infoButton.setOnAction((event) -> {
            if(infoButton.getText() == "View User Information") {
                infoButton.setText("Hide User Information");
                User user = this.apiComm.getInfo().getUser();
                if(user != null) {
                    String userInfo = String.format("\nUsername: %s\nCredits: %d\nJoined At: %s\nShip Count: %d\nStructure Count: %d\n",
                        user.getUsername(), user.getCredits(), user.getJoinedAt(),
                        user.getShipCount(), user.getStructureCount());
                    infoLabel.setText(userInfo);
                } else {
                    infoLabel.setText("User information not found");
                }
            } else {
                infoLabel.setText("");
                infoButton.setText("View User Information");
            }
        });
        this.vBox.getChildren().add(hBox);
    }

    public void viewLoans() {
        Button loanButton = new Button();
        HBox hBox = new HBox();
        Label availableLabel = new Label("");
        hBox.getChildren().add(loanButton);
        hBox.getChildren().add(availableLabel);
        loanButton.setText("View Available Loans");
        loanButton.setOnAction((event) -> {
            if(loanButton.getText() == "View Available Loans") {
                loanButton.setText("Hide Available Loans");
                LoansWrapper loansWrap = this.apiComm.viewLoans();
                if(loansWrap != null) {
                    List<Loans> loans = loansWrap.getLoans();
                    availableLabel.setText("");
                    for(int i=0; i<loans.size(); i++) {
                        String loanText = "";
                        Loans loan = loans.get(i);
                        String collateral = "No";
                        if(loan.getCollateralRequired()) {
                            collateral = "Yes";
                        }
                        String loanInfo = String.format("\nAmount %d\nCollateral Required?: %s\nRate: %d\nTerm (days): %d\nType: %s\n\n",
                            loan.getAmount(), collateral, loan.getRate(), loan.getTermInDays(), loan.getType());
                        loanText = loanText + loanInfo;
                        hBox.getChildren().add(new Label(loanText));
                        Button takeLoanButton = new Button();
                        takeLoanButton.setText("Take");
                        takeLoanButton.setOnAction((event2) -> {
                            if(this.apiComm.takeLoan(loan.getType())) {
                                takeLoanButton.setText("Taken");
                            } else {
                                takeLoanButton.setText("X");
                                hBox.getChildren().add(new Label("Unable to take loan (Already taken)"));
                            }
                        });
                        hBox.getChildren().add(takeLoanButton);
                    }
                }
                else {
                    availableLabel.setText("No loans available right now.");//Error Handling
                }
            } else {
                availableLabel.setText("");
                hBox.getChildren().clear();
                hBox.getChildren().add(loanButton);
                hBox.getChildren().add(availableLabel);
                loanButton.setText("View Available Loans");
            }
        });
        this.vBox.getChildren().add(hBox);
    }

    public void viewActiveLoans() {
        Button loanButton = new Button();
        HBox hBox = new HBox();
        Label activeLabel = new Label("");
        hBox.getChildren().add(loanButton);
        hBox.getChildren().add(activeLabel);
        loanButton.setText("View Active Loans");
        loanButton.setOnAction((event) -> {
            if(loanButton.getText() == "View Active Loans") {
                loanButton.setText("Hide Active Loans");
                LoansWrapper loansWrap = this.apiComm.viewActiveLoans();
                if(loansWrap != null) {
                    List<Loans> loans = loansWrap.getLoans();
                    if(loans.size() != 0) {
                        activeLabel.setText("");
                        String loanText = "";
                        for(int i=0; i<loans.size(); i++) {
                            Loans loan = loans.get(i);
                            String collateral = "No";
                            if(loan.getCollateralRequired()) {
                                collateral = "Yes";
                            }
                            String loanInfo = String.format("\nDue: %s\nID: %s\nRepayment Amount: %d\nStatus: %s\nType %s\n\n",
                                loan.getDue(), loan.getId(), loan.getRepaymentAmount(), loan.getStatus(), loan.getType());
                            loanText = loanText + loanInfo;
                        }
                        activeLabel.setText(loanText);
                    } else {
                        activeLabel.setText("No loans taken out.");
                    }
                } else {
                    activeLabel.setText("No loans taken out.");//Error Handling
                }
            } else {
                hBox.getChildren().clear();
                hBox.getChildren().add(loanButton);
                hBox.getChildren().add(activeLabel);
                loanButton.setText("View Active Loans");
            }
        });
        this.vBox.getChildren().add(hBox);
    }

    public void listAvailableShips() {
        Button availableButton = new Button();
        HBox hBox = new HBox();
        Label availableLabel = new Label("");
        hBox.getChildren().add(availableButton);
        hBox.getChildren().add(availableLabel);
        availableButton.setText("View Available Ships");
        availableButton.setOnAction((event) -> {
            if(availableButton.getText() == "View Available Ships") {
                availableButton.setText("Hide Available Ships");
                ShipsWrapper shipsWrap = this.apiComm.listAvailableShips();
                if(shipsWrap != null) {
                    List<Ship> ships = shipsWrap.getShips();
                    String locationFormat = "";
                    availableLabel.setText(String.format("%d/%d",(this.shipCount+1),ships.size()));
                    Ship ship = ships.get(this.shipCount);//Show one ship at a time
                    String shipInfo = String.format("\nClass: %s\nManufacturer: %s\nMaximum Cargo: %d\nPlating: %d\nSpeed: %d\nType: %s\nWeapons: %d\nPurchase Locations:\n",
                        ship.getShipClass(), ship.getManufacturer(),
                        ship.getMaxCargo(), ship.getPlating(),
                        ship.getSpeed(), ship.getType(), ship.getWeapons());
                    List<Location> locations = ship.getPurchaseLocations();
                    if(locations != null) {
                        for(int i = 0; i < locations.size(); i++) {
                            locationFormat = String.format("%s\n - Price: %d\n - System: %s\n",
                            locations.get(i).getLocation(),
                            locations.get(i).getPrice(),
                            locations.get(i).getSystem());
                            shipInfo = shipInfo + locationFormat;
                        }
                    }
                    Label shipLabel = new Label(shipInfo);
                    hBox.getChildren().add(shipLabel);

                    Button prevButton = new Button();
                    prevButton.setText("Prev");
                    prevButton.setOnAction((event2) -> {
                        if(this.shipCount > 0) {
                            this.shipCount--;
                            availableLabel.setText(String.format("%d/%d",(this.shipCount+1),ships.size()));
                            Ship ship1 = ships.get(this.shipCount);//Show one ship at a time
                            String shipInfo1 = String.format("\nClass: %s\nManufacturer: %s\nMaximum Cargo: %d\nPlating: %d\nSpeed: %d\nType: %s\nWeapons: %d\nPurchase Locations:\n",
                                ship1.getShipClass(), ship1.getManufacturer(),
                                ship1.getMaxCargo(), ship1.getPlating(),
                                ship1.getSpeed(), ship1.getType(), ship1.getWeapons());
                            List<Location> locations1 = ship1.getPurchaseLocations();
                            if(locations1 != null) {
                                for(int i = 0; i < locations1.size(); i++) {
                                    String locationFormat1 = String.format("%s\n - Price: %d\n - System: %s\n",
                                    locations1.get(i).getLocation(),
                                    locations1.get(i).getPrice(),
                                    locations1.get(i).getSystem());
                                    shipInfo1 = shipInfo1 + locationFormat1;
                                }
                            }
                            shipLabel.setText(shipInfo1);
                        }
                    });
                    hBox.getChildren().add(prevButton);

                    Button purchaseButton = new Button();
                    purchaseButton.setText("Purchase Ship");
                    purchaseButton.setOnAction((event3) -> {
                        if(this.apiComm.purchaseShip(
                            ship.getPurchaseLocations().get(0).getLocation(),
                            ship.getType())) {
                                this.userShips++;
                                availableLabel.setText("Purchased");
                            } else {
                                availableLabel.setText("Insufficient Funds");
                            }
                    });
                    hBox.getChildren().add(purchaseButton);


                    Button nextButton = new Button();
                    nextButton.setText("Next");
                    nextButton.setOnAction((event4) -> {
                        if(this.shipCount+1 < ships.size()) {
                            this.shipCount++;
                            availableLabel.setText(String.format("%d/%d",(this.shipCount+1),ships.size()));
                            Ship ship2 = ships.get(this.shipCount);//Show one ship at a time
                            String shipInfo2 = String.format("\nClass: %s\nManufacturer: %s\nMaximum Cargo: %d\nPlating: %d\nSpeed: %d\nType: %s\nWeapons: %d\nPurchase Locations:\n",
                                ship2.getShipClass(), ship2.getManufacturer(),
                                ship2.getMaxCargo(), ship2.getPlating(),
                                ship2.getSpeed(), ship2.getType(), ship2.getWeapons());
                            List<Location> locations2 = ship.getPurchaseLocations();
                            if(locations2 != null) {
                                for(int i = 0; i < locations.size(); i++) {
                                    String locationFormat2 = String.format("%s\n - Price: %d\n - System: %s\n",
                                    locations2.get(i).getLocation(),
                                    locations2.get(i).getPrice(),
                                    locations2.get(i).getSystem());
                                    shipInfo2 = shipInfo2 + locationFormat2;
                                }
                            }
                            shipLabel.setText(shipInfo2);
                        }
                    });
                    hBox.getChildren().add(nextButton);
                }
                else {
                    availableLabel.setText("No Available Ships");//Error Handling
                }
            } else {
                hBox.getChildren().clear();
                hBox.getChildren().add(availableButton);
                hBox.getChildren().add(availableLabel);
                availableLabel.setText("");
                availableButton.setText("View Available Ships");
            }
        });
        this.vBox.getChildren().add(hBox);
    }

    public void viewShips() {
        Button availableButton = new Button();
        HBox hBox = new HBox();
        Label availableLabel = new Label("");
        hBox.getChildren().add(availableButton);
        hBox.getChildren().add(availableLabel);
        availableButton.setText("View Your Ships");
        availableButton.setOnAction((event) -> {
            if(availableButton.getText() == "View Your Ships") {
                availableButton.setText("Hide Your Ships");
                ShipInventory shipsWrap = this.apiComm.viewShips();
                if(shipsWrap != null) {
                    List<Ship> ships = shipsWrap.getShips();

                    if(ships.size() > 0) {
                        if(this.shipCount >= ships.size()) {
                            this.shipCount = 0;
                        }
                        availableLabel.setText(String.format("%d",(this.shipCount+1)));
                        Ship ship = ships.get(this.shipCount);//Show one ship at a time
                        String shipInfo = String.format("\nClass: %s\nManufacturer: %s\nMaximum Cargo: %d\nPlating: %d\nSpeed: %d\nType: %s\nWeapons: %d\nPurchase Locations:\n",
                            ship.getShipClass(), ship.getManufacturer(),
                            ship.getMaxCargo(), ship.getPlating(),
                            ship.getSpeed(), ship.getType(), ship.getWeapons());

                        Label shipLabel = new Label(shipInfo);
                        hBox.getChildren().add(shipLabel);

                        Button prevButton = new Button();
                        prevButton.setText("Prev");
                        prevButton.setOnAction((event2) -> {
                            if(this.shipCount > 0) {
                                this.shipCount--;
                                availableLabel.setText(String.format("%d",(this.shipCount+1)));
                                Ship ship1 = ships.get(this.shipCount);//Show one ship at a time
                                String shipInfo1 = String.format("\nClass: %s\nManufacturer: %s\nMaximum Cargo: %d\nPlating: %d\nSpeed: %d\nType: %s\nWeapons: %d\nPurchase Locations:\n",
                                    ship1.getShipClass(), ship1.getManufacturer(),
                                    ship1.getMaxCargo(), ship1.getPlating(),
                                    ship1.getSpeed(), ship1.getType(), ship1.getWeapons());
                                shipLabel.setText(shipInfo1);
                            }
                        });
                        hBox.getChildren().add(prevButton);

                        Button nextButton = new Button();
                        nextButton.setText("Next");
                        nextButton.setOnAction((event4) -> {
                            if(this.shipCount+1 < this.userShips) {
                                this.shipCount++;
                                availableLabel.setText(String.format("%d",(this.shipCount+1)));
                                Ship ship2 = ships.get(this.shipCount);//Show one ship at a time
                                String shipInfo2 = String.format("\nClass: %s\nManufacturer: %s\nMaximum Cargo: %d\nPlating: %d\nSpeed: %d\nType: %s\nWeapons: %d\nPurchase Locations:\n",
                                    ship2.getShipClass(), ship2.getManufacturer(),
                                    ship2.getMaxCargo(), ship2.getPlating(),
                                    ship2.getSpeed(), ship2.getType(), ship2.getWeapons());
                                shipLabel.setText(shipInfo2);
                            }
                        });
                        hBox.getChildren().add(nextButton);
                    } else {
                        availableLabel.setText("No Ships");
                    }
                } else {
                    availableLabel.setText("No Ships");//Error Handling
                }
            } else {
                hBox.getChildren().clear();
                hBox.getChildren().add(availableButton);
                hBox.getChildren().add(availableLabel);
                availableLabel.setText("");
                availableButton.setText("View Your Ships");
            }
        });
        this.vBox.getChildren().add(hBox);
    }

}
