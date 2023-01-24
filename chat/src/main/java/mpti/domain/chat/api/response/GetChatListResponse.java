package mpti.domain.chat.api.response;

import lombok.Data;

@Data
public class GetChatListResponse {

    private String writer;

    private String content;

//    private LocalDateTime lastCreated;

//    private Long unRead;


    public GetChatListResponse(String writer, String content) {
        this.writer = writer;
        this.content = content;
    }
}
