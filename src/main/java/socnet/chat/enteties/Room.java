package socnet.chat.enteties;

import socnet.entities.User;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(targetEntity = Message.class, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "room_id")
    @OrderBy("created_at")
    private List<Message> messageList;

    @ManyToMany(mappedBy = "chatRooms")
    private Set<User> users;

    private Timestamp lastMessageAt;

    @Enumerated
    private RoomStatus status;
}
