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
public class CIMSSBean {
    private String groupId;
    private UserId userId;
    private String message;

    public static CIMSSBean CreateBroadCastRequestBody(String groupId,String message){
        return new CIMSSBean(groupId,null,message);
    }
    public static CIMSSBean CreateSendRequestBody(String instantMessagingSoftware,String instantMessagingSoftwareUserId,String message){
        return new CIMSSBean(null,new UserId(instantMessagingSoftware,instantMessagingSoftwareUserId),message);
    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class UserId{
        private String instantMessagingSoftware;
        private String instantMessagingSoftwareUserId;
    }
}
