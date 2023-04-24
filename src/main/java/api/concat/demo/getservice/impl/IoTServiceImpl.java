package api.concat.demo.getservice.impl;

import api.concat.demo.getservice.InstantMessagingService;
import api.concat.demo.getservice.IoTService;
import api.concat.demo.getservice.jsonBean.EventBean;
import api.concat.demo.getservice.jsonBean.IoTBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class IoTServiceImpl implements IoTService {
    @Autowired
    private InstantMessagingService instantMessagingService;

    private InstantMessagingService.GroupData groupData;

    private final static String IOT_URL="http://cimss.csie.fju.edu.tw:10000/";
    private final static RestTemplate restTemplate = new RestTemplate();

    public IoTServiceImpl(){
        groupData = InstantMessagingService.getGroupData(System.getenv("IOT_GROUP_ID"),System.getenv("IOT_GROUP_API_KEY"));
    }
    @Override
    public String powerSwitch(String state) {
        return restTemplate.postForObject(IOT_URL, IoTBean.CreateSwitchBean(state), String.class);
    }
    public String getState(){
        return restTemplate.getForObject(IOT_URL,IoTBean.class).getState().equals("0")?"Power off":"Power on";
    }

    @Override
    public void webhookHandler(EventBean event) {
        if(event!=null&&event.getMessage()!=null&&event.getMessage().matches("(?i)iot.*")){
            if(event.getMessage().matches("(?i)iot on *")){
                instantMessagingService.replyMessage(groupData,event,powerSwitch("1"));
            }else if(event.getMessage().matches("(?i)iot off *")){
                instantMessagingService.replyMessage(groupData,event,powerSwitch("0"));
            }else if(event.getMessage().matches("(?i)iot state *")){
                instantMessagingService.replyMessage(groupData,event,getState());
            }
        }
    }

}
