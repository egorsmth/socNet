package socnet.services;

import socnet.entities.Friendship;
import socnet.entities.User;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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

    public Set<User> getFriends(User u) {
        Query query = em.createQuery(
                "SELECT u FROM User u JOIN Friendship f ON f.user_a = :cur OR f.user_b = :cur WHERE u.id != :cur",
                User.class)
                .setParameter("cur", u.getId());
        List res = query.getResultList();
        return new HashSet<User>(res);
    }
}
