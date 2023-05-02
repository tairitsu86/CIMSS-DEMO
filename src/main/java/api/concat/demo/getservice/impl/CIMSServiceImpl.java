package api.concat.demo.getservice.impl;

import api.concat.demo.getservice.CIMSService;
import api.concat.demo.getservice.jsonBean.EventBean;
import api.concat.demo.getservice.jsonBean.CIMSSBean;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CIMSServiceImpl implements CIMSService {
    private final static String IMURL=System.getenv("CIMSS_URL");

    @Override
    public void broadcastMessage(GroupData groupData,String message){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", groupData.getAPI_KEY());
        CIMSSBean requestBody = CIMSSBean.CreateBroadCastRequestBody(groupData.getGroupId(),message);
        HttpEntity<CIMSSBean> request = new HttpEntity<>(requestBody,headers);
        restTemplate.put(IMURL+"/broadcast/text",request);
    }

    @Override
    public void sendMessage(GroupData groupData,String instantMessagingSoftware, String instantMessagingSoftwareUserId, String message) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", groupData.getAPI_KEY());
        CIMSSBean requestBody = CIMSSBean.CreateSendRequestBody(instantMessagingSoftware,instantMessagingSoftwareUserId,message);
        HttpEntity<CIMSSBean> request = new HttpEntity<>(requestBody,headers);
        restTemplate.postForObject(IMURL+"/send/text",request,String.class);
    }

    @Override
    public void replyMessage(GroupData groupData,EventBean.TextMessageEvent event, String replyMessage) {
        sendMessage(groupData,event.getMember().getInstantMessagingSoftware(),event.getMember().getInstantMessagingSoftwareUserId(),replyMessage);
    }

}
