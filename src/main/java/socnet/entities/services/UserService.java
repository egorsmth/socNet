package socnet.entities.services;

import socnet.entities.User;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

public class UserService {
    protected EntityManager em;

    public UserService(EntityManager em) {
        this.em = em;
    }

    public User createUser(int id, String name, String password) {
        User u = new User();
        u.setId(id);
        u.setName(name);
        u.setPassword(password);
        em.persist(u);
        return u;
    }

    public void removeUser(int id) {
        Optional<User> u = this.findById(id);
        u.ifPresent(user -> em.remove(user));
    }

    public Optional<User> findById(int id) {
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
        TypedQuery<User> query = em.createQuery(
                "SELECT * FROM User WHERE name= pass=", User.class);
        return query.getResultList();
    }
}