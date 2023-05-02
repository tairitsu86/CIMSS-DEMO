package api.concat.demo.getservice.impl;

import api.concat.demo.getservice.CIMSService;
import api.concat.demo.getservice.jsonBean.EventBean;
import api.concat.demo.getservice.jsonBean.InstantMessagingBean;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class InstantMessagingServiceImpl implements CIMSService {
    private final static String IMURL=System.getenv("CIMSS_URL");

    @Override
    public void broadcastMessage(GroupData groupData,String message){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", groupData.getAPI_KEY());
        InstantMessagingBean requestBody = new InstantMessagingBean(groupData.getGroupId(),"","",message,null);
        HttpEntity<InstantMessagingBean> request = new HttpEntity<>(requestBody,headers);
        restTemplate.put(IMURL+"/broadcast/text",request);
    }

    @Override
    public void sendMessage(GroupData groupData,String instantMessagingSoftware, String instantMessagingSoftwareUserId, String message) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", groupData.getAPI_KEY());
        InstantMessagingBean.User requestBody = InstantMessagingBean.User.createSendUserBean(instantMessagingSoftware,instantMessagingSoftwareUserId,message);
        HttpEntity<InstantMessagingBean.User> request = new HttpEntity<>(requestBody,headers);
        restTemplate.postForObject(IMURL+"/send/text",request,String.class);
    }

    @Override
    public void replyMessage(GroupData groupData,EventBean.TextMessageEvent event, String replyMessage) {
        sendMessage(groupData,event.getMember().getInstantMessagingSoftware(),event.getMember().getInstantMessagingSoftwareUserId(),replyMessage);
    }

}
