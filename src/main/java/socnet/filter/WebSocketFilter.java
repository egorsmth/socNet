package socnet.filter;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import socnet.entities.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter
public class WebSocketFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain) throws IOException, ServletException {
        User u = (User) req.getAttribute("user_obj");
        if (u == null) {
            filterChain.doFilter(req, res);
        }
        String QUEUE_NAME = "ws-notification-" + u.getId();
        if (req.getServletContext().getAttribute(QUEUE_NAME) != null) {
            filterChain.doFilter(req, res);
        }

        Connection connection = (Connection) req.getServletContext().getAttribute("queueConnection");
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, true, true, true, null);
        req.getServletContext().setAttribute(QUEUE_NAME, channel);
    }

    @Override
    public void destroy() {

    }
}
