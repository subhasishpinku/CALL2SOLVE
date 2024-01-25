package callsolve.call.call2solve.SetgetActivity;

public class Product {
    private String prductid;
    private String Itemname;
    private String Itemimageurl;
    private String totsubprodct;
    public Product(String prductid, String Itemname, String Itemimageurl, String totsubprodct) {
        this.prductid =prductid;
        this.Itemname = Itemname;
        this.Itemimageurl = Itemimageurl;
        this.totsubprodct =totsubprodct;
    }

    public String getPrductid() {
        return prductid;
    }

    public void setPrductid(String prductid) {
        this.prductid = prductid;
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

    public String getTotsubprodct() {
        return totsubprodct;
    }

    public void setTotsubprodct(String totsubprodct) {
        this.totsubprodct = totsubprodct;
    }
}
