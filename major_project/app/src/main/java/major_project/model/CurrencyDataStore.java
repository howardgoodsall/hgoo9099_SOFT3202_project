package major_project.model;

import java.util.*;

import java.io.File;
import java.sql.*;

/**
 * SQL caching file
 */
public class CurrencyDataStore {
    private static final String dbName = "saveData.db";
    private static final String dbURL = "jdbc:sqlite:" + dbName;
    private boolean tableExists = false;

    public CurrencyDataStore() {
        if(createDB()) {
            createRatesSchema();
            createUsersSchema();
            createViewingCurrenciesSchema();
        }
    }

    /**
     * Create the file, partially copied from my task 3 work.
     */
    public boolean createDB() {
        File dbFile = new File(dbName);
        if (dbFile.exists()) {
            return true;
        }
        try (Connection ignored = DriverManager.getConnection(dbURL)) {
            return true;//Test connection
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * Execute sql queries that don't return results
     */
    public boolean executeSQL(String sqlString) {
        try (Connection conn = DriverManager.getConnection(dbURL);
             Statement statement = conn.createStatement()) {
            statement.execute(sqlString);
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * Retrieve data via sql, where data is 1 column of doubles
     */
    public ArrayList<Double> retrieveDoubleViaSQL(String sqlString) {
        try (Connection conn = DriverManager.getConnection(dbURL);
             Statement statement = conn.createStatement()) {
            ResultSet result = statement.executeQuery(sqlString);
            ArrayList<Double> resultsList = new ArrayList<Double>();
            while (result.next()) {
                resultsList.add(result.getDouble(1));
            }
            return resultsList;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * Retrieve data via SQL, where data is 1 column of Text
     */
    public ArrayList<String> retrieveStringViaSQL(String sqlString) {
        try (Connection conn = DriverManager.getConnection(dbURL);
             Statement statement = conn.createStatement()) {
            ResultSet result = statement.executeQuery(sqlString);
            ArrayList<String> resultsList = new ArrayList<String>();
            while (result.next()) {
                resultsList.add(result.getString(1));
            }
            return resultsList;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * Retrieve data via SQL, where data is 2 columns of Text
     */
    public ArrayList<String[]> retrieveTwoStringsViaSQL(String sqlString) {
        try (Connection conn = DriverManager.getConnection(dbURL);
             Statement statement = conn.createStatement()) {
            ResultSet result = statement.executeQuery(sqlString);
            ArrayList<String[]> resultsList = new ArrayList<String[]>();
            while (result.next()) {
                String[] resultsItem = new String[2];
                resultsItem[0] = result.getString("curr_code");
                resultsItem[1] = result.getString("curr_name");
                resultsList.add(resultsItem);
            }
            return resultsList;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * Create the table(s)
     */
    public void createRatesSchema() {
        String schemaSQL = """
        CREATE TABLE IF NOT EXISTS rates(
          from_curr_code TEXT,
          to_curr_code TEXT,
          rate DOUBLE,
          PRIMARY KEY(from_curr_code, to_curr_code)
        );
        """;

        if(!executeSQL(schemaSQL)) {
            System.out.println("Couldn't create Schema");
        } else {
            this.tableExists = true;
        }
    }

    public void createUsersSchema() {
        String usersSchemaSQL = """
        CREATE TABLE IF NOT EXISTS users(
          username TEXT UNIQUE PRIMARY KEY,
          password TEXT,
          theme TEXT
        );
        """;
        if(!executeSQL(usersSchemaSQL)) {
            System.out.println("Couldn't create users table");
        }
    }

    public void createViewingCurrenciesSchema() {
        String viewCurrSQL = """
        CREATE TABLE IF NOT EXISTS viewing_currencies(
          curr_code TEXT,
          curr_name TEXT,
          username TEXT,
          FOREIGN KEY(username) REFERENCES users(username),
          PRIMARY KEY(username, curr_code)
        );
        """;
        if(!executeSQL(viewCurrSQL)) {
            System.out.println("Couldn't create viewing currencies table");
        }
    }

    /**
     * Insert a new rate into cache table
     */
    public void insertRate(String from_curr_code, String to_curr_code,
        Double rate) {
        if(!this.tableExists) {
            createRatesSchema();//Force table creation after deletion
        } else {
            if(getCacheRate(from_curr_code, to_curr_code) != null) {
                return;
            }
        }
        String insertSQL = String.format(
            "INSERT INTO rates(from_curr_code, to_curr_code, rate) VALUES (\"%s\", \"%s\", %f);",
            from_curr_code, to_curr_code, rate);//Using snakecase to match sql
        if(!executeSQL(insertSQL)) {
            System.out.println("Failed to insert into database");
        }
    }

    /**
     * Drop rates table if it exists
     */
    public void dropRatesTable() {
        String dropRatesTableSQL = "DROP TABLE IF EXISTS rates;";
        if(!executeSQL(dropRatesTableSQL)) {
            System.out.println("Could not delete rates table");
        } else {
            this.tableExists = false;
        }
    }

    public void updateRate(double newRate, String from_curr_code,
        String to_curr_code) {
        String rateUpdateSQL = String.format(
        "UPDATE rates Set rate = %f WHERE from_curr_code = \"%s\" AND to_curr_code = \"%s\";",
        newRate, from_curr_code, to_curr_code
        );
        if(!executeSQL(rateUpdateSQL)) {
            System.out.println("Could not update rate");
        }
    }

    /**
     * SELECT from rates table
     */
    public Double getCacheRate(String from_curr_code, String to_curr_code) {
        if(!this.tableExists) {
            return null;
        }
        String getCacheRateSQL = String.format(
            "SELECT rates.rate FROM rates WHERE rates.from_curr_code=\"%s\" AND rates.to_curr_code=\"%s\";",
            from_curr_code, to_curr_code
        );
        ArrayList<Double> result = retrieveDoubleViaSQL(getCacheRateSQL);
        if(result == null) {
            return null;
        }
        if(result.size() > 1) {
            System.out.println("Exchange rate clash occured");
            return null;
        } else if(result.size() == 0) {
            return null;
        } else {
            return result.get(0);
        }
    }

    /**
     * Check if the username already exists in users table
     */
    public boolean searchUsers(String username) {
        String searchUsersSQL = String.format(
        "SELECT username FROM users WHERE username=\"%s\";", username);
        ArrayList<String> result = retrieveStringViaSQL(searchUsersSQL);
        if(result == null) {
            return false;
        } else if(result.size() == 1) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Check if the username and pwdHash exists in table, return theme
     */
    public String login(String username, String pwdHash) {
        String searchUsersSQL = String.format(
        "SELECT theme FROM users WHERE username=\"%s\" AND password = \"%s\";",
            username, pwdHash);
        ArrayList<String> result = retrieveStringViaSQL(searchUsersSQL);
        if(result == null) {
            return null;
        } else if(result.size() == 1) {
            return result.get(0);
        } else {
            return null;
        }
    }

    /**
     * Insert username and pwdHash into users table
     */
    public void insertUser(String username, String pwdHash) {
        String insertUserSQL = String.format(
        "INSERT into users(username, password, theme) VALUES (\"%s\", \"%s\", \"white\");",
            username, pwdHash);
        if(!executeSQL(insertUserSQL)) {
            System.out.println("Could not insert user");
        }
    }

    /**
     * Update theme for user in users table
     */
    public void updateTheme(String theme, String username) {
        String updateThemeSQL = String.format(
        "UPDATE users SET theme = \"%s\" WHERE username = \"%s\";",
            theme, username);
        if(!executeSQL(updateThemeSQL)) {
            System.out.println("Could not update theme");
        }
    }

    /**
     * Insert currency into viewing table
     */
    public void insertViewCurrency(String currCode, String currName,
        String username) {
        String insertViewCurrSQL = String.format(
        "INSERT INTO viewing_currencies(curr_code, curr_name, username) VALUES (\"%s\", \"%s\", \"%s\");",
            currCode, currName, username);
        if(!executeSQL(insertViewCurrSQL)) {
            System.out.println("Could not insert currency into database");
        }
    }

    /**
     * Delete currency from viewing table
     */
    public void deleteViewCurrency(String currCode, String username) {
        String deleteViewCurrSQL = String.format(
        "DELETE FROM viewing_currencies WHERE username = \"%s\" and curr_code = \"%s\";",
            username, currCode);
        if(!executeSQL(deleteViewCurrSQL)) {
            System.out.println("Could not remove currency from database");
        }
    }

    public ArrayList<String[]> getViewingCurrencies(String username) {
        String selectViewCurrSQL = String.format(
        "SELECT curr_code, curr_name FROM viewing_currencies WHERE username = \"%s\";",
        username);
        ArrayList<String[]> results = retrieveTwoStringsViaSQL(selectViewCurrSQL);
        if(results == null){
            System.out.println("Could get user's viewing list from database");
        }
        return results;
    }
}
