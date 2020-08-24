package socnet.servlet;

import socnet.entities.User;
import socnet.utils.PermissionUtils;
import socnet.utils.Roles;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Map;

public class PredefinedContextServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        ServletContext ctx = this.getServletContext();

        Map<String, Roles[]> perms = (Map<String, Roles[]>) ctx.getAttribute("permissions");
        Method m = null;
        Perms a = null;
        final Class[] sFormalArgs = {HttpServletRequest.class, HttpServletResponse.class};
        final String[] mainRestMethods = new String[]{"doGet", "doPost"};
        for (String method : mainRestMethods) {
            try {
                m = this.getClass().getDeclaredMethod(method, sFormalArgs);
                a = m.getAnnotation(Perms.class);
                if (a == null) continue;
                Roles[] value = a.value();
                String n = PermissionUtils.getServletPermissionName(this.getServletName(), method);
                perms.put(n, value);
            } catch (NoSuchMethodException ignored) { }
        }
        ctx.setAttribute("permissions", perms);
    }

    protected User getSessionUser(HttpServletRequest req) {
        Object user = req.getAttribute("user_obj");
        if (!(user instanceof User)) {
            throw new RuntimeException("User not found in session");
        }
        return (User) user;
    }
}
