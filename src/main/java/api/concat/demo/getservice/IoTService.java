package api.concat.demo.getservice;

import api.concat.demo.getservice.jsonBean.EventBean;

public interface IoTService {
    void webhookHandler(EventBean event);
}
