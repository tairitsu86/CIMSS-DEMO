package api.concat.demo.getservice;

import api.concat.demo.getservice.jsonBean.EventBean;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public interface InstantMessagingService {

    void broadcastMessage(GroupData groupData,String message);

    void sendMessage(GroupData groupData,String instantMessagingSoftware,String instantMessagingSoftwareUserId,String message);

    void replyMessage(GroupData groupData,EventBean eventBean,String replyMessage);

    static GroupData getGroupData(String groupId, String API_KEY) {
        return new GroupData(groupId,API_KEY);
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    class GroupData {
        private String groupId;
        private String API_KEY;
    }
}
