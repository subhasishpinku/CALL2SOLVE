package callsolve.call.call2solve.SetgetActivity;

public class OrderSetGet {
    private String recordid;
    private String jobid;
    private String productname;
    private String producticon;
    private String productmodelno;
    private String chrgname;
    private String srvcchrg;
    private String srvcdatetime;
    private String orderon;
    private String totchrg;


    public OrderSetGet(String recordid, String jobid, String productname, String producticon, String productmodelno, String chrgname, String srvcchrg,String srvcdatetime,String orderon,String totchrg){
       this.recordid =recordid;
       this.jobid=jobid;
       this.productname=productname;
       this.producticon=producticon;
       this.productmodelno=productmodelno;
       this.chrgname=chrgname;
       this.srvcchrg=srvcchrg;
       this.srvcdatetime=srvcdatetime;
       this.orderon=orderon;
       this.totchrg=totchrg;
    }

    public String getRecordid() {
        return recordid;
    }

    public void setRecordid(String recordid) {
        this.recordid = recordid;
    }

    public String getJobid() {
        return jobid;
    }

    public void setJobid(String jobid) {
        this.jobid = jobid;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getProducticon() {
        return producticon;
    }

    public void setProducticon(String producticon) {
        this.producticon = producticon;
    }

    public String getProductmodelno() {
        return productmodelno;
    }

    public void setProductmodelno(String productmodelno) {
        this.productmodelno = productmodelno;
    }

    public String getChrgname() {
        return chrgname;
    }

    public void setChrgname(String chrgname) {
        this.chrgname = chrgname;
    }

    public String getSrvcchrg() {
        return srvcchrg;
    }

    public void setSrvcchrg(String srvcchrg) {
        this.srvcchrg = srvcchrg;
    }

    public String getSrvcdatetime() {
        return srvcdatetime;
    }

    public void setSrvcdatetime(String srvcdatetime) {
        this.srvcdatetime = srvcdatetime;
    }

    public String getOrderon() {
        return orderon;
    }

    public void setOrderon(String orderon) {
        this.orderon = orderon;
    }

    public String getTotchrg() {
        return totchrg;
    }

    public void setTotchrg(String totchrg) {
        this.totchrg = totchrg;
    }
}
