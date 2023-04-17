package api.concat.demo.getservice.impl;

import api.concat.demo.getservice.InstantMessagingService;
import api.concat.demo.getservice.WeatherService;
import api.concat.demo.getservice.jsonBean.EnglishWeatherBean;
import api.concat.demo.getservice.jsonBean.EventBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Service("EN-Weather")
public class EnglishWeatherServiceImpl implements WeatherService {
    @Autowired
    private InstantMessagingService instantMessagingService;

    private static EnglishWeatherBean todayWeather;

    private static String WEATHER_API_KEY = System.getenv("WEATHER_API_KEY");

    private static String WEATHER_API_URL = String.format("https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/taiwan?unitGroup=metric&key=%s&contentType=json",WEATHER_API_KEY);

    private InstantMessagingService.GroupData groupData;

    public EnglishWeatherServiceImpl(){
        groupData = InstantMessagingService.getGroupData(System.getenv("WEATHER_GROUP_ID"),System.getenv("WEATHER_GROUP_API_KEY"));
    }

    @Override
    public String getWeatherData() {
        if(todayWeather==null) UpdateWeatherByRequest();
        String weatherData = String.format("%s weather",todayWeather.getAddress());
        EnglishWeatherBean.Day day = todayWeather.getDays().get(0);
        if(!isToday(day.getDatetime())){
            System.out.println("Old weather data bean!");
            UpdateWeatherByRequest();
            return getWeatherData();
        }
        weatherData = String.format("%s%s\n",weatherData,day.getDatetime());
        for(EnglishWeatherBean.Hour hour: day.getHours()){
            weatherData = String.format("%s%s->temp:%s, feel like:%s, cloud cover:%s, visibility:%s\n",weatherData,hour.getDatetime(),hour.getTemp(),hour.getFeelslike(),hour.getCloudcover(),hour.getVisibility());
        }
        return weatherData;
    }

    @Override
    public void webhookHandler(EventBean event) {
        if(event!=null&&event.getMessage()!=null&&event.getMessage().equalsIgnoreCase("weather"))
            instantMessagingService.replyMessage(groupData,event,getWeatherData());
    }
    public boolean isToday(String datetime){
        ZonedDateTime utc8Now = ZonedDateTime.now(ZoneId.of("UTC+8"));
        System.out.printf("now is %s!\n",utc8Now.toString());
        LocalDate todayDate = utc8Now.toLocalDate();
        String temp[] = datetime.split("-");
        int year = Integer.parseInt(temp[0]),month = Integer.parseInt(temp[1]),day = Integer.parseInt(temp[2]);
        LocalDate date = LocalDate.of(year,month,day);
        return todayDate.isEqual(date);
    }

    public void UpdateWeatherByRequest(){
        RestTemplate restTemplate = new RestTemplate();
        todayWeather = restTemplate.getForObject(WEATHER_API_URL, EnglishWeatherBean.class);
    }
}
