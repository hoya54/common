package mpti.domain.chat.api.controller;

import lombok.RequiredArgsConstructor;
import mpti.domain.chat.api.response.GetChatListResponse;
import mpti.domain.chat.application.ChatService;
import mpti.domain.chat.dto.MsgDto;
import mpti.domain.chat.entity.Channel;
import mpti.domain.chat.entity.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate simpMessagingTemplate;

    private final ChatService chatService;

    // /receive를 메시지를 받을 endpoint로 설정합니다.
    @MessageMapping("/chat/receive")
    public void SocketHandler(MsgDto msgDto){

        System.out.println(msgDto);

        chatService.saveMessage(msgDto);

        simpMessagingTemplate.convertAndSend("/send" + "/" + msgDto.getChannelId(), msgDto);
    }

//    [GET] 현재 본인과 상대방의 아이디를 가지고 채널 아이디를 받아옴(기존에 채널이 없었다면 만들어서 반환)

    @GetMapping("/channel/{trainerId}/{userId}")
    public Long getChannelId(@PathVariable Long trainerId, @PathVariable Long userId){

        Channel channel = chatService.getChannel(trainerId, userId);
        Long channelId = channel.getId();

        return channelId;
    }

//    [GET] 해당 채팅방의 과거 대화 기록 조회

    @GetMapping("/load/{channelId}")
    public List<Message> getChatHistory(@PathVariable Long channelId){

        List<Message> chatHistory = chatService.getChatHistory(channelId);

        return chatHistory;
    }

//    [GET] 현재 대화를 한 기록이 있는 모든 상대와의 마지막 메시지 반환 (카카오톡 채팅 목록과 같은 기능)

    @GetMapping("/load/list/{id}/{role}")
    public List<GetChatListResponse> getChatList(@PathVariable Long id,
                                                 @PathVariable String role){

        List<GetChatListResponse> chatList = chatService.getChatList(id, role);

        return chatList;
    }
}
