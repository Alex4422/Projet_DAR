package db;

import app.App;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBService {
    private static Connection connexion;

    public static Connection getConnexion() {
        if (connexion == null) {
            initConnexion();
        }
        return connexion;
    }

    private static void initConnexion() {
        loadDriverClass();
        try {
            connexion = DriverManager.getConnection(App.dbUrl());
            connexion.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void loadDriverClass() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void closeConnection() {
        try {
            connexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
