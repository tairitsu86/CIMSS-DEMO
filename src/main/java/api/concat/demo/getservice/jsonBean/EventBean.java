package api.concat.demo.getservice.jsonBean;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

public class EventBean {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class TextMessageEvent extends EventBean{
        private final String eventType="TextMessage";
        private MemberData member;
        private String message;

    }
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class MemberData{
        private String instantMessagingSoftware;
        private String instantMessagingSoftwareUserId;
        private String userName;
        private Boolean isManager;
    }
}