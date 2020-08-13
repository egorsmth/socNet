package socnet.utils;

import socnet.entities.User;
import socnet.entities.services.UserService;

import java.util.NoSuchElementException;
import java.util.Optional;

public class SecurityUtils {
    public static Optional<Integer> decodeUserId(String userIdEncrypted) {
        // TODO: use cryptography
        if (userIdEncrypted == null) {
            return Optional.empty();
        }
        return Optional.of(Integer.parseInt(userIdEncrypted));
    }

    public static boolean getPagePermission(String path, Optional<User> user) {
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
