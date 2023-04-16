package api.concat.demo.getservice.impl;

import api.concat.demo.getservice.InstantMessagingService;
import api.concat.demo.getservice.WeatherService;
import api.concat.demo.getservice.jsonBean.EnglishWeatherBean;
import api.concat.demo.getservice.jsonBean.EventBean;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.io.*;
import java.time.LocalDate;

@Service("EN-Weather")
public class EnglishWeatherServiceImpl implements WeatherService {
    @Autowired
    private InstantMessagingService instantMessagingService;

    private EnglishWeatherBean todayWeather;

    private InstantMessagingService.GroupData groupData;

    public EnglishWeatherServiceImpl(){
        groupData = InstantMessagingService.getGroupData(System.getenv("WEATHER_GROUP_ID"),System.getenv("WEATHER_GROUP_API_KEY"));
    }

    @Override
    public String getWeatherData() {
        if(todayWeather==null) UpdateWeatherByFile();
        String weatherData = String.format("%s weather",todayWeather.getAddress());
        EnglishWeatherBean.Day day = todayWeather.getDays().get(0);
        if(!isToday(day.getDatetime())){
            System.out.println("Old weather data bean!");
            UpdateWeatherByFile();
            if(!isToday(day.getDatetime())){
                System.out.println("Old weather data file!");
                UpdateWeatherByRequest();
            }
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
        LocalDate todayDate = LocalDate.now();
        String temp[] = datetime.split("-");
        int year = Integer.parseInt(temp[0]),month = Integer.parseInt(temp[1]),day = Integer.parseInt(temp[2]);
        LocalDate date = LocalDate.of(year,month,day);
        return todayDate.isEqual(date);
    }
    public void UpdateWeatherByFile(){
        if(this.getClass().getResourceAsStream("/EnglishWeatherJson.json")==null){
            UpdateWeatherByRequest();
            return;
        }
        StringBuilder fileTemp = new StringBuilder("");
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/EnglishWeatherJson.json"), "UTF-8"));
            String temp;
            while((temp=br.readLine())!=null) {
                fileTemp.append(temp);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            todayWeather = objectMapper.readValue(fileTemp.toString(), EnglishWeatherBean.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
    public void UpdateWeatherByRequest(){
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper objectMapper = new ObjectMapper();
        todayWeather = restTemplate.getForObject("https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/taiwan?unitGroup=metric&key=HCMGZY5UNQL5C877RCRH4ZZAP&contentType=json", EnglishWeatherBean.class);
        File file = new File("target/classes/EnglishWeatherJson.json");
        try (FileWriter fileWriter = new FileWriter(file);){
            fileWriter.write(objectMapper.writeValueAsString(todayWeather));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
