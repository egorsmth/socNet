package socnet.filter;

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
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        String path = req.getServletPath();

        Optional<User> user = Optional.empty();
        String id = (String) req.getSession().getAttribute("user_id");
        if (id != null) {
            Optional<Integer> userId = SecurityUtils.decodeUserId(id);
            if (userId.isPresent()) {
                UserService userService = (UserService) req.getServletContext().getAttribute("userService");
                user = userService.findById(userId.get());
            }
        }

        boolean canVisit = SecurityUtils.getPagePermission(path, user);
        if (canVisit) {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}
