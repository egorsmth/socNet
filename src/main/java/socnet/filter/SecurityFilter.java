package socnet.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.NoSuchElementException;

import socnet.User;

import socnet.db.UserDAO;
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
        String userId = SecurityUtils.getUserId(req.getSession());
        UserDAO userDAO = new UserDAO((Connection) req.getServletContext().getAttribute("dbConnection"));
        User user = userDAO.findById(userId);

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
