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
            createSchema();
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
     * Same as above for queries that do return results
     */
    public ArrayList<Double> retreiveViaSQL(String sqlString) {
        try (Connection conn = DriverManager.getConnection(dbURL);
             Statement statement = conn.createStatement()) {
            ResultSet result = statement.executeQuery(sqlString);
            ArrayList<Double> resultsList = new ArrayList<Double>();
            while (result.next()) {
                resultsList.add(result.getDouble(1));//Get int
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
    public void createSchema() {
        String schemaSQL = """
        CREATE TABLE IF NOT EXISTS rates(
          from_curr_code TEXT,
          to_curr_code TEXT,
          rate Double,
          PRIMARY KEY(from_curr_code, to_curr_code)
        );
        """;

        if(!executeSQL(schemaSQL)) {
            System.out.println("Couldn't create Schema");
        } else {
            this.tableExists = true;
        }
    }

    /**
     * Insert a new rate into cache table
     */
    public void insertRate(String from_curr_code, String to_curr_code,
        Double rate) {
        if(!this.tableExists) {
            createSchema();//Force table creation after deletion
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
        ArrayList<Double> result = retreiveViaSQL(getCacheRateSQL);
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
}
