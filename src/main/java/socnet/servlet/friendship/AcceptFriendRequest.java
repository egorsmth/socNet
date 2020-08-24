package socnet.servlet.friendship;

import socnet.entities.User;
import socnet.services.FriendsActionService;
import socnet.services.UserService;
import socnet.servlet.Perms;
import socnet.servlet.PredefinedContextServlet;
import socnet.utils.Roles;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class AcceptFriendRequest extends PredefinedContextServlet {
    @EJB
    UserService us;

    @EJB
    FriendsActionService fas;

    @Perms({Roles.AUTH})
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = getSessionUser(req);

        String otherId = req.getParameter("userId");
        Optional<User> optTo = us.findById(Long.parseLong(otherId));
        if (optTo.isPresent()) {
            fas.acceptRequest(optTo.get(), user);
        } else {
            throw new RuntimeException("Other user not found in db");
        }

        resp.sendRedirect("personal");
    }
}
