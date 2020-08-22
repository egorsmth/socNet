package socnet.entities;

import java.io.Serializable;

public class FriendshipId implements Serializable {


    private long user_a;
        private long user_b;
        public FriendshipId() {}
        public FriendshipId(long user_a, long user_b) {
            this.user_a = user_a;
            this.user_b = user_b;
        }

    public long getUser_a() {
        return user_a;
    }


    public long getUser_b() {
        return user_b;
    }

        public boolean equals(Object o) {
            return ((o instanceof FriendshipId) &&
                    user_a == ((FriendshipId) o).getUser_a() &&
                    user_b == ((FriendshipId) o).getUser_b());
        }
        public int hashCode() {
            return (int) ((int) user_a + user_b);
        }
    }
