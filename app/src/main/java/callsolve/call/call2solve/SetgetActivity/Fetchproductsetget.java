package callsolve.call.call2solve.SetgetActivity;

public class Fetchproductsetget {
    private String chargename;
    private String mainrate;
    private String ofrrate;
    public Fetchproductsetget(String chargename, String mainrate, String ofrrate){
        this.chargename = chargename;
        this.mainrate = mainrate;
        this.ofrrate = ofrrate;
    }

    public String getChargename() {
        return chargename;
    }

    public void setChargename(String chargename) {
        this.chargename = chargename;
    }

    public String getMainrate() {
        return mainrate;
    }

    public void setMainrate(String mainrate) {
        this.mainrate = mainrate;
    }

    public String getOfrrate() {
        return ofrrate;
    }

    public void setOfrrate(String ofrrate) {
        this.ofrrate = ofrrate;
    }
}
