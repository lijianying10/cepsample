package tutorial;

public class event {
    private String log;
    private String service;
    private Double val;
    private Integer code;

    public event(String log,String service,Double val){
        this.log = log;
        this.service = service;
        this.val = val;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }


    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public Double getVal() {
        return val;
    }

    public void setVal(Double val) {
        this.val = val;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
