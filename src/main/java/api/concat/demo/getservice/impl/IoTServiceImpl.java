package api.concat.demo.getservice.impl;

import api.concat.demo.getservice.CIMSService;
import api.concat.demo.getservice.MicroService;
import api.concat.demo.getservice.jsonBean.EventBean;
import api.concat.demo.getservice.jsonBean.IoTBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service("IoT-control")
public class IoTServiceImpl implements MicroService {
    @Autowired
    private CIMSService cimsService;
    private CIMSService.GroupData groupData;
    private final static String IOT_URL=System.getenv("IOT_SERVICE_URL");
    private final static RestTemplate restTemplate = new RestTemplate();

    public IoTServiceImpl(){
        groupData = CIMSService.getGroupData(System.getenv("IOT_GROUP_ID"),System.getenv("IOT_GROUP_API_KEY"));
    }
    @Override
    public void webhookHandler(EventBean.TextMessageEvent event) {
        String result = null,state=null;
        if(event!=null&&event.getMessage()!=null&&event.getMessage().matches("(?i)iot.*")){
            String id = event.getMessage().split(" ",3)[2];
            if(event.getMessage().matches("(?i)iot on .*")){
                result = setPowerOn(id);
                state = "on";
            }else if(event.getMessage().matches("(?i)iot off .*")){
                result = setPowerOff(id);
                state = "off";
            }else if(event.getMessage().matches("(?i)iot state .*")){
                result = getState(id);
            }
            if(result!=null)
                cimsService.replyMessage(groupData, event, getReplyMessage(state,id));
            if ("Success".equalsIgnoreCase(result)&&state!=null)
                cimsService.broadcastMessage(groupData, getBroadcastMessage(event,state,id),event.getMember().getUserId());
        }
    }
    public String setPowerOn(String id) {
        return restTemplate.getForObject(IOT_URL+"/on?id={id}", IoTBean.class,id).getResult();
    }
    public String setPowerOff(String id) {
        return restTemplate.getForObject(IOT_URL+"/off?id={id}", IoTBean.class,id).getResult();
    }
    public String getState(String id){
        return restTemplate.getForObject(IOT_URL+"/state?id={id}",IoTBean.class,id).getState();
    }
    public String getBroadcastMessage(EventBean.TextMessageEvent event,String state,String id){
        return String.format("%s in Group [%s] is turned %s by %s.",id,"School Lab",state,event.getMember().getUserName());
    }
    public String getReplyMessage(String state,String id){
        return String.format("%s in Group [%s] was turned %s.",id,"School Lab",state);
    }
}
