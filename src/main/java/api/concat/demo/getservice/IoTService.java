package api.concat.demo.getservice;

import api.concat.demo.getservice.jsonBean.EventBean;

public interface IoTService {
    String powerSwitch(String state);
    void webhookHandler(EventBean event);
}
