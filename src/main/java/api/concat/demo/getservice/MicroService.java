package api.concat.demo.getservice;

import api.concat.demo.getservice.jsonBean.EventBean;

public interface MicroService {
    void webhookHandler(EventBean.TextMessageEvent event);
}
