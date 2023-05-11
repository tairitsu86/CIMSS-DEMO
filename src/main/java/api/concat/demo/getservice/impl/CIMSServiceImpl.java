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
//    private final static String IMURL=System.getenv("CIMSS_URL");
    private final static String IMURL="https://cimss.csie.fju.edu.tw/";

    @Override
    public void broadcastMessage(GroupData groupData,String message,CIMSSBean.UserId userId){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", groupData.getAPI_KEY());
        CIMSSBean requestBody = CIMSSBean.CreateBroadCastRequestBody(groupData.getGroupId(),message,userId);
        HttpEntity<CIMSSBean> request = new HttpEntity<>(requestBody,headers);
        restTemplate.put(IMURL+"/broadcast/text",request);
    }

    @Override
    public void sendMessage(GroupData groupData, CIMSSBean.UserId userId, String message) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", groupData.getAPI_KEY());
        CIMSSBean requestBody = CIMSSBean.CreateSendRequestBody(userId,message);
        HttpEntity<CIMSSBean> request = new HttpEntity<>(requestBody,headers);
        restTemplate.postForObject(IMURL+"/send/text",request,String.class);
    }

    @Override
    public void replyMessage(GroupData groupData,EventBean.TextMessageEvent event, String replyMessage) {
        sendMessage(groupData,event.getMember().getUserId(),replyMessage);
    }

}
