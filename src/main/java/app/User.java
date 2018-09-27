package app;

import db.DBTable;

public class User implements DBTable {
    private String username;
    private String password;
    private Integer id = -1;

    private static String insertTemplate = "INSERT INTO users (username, password) VALUES ('%s', '%s');";

    public static String tableCreationQuery =
            "CREATE TABLE IF NOT EXISTS users (" +
                "id integer PRIMARY KEY AUTOINCREMENT," +
                "username text NOT NULL," +
                "password text NOT NULL," +
                "CONSTRAINT name_unique UNIQUE (username));";

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public String insertStatement() {
        return String.format(insertTemplate, username, password);
    }
}
