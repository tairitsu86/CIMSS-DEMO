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
        private String eventType;
        private MemberData member;
        private String message;

    }
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public static class MemberData{
        private CIMSSBean.UserId userId;
        private String userName;
        private Boolean isManager;
    }
}