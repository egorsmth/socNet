package socnet.servlet;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

@WebServlet("/userInfo")
public class UserInfo extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        Writer pw = resp.getWriter();

        Configuration cfg = (Configuration) this.getServletContext().getAttribute("templates");


        Template temp = cfg.getTemplate("userInfo.ftl");
        try {
            temp.process(null, pw);
        } catch (TemplateException e) {
            e.printStackTrace();
        }

        pw.close();
    }
}
