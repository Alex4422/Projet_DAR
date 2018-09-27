package app;

public class App {
    public static String MOVIES_API_KEY = "c4535994aaf14f4dd3320a8a24923598";

    public static String dbUrl() {
        String url = System.getenv("JDBC_DATABASE_URL");
        if (url.isEmpty()) {
            url = "jdbc:sqlite:localdb.sql";
        }
        return url;
    }
}
