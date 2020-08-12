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
        int userId = Integer.parseInt(SecurityUtils.getUserId(req.getSession()));
        UserService userService = (UserService) req.getServletContext().getAttribute("userService");
        Optional<User> user = userService.findById(userId);

        if (path.startsWith("/login") && user.isPresent())
        {
            res.sendRedirect(req.getContextPath() + "/userInfo");
            return;
        }

        if (!user.isPresent())
        {
            res.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        boolean canVisit = SecurityUtils.getPagePermission(path, user.get());
        if (canVisit) {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}
