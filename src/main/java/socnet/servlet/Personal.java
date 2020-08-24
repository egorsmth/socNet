package socnet.servlet;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import socnet.entities.Friend;
import socnet.entities.Friendship;
import socnet.entities.User;
import socnet.services.UserService;
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
import java.util.Set;

@WebServlet("/personal")
public class Personal extends PredefinedContextServlet {
    @EJB
    UserService us;

    @Perms({Roles.AUTH})
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User u = getSessionUser(req);

        resp.setContentType("text/html");
        Writer pw = resp.getWriter();
        Configuration cfg = (Configuration) this.getServletContext().getAttribute("templates");
        Template temp = cfg.getTemplate("personal.ftl");
        try {
            Map<String, Object> dict = new HashMap<>();
            dict.put("user", u);
            dict.put("logoutUrl", req.getContextPath() + "/logout");
            dict.put("homeUrl", req.getContextPath() + "/personal");

            Set<Friend> friends = us.getFriends(u);
            dict.put("friends", friends);
            temp.process(dict, pw);
        } catch (TemplateException e) {
            e.printStackTrace();
        }

        pw.close();
    }
}
