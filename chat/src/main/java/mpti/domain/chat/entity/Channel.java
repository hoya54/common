package mpti.domain.chat.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Channel {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "channel_id")
    private Long id;

    private Long trainerId;
    private Long userId;

//    @OneToMany(mappedBy = "channel")
//    private List<Message> messages = new ArrayList<>();

    public Channel(Long trainerId, Long userId) {
        this.trainerId = trainerId;
        this.userId = userId;
    }
}
