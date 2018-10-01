package db;

import db.errors.UninitializedException;

import java.sql.SQLException;
import java.sql.Statement;

public interface DBTable<T> {
    void save(T record) throws SQLException, UninitializedException;

    default void execute(String statement) throws SQLException {
        execute(statement, DBService.statement());
    }

    default void execute(String strStatement, Statement s) throws SQLException {
        s.execute(strStatement);
    }
}
