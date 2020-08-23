package socnet.entities;

public class Friend {
    public User getUser() {
        return user;
    }

    public Friendship getFriendship() {
        return friendship;
    }

    private final User user;
    private final Friendship friendship;
    public Friend(User u, Friendship f) {
        user = u;
        friendship = f;
    }
    public RelationStatus getRelationStatus() {
        if (user.getId() == friendship.getUser_a()) {
            switch (friendship.getStatus()) {
                case FRIENDS:
                    return RelationStatus.FRIEND;
                case BLOCK_BY_A:
                    return RelationStatus.YOU_BLOCKED;
                case BLOCK_BY_B:
                    return RelationStatus.BLOCKED;
                case REQUEST_FROM_A:
                    return RelationStatus.REQUEST_RECEIVED;
                case REQUEST_FROM_B:
                    return RelationStatus.REQUEST_SENT;
            }
        } else {
            switch (friendship.getStatus()) {
                case FRIENDS:
                    return RelationStatus.FRIEND;
                case BLOCK_BY_A:
                    return RelationStatus.BLOCKED;
                case BLOCK_BY_B:
                    return RelationStatus.YOU_BLOCKED;
                case REQUEST_FROM_A:
                    return RelationStatus.REQUEST_SENT;
                case REQUEST_FROM_B:
                    return RelationStatus.REQUEST_RECEIVED;
            }
        }
        throw new RuntimeException("No such status");
    }

    public String getRelationStatusString() {
        switch (this.getRelationStatus()) {
            case FRIEND:
                return "FRIEND";
            case BLOCKED:
                return "BLOCKED";
            case YOU_BLOCKED:
                return "YOU_BLOCKED";
            case REQUEST_SENT:
                return "REQUEST_SENT";
            case REQUEST_RECEIVED:
                return "REQUEST_RECEIVED";
            default:
                return "VOID";
        }
    }
}
