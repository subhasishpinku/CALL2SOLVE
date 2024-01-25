package callsolve.call.call2solve;

public class Locationsetget {
    private String jobid;
    private String techid;
    private String date;
    private String time;
    private String latitude;
    private String longitude;
    private String actualloc;
    private String status;

    public Locationsetget(String jobid, String techid, String date, String time,
                          String latitude, String longitude,String actualloc,String status){
        this.jobid=jobid;
        this.techid=techid;
        this.date=date;
        this.time=time;
        this.latitude=latitude;
        this.longitude=longitude;
        this.actualloc=actualloc;
        this.status=status;
    }

    public String getJobid() {
        return jobid;
    }

    public void setJobid(String jobid) {
        this.jobid = jobid;
    }

    public String getTechid() {
        return techid;
    }

    public void setTechid(String techid) {
        this.techid = techid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getActualloc() {
        return actualloc;
    }

    public void setActualloc(String actualloc) {
        this.actualloc = actualloc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
