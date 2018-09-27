package db;

import java.sql.SQLException;
import java.sql.Statement;

public interface DBTable {
    String insertStatement();

    default void save() throws SQLException {
        save(DBService.getConnexion().createStatement());
    }

    default void save(Statement statement) throws SQLException {
        statement.execute(insertStatement());
    }
}
