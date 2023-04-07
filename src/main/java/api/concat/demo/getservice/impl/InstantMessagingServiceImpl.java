package api.concat.demo.getservice.impl;

import api.concat.demo.getservice.InstantMessagingService;
import api.concat.demo.getservice.jsonBean.EventBean;
import api.concat.demo.getservice.jsonBean.InstantMessagingBean;
import api.concat.demo.getservice.jsonBean.WeatherBean;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.util.List;

@Service
public class InstantMessagingServiceImpl implements InstantMessagingService {
    final static String IMURL="http://localhost:8080";
    String groupId = "2c928082873b081801873b09264b0000";
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();

    @Override
    public void addUser() {
        headers.clear();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "OWO");
        HttpEntity<InstantMessagingBean.User> request = new HttpEntity<>(headers);
        ResponseEntity<List<InstantMessagingBean.User>> response = restTemplate.exchange(IMURL+"/manage/users", HttpMethod.GET, request, new ParameterizedTypeReference<>() {});
        List<InstantMessagingBean.User> users = response.getBody();
        headers.clear();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "OWO");
        for(InstantMessagingBean.User user:users){
            user.setGroupId(groupId);
            HttpEntity<InstantMessagingBean.User> joinRequest = new HttpEntity<>(user, headers);
            restTemplate.postForObject(IMURL+"/join",joinRequest,InstantMessagingBean.class);
        }
    }

    @Override
    public void broadcastMessage(String message){
        headers.clear();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "OWO");
        InstantMessagingBean requestBody = new InstantMessagingBean(groupId,"","",message,null);
        HttpEntity<InstantMessagingBean> request = new HttpEntity<>(requestBody,headers);
        InstantMessagingBean responseBody = restTemplate.postForObject(IMURL+"/broadcast",request,InstantMessagingBean.class);
    }

    @Override
    public void sendMessage(String instantMessagingSoftware, String instantMessagingSoftwareUserId, String message) {
        headers.clear();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "OWO");
        InstantMessagingBean.User requestBody = InstantMessagingBean.User.createSendUserBean(instantMessagingSoftware,instantMessagingSoftwareUserId,message);
        HttpEntity<InstantMessagingBean.User> request = new HttpEntity<>(requestBody,headers);
        restTemplate.postForObject(IMURL+"/send",request,String.class);
    }

    @Override
    public void replyMessage(EventBean eventBean, String replyMessage) {
        sendMessage(eventBean.getInstantMessagingSoftware(),eventBean.getInstantMessagingSoftwareUserId(),replyMessage);
    }


}
