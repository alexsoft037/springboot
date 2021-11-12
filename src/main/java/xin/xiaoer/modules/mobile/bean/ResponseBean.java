package xin.xiaoer.modules.mobile.bean;

public class ResponseBean {

    public static final String SUCCESS = "success";
    public static final String FAILED = "failed";
    // 返回信息
    private boolean isLoading;

    // 返回信息
    private String status;

    // 返回信息
    private String error;

    // 返回的数据
    private Object data;

    private String message;

    public ResponseBean(boolean isLoading, String status, String error, Object data) {
        this.isLoading = isLoading;
        this.status = status;
        if (status.equals(ResponseBean.SUCCESS)){
            this.message = "操作成功！";
        }
        this.error = error;
        this.data = data;
    }

    public ResponseBean(boolean isLoading, String status, String error, String message, Object data) {
        this.isLoading = isLoading;
        this.status = status;
        this.message = message;
        this.error = error;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean getIsLoading() {
        return isLoading;
    }

    public void setIsLoading(boolean loading) {
        isLoading = loading;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
