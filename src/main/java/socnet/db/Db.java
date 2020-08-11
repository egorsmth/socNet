package socnet.db;

import java.sql.*;

public class Db {

    private Db() {}

    private static Connection c;

    public static void Init(String host, String user, String pass, String db) throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        c = DriverManager.getConnection(
                "jdbc:postgresql://" + host + "/" + db,
                user,
                pass);
    }
    public static Connection getConnection() {
        if (c == null)
        {
            throw new IllegalStateException("Connection not initialized");
        }
        return c;
    }
}
