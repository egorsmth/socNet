package socnet.services;

import socnet.entities.Friend;
import socnet.entities.Friendship;
import socnet.entities.FriendshipStatus;
import socnet.entities.User;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.*;
import java.util.*;

@SqlResultSetMapping(
        name = "FriendResultSet",
        entities = {
                @EntityResult(
                        entityClass = User.class,
                        fields={
                                @FieldResult(name="id",column="id"),
                                @FieldResult(name="name", column="name"),
                        }
                ),
                @EntityResult(
                        entityClass = Friendship.class,
                        fields={
                                @FieldResult(name="user_a",column="user_a"),
                                @FieldResult(name="user_b",column="user_b"),
                                @FieldResult(name="status", column="status"),
                        }
                ),
        }
)
@Stateless
@LocalBean
public class UserService {
    @PersistenceContext(unitName = "UserService")
    protected EntityManager em;

    public User createUser(User u) {
        em.persist(u);
        return u;
    }

    public void removeUser(long id) {
        Optional<User> u = this.findById(id);
        u.ifPresent(user -> em.remove(user));
    }

    public Optional<User> findById(long id) {
        User u = em.find(User.class, id);
        return Optional.of(u);
    }

    public List<User> findAll() {
        TypedQuery<User> query = em.createQuery(
                "SELECT e FROM User e", User.class);
        return query.getResultList();
    }

    public User findOne(String name, String pass) {
        Query query = em.createQuery(
                "SELECT e FROM User e WHERE e.name = :name AND e.password = :pass",
                User.class)
                .setParameter("name", name)
                .setParameter("pass", pass);
        List res = query.getResultList();
        if (res.isEmpty()) {
            return null;
        }
        return (User) res.get(0);
    }


    public Set<Friend> getFriends(User u) {
        Query query = em.createNativeQuery(
                "SELECT id, name, user_a, user_b, status FROM soc_user as u JOIN friendship as f ON f.user_a = ?1 OR f.user_b = ?1 " +
                       "WHERE u.id <> ?1",
                "FriendResultSet");
        query = query.setParameter(1, u.getId());

        List<Object[]> results = query.getResultList();
        Set<Friend> hs = new HashSet<>();
        for (Object[] x : results) {
            User uu = new User((Integer) x[0], (String) x[1]);
            Friendship f = new Friendship(
                    (Long) x[2],
                    (Long) x[3],
                    FriendshipStatus.values()[(Integer)x[4]]);
            hs.add(new Friend(uu, f));
        }
        return hs;
    }
}
