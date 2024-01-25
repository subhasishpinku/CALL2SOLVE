package callsolve.call.call2solve.SetgetActivity;

public class ServiceChargesetget {
    private String title;
    private String name;
    private String Normalchage;
    private String oferrate;
   // private String breakdowenchage;
   // private String installcharge;


    public ServiceChargesetget(String title, String name, String Normalchage, String oferrate){
        this.title =title;
        this.name =name;
        this.Normalchage =Normalchage;
        this.oferrate =oferrate;
       // this.breakdowenchage =breakdowenchage;
       // this.installcharge=installcharge;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getNormalchage() {
        return Normalchage;
    }

    public void setNormalchage(String normalchage) {
        Normalchage = normalchage;
    }
    public String getOferrate() {
        return oferrate;
    }

    public void setOferrate(String oferrate) {
        this.oferrate = oferrate;
    }
//    public String getBreakdowenchage() {
//        return breakdowenchage;
//    }
//
//    public void setBreakdowenchage(String breakdowenchage) {
//        this.breakdowenchage = breakdowenchage;
//    }
//
//    public String getInstallcharge() {
//        return installcharge;
//    }
//
//    public void setInstallcharge(String installcharge) {
//        this.installcharge = installcharge;
//    }





}
