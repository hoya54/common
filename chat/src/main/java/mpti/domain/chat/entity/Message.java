package mpti.domain.chat.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
public class Message {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long id;

//    @JsonIgnore
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "channel_id")
//    private Channel channel;
    private Long channelId;

    private String writer;
    private String content;

//    private int read;

    @CreatedDate
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    public Message(Long channelId, String writer, String content) {
        this.channelId = channelId;
        this.writer = writer;
        this.content = content;
    }
}
