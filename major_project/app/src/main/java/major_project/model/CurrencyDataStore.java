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

    public void createSchema() {
        return;
    }
}
