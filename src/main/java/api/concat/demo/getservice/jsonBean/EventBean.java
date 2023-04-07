package api.concat.demo.getservice.jsonBean;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EventBean {
    private String eventType;
    private String instantMessagingSoftware;
    private String instantMessagingSoftwareUserId;
    private String message;
    private Object eventObject;
}
