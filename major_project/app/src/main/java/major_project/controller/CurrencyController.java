package major_project.controller;

import major_project.model.CurrencyModel;
import major_project.model.CurrencyOutput;
import java.util.ArrayList;

/**
 * An attempt at implementing MVC, though it is unfinished (targeted grade
 * boundary is distinction) so it can be mostly ignored.
 * Threads are implemented in CurrencyView
 */
public class CurrencyController {
    private final CurrencyModel model;
    private final CurrencyOutput outputModel;

    public CurrencyController(CurrencyModel model, CurrencyOutput outputModel) {
        this.model = model;
        this.outputModel = outputModel;
    }

    /**
     * Call corresponding function in model
     */
    public String login(String username, String pwdHash) {
        return this.model.login(username, pwdHash);
    }

    /**
     * Call corresponding function in model
     */
    public boolean signUp(String username, String pwdHash) {
        return this.model.signUp(username, pwdHash);
    }

    /**
     * Call corresponding function in model
     */
    public void updateTheme(String theme, String username) {
        this.model.updateTheme(theme, username);
    }

    /**
     * Call corresponding function in model
     */
    public String getUserColour(String username) {
        return this.model.getUserColour(username);
    }

    /**
     * Call corresponding function in model
     */
    public void updateColour(String colour, String username) {
        this.model.updateColour(colour, username);
    }

    /**
     * Call corresponding function in model
     */
    public ArrayList<String[]> getViewingCurrencies(String username) {
        return this.model.getViewingCurrencies(username);
    }

    /**
     * Call corresponding function in model
     */
    public void clearCache() {
        this.model.clearCache();
    }

    /**
     * Call corresponding function in model
     */
    public ArrayList<String[]> supportedCurrencies(String country) {
        return this.model.supportedCurrencies(country);
    }

    /**
     * Call corresponding function in model
     */
    public String getExchangeRateCache(String fromCurrCode, String toCurrCode) {
        return this.model.getExchangeRateCache(fromCurrCode, toCurrCode);
    }

    /**
     * Call corresponding function in model& return as double
     */
    public Double calcExchangeRate(String amount, String value) {
        return Double.parseDouble(this.model.calcExchangeRate(amount, value));
    }

    /**
     * Call corresponding function in model
     */
    public String getExchangeRate(String fromCurrCode, String toCurrCode) {
        return this.model.getExchangeRate(fromCurrCode, toCurrCode);
    }

    /**
     * Call corresponding function in model
     */
    public void updateRate(Double exRateDouble, String fromCurrCode,
        String toCurrCode) {
        this.model.updateRate(exRateDouble, fromCurrCode, toCurrCode);
    }

    /**
     * Call corresponding function in model
     */
    public void removeViewCurrency(String currCode, String username) {
        this.model.removeViewCurrency(currCode, username);
    }

    /**
     * Call corresponding function in model
     */
    public void clearViewingTable(String username) {
        this.model.clearViewingTable(username);
    }

    /**
     * Call corresponding function in model
     */
    public void insertViewCurrency(String currCode, String currName,
        String username) {
        this.model.insertViewCurrency(currCode, currName, username);
    }

    /**
     * Call corresponding function in model
     */
    public String currConversion(String curr1Code, String curr2Code,
        String curr1Val) {
        return this.model.currConversion(curr1Code, curr2Code,
            curr1Val);
    }

    /**
     * Combine creation and sending of report
     */
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
