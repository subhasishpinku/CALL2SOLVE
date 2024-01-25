package callsolve.call.call2solve.SetgetActivity;

public class Subproductsetget {
    private String subid;
    private String subprductnm;
    private String img;
    public Subproductsetget(String subid, String subprductnm, String img){
        this.subid=subid;
        this.subprductnm=subprductnm;
        this.img = img;
    }

    public String getSubid() {
        return subid;
    }

    public void setSubid(String subid) {
        this.subid = subid;
    }

    public String getSubprductnm() {
        return subprductnm;
    }

    public void setSubprductnm(String subprductnm) {
        this.subprductnm = subprductnm;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
