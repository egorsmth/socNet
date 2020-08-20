package socnet.servlet;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import socnet.entities.User;
import socnet.entities.services.UserService;
import socnet.utils.Roles;
import socnet.utils.SecurityUtils;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.Optional;

@WebServlet("/personal")
public class Personal extends PredefinedContextServlet {
    @EJB
    UserService us;

    @Perms({Roles.AUTH})
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userEncId = (String) req.getSession().getAttribute("user_id");
        Optional<Long> userId = SecurityUtils.decodeUserId(userEncId);
        if (!userId.isPresent()) {
            throw new RuntimeException("Wrong user id in session");
        }
        Optional<User> u = us.findById(userId.get());
        if (!u.isPresent()) {
            throw new RuntimeException("User not found in db");
        }

        resp.setContentType("text/html");
        Writer pw = resp.getWriter();

        Configuration cfg = (Configuration) this.getServletContext().getAttribute("templates");


        Template temp = cfg.getTemplate("personal.ftl");
        try {
            temp.process(u.get(), pw);
        } catch (TemplateException e) {
            e.printStackTrace();
        }

        pw.close();
    }
}
