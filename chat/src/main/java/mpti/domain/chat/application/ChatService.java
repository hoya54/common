package mpti.domain.chat.application;

import lombok.RequiredArgsConstructor;
import mpti.domain.chat.api.response.GetChatListResponse;
import mpti.domain.chat.dao.ChannelRepository;
import mpti.domain.chat.dao.MessageRepository;
import mpti.domain.chat.dto.MsgDto;
import mpti.domain.chat.entity.Channel;
import mpti.domain.chat.entity.Message;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatService {

    private final MessageRepository messageRepository;
    private final ChannelRepository channelRepository;

    public void saveMessage(MsgDto msgDto) {
//        Optional<Channel> channel = channelRepository.findById(msgDto.getChannelId());

        Message message = new Message(msgDto.getChannelId(), msgDto.getUserName(), msgDto.getContent());

        messageRepository.save(message);
    }

    public Channel getChannel(Long trainerId, Long userId) {
        Optional<Channel> channel = channelRepository.findChannelByTrainerIdAndUserId(trainerId, userId);

        if (channel.isEmpty()) {
            Channel newChannel = new Channel(trainerId, userId);
            Channel savedChannel = channelRepository.save(newChannel);
            return savedChannel;
        } else {
            return channel.get();
        }
    }

    public List<Message> getChatHistory(Long channelId) {
        return messageRepository.findByChannelId(channelId);
    }


    public List<GetChatListResponse> getChatList(Long id, String role) {

        List<Channel> channels;
        List<GetChatListResponse> chatList = new ArrayList<>();

        if (role.equals("TRAINER")) {
            channels = channelRepository.findByTrainerId(id);
        } else {
            channels = channelRepository.findByUserId(id);
        }

        for (Channel channel : channels) {
            Message message = messageRepository.findTop1ByChannelIdOrderByCreatedDateDesc(channel.getId());
            GetChatListResponse getChatListResponse = new GetChatListResponse(message.getWriter(), message.getContent());
            chatList.add(getChatListResponse);
        }

        return chatList;
    }
}
