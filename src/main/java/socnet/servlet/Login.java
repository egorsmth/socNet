package socnet.servlet;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import socnet.entities.User;
import socnet.services.AuthService;
import socnet.utils.Roles;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@WebServlet("/login")
public class Login extends PredefinedContextServlet {
    @EJB
    AuthService authService;

    @Perms({Roles.NON_AUTH})
    @Override
    protected void doGet(
            HttpServletRequest req,
            HttpServletResponse resp) throws ServletException, IOException
    {
        resp.setContentType("text/html");
        Writer pw = resp.getWriter();

        Configuration cfg = (Configuration) this.getServletContext().getAttribute("templates");

        Template temp = cfg.getTemplate("login.ftl");
        try {
            Map<String, String> o = new HashMap<>();
            o.put("actionUrl", req.getContextPath() + "/login");
            o.put("registrationUrl", req.getContextPath() + "/registration");
            temp.process(o, pw);
        } catch (TemplateException e) {
            e.printStackTrace();
        }

        pw.close();
    }

    @Perms({Roles.NON_AUTH})
    @Override
    protected void doPost(
            HttpServletRequest req,
            HttpServletResponse resp) throws ServletException, IOException
    {
        String name = req.getParameter("name");
        String pass = req.getParameter("password");

        Optional<User> user = authService.auth(name, pass);

        if (user.isPresent())
        {
            req.getSession().setAttribute("user_id", String.valueOf(user.get().getId()));
            resp.sendRedirect(req.getContextPath() + "/personal");
            return;
        }
        resp.sendRedirect("/login");
    }
}
