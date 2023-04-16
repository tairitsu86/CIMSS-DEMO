package api.concat.demo.getservice.impl;

import api.concat.demo.getservice.InstantMessagingService;
import api.concat.demo.getservice.jsonBean.EventBean;
import api.concat.demo.getservice.jsonBean.InstantMessagingBean;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class InstantMessagingServiceImpl implements InstantMessagingService {
    private final static String IMURL=System.getenv("CIMSS_URL");
    private final static RestTemplate restTemplate = new RestTemplate();
    private final static HttpHeaders headers = new HttpHeaders();

    @Override
    public void broadcastMessage(GroupData groupData,String message){
        headers.clear();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", groupData.getAPI_KEY());
        InstantMessagingBean requestBody = new InstantMessagingBean(groupData.getGroupId(),"","",message,null);
        HttpEntity<InstantMessagingBean> request = new HttpEntity<>(requestBody,headers);
        InstantMessagingBean responseBody = restTemplate.postForObject(IMURL+"/broadcast",request,InstantMessagingBean.class);
    }

    @Override
    public void sendMessage(GroupData groupData,String instantMessagingSoftware, String instantMessagingSoftwareUserId, String message) {
        headers.clear();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", groupData.getAPI_KEY());
        InstantMessagingBean.User requestBody = InstantMessagingBean.User.createSendUserBean(instantMessagingSoftware,instantMessagingSoftwareUserId,message);
        HttpEntity<InstantMessagingBean.User> request = new HttpEntity<>(requestBody,headers);
        restTemplate.postForObject(IMURL+"/send",request,String.class);
    }

    @Override
    public void replyMessage(GroupData groupData,EventBean eventBean, String replyMessage) {
        sendMessage(groupData,eventBean.getInstantMessagingSoftware(),eventBean.getInstantMessagingSoftwareUserId(),replyMessage);
    }

}
