import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

public class HelloTest extends HttpServlet {
    static final Logger LOGGER = LoggerFactory.getLogger(String.valueOf(HelloTest.class));


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        LOGGER.info("request received");
        resp.setContentType("text/html");
        Writer pw = resp.getWriter();

        Configuration cfg = new Configuration(Configuration.VERSION_2_3_29);
        cfg.setServletContextForTemplateLoading(this.getServletContext(), "WEB-INF");
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
        cfg.setLogTemplateExceptions(false);
        cfg.setWrapUncheckedExceptions(true);
        cfg.setFallbackOnNullLoopVariable(false);

        User u = new User("average Joe", new Product("simple product"));
        Template temp = cfg.getTemplate("test.ftl");
        try {
            temp.process(u, pw);
        } catch (TemplateException e) {
            e.printStackTrace();
        }

        pw.close();
    }
}
