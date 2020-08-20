package socnet.entities.services;

import socnet.entities.User;
import socnet.utils.Roles;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Stateless
public class UserService {
    @PersistenceContext(unitName = "UserService")
    protected EntityManager em;

    public User createUser(String name, String password, Roles[] roles) {
        User u = new User();
        u.setName(name);
        u.setPassword(password);
        u.setRoles(roles);
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
        // TODO: 8/12/2020 make query 
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
}
