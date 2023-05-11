package api.concat.demo.getservice.jsonBean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CIMSSBean {
    private String groupId;
    private UserId userId;
    private String message;
    private List<UserId> ignoreList;

    public static CIMSSBean CreateBroadCastRequestBody(String groupId,String message,UserId userId){
        List<UserId> ignoreList = new ArrayList<>();
        ignoreList.add(userId);
        return new CIMSSBean(groupId,null,message,ignoreList);
    }
    public static CIMSSBean CreateSendRequestBody(UserId userId,String message){
        return new CIMSSBean(null,userId,message,null);
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
