package db;

import app.App;
import app.User;
import app.UserService;
import db.errors.UninitializedException;
import servlet.Users;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBService {
    private static Connection connexion;
    private static String dbUrl = "";

    public static void init(String url) {
        dbUrl = url;
        initConnexion();
        try {
            createTables();
            connexion.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnexion() throws UninitializedException {
        if (dbUrl.isEmpty()) {
            dbUrl = App.dbUrl();
        }
        if (connexion == null) {
            initConnexion();
        }
        return connexion;
    }

    private static void initConnexion() {
        loadDriverClass();
        try {
            connexion = DriverManager.getConnection(dbUrl);
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

    private static void createTables() throws SQLException {
        Statement s = connexion.createStatement();
        s.execute(UserService.tableCreationQuery);
    }

    public static void closeConnection() {
        try {
            connexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Statement statement() {
        try {
            return getConnexion().createStatement();
        } catch (SQLException | UninitializedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
