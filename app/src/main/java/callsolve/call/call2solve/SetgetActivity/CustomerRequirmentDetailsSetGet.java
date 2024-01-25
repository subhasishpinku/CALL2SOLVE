package callsolve.call.call2solve.SetgetActivity;

public class CustomerRequirmentDetailsSetGet {
    private String date;
    private String month;
    private String year;
    private String allDate;
    public CustomerRequirmentDetailsSetGet(String date, String month, String year, String allDate){
        this.date =date;
        this.month= month;
        this.year =year;
        this.allDate =allDate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getAllDate() {
        return allDate;
    }

    public void setAllDate(String allDate) {
        this.allDate = allDate;
    }
}
