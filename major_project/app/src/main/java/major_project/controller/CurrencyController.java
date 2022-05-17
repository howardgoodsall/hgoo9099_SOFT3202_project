package major_project.controller;

import major_project.model.CurrencyModel;
import major_project.model.CurrencyOutput;
import java.util.ArrayList;

public class CurrencyController {
    private final CurrencyModel model;
    private final CurrencyOutput outputModel;

    public CurrencyController(CurrencyModel model, CurrencyOutput outputModel) {
        this.model = model;
        this.outputModel = outputModel;
    }

    public String login(String username, String pwdHash) {
        return this.model.login(username, pwdHash);
    }

    public boolean signUp(String username, String pwdHash) {
        return this.model.signUp(username, pwdHash);
    }

    public void updateTheme(String theme, String username) {
        this.model.updateTheme(theme, username);
    }

    public String getUserColour(String username) {
        return this.model.getUserColour(username);
    }

    public void updateColour(String colour, String username) {
        this.model.updateColour(colour, username);
    }
    public ArrayList<String[]> getViewingCurrencies(String username) {
        return this.model.getViewingCurrencies(username);
    }

    public void clearCache() {
        this.model.clearCache();
    }

    public ArrayList<String[]> supportedCurrencies(String country) {
        return this.model.supportedCurrencies(country);
    }

    public String getExchangeRateCache(String fromCurrCode, String toCurrCode) {
        return this.model.getExchangeRateCache(fromCurrCode, toCurrCode);
    }

    public Double calcExchangeRate(String amount, String value) {
        return Double.parseDouble(this.model.calcExchangeRate(amount, value));
    }

    public String getExchangeRate(String fromCurrCode, String toCurrCode) {
        return this.model.getExchangeRate(fromCurrCode, toCurrCode);
    }

    public void updateRate(Double exRateDouble, String fromCurrCode,
        String toCurrCode) {
        this.model.updateRate(exRateDouble, fromCurrCode, toCurrCode);
    }

    public void removeViewCurrency(String currCode, String username) {
        this.model.removeViewCurrency(currCode, username);
    }

    public void clearViewingTable(String username) {
        this.model.clearViewingTable(username);
    }

    public void insertViewCurrency(String currCode, String currName,
        String username) {
        this.model.insertViewCurrency(currCode, currName, username);
    }

    public String currConversion(String curr1Code, String curr2Code,
        String curr1Val) {
        return this.model.currConversion(curr1Code, curr2Code,
            curr1Val);
    }

    public void createAndSendReport(String curr1Name, String curr1Code,
        String curr2Name, String curr2Code, String curr1Val) {
        this.outputModel.sendReport(
            this.outputModel.createReport(
            curr1Name,
            curr1Code,
            curr2Name,
            curr2Code,
            getExchangeRate(curr1Code, curr2Code),
            curr1Val,
            currConversion(curr1Code, curr2Code, curr1Val)
            ));
    }
}
