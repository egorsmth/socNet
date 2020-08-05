package socnet.servlet;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import socnet.User;
import socnet.db.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/login")
public class Login extends HttpServlet {
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
            temp.process(o, pw);
        } catch (TemplateException e) {
            e.printStackTrace();
        }

        pw.close();
    }

    @Override
    protected void doPost(
            HttpServletRequest req,
            HttpServletResponse resp) throws ServletException, IOException
    {
        String name = req.getParameter("name");
        String pass = req.getParameter("password");

        UserDAO userDAO = new UserDAO((Connection) this.getServletContext().getAttribute("connectionDb"));
        User user = userDAO.auth(name, pass);
        if (user != null)
        {
            req.getSession().setAttribute("user_id", user.getId());
            resp.sendRedirect(req.getContextPath() + "/userInfo");
        }
    }
}
