package major_project.model;

import java.util.ArrayList;

/**
 * Interface for input API
 */
public interface CurrencyModel {
    /** Retrieve all supported currencies */
    public void getSupportedCurrencies();

    /** Retrieve all supported currencies for a particular country */
    public ArrayList<String[]> supportedCurrencies(String country);

    /** Perform a currency conversion, return as string*/
    public String currConversion(String fromCurrCode, String toCurrCode,
        String amount);

    /** Update a rate in the cache, or not if offline */
    public void updateRate(double newRate, String from_curr_code,
        String to_curr_code);

    /** clear the cache, called via a button */
    public void clearCache();

    /** Get the cache of an exchange rate */
    public String getExchangeRateCache(String fromCurrCode, String toCurrCode);

    /** Calculate an exchnage rate (not used) */
    public String calcExchangeRate(String inp, String out);

    /** retrieve an exchange rate */
    public String getExchangeRate(String fromCurrCode, String toCurrCode);

    /** Sign up with username and pwd hash */
    public boolean signUp(String username, String pwdHash);

    /** Login with username and pwd hash, return user's theme as string */
    public String login(String username, String pwdHash);

    /** Get the user's colour from database */
    public String getUserColour(String username);

    /** Update the user's colour in the database */
    public void updateColour(String colour, String username);

    /** update user's theme in cache (light/dark) */
    public void updateTheme(String theme, String username);

    /** insert a currency into the user's table in database */
    public void insertViewCurrency(String currCode, String currName,
        String username);

    /** Remove a currency from the user's table in database */
    public void removeViewCurrency(String currCode, String username);

    /** clear the user's table in database */
    public void clearViewingTable(String username);

    /** get user's table data from database as ArrayList of string arrays */
    public ArrayList<String[]> getViewingCurrencies(String username);
}
