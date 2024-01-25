package callsolve.call.call2solve.SetgetActivity;

public class HomeViewSetget {
    private String ItemId;
    private String Itemname;
    private String Itemimageurl;
    public HomeViewSetget(String ItemId, String Itemname, String Itemimageurl) {
        this.ItemId =ItemId;
        this.Itemname = Itemname;
        this.Itemimageurl = Itemimageurl;
    }

    public String getItemId() {
        return ItemId;
    }

    public void setItemId(String itemId) {
        ItemId = itemId;
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
