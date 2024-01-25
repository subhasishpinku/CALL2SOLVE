package callsolve.call.call2solve.SetgetActivity;

public class User {
    private String customerid;

    public User(String customerid){
        this.customerid =customerid;
    }

    public String getCustomerid() {
        return customerid;
    }

    public void setCustomerid(String customerid) {
        this.customerid = customerid;
    }
}
