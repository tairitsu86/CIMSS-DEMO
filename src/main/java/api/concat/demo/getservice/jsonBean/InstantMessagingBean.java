package api.concat.demo.getservice.jsonBean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class InstantMessagingBean {
    private String groupId;
    private String groupName;
    private String groupWebhook;
    private String message;
    private List<User> users;
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class User{
        public static User createSendUserBean(String instantMessagingSoftware,String instantMessagingSoftwareUserId,String message){
            return new User(instantMessagingSoftware,instantMessagingSoftwareUserId,null,null,message);
        }
        private String instantMessagingSoftware;
        private String instantMessagingSoftwareUserId;
        private String userName;
        private String groupId;
        private String message;
    }
}
