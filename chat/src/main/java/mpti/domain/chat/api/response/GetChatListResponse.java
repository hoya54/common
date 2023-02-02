package mpti.domain.chat.api.response;

import lombok.Data;

@Data
public class GetChatListResponse {

    private String channelId;

    private Long yourId;

    private String content;

//    private LocalDateTime lastCreated;

//    private Long unRead;


    public GetChatListResponse(String channelId, Long yourId, String content) {
        this.channelId = channelId;
        this.yourId = yourId;
        this.content = content;
    }
}