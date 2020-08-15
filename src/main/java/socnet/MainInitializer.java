package socnet;

import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;
import org.apache.log4j.PropertyConfigurator;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

@WebListener
public class MainInitializer implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent event) {
        ServletContext context = event.getServletContext();
        String log4jConfigFile = "WEB-INF/log4j.properties";
        String fullPath = context.getRealPath("") + File.separator + log4jConfigFile;

        PropertyConfigurator.configure(fullPath);

        Configuration cfg = new Configuration(Configuration.VERSION_2_3_29);
        cfg.setServletContextForTemplateLoading(context, "WEB-INF/templates");
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
        cfg.setLogTemplateExceptions(false);
        cfg.setWrapUncheckedExceptions(true);
        cfg.setFallbackOnNullLoopVariable(false);
        context.setAttribute("templates", cfg);

        Map<String, String[]> perms = new HashMap<>();
        context.setAttribute("permissions", perms);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }

//    private Connection connectDb()
//    {
//        Connection c = null;
//        String s = "SELECT name FROM public.users";
//
//        try
//        {
//            Class.forName("org.postgresql.Driver");
//            c = DriverManager.getConnection(
//                    "jdbc:postgresql://localhost:5432/testdb",
//                    "admin", "1234");
//            System.out.println("Database Connected ..");
//
//            try (
//                    Statement stmt = c.createStatement();
//                    ResultSet r = stmt.executeQuery(s);)
//            {
//
//                while (r.next()) {
//                    System.out.println(r.getString(1));
//                }
//            }
//
//        } catch (Exception e) {
//            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
//            System.exit(0);
//        }
//        return c;
//    }
}
