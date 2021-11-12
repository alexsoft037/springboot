package xin.xiaoer.modules.mobile.entity;

import xin.xiaoer.modules.monitor.entity.CameraCapture;

import java.io.Serializable;
import java.util.List;

public class CaptureListItem implements Serializable {
    private static final long serialVersionUID = 1L;
    /****/
    private String date;
    /****/
    private List<CameraCapture> cameraCaptureList;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<CameraCapture> getCameraCaptureList() {
        return cameraCaptureList;
    }

    public void setCameraCaptureList(List<CameraCapture> cameraCaptureList) {
        this.cameraCaptureList = cameraCaptureList;
    }
}
