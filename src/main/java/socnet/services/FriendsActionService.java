package socnet.services;

import socnet.entities.Friendship;
import socnet.entities.FriendshipId;
import socnet.entities.FriendshipStatus;
import socnet.entities.User;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Local
@Stateless
public class FriendsActionService {
    @PersistenceContext(unitName = "UserService")
    protected EntityManager em;

    public void sendRequest(User from, User to) {
        Friendship fs = new Friendship();
        if (from.getId() < to.getId()) {
            fs.setUser_a(from.getId());
            fs.setUser_b(to.getId());
            fs.setStatus(FriendshipStatus.REQUEST_FROM_A);
        } else {
            fs.setUser_a(to.getId());
            fs.setUser_b(from.getId());
            fs.setStatus(FriendshipStatus.REQUEST_FROM_B);
        }

        em.persist(fs);
    }

    public void acceptRequest(User from, User to) {
        FriendshipId fid;
        if (from.getId() < to.getId()) {
            fid = new FriendshipId(from.getId(), to.getId());

        } else {
            fid = new FriendshipId(to.getId(), from.getId());
        }

        Friendship fs = em.find(Friendship.class, fid);
        fs.setStatus(FriendshipStatus.FRIENDS);
        em.persist(fs);
    }

    public void unfriend(User from, User to) {
        FriendshipId fid;
        if (from.getId() < to.getId()) {
            fid = new FriendshipId(from.getId(), to.getId());

        } else {
            fid = new FriendshipId(to.getId(), from.getId());
        }

        Friendship fs = em.find(Friendship.class, fid);
        em.remove(fs);
    }
}
