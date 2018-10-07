package app;

public class App {
    public static String MOVIES_API_KEY = "c4535994aaf14f4dd3320a8a24923598";

    public static String dbUrl() {
        String url = System.getenv("JDBC_DATABASE_URL");
        if (url == null) {
            url = "jdbc:h2:./localdb";
        }
        return url;
    }

    public static String dbClass() {
        String dbms = getDBMS();
        switch (dbms) {
            case "h2":
                return "org.h2.Driver";
            case "postgresql":
                return "org.postgresql.Driver";
            default:
                throw new RuntimeException("Unsupported DBMS in database url !");
        }
    }

    public static String dbDialect() {
        String dbms = getDBMS();
        String base = "org.hibernate.dialect.";
        switch (dbms) {
            case "h2":
                return base + "H2Dialect";
            case "postgresql":
                return base + "PostgreSQLDialect";
            default:
                throw new RuntimeException("Unsupported DBMS in database url !");
        }
    }

    private static String getDBMS() {
        return dbUrl().split(":")[1];
    }
}
