package socnet.servlet.auth;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import socnet.entities.User;
import socnet.services.AuthService;
import socnet.services.UserService;
import socnet.servlet.Perms;
import socnet.servlet.PredefinedContextServlet;
import socnet.utils.Either;
import socnet.utils.Roles;
import socnet.utils.SecurityUtils;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@WebServlet("/registration")
public class Registration extends PredefinedContextServlet {
    @EJB
    AuthService authService;

    @Perms({Roles.NON_AUTH})
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        Writer pw = resp.getWriter();
        Configuration cfg = (Configuration) this.getServletContext().getAttribute("templates");
        Template temp = cfg.getTemplate("registration.ftl");
        try {
            Map<String, String> o = new HashMap<>();
            o.put("actionUrl", req.getContextPath() + "/registration");
            temp.process(o, pw);
        } catch (TemplateException e) {
            e.printStackTrace();
        }

        pw.close();
    }

    @Perms({Roles.NON_AUTH})
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String name = req.getParameter("name");
        String pass = req.getParameter("password");

        Either<User, Set<ConstraintViolation<User>>> either = authService.register(name, pass);
        if (either.isLefty()) {
            req.getSession().setAttribute("user_id", SecurityUtils.encodeUserId(either.left().getId()));
            res.sendRedirect(req.getContextPath() + "/personal");
        } else {
            StringBuilder errors = new StringBuilder("<ul>");
            for (ConstraintViolation<User> constraintViolation : either.right()) {
                errors
                        .append("<li>")
                        .append("<b>")
                        .append(constraintViolation.getPropertyPath())
                        .append("</b>")
                        .append(" ")
                        .append(constraintViolation.getMessage())
                        .append("</li>");
            }
            errors.append("</ul>");
            res.setContentType("text/html");
            Writer pw = res.getWriter();
            Configuration cfg = (Configuration) this.getServletContext().getAttribute("templates");
            Template temp = cfg.getTemplate("registration.ftl");
            try {
                Map<String, String> o = new HashMap<>();
                o.put("actionUrl", req.getContextPath() + "/registration");
                o.put("name", name);
                o.put("errors", errors.toString());
                temp.process(o, pw);
            } catch (TemplateException e) {
                e.printStackTrace();
            }

            pw.close();
        }
    }
}
