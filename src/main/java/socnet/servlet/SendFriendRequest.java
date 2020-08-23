package socnet.servlet;

import socnet.entities.User;
import socnet.services.FriendsActionService;
import socnet.services.UserService;
import socnet.utils.Roles;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/sendFriendRequest")
public class SendFriendRequest extends PredefinedContextServlet {
    @EJB
    FriendsActionService fas;

    @EJB
    UserService us;

    @Perms({Roles.AUTH})
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User from = (User) req.getAttribute("user_obj");
        if (from == null) {
            throw new RuntimeException("User not found in session");
        }

        String otherId = req.getParameter("userId");
        Optional<User> optTo = us.findById(Long.parseLong(otherId));
        if (optTo.isPresent()) {
            fas.sendRequest(from, optTo.get());
            resp.setStatus(HttpServletResponse.SC_OK);
        } else {
            throw new RuntimeException("Other user not found in db");
        }
    }
}
