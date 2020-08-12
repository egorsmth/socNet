package socnet.utils;

import socnet.entities.User;
import socnet.entities.services.UserService;

import javax.servlet.http.HttpSession;
import java.util.NoSuchElementException;
import java.util.Optional;

public class SecurityUtils {
    public static String getUserId(HttpSession session) {
        // TODO: use cryptography
        String userIdEncrypted = (String) session.getAttribute("user_id");
        if (userIdEncrypted == null) {
            throw new NoSuchElementException();
        }
        return userIdEncrypted;
    }

    public static boolean getPagePermission(String path, User user) {
        // TODO: implement permission management
        return true;
    }

    public static Optional<User> auth(String name, String pass, UserService us) {
        User u = us.findOne(name, pass);
        return Optional.of(u);
    }

    public static Optional<User> register(String name, String pass, UserService us) {
        // TODO: 8/12/2020 encrypt password
        User u = us.createUser(1, name, pass);
        return Optional.of(u);
    }
}
