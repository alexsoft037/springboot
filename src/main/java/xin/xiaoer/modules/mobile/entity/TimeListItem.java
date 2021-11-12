package xin.xiaoer.modules.mobile.entity;

import java.io.Serializable;
import java.util.Date;

public class TimeListItem implements Serializable {
    private static final long serialVersionUID = 1L;
    /****/
    private String time;
    /****/
    private Double distance;

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
