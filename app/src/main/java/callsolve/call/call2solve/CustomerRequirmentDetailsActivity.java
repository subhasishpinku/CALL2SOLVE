package callsolve.call.call2solve;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import callsolve.call.call2solve.SetgetActivity.CustomerRequirmentDetailsSetGet;
import callsolve.call.call2solve.URL.URLs;

public class CustomerRequirmentDetailsActivity extends AppCompatActivity implements Spinner.OnItemSelectedListener {
    Toolbar toolbar;
    RecyclerView rcv;
    CustomerRequirmentDetailsAdapter customerRequirmentDetailsAdapter;
    List<CustomerRequirmentDetailsSetGet> customerRequirmentDetailsSetGets;
    ArrayList<CustomerRequirmentDetailsSetGet> arrayList = new ArrayList<CustomerRequirmentDetailsSetGet>();
    Spinner productspID, serviceSpID, TypeserviceSpID,currentdateID,getdateId;
    EditText cumpanyId, mobileId, addressID;
    TextView timeID;
    ImageView timeclockID;
    Button conbookingId;
    private int mYear, mMonth, mDay, mHour, mMinute,hour,minute;
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;
    String imageUrl,dist,pinid,prdctid,disName,Itemname,catID,productname;
    ArrayList<String> ProductArray;
    private JSONArray array;
    private JSONArray arrayy;
    ArrayList<String> Productname;
    String ProductId = "";
    String PId,PName,productName,subproductName;
    ArrayList<String> productype;
    ArrayList<String> currentdate;
    ArrayList<String> currenttime;
    TimePickerDialog mTimePicker;
    String dat,datee,month,year,monthname,fulldate,districtv,subproductID,SubproductIDD,fulltime,btime ;
    TextView visitId;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_requirment_details_activity);
        Intent intent = getIntent();
        imageUrl = intent.getStringExtra("imageUrl");
        dist = intent.getStringExtra("dist");
        pinid = intent.getStringExtra("pinid");
        prdctid = intent.getStringExtra("prdctid");
        disName = intent.getStringExtra("disName");
        Itemname = intent.getStringExtra("Itemname");
        catID = intent.getStringExtra("catID");
        productname = intent.getStringExtra("productname");
        districtv = intent.getStringExtra("districtv");
        ProductArray = new ArrayList<>();
        ProductArray.add(Itemname);
        Log.e("CustomerDetails"," "+imageUrl+" "+dist+" "+pinid+" "+prdctid+" "+disName+" "+" "+Itemname+" "+catID+" "+productname+" "+districtv);
        initToolBar();
        rcv = (RecyclerView) findViewById(R.id.rcv);
        productspID = (Spinner) findViewById(R.id.productspID);
        serviceSpID = (Spinner) findViewById(R.id.serviceSpID);
        TypeserviceSpID = (Spinner) findViewById(R.id.TypeserviceSpID);
        currentdateID = (Spinner)findViewById(R.id.currentdateID);
        getdateId  = (Spinner)findViewById(R.id.getdateId);
        cumpanyId = (EditText) findViewById(R.id.cumpanyId);
        mobileId = (EditText) findViewById(R.id.mobileId);
        timeID = (TextView) findViewById(R.id.timeID);
        addressID = (EditText) findViewById(R.id.addressID);
        timeclockID = (ImageView) findViewById(R.id.timeclockID);
        conbookingId = (Button) findViewById(R.id.conbookingId);
        visitId= (TextView)findViewById(R.id.visitId);
        myCalendar = Calendar.getInstance();
        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                //updateLabel();
            }
        };
        customerRequirmentDetailsSetGets = new ArrayList<>();
        rcv.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 4);
        rcv.setLayoutManager(layoutManager);
