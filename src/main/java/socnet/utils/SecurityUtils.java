package socnet.utils;

import socnet.entities.User;


import java.util.*;

public class SecurityUtils {
    public static Optional<Long> decodeUserId(String userIdEncrypted) {
        // TODO: use cryptography
        if (userIdEncrypted == null) {
            return Optional.empty();
        }
        return Optional.of(Long.parseLong(userIdEncrypted));
    }

    public static boolean getPagePermission(Map<String, Roles[]> perms, String permissionName, Optional<User> user) {
        if (perms.containsKey(permissionName)) {
            Roles[] roles = perms.get(permissionName);
            HashSet<Roles> set1 = new HashSet<>(Arrays.asList(roles));
            HashSet<Roles> set2 = new HashSet<>();
            user.ifPresent(u -> set2.addAll(Arrays.asList(u.getRoles())));
            set1.retainAll(set2);
            if (user.isPresent() && !set1.isEmpty()) {
                return true;
            } else if (Arrays.asList(roles).contains(Roles.ANY)) {
                return true;
            } else if (!user.isPresent() && Arrays.asList(roles).contains(Roles.NON_AUTH)) {
                return true;
            } else {
                return false;
            }
        }
        return true;
    }

    public static String encodeUserId(long id) {
        // TODO: encrypt user id
        return String.valueOf(id);
    }
}
