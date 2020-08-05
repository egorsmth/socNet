package socnet.db;

import socnet.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDAO {
    private final Connection c;
    public UserDAO(Connection dbconn) {
        this.c = dbconn;
    }

    public User findById(String id) {
        User u = null;
        try (
                Statement stmt = c.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT name from users where id=" + id)
        ) {
            String name = rs.getString("name");
            u = new User(name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return u;
    }
}
