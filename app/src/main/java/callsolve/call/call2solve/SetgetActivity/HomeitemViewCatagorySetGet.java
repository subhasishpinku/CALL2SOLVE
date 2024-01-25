package callsolve.call.call2solve.SetgetActivity;

public class HomeitemViewCatagorySetGet {
    private String catid;
    private String catname;
    private String img;

    public HomeitemViewCatagorySetGet(String catid, String catname, String img){
        this.catid = catid;
        this.catname = catname;
        this.img = img;
    }

    public String getCatid() {
        return catid;
    }

    public String getCatname() {
        return catname;
    }

    public String getImg() {
        return img;
    }
}
