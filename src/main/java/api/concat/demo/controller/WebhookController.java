package api.concat.demo.controller;

import api.concat.demo.getservice.CIMSService;
import api.concat.demo.getservice.MicroService;
import api.concat.demo.getservice.jsonBean.EventBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebhookController {

    @Autowired
    private CIMSService instantMessagingService;
    @Autowired
    @Qualifier("EN-Weather")
    private MicroService weatherService;
    @Autowired
    @Qualifier("IoT-control")
    private MicroService ioTService;


    @GetMapping("/")
    public String home(){
        return "OwO";
    }

    @PostMapping("/weatherServiceWebhook")
    public void weatherWebhook(@RequestBody EventBean.TextMessageEvent event){
        System.out.println(event);
        weatherService.webhookHandler(event);
    }
    @PostMapping("/iotServiceWebhook")
    public void iotWebhook(@RequestBody EventBean.TextMessageEvent event){
        System.out.println(event);
        ioTService.webhookHandler(event);
    }

}
