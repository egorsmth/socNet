package socnet.utils;

import socnet.entities.User;
import socnet.entities.services.UserService;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;

public class SecurityUtils {
    public static Optional<Integer> decodeUserId(String userIdEncrypted) {
        // TODO: use cryptography
        if (userIdEncrypted == null) {
            return Optional.empty();
        }
        return Optional.of(Integer.parseInt(userIdEncrypted));
    }

    public static boolean getPagePermission(HttpServletRequest req, Optional<User> user) {
        Map<String, Roles[]> perms = (Map<String, Roles[]>) req.getServletContext().getAttribute("permissions");
        String n = req.getHttpServletMapping().getServletName();
        n += "-" + req.getMethod();
        if (perms.containsKey(n)) {
            Roles[] roles = perms.get(n);
            HashSet<Roles> set1 = new HashSet<>(Arrays.asList(roles));
            HashSet<Roles> set2 = new HashSet<>();
            user.ifPresent(u -> set2.addAll(Arrays.asList(u.getRoles())));
            set1.retainAll(set2);
            if (user.isPresent() && !set1.isEmpty()) {
                return true;
            } else if (Arrays.asList(roles).contains(Roles.ANY)) {
                return true;
            } else {
                return false;
            }
        }
        return true;
    }

    public static Optional<User> auth(String name, String pass, UserService us) {
        User u = us.findOne(name, pass);
        return Optional.of(u);
    }

    public static Optional<User> register(String name, String pass, UserService us) {
        // TODO: 8/12/2020 encrypt password
        User u = us.createUser(1, name, pass, new Roles[]{Roles.AUTH});
        return Optional.of(u);
    }

}
