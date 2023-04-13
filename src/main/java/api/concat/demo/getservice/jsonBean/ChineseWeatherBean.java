package api.concat.demo.getservice.jsonBean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChineseWeatherBean {
    String success;
    Records records;
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Records{
        List<Locations> locations;
    }
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Locations{
        String datasetDescription;
        String locationsName;
        List<Location> location;
    }
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Location{
        String locationName;
        List<WeatherElement> weatherElement;
    }
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class WeatherElement{
        String description;
        List<Time> time;
    }
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Time{
        String startTime;
        String endTime;
        List<ElementValue> elementValue;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ElementValue{
        String value;
        String measures;
    }
}
//draw chart
//    public static void main(String[] args) {
//
//        // create dataset
//        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
//        dataset.setValue(120, "Sales", "Product A");
//        dataset.setValue(240, "Sales", "Product B");
//        dataset.setValue(180, "Sales", "Product C");
//        dataset.setValue(90, "Sales", "Product D");
//
//
//        // create chart
//        JFreeChart chart = ChartFactory.createBarChart(
//                "title",
//                "Category",
//                "Score",
//                dataset,
//                PlotOrientation.VERTICAL,
//                true, true, false);
//
//        // save chart as PNG image
//        try {
//            ChartUtilities.saveChartAsPNG(new File("src/main/resources/barchart.png"), chart, 500, 300);
//        } catch (IOException e) {
//            System.err.println("Error saving chart.");
//        }
//    }