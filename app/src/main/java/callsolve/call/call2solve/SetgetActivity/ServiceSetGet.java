package callsolve.call.call2solve.SetgetActivity;

public class ServiceSetGet {
    private String servicename;
    private String servicetext;
    private String image;

    public ServiceSetGet(String servicename, String servicetext, String image){
        this.servicename = servicename;
        this.servicetext =servicetext;
        this.image = image;
    }

    public String getServicename() {
        return servicename;
    }

    public String getServicetext() {
        return servicetext;
    }

    public String getImage() {
        return image;
    }
}
