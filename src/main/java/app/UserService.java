package app;

import db.DBService;
import db.DBTable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserService implements DBTable<User> {
    public static String tableCreationQuery =
            "CREATE TABLE IF NOT EXISTS users (" +
                    "id integer PRIMARY KEY AUTOINCREMENT," +
                    "username text NOT NULL," +
                    "password text NOT NULL," +
                    "CONSTRAINT name_unique UNIQUE (username));";

    @Override
    public void save(User record) throws SQLException  {
        String insertTemplate = "INSERT INTO users (username, password) VALUES ('%s', '%s');";
        String statement = String.format(insertTemplate, record.getUsername(), record.getPassword());
        execute(statement);
    }

    public static List<User> getAll() throws SQLException {
        Statement s = DBService.statement();
        ResultSet result = s.executeQuery("SELECT * from users");
        return usersFromResultSet(result);
    }

    public static List<User> usersFromResultSet(ResultSet set) throws SQLException {
        if (set == null) {
            return new ArrayList<>();
        }

        List<User> users = new ArrayList<>();

        while (set.next()) {
            int id = set.getInt("id");
            String username = set.getString("username");
            String password = set.getString("password");
            users.add(new User(username, password, id));
        }

        return users;
    }
}
