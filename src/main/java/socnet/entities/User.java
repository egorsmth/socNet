package socnet.entities;

import socnet.utils.Roles;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "soc_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Size(min = 3, max = 200, message = "Name must be between 3 and 200 characters")
    @Column(unique=true)
    private String name;

    @NotNull
    @Size(min = 3, max = 200, message = "Password must be between 3 and 200 characters")
    private String password;

    private Roles[] roles;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Roles[] getRoles() {
        return roles;
    }

    public void setRoles(Roles[] roles) {
        this.roles = roles;
    }

    @ElementCollection(targetClass=Friendship.class)
    @CollectionTable(
            name="friendship",
            joinColumns=@JoinColumn(name="user_a"))
    private Set<Friendship> sent;

    @ElementCollection(targetClass=Friendship.class)
    @CollectionTable(
            name="friendship",
            joinColumns=@JoinColumn(name="user_b"))
    private Set<Friendship> received;
}
