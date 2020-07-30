import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

public class HelloTest extends HttpServlet {
    static final Logger LOGGER = Logger.getLogger(String.valueOf(HelloTest.class));


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        LOGGER.info("request received");
        resp.setContentType("text/html");
        PrintWriter pw = resp.getWriter();
        pw.println("Hello mister");

        SIng s = SIng.init();
        s.setA("From servlet");
        pw.println(s.getA());

        pw.close();
    }
}
