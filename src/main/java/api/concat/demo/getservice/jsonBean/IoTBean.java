package api.concat.demo.getservice.jsonBean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class IoTBean {
    private String data;
    private String state;
    public static IoTBean CreateSwitchBean(String data){
        return new IoTBean(data,null);
    }
}
