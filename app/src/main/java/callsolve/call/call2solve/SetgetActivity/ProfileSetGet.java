package callsolve.call.call2solve.SetgetActivity;

public class ProfileSetGet {
    private String customername;
    private String customerphnno;
    private String customeremail;
    private String customeraddress;
    private String customerdistrict;
    private String customerdistrictname;
    private String customerpincode;
    private String customerimage;

   public ProfileSetGet(String customername, String customerphnno, String customeremail, String customeraddress, String customerdistrict, String customerdistrictname, String customerpincode, String customerimage){
       this.customername =customername;
       this.customerphnno =customerphnno;
       this.customeremail =customeremail;
       this.customeraddress =customeraddress;
       this.customerdistrict =customerdistrict;
       this.customerdistrictname =customerdistrictname;
       this.customerpincode =customerpincode;
       this.customerimage =customerimage;
   }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    public String getCustomerphnno() {
        return customerphnno;
    }

    public void setCustomerphnno(String customerphnno) {
        this.customerphnno = customerphnno;
    }

    public String getCustomeremail() {
        return customeremail;
    }

    public void setCustomeremail(String customeremail) {
        this.customeremail = customeremail;
    }

    public String getCustomeraddress() {
        return customeraddress;
    }

    public void setCustomeraddress(String customeraddress) {
        this.customeraddress = customeraddress;
    }

    public String getCustomerdistrict() {
        return customerdistrict;
    }

    public void setCustomerdistrict(String customerdistrict) {
        this.customerdistrict = customerdistrict;
    }
    public String getCustomerdistrictname() {
        return customerdistrictname;
    }

    public void setCustomerdistrictname(String customerdistrictname) {
        this.customerdistrictname = customerdistrictname;
    }
    public String getCustomerpincode() {
        return customerpincode;
    }

    public void setCustomerpincode(String customerpincode) {
        this.customerpincode = customerpincode;
    }

    public String getCustomerimage() {
        return customerimage;
    }

    public void setCustomerimage(String customerimage) {
        this.customerimage = customerimage;
    }


}
