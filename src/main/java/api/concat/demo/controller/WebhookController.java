package api.concat.demo.controller;

import api.concat.demo.getservice.InstantMessagingService;
import api.concat.demo.getservice.WeatherService;
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
    private InstantMessagingService instantMessagingService;
    @Autowired
    @Qualifier("EN-Weather")
    private WeatherService weatherService;

    @GetMapping("/")
    public String home(){
        return "OwO";
    }

    @PostMapping("/weatherServiceWebhook")
    public void weatherWebhook(@RequestBody EventBean event){
        System.out.println(event);
        weatherService.webhookHandler(event);
    }

}
