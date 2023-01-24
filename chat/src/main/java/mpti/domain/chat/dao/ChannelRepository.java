package mpti.domain.chat.dao;

import mpti.domain.chat.entity.Channel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChannelRepository extends JpaRepository<Channel, Long> {

    Optional<Channel> findChannelByTrainerIdAndUserId(Long trainerId, Long userId);


    List<Channel> findByTrainerId(Long trainerId);

    List<Channel> findByUserId(Long userId);
}
