package socnet.utils;

import socnet.User;

import javax.servlet.http.HttpSession;
import java.util.NoSuchElementException;

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
}
