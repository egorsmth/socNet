package socnet.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@IdClass(FriendshipId.class)
public class Friendship implements Serializable {
    @Id
    private long user_a;
    @Id
    private long user_b;

    private FriendshipStatus status;

    public Friendship() {}
    public Friendship(long user_a, long user_b, FriendshipStatus status) {
        this.user_a = user_a;
        this.user_b = user_b;
        this.status = status;
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