//        rcv.setAdapter(customerRequirmentDetailsAdapter);
//        customerRequirmentDetailsSetGets.add(
//                new CustomerRequirmentDetailsSetGet(
//                        "5 th", "June","2019" ,"10/06/2019"));
//        customerRequirmentDetailsSetGets.add(
//                new CustomerRequirmentDetailsSetGet(
//                        "6 th", "June", "2019", "11/06/2019"));
//        customerRequirmentDetailsSetGets.add(
//                new CustomerRequirmentDetailsSetGet(
//                        "7 th", "June","2019","12/06/2019"));
//        customerRequirmentDetailsSetGets.add(
//                new CustomerRequirmentDetailsSetGet(
//                        "8 th", "June", "2019","13/06/2019"));
//        customerRequirmentDetailsSetGets.add(
//                new CustomerRequirmentDetailsSetGet(
//                        "9 th", "June", "2019","14/06/2019"));
//        customerRequirmentDetailsSetGets.add(
//                new CustomerRequirmentDetailsSetGet(
//                        "10 th", "June", "2019","15/06/2019"));
//        customerRequirmentDetailsSetGets.add(
//                new CustomerRequirmentDetailsSetGet(
//                        "11 th", "June", "2019","16/06/2019"));
//        customerRequirmentDetailsAdapter = new CustomerRequirmentDetailsAdapter(this, customerRequirmentDetailsSetGets);
//        rcv.setAdapter(customerRequirmentDetailsAdapter);
        ShowSevenDays();
        timeclockID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                new DatePickerDialog(CustomerRequirmentDetailsActivity.this, date, myCalendar.get(Calendar.YEAR),
//                        myCalendar.get(Calendar.MONTH),
//                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                mTimePicker = new TimePickerDialog(CustomerRequirmentDetailsActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        timeID.setText( selectedHour + ":" +selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        conbookingId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String companyName = cumpanyId.getText().toString().trim();
                final String modelNo = mobileId.getText().toString().trim();
              //  final String time = timeID.getText().toString().trim();
                final String productIssu = addressID.getText().toString().trim();
                if (TextUtils.isEmpty(companyName)) {
                    cumpanyId.setError("Please enter Company");
                    cumpanyId.requestFocus();
                    return;
                }
//                if (TextUtils.isEmpty(modelNo)) {
//                    mobileId.setError("Please enter Model No");
//                    mobileId.requestFocus();
//                    return;
//                }
//                if (TextUtils.isEmpty(time)) {
//                    timeID.setError("Please enter Time");
//                    timeID.requestFocus();
//                    return;
//                }
                if (TextUtils.isEmpty(productIssu)) {
                    addressID.setError("Please enter Address");
                    addressID.requestFocus();
                    return;
                }
                if (subproductName!=null) {
                    if (fulldate != null) {
                        if (fulltime != null) {
                            Intent intent = new Intent(getApplicationContext(), CustomerAddress.class);
                            Bundle bundle_edit = new Bundle();
                            bundle_edit.putString("catID", catID);
                            bundle_edit.putString("dist", dist);
                            bundle_edit.putString("disName", disName);
                            bundle_edit.putString("pinid", pinid);
                            bundle_edit.putString("Itemname", Itemname);
                            bundle_edit.putString("prdctid", prdctid);
                            bundle_edit.putString("productName", productName);
                            bundle_edit.putString("PId", PId);
                            bundle_edit.putString("subproductName", subproductName);
                            bundle_edit.putString("imageUrl", imageUrl);
                            bundle_edit.putString("companyName", companyName);
                            bundle_edit.putString("modelNo", modelNo);
                            bundle_edit.putString("time", fulltime);
                            bundle_edit.putString("productIssu", productIssu);
                            bundle_edit.putString("fulldate", fulldate);
                            bundle_edit.putString("districtv", districtv);
                            bundle_edit.putString("SubproductIDD", SubproductIDD);
                            intent.putExtras(bundle_edit);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Please Select Time", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Please Select Date", Toast.LENGTH_SHORT).show();

                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "Please Select Service Name", Toast.LENGTH_SHORT).show();
                }

            }
        });
        productspID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                productName  = productspID.getItemAtPosition(productspID.getSelectedItemPosition()).toString();
              // Toast.makeText(getApplicationContext(),productName,Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });
        productspID.setAdapter(new ArrayAdapter<String>(CustomerRequirmentDetailsActivity.this, android.R.layout.simple_spinner_dropdown_item, ProductArray));
        serviceSpID.setOnItemSelectedListener(this);
        //TypeserviceSpID.setOnItemSelectedListener(this);
        TypeserviceSpID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                subproductName  = TypeserviceSpID.getItemAtPosition(TypeserviceSpID.getSelectedItemPosition()).toString();
                SubproductIDD = getfetchID(position);
               // Toast.makeText(getApplicationContext(),subproductName+" "+SubproductIDD,Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        currentdateID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                fulldate  = currentdateID.getItemAtPosition(currentdateID.getSelectedItemPosition()).toString();
                //Toast.makeText(getApplicationContext(),fulldate+" ",Toast.LENGTH_LONG).show();
                currenttime.clear();
                CheckDate(fulldate);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Productname = new ArrayList<>();
        productype = new ArrayList<>();
        currentdate = new ArrayList<>();
        currenttime = new ArrayList<>();
        Checkrate();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 1);
        Date tommrrow = cal.getTime();
        Log.e("Date", " " +tommrrow);
        getdateId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                fulltime = getdateId.getItemAtPosition(getdateId.getSelectedItemPosition()).toString();
              // Toast.makeText(getApplicationContext(),fulltime+" ",Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Customer Requirment Details");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_home_black);
        toolbar.setNavigationOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // finish();
                        Intent intent = new Intent(getApplicationContext(),HomeNavigationDrawerActivity.class);
                        startActivity(intent);
                    }
                }
        );
    }
     private void CheckDate(final String fulldate){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.GATEDATE,
                 new Response.Listener<String>() {
                     @Override
                     public void onResponse(String response) {
                         Log.e("getdate"," "+response);
                         try {
                          JSONObject obj = new JSONObject(response);
                          JSONArray jsonArray = obj.getJSONArray("time");
                             for (int i = 0; i<jsonArray.length(); i++) {
                                 JSONObject jsonObject = jsonArray.getJSONObject(i);
                                 btime = jsonObject.getString("b_time");
                                 Log.e("btime",btime);
                                 currenttime.add(btime);
                                 Log.e("TT",btime);
                                 getdateId.setAdapter(new ArrayAdapter<String>(CustomerRequirmentDetailsActivity.this, android.R.layout.simple_spinner_dropdown_item, currenttime));
                             }
                         } catch (JSONException e) {
                             e.printStackTrace();
                         }

                     }
                 },
                 new Response.ErrorListener() {
                     @Override
                     public void onErrorResponse(VolleyError error) {

                     }
                 }) {
             @Override
             protected Map<String, String> getParams() throws AuthFailureError {
                 Map<String, String> params = new HashMap<>();
                 params.put("book_date",fulldate);
                 Log.e("fulldate"," "+fulldate);
                 return params;

             }
         };
         stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
         //VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
         VolleySingleton volleySingleton = VolleySingleton.getInstance(this);
         stringRequest.setShouldCache(false);
         volleySingleton.addToRequestQueue(stringRequest);
     }
    private void updateLabel() {
        String myFormat = "dd-MM-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        timeID.setText(sdf.format(myCalendar.getTime()));
    }
    public void Checkrate(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.FREACHPRODUCT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("ratecheck"," "+response);
                        try {
                             array = new JSONArray(response);
                            Log.e("array", " "+array);
                            for (int i = 0; i<array.length(); i++) {
                                JSONObject jsonObject = array.getJSONObject(i);
                                String subid = jsonObject.getString("id");
                                String subprductnm = jsonObject.getString("sub_prduct_nm");
                                String img = jsonObject.getString("img");
                                Log.e("Customer_fetchproduct", subid + " " + subprductnm + " " + img);
                            }
                            getdis(array);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("dist",dist);
                params.put("pin_id", pinid);
                params.put("prdct_id", prdctid);
                Log.e("customer_rate",dist+" "+pinid+" "+prdctid);
                return params;

            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
        VolleySingleton volleySingleton = VolleySingleton.getInstance(this);
        stringRequest.setShouldCache(false);
        volleySingleton.addToRequestQueue(stringRequest);
    }

    private void getdis(JSONArray j){
        for(int i=0;i<j.length();i++){
            try {
                JSONObject json = j.getJSONObject(i);
                Productname.add(json.getString(URLs.TAG_SUB_PRO_NAME));
                Log.e("pname"," "+Productname);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        serviceSpID.setAdapter(new ArrayAdapter<String>(CustomerRequirmentDetailsActivity.this, android.R.layout.simple_spinner_dropdown_item, Productname));
    }
    private String getID(int position){
        try {
            JSONObject json = array.getJSONObject(position);
            ProductId = json.getString(URLs.TAG_SUB_PRO_ID);
            Log.e("pId"," "+ProductId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ProductId;
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        PId = getID(position);
        Listproduct(PId);
        productype.clear();
        PName = serviceSpID.getSelectedItem().toString();
        VisitingCharge(PId);
       // Toast.makeText(parent.getContext(),PName+" "+" "+PId, Toast.LENGTH_LONG).show();
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    public void VisitingCharge(final String PId){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.VISITINGCHARGE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("VISITINGCHARGE"," "+response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String gnrate = jsonObject.getString("visiting_charge");
                            visitId.setText("Visiting Charge @" +gnrate);
                            Log.e("gnrate",gnrate);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("sub_pro_id",PId);
                Log.e("VISITHINGCHAGE",PId);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
        VolleySingleton volleySingleton = VolleySingleton.getInstance(this);
        stringRequest.setShouldCache(false);
        volleySingleton.addToRequestQueue(stringRequest);
    }
    public void Listproduct(final String PId){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.FATCHPRODUCTWISERAT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("fetchproduct"," "+response);
                        try {
                                arrayy = new JSONArray(response);
                                Log.e("array", " "+arrayy);
                                for (int i = 0; i<arrayy.length(); i++){
                                JSONObject jsonObject = arrayy.getJSONObject(i);
                                String fetId =jsonObject.getString("id");
                                String chargename = jsonObject.getString("charge_name");
                                String mainrate = jsonObject.getString("main_rate");
                                String ofrrate  = jsonObject.getString("ofr_rate");
                                Log.e("customer_fetchdata", chargename+" "+mainrate+" "+ofrrate+" "+fetId);
                                //productype.add(chargename+" "+"/"+mainrate);
                               // TypeserviceSpID.setAdapter(new ArrayAdapter<String>(CustomerRequirmentDetailsActivity.this, android.R.layout.simple_spinner_dropdown_item, productype));
                                }
                            getsubname(arrayy);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("sub_pro_id",PId);
                Log.e("productrate",PId);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
        VolleySingleton volleySingleton = VolleySingleton.getInstance(this);
        stringRequest.setShouldCache(false);
        volleySingleton.addToRequestQueue(stringRequest);
    }
    private void getsubname(JSONArray j){
        for(int i=0;i<j.length();i++){
            try {
                JSONObject json = j.getJSONObject(i);
                String chargename = json.getString(URLs.TAG_SUB_FETCICHARGENAME);
                String ofrrate  = json.getString(URLs.TAG_SUB_FETCICHARGEOFRRATE);
                String mainrate =  json.getString(URLs.TAG_SUB_FETCICHARGEMAINRATE);
                if (ofrrate.equals("0")){
                    productype.add(chargename+" "+"@"+mainrate);
                }
                else {
                    productype.add(chargename+" "+"@"+ofrrate);
                }


                Log.e("pname"," "+productype);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        TypeserviceSpID.setAdapter(new ArrayAdapter<String>(CustomerRequirmentDetailsActivity.this, android.R.layout.simple_spinner_dropdown_item, productype));
    }
    private String getfetchID(int position){
        try {
            JSONObject json = arrayy.getJSONObject(position);
            subproductID = json.getString(URLs.TAG_SUB_FETCID);
            Log.e("subpro"," "+subproductID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return subproductID;
    }
    public void ShowSevenDays(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.SEVENDAYS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("SEVEN_DAYS"," "+response);
                        try {
                            //arrayy = new JSONArray(response);
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("day");
                            Log.e("array", " "+arrayy);
                            for (int i = 0; i<jsonArray.length(); i++){
                                JSONObject json = jsonArray.getJSONObject(i);
                                Log.e("seven_days", " "+jsonObject);
                                dat = json.getString("date");
                                currentdate.add(dat);
                                Log.e("dd",dat);
                                currentdateID.setAdapter(new ArrayAdapter<String>(CustomerRequirmentDetailsActivity.this, android.R.layout.simple_spinner_dropdown_item, currentdate));
//                                String date=dat;
//                                String[] items1 = date.split("-");
//                                datee=items1[0];
//                                month=items1[1];
//                                year=items1[2];
                                Log.e("YourDate",datee+" "+month+" "+year);
                                Calendar cal=Calendar.getInstance();
                                SimpleDateFormat month_date = new SimpleDateFormat("MMM");
                                monthname = month_date.format(cal.getTime());
                                Log.e("Month", monthname);
                                customerRequirmentDetailsSetGets.add(
                                        new CustomerRequirmentDetailsSetGet(
                                                datee, monthname,year ,dat));
                            }
                           // customerRequirmentDetailsAdapter = new CustomerRequirmentDetailsAdapter(getApplicationContext(), customerRequirmentDetailsSetGets);
                           // rcv.setAdapter(customerRequirmentDetailsAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
//                params.put("sub_pro_id",PId);
//                Log.e("productrate",PId);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
        VolleySingleton volleySingleton = VolleySingleton.getInstance(this);
        stringRequest.setShouldCache(false);
        volleySingleton.addToRequestQueue(stringRequest);

    }
    public class CustomerRequirmentDetailsAdapter extends RecyclerView.Adapter<CustomerRequirmentDetailsAdapter.ViewHolder> {
        private Context mCtx;
        private List<CustomerRequirmentDetailsSetGet> customerRequirmentDetailsSetGets;
        ArrayList<String> lstChk= new ArrayList<>();
        ArrayList<String> topics_title, topics_id;
        private int lastSelectedPosition = -1;
        int type=-1;
        boolean isChecked;
        private SparseBooleanArray itemStateArray= new SparseBooleanArray();
        public CustomerRequirmentDetailsAdapter(Context mCtx, List<CustomerRequirmentDetailsSetGet> customerRequirmentDetailsSetGets) {
            this.mCtx = mCtx;
            this.customerRequirmentDetailsSetGets = customerRequirmentDetailsSetGets;
        }
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_requirmentde, parent, false);
            return new ViewHolder(view);
        }
        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            CustomerRequirmentDetailsSetGet customerRequirmentDetailsSetGet = customerRequirmentDetailsSetGets.get(position);
            type =position;

            String datee = customerRequirmentDetailsSetGet.getDate();
            if (datee.equals("01")){
                holder.dateId.setText(datee+" "+"st");
            }
            else if (datee.equals("02")){
                holder.dateId.setText(datee+" "+"nd");
            }
            else if (datee.equals("03")){
                holder.dateId.setText(datee+" "+"rd");
            }
            else {
                holder.dateId.setText(datee+" "+"th");
            }

            holder.monthId.setText(customerRequirmentDetailsSetGet.getMonth());
            holder.fulldatID.setText(customerRequirmentDetailsSetGet.getAllDate());
            holder.dateId.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fulldate = holder.fulldatID.getText().toString();
                    Log.e("FULLDATE",fulldate);
                    type = position;
//                    if (position==0) {
//                        Log.e("position", "0");
//                        holder.lvColorID.setBackgroundColor(mCtx.getResources().getColor(android.R.color.holo_red_light));
//                        fulldate = holder.fulldatID.getText().toString();
//                        Log.e("FULLDATE",fulldate);
//                    }
//                    else {
//                        fulldate = "";
//                        holder.lvColorID.setBackgroundColor(mCtx.getResources().getColor(android.R.color.holo_green_dark));
//                    }
//                    if (position==1){
//                        Log.e("position","1");
//                        Log.e("position", "0");
//                        holder.lvColorID.setBackgroundColor(mCtx.getResources().getColor(android.R.color.holo_red_light));
//                        fulldate = holder.fulldatID.getText().toString();
//                        Log.e("FULLDATE",fulldate);
//                    }
//                    else {
//                        fulldate = "";
//                        holder.lvColorID.setBackgroundColor(mCtx.getResources().getColor(android.R.color.holo_green_dark));
//                    }
//                    if (position==2){
//                        Log.e("position","2");
//                    }
//                    if (position==3){
//                        Log.e("position","3");
//                    }
//                    if (position==4){
//                        Log.e("position","4");
//                    }
//                    if (position==5){
//                        Log.e("position","5");
//                    }
//                    if (position==6){
//                        Log.e("position","6");
//                    }

             //       holder.bind(position);

                }
            });
            holder.ck.setChecked(position== lastSelectedPosition);
            holder.ck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked == true) {
                              //  holder.lvColorID.setBackgroundColor(mCtx.getResources().getColor(android.R.color.holo_red_light));
                                fulldate = holder.fulldatID.getText().toString();
                                Log.e("FULLDATE", fulldate);
                               // notifyItemChanged(position);
                               lastSelectedPosition =  position;
                               Log.e("position", " "+position);

                        }
                        else  {
                            fulldate = "";
                            //holder.lvColorID.setBackgroundColor(mCtx.getResources().getColor(android.R.color.holo_green_dark));
                            lastSelectedPosition = -1;
                            Log.e("FULLDATE", "no");
                            notifyDataSetChanged();

                        }
                    }
                });


//           holder.ck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                    if(compoundButton.isChecked())
//                    {
//                        if (position==0){
//                            Log.e("position","0");
//                            compoundButton.setChecked(true);
//                            holder.lvColorID.setBackgroundColor(mCtx.getResources().getColor(android.R.color.holo_red_light));
//                        }
//                        else if (position==1){
//                            compoundButton.setChecked(false);
//                            holder.lvColorID.setBackgroundColor(mCtx.getResources().getColor(android.R.color.holo_green_dark));
//
//                        }
//
//                    }
//                    else
//                    {
//                        compoundButton.setChecked(false);
//                        holder.lvColorID.setBackgroundColor(mCtx.getResources().getColor(android.R.color.holo_green_dark));
//
//                    }
//                }
//            });

           // holder.ck.setChecked(lastSelectedPosition == position);

//            if  (type == position){
//                holder.cardview1.setBackgroundColor(Color.parseColor("#FF00A7"));
//
//            }
//
//            else {
//
//                holder.cardview1.setBackgroundColor(Color.parseColor("#179EDE"));
//
//            }
        }


        @Override
        public int getItemCount() {
            return customerRequirmentDetailsSetGets.size();
//            if (customerRequirmentDetailsSetGets == null) {
//                return 0;
//            }
//            return customerRequirmentDetailsSetGets.size();
        }
        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView dateId,monthId,fulldatID;
            private LinearLayout lvColorID;
            public CardView cardview1;
            private CheckBox ck;
            public ViewHolder(View view) {
                super(view);
                dateId = (TextView) view.findViewById(R.id.dateId);
                monthId = (TextView) view.findViewById(R.id.monthId);
                fulldatID = (TextView) view.findViewById(R.id.fulldatID);
                lvColorID = (LinearLayout) view.findViewById(R.id.lvColorID);
                cardview1 = (CardView) view.findViewById(R.id.cardview1);
                ck = (CheckBox) view.findViewById(R.id.ck);
//                ck.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        lastSelectedPosition = getAdapterPosition();
//                        notifyDataSetChanged();
//                        Toast.makeText(CustomerRequirmentDetailsAdapter.this.mCtx,
//                            "selected offer is " + lastSelectedPosition,
//                                Toast.LENGTH_LONG).show();
//
//
//                    }
//                });

                //view.setOnClickListener((View.OnClickListener) mCtx);
            }

//            public void bind(int position) {
//                if (!itemStateArray.get(position, false)) {
//                    ck.setChecked(false);
//                }
//                else {
//                    ck.setChecked(true);
//                    if (itemStateArray.equals(0)){
//                        lvColorID.setBackgroundColor(mCtx.getResources().getColor(android.R.color.holo_red_light));
//                        if (itemStateArray.equals(1)){
//                            lvColorID.setBackgroundColor(mCtx.getResources().getColor(android.R.color.holo_green_dark));
//                        }
//                    }
//                }
//            }
        }
    }


    @Override
    public void onBackPressed() {
        Intent intent_info = new Intent(getApplicationContext(),HomeNavigationDrawerActivity.class);
        startActivity(intent_info);
        overridePendingTransition(R.anim.slide_up_info,R.anim.no_change);
    }

    @Override
    public void onResume(){
        super.onResume();
        System.out.println("Inside onResume");
    }

    @Override
    public void onStart(){
        super.onStart();
        System.out.println("Inside onStart");
    }

    @Override
    public void onRestart(){
        super.onRestart();
        System.out.println("Inside onReStart");
    }

    @Override
    public void onPause(){
        super.onPause();
        System.out.println("Inside onPause");
    }

    @Override
    public void onStop(){
        super.onStop();
        System.out.println("Inside onStop");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        System.out.println("Inside onDestroy");
    }
}
