package socnet;

import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import socnet.db.Db;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

public class MainInitializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        ServletContext context = event.getServletContext();
        String log4jConfigFile = context.getInitParameter("log4j-config-location");
        String fullPath = context.getRealPath("") + File.separator + log4jConfigFile;

        PropertyConfigurator.configure(fullPath);
        Logger Logger = LoggerFactory.getLogger(String.valueOf(MainInitializer.class));
        try {
            Db.Init("localhost:5432", "admin", "1234", "testdb");
        } catch (ClassNotFoundException | SQLException e) {
            Logger.error(e.getMessage());
            System.exit(e.hashCode());
        }
        Connection c = Db.getConnection();
        context.setAttribute("dbConnection", c);

        Configuration cfg = new Configuration(Configuration.VERSION_2_3_29);
        cfg.setServletContextForTemplateLoading(context, "WEB-INF");
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
        cfg.setLogTemplateExceptions(false);
        cfg.setWrapUncheckedExceptions(true);
        cfg.setFallbackOnNullLoopVariable(false);
        context.setAttribute("templates", cfg);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        Connection c = Db.getConnection();
        Logger Logger = LoggerFactory.getLogger(String.valueOf(MainInitializer.class));

        try {
            c.close();
        } catch (SQLException e) {
            Logger.error(e.getMessage());
            System.exit(e.hashCode());
        }
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
