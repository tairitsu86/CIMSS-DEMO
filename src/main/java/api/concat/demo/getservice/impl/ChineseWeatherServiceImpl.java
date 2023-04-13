package api.concat.demo.getservice.impl;

import api.concat.demo.getservice.InstantMessagingService;
import api.concat.demo.getservice.WeatherService;
import api.concat.demo.getservice.jsonBean.EventBean;
import api.concat.demo.getservice.jsonBean.ChineseWeatherBean;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

@Service("CH-Weather")
public class ChineseWeatherServiceImpl implements WeatherService {

    @Autowired
    private InstantMessagingService instantMessagingService;

    private InstantMessagingService.GroupData groupData;

    public ChineseWeatherServiceImpl(){
        groupData = InstantMessagingService.getGroupData("2c9280828779107e01877910b87c0000","d43276e8-19a7-4497-92b3-66784904e907");
    }

//    public static void main(String[] args) {
//    	RestTemplate rest = new RestTemplate();
//    	String s = rest.getForObject("https://9b26-140-136-149-165.jp.ngrok.io", String.class);
//    	System.out.println(s);
//    }
//    @Override
//    public void newGroup(String groupName,String groupWebhook) {
//        headers.clear();
//        headers.set("Content-Type", "application/json");
//        headers.set("Authorization", "OWO");
//        InstantMessagingBean requestBody = new InstantMessagingBean("",groupName,groupWebhook,"",null);
//        HttpEntity<InstantMessagingBean> request = new HttpEntity<>(requestBody, headers);
//        InstantMessagingBean responseBody = restTemplate.postForObject(IMURL+"/newgroup",request,InstantMessagingBean.class);
//        groupId = responseBody.getGroupId();
//    }


    @Override
    public String getWeatherData() {
        String message;
        ObjectMapper objectMapper = new ObjectMapper();
        ChineseWeatherBean chineseWeatherBean;
//        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
//        //創建主題樣式
//        StandardChartTheme standardChartTheme=new StandardChartTheme("CN");
//        //設置標題字體
//        standardChartTheme.setExtraLargeFont(new Font("隸書",Font.BOLD,20));
//        //設置圖例的字體
//        standardChartTheme.setRegularFont(new Font("宋書",Font.BOLD,12));
//        //設置軸向的字體
//        standardChartTheme.setLargeFont(new Font("宋書",Font.BOLD,15));
//        //應用主題樣式
//        ChartFactory.setChartTheme(standardChartTheme);
        message = "天氣預報:";
//        try {
//            httpResponse = Unirest.get(URL).asString();
//            weatherData = objectMapper.readValue( httpResponse.getBody(), WeatherData.class);
//        } catch (UnirestException | JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
        try {
//            httpResponse = Unirest.get(URL).asString();
            chineseWeatherBean = objectMapper.readValue( readFile(), ChineseWeatherBean.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        for (ChineseWeatherBean.Locations locations: chineseWeatherBean.getRecords().getLocations()) {
            message = String.format("%s\n%s:",message,locations.getLocationsName());
            for (ChineseWeatherBean.Location location : locations.getLocation()) {
//                dataset.clear();
                if(!location.getLocationName().equals("新莊區")) continue;
                message = String.format("%s\n%s",message,location.getLocationName());
                for (ChineseWeatherBean.WeatherElement weatherElement: location.getWeatherElement()) {
                    message = String.format("%s%s:",message,weatherElement.getDescription());
                    for(ChineseWeatherBean.Time time:weatherElement.getTime()){
//                        message = String.format("%s\n%s~%s ",message,time.getStartTime(),time.getEndTime());
                        for(ChineseWeatherBean.ElementValue elementValue:time.getElementValue()){
                            if(elementValue.getValue()==null||elementValue.getValue().equals("")) continue;
//                            dataset.setValue(Integer.parseInt(elementValue.getValue()) , weatherElement.getDescription(), date(time.getStartTime()));
                            message = String.format("%s\n%s %s%s",message,date(time.getStartTime()),elementValue.getValue(),elementValue.getMeasures());
                        }
                    }
                    break;
                }
//                JFreeChart chart = ChartFactory.createBarChart(
//                        String.format("%s%s",locations.getLocationsName(),location.getLocationName()),
//                        "時間",
//                        "降雨機率",
//                        dataset,
//                        PlotOrientation.VERTICAL,
//                        true, true, false);
//                try {
//                    ChartUtilities.saveChartAsPNG(new File("src/main/resources/barchart.png"), chart, 500, 300);
//                } catch (IOException e) {
//                    System.err.println("Error saving chart.");
//                }
                break;
            }
        }
//        System.out.println(message);
        return message;
    }

    @Override
    public void webhookHandler(EventBean event) {
        if(event!=null&&event.getMessage()!=null&&event.getMessage().equalsIgnoreCase("weather"))
        	instantMessagingService.replyMessage(groupData,event,getWeatherData());
    }

    public String readFile(){
        StringBuilder fileTemp = new StringBuilder("");
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/ChineseWeatherJson.json"), "UTF-8"));
            String temp;
            while((temp=br.readLine())!=null) {
                fileTemp.append(temp);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return fileTemp.toString();
    }

    public String date(String datetime){
        String tmp[] = datetime.split("[- :]");
        return String.format("%s日%s",tmp[2],tmp[3].equals("00")?"早":"晚");
    }
}