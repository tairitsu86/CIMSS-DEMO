package api.concat.demo.controller;

import api.concat.demo.getservice.InstantMessagingService;
import api.concat.demo.getservice.WeatherService;
import api.concat.demo.getservice.jsonBean.EventBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebhookController {

    @Autowired
    InstantMessagingService instantMessagingService;
    @Autowired
    WeatherService weatherService;

    @GetMapping("/")
    public String home(){
        instantMessagingService.addUser();
        return "OwO";
    }

    @PostMapping("/weatherServiceWebhook")
    public void webhookHandler(@RequestBody EventBean event){
        System.out.println(event);
        if(event!=null&&event.getMessage()!=null&&event.getMessage().toLowerCase().equals("weather"))
        	instantMessagingService.replyMessage(event,weatherService.getWeatherData());
    }

}
