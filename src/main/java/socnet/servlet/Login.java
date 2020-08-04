package socnet.servlet;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import socnet.Product;
import socnet.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
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

        // TODO: Make singleton or DI
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_29);
        cfg.setServletContextForTemplateLoading(this.getServletContext(), "WEB-INF");
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
        cfg.setLogTemplateExceptions(false);
        cfg.setWrapUncheckedExceptions(true);
        cfg.setFallbackOnNullLoopVariable(false);

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

        if (name != null && pass != null)
        {
            resp.sendRedirect(req.getContextPath() + "/test");
        }
    }
}
