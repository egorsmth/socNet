package socnet.servlet;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import socnet.entities.User;
import socnet.entities.services.UserService;
import socnet.utils.SecurityUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Registration extends HttpServlet {
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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String pass = req.getParameter("password");

        UserService us = (UserService) this.getServletContext().getAttribute("userService");
        Optional<User> user = SecurityUtils.register(name, pass, us);

        if (user.isPresent()) {
            req.getSession().setAttribute("user_id", user.get().getId());
            resp.sendRedirect(req.getContextPath() + "/userInfo");
        }
    }
}
