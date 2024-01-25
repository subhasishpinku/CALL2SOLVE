package callsolve.call.call2solve.SetgetActivity;

public class DistricViewSetGet {
    private String distid;
    private String Itemname;
    private String Itemimageurl;
    public DistricViewSetGet(String distid, String Itemname, String Itemimageurl) {
        this.distid =distid;
        this.Itemname = Itemname;
        this.Itemimageurl = Itemimageurl;
    }

    public String getDistid() {
        return distid;
    }

    public void setDistid(String distid) {
        this.distid = distid;
    }

    public String getItemname() {
        return Itemname;
    }

    public void setItemname(String itemname) {
        Itemname = itemname;
    }

    public String getItemimageurl() {
        return Itemimageurl;
    }

    public void setItemimageurl(String itemimageurl) {
        Itemimageurl = itemimageurl;
    }
}
