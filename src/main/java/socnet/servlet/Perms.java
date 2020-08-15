package socnet.servlet;

import socnet.utils.Roles;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Perms {
    Roles[] value();
}
