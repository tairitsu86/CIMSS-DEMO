package api.concat.demo.getservice.jsonBean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EnglishWeatherBean {
    private String address;
    private List<Day> days;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Day{
        private String datetime;
        private List<Hour> hours;
    }
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Hour{
        private String datetime;
        private double temp;
        private double feelslike;
        private double visibility;
        private double cloudcover;

    }
}
