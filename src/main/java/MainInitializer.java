import org.apache.log4j.PropertyConfigurator;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MainInitializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        SIng s = SIng.init();
        s.setA("from main");

        ServletContext context = event.getServletContext();
        String log4jConfigFile = context.getInitParameter("log4j-config-location");
        String fullPath = context.getRealPath("") + File.separator + log4jConfigFile;

        PropertyConfigurator.configure(fullPath);

        connectDb();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

    private void connectDb()
    {
        Connection c = null;
        String s = "SELECT name FROM public.users";

        try
        {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/testdb",
                    "admin", "1234");
            System.out.println("Database Connected ..");

            try (
                    Statement stmt = c.createStatement();
                    ResultSet r = stmt.executeQuery(s);)
            {

                while (r.next()) {
                    System.out.println(r.getString(1));
                }
            }
           c.close();

        } catch (Exception e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }


        System.out.println("Table Created successfully");
    }
}
