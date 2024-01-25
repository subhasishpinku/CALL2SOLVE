package callsolve.call.call2solve.SetgetActivity;

public class OffersModel {
    private String fiD;
    private String fname;
     public OffersModel(String fiD, String fname){
         this.fiD = fiD;
         this.fname = fname;
     }

    public String getFiD() {
        return fiD;
    }

    public void setFiD(String fiD) {
        this.fiD = fiD;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }
}
