package socnet.servlet;

import socnet.utils.Roles;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Map;

public class PredefinedContextServlet extends HttpServlet {
    private String methodToHttp(String method) {
        switch (method) {
            case "doGet":
                return "GET";
            case "doPost":
                return "POST";
        }
        return "";
    }
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
                perms.put(this.getServletName() + "-" + methodToHttp(method), value);
            } catch (NoSuchMethodException ignored) { }
        }
        ctx.setAttribute("permissions", perms);
    }
}
