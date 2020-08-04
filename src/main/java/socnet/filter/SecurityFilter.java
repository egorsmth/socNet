package socnet.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.NoSuchElementException;

import socnet.User;
import socnet.db.DataDAO;
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
        User user;
        try {
            String userId = SecurityUtils.getUserId(req.getSession());
            user = DataDAO.instance().findUser(userId);
        } catch (NoSuchElementException e) {
            user = null;
        }

        if (path.startsWith("/login") && user != null)
        {
            res.sendRedirect(req.getContextPath() + "/userInfo");
            return;
        }

        boolean canVisit = SecurityUtils.getPagePermission(path, user);
        if (canVisit) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        res.sendRedirect(req.getContextPath() + "/login");
    }
}
