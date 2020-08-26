package socnet.chat.enteties;

import socnet.entities.User;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Message {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String content;

    @OneToOne(targetEntity = User.class)
    private long authorId;

    @Column(name = "created_at")
    private Timestamp createdAt;
}
