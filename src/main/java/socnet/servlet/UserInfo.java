package socnet.servlet;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
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
import java.util.Optional;

@WebServlet("/userInfo")
public class UserInfo extends PredefinedContextServlet {
    @EJB
    UserService us;

    @Perms({Roles.AUTH})
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = getSessionUser(req);

        long userId = Long.parseLong(req.getParameter("userId"));
        Optional<User> u = us.findById(userId);
        if (!u.isPresent()) {
            throw new RuntimeException("Other user not found in db");
        }

        resp.setContentType("text/html");
        Writer pw = resp.getWriter();
        Configuration cfg = (Configuration) this.getServletContext().getAttribute("templates");
        Template temp = cfg.getTemplate("userInfo.ftl");
        try {
            Map<String, Object> dict = new HashMap<>();
            dict.put("user", user);
            dict.put("other_user", u.get());
            dict.put("logoutUrl", req.getContextPath() + "/logout");
            dict.put("homeUrl", req.getContextPath() + "/personal");
            // TODO: add logic to handle friendship
            dict.put("friendRequestUrl", req.getContextPath() + "/sendFriendRequest?userId=" + u.get().getId());
            temp.process(dict, pw);
        } catch (TemplateException e) {
            e.printStackTrace();
        }

        pw.close();
    }
}
