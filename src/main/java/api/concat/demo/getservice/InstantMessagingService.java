package api.concat.demo.getservice;

import api.concat.demo.getservice.jsonBean.EventBean;

public interface InstantMessagingService {

    void addUser();
    void broadcastMessage(String message);

    void sendMessage(String instantMessagingSoftware,String instantMessagingSoftwareUserId,String message);

    void replyMessage(EventBean eventBean,String replyMessage);
}
