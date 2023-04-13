package api.concat.demo.getservice;

import api.concat.demo.getservice.jsonBean.EventBean;

public interface WeatherService {
    String getWeatherData();

    void webhookHandler(EventBean event);

}
