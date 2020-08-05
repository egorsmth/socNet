package socnet.db;

import socnet.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class UserDAO {
    private final Connection c;
    public UserDAO(Connection dbconn) {
        this.c = dbconn;
    }

    public Optional<User> findById(String id) {
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
        return Optional.of(u);
    }

    public Optional<User> auth(String qname, String pass) {
        User u = null;
        try (
                Statement stmt = c.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT id, name from users where name=" + qname + " and password=" + pass)
        ) {
            String name = rs.getString("name");
            String id = rs.getString("id");
            u = new User(id, name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.of(u);
    }
}
