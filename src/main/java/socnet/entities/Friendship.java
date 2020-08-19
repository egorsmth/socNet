package socnet.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Friendship implements Serializable {
    @Id private long id;
    private long user_a;
    private long user_b;
    private FriendshipStatus status;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUser_a() {
        return user_a;
    }

    public void setUser_a(long user_a) {
        this.user_a = user_a;
    }

    public long getUser_b() {
        return user_b;
    }

    public void setUser_b(long user_b) {
        this.user_b = user_b;
    }

    public FriendshipStatus getStatus() {
        return status;
    }

    public void setStatus(FriendshipStatus status) {
        this.status = status;
    }
}
