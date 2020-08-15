package socnet.entities;

import socnet.utils.Roles;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {
    @Id private int id;

    private String name;

    private String password;

    private Roles[] roles;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
}
