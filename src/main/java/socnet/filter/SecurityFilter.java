package socnet.filter;

import javax.ejb.EJB;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import socnet.entities.User;
import socnet.entities.services.UserService;
import socnet.utils.SecurityUtils;

@WebFilter("/*")
public class SecurityFilter implements Filter {
    @EJB
    UserService us;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(
            ServletRequest servletRequest,
            ServletResponse servletResponse,
            FilterChain filterChain
    ) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;

        Optional<User> user = Optional.empty();
        String id = (String) req.getSession().getAttribute("user_id");
        if (id != null) {
            Optional<Long> userId = SecurityUtils.decodeUserId(id);
            if (userId.isPresent()) {
                user = us.findById(userId.get());
            }
        }

        boolean canVisit = SecurityUtils.getPagePermission(req, user);
        if (canVisit) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        if (!user.isPresent()) {
            ((HttpServletResponse) servletResponse).sendRedirect("login");
        } else {
            ((HttpServletResponse) servletResponse).sendRedirect("personal");
        }
    }
}
