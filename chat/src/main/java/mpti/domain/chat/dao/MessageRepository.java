package mpti.domain.chat.dao;

import mpti.domain.chat.entity.Channel;
import mpti.domain.chat.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByChannelId(Long ChannelId);


    Message findTop1ByChannelIdOrderByCreatedDateDesc(Long channelId);

}
