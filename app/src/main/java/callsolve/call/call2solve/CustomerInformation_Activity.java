package callsolve.call.call2solve;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import callsolve.call.call2solve.SetgetActivity.OffersModel;
import callsolve.call.call2solve.SetgetActivity.User;
import callsolve.call.call2solve.SharePreferance.SharedPrefManager;
import callsolve.call.call2solve.URL.URLs;
public class CustomerInformation_Activity extends AppCompatActivity {
    Toolbar toolbar;
    Button conbookingId;
    String imageUrl,dist,pinid,prdctid,disName,Itemname,catID,productName,PId,subproductName,companyName,modelNo,time,productIssu,customername,customenumber,customeaddress,state,city,distric,pincode,landmark,fulldate;
    TextView NameId,addId,contractId;
    String customerId,customerName,customerphnno,customeremail,customeraddress,customerdistrict,customerdistrictname,customerpincode,customerimage,districtv;
    RadioGroup GrentedbuildigID;
    RadioButton cashId,cardId,neftId;
    String cash,card,neft;
    String paymentmode,paymentamnt;
    TextView mintorSerId,maintanceId,totalIdd,tvserviceId;
    private JSONArray array;
    RecyclerView offers_lst;
    List<OffersModel> offersList;
    ArrayList<OffersModel> arrlist =new ArrayList<>();
    String paymentamntName,SubproductIDD;
    TextView patymentID;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.customer_information);
        initToolBar();
        Intent intent = getIntent();
        catID = intent.getStringExtra("catID");
        dist = intent.getStringExtra("dist");
        disName = intent.getStringExtra("disName");
        pinid = intent.getStringExtra("pinid");
        Itemname = intent.getStringExtra("Itemname");
        prdctid = intent.getStringExtra("prdctid");
        productName = intent.getStringExtra("productName");
        PId = intent.getStringExtra("PId");
        subproductName = intent.getStringExtra("subproductName");
        imageUrl = intent.getStringExtra("imageUrl");
        companyName = intent.getStringExtra("companyName");
        modelNo = intent.getStringExtra("modelNo");
        time = intent.getStringExtra("time");
        productIssu = intent.getStringExtra("productIssu");
        customername = intent.getStringExtra("customername");
        customenumber = intent.getStringExtra("customenumber");
        customeaddress = intent.getStringExtra("customeaddress");
      //  state = intent.getStringExtra("state");
      //  city = intent.getStringExtra("city");
        distric = intent.getStringExtra("distric");
        pincode = intent.getStringExtra("pincode");
        landmark = intent.getStringExtra("landmark");
        fulldate = intent.getStringExtra("fulldate");
        districtv = intent.getStringExtra("districtv");
        SubproductIDD = intent.getStringExtra("SubproductIDD");
        Log.e("cusInformation"," "+catID+" "+dist+" "+disName+" "+pinid+" "+Itemname+" "
                +prdctid+" "+productName+" "+PId+" "+subproductName+" "+imageUrl+" "+companyName+" "+modelNo+" "+time+ " "+productIssu+" "
                +customername+" "+customenumber+" "+customeaddress+" "+" "+distric+" "+pincode+" "+landmark+" "+fulldate+" "+districtv+" "+SubproductIDD);
        String str = subproductName;
        int index = str.lastIndexOf('@');
        paymentamnt = str.substring(index +1);
        Log.e("Split_Val", " "+paymentamnt);
        String pname = subproductName;
        String[] output = pname.split("@");
        System.out.println(output[0]);
        System.out.println(output[1]);
        paymentamntName = output[0];
        mintorSerId = (TextView)findViewById(R.id.mintorSerId);
        maintanceId = (TextView)findViewById(R.id.maintanceId);
        totalIdd = (TextView)findViewById(R.id.totalIdd);
        tvserviceId =(TextView)findViewById(R.id.tvserviceId);
        mintorSerId.setText(paymentamnt);
        maintanceId.setText(paymentamnt);
        totalIdd.setText(paymentamnt);
        tvserviceId.setText(paymentamntName);
        conbookingId = (Button)findViewById(R.id.conbookingId);
        NameId = (TextView)findViewById(R.id.NameId);
        addId = (TextView)findViewById(R.id.addId);
        contractId = (TextView)findViewById(R.id.contractId);
        patymentID =(TextView)findViewById(R.id.patymentID);
        GrentedbuildigID =(RadioGroup)findViewById(R.id.GrentedbuildigID);
        cashId =(RadioButton)findViewById(R.id.cashId);
        cardId =(RadioButton)findViewById(R.id.cardId);
        neftId =(RadioButton)findViewById(R.id.neftId);
        offers_lst =(RecyclerView)findViewById(R.id.offers_lst);
        offersList =new ArrayList<>();
        offers_lst.setHasFixedSize(true);
        offers_lst.setLayoutManager(new LinearLayoutManager(this));

        GrentedbuildigID.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.cashId) {
                    cash = "cash";
                    paymentmode = cash;
                    Log.e("cash",cash);
                } else if(checkedId == R.id.cardId) {
                    card = "card";
                    paymentmode = card;
                    Log.e("card",card);
                }
                else if (checkedId== R.id.neftId){
                    neft = "neft";
                    paymentmode = neft;
                    Log.e("neft",neft);
                }

                else {
                    Toast.makeText(getApplicationContext(), "No Selected",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        conbookingId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bookingsuccess(customerId,catID,dist,pinid,prdctid,PId,SubproductIDD,companyName,modelNo,fulldate,time,productIssu,customenumber,customeaddress,landmark,paymentmode,paymentamnt);
            }
        });

        User user = SharedPrefManager.getInstance(getApplicationContext()).getUser();
        customerId = String.valueOf(user.getCustomerid());
        Log.e("CustomerID", customerId);
        FetchProfile(customerId);
        radiobutton();
    }
    public void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Customer Information");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_home_black);
        toolbar.setNavigationOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(),HomeNavigationDrawerActivity.class);
                        startActivity(intent);
                    }
                }
        );
    }

    public void FetchProfile(final String customerId){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.FETCHPROFILE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // progressBar.setVisibility(View.GONE);
                        Log.e("fetchProfile", response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            customerName = obj.getString("customer_name");
                            customerphnno = obj.getString("customer_phn_no");
                            customeremail = obj.getString("customer_email");
                            customeraddress = obj.getString("customer_address");
                            customerdistrict = obj.getString("customer_district");
                            customerdistrictname = obj.getString("customer_district_name");
                            customerpincode = obj.getString("customer_pincode");
                            customerimage = obj.getString("customer_image");
                            NameId.setText(customerName);
                            addId.setText(customeraddress);
                            contractId.setText(customerphnno);
                            Log.e("Customer_information",customername+" "+customerphnno+" "+customeremail+" "+customeraddress+" "+customerdistrict+" "+customerpincode+" "+customerimage);

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
                params.put("customer_id", customerId);
                Log.e("CusID",customerId);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton volleySingleton = VolleySingleton.getInstance(getApplicationContext());
        stringRequest.setShouldCache(false);
        volleySingleton.addToRequestQueue(stringRequest);
    }
    public void bookingsuccess(final String customerId,final String catID,final String dist,final String pinid,final String prdctid,final String PId,final String SubproductIDD,final String companyName,final String modelNo,final String fulldate,final String time,final String productIssu,final String customenumber,final String customeaddress,final String landmark,final String paymentmode,final String paymentamnt){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.BOOKINGSUCCESS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // progressBar.setVisibility(View.GONE);
                        Log.e("betch_booking", response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            String msg = obj.getString("msg");
                            String bookingid = obj.getString("booking_id");
                            String productname = obj.getString("product_name");
                            String subproductname = obj.getString("sub_product_name");
                            String servicename = obj.getString("service_name");
                            String productissue = obj.getString("product_issue");
                            String companyname = obj.getString("company_name");
                            String modelname = obj.getString("model_name");
                            String customername = obj.getString("customer_name");
                            String customerphn = obj.getString("customer_phn");
                            String customeradrs = obj.getString("customer_adrs");
                            String preferdate = obj.getString("prefer_date");
                            String prefertime = obj.getString("prefer_time");
                            String district = obj.getString("district");
                            String pincode = obj.getString("pincode");
                            String paymentmode =obj.getString("payment_mode");
                            String payment =obj.getString("payment");
                            String bookingrowid = obj.getString("booking_row_id");
                            String  landmark = obj.getString("land_mark");
                            String techname = obj.getString("tech_name");
                            String techimg = obj.getString("tech_img");
                            Log.e("UserDetails",msg+" "+bookingid+" "+productname+" "
                                    +subproductname+" "+servicename+" "
                                    +productissue+" "+companyname+" "
                                    +modelname+" "+customername+" "
                                    +customerphn+" "+customeradrs+" "
                                    +preferdate+" "+prefertime+" "+district+" "+pincode+" "+paymentmode+" "+payment+" "+bookingrowid+" "+techname+" "+techimg);
                            Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), Product_DetailsActivity.class);
                            Bundle bundle_edit = new Bundle();
                            bundle_edit.putString("msg", msg);
                            bundle_edit.putString("bookingid", bookingid);
                            bundle_edit.putString("productname", productname);
                            bundle_edit.putString("subproductname", subproductname);
                            bundle_edit.putString("servicename", servicename);
                            bundle_edit.putString("productissue", productissue);
                            bundle_edit.putString("companyname", companyname);
                            bundle_edit.putString("customername",customername);
                            bundle_edit.putString("customerphn", customerphn);
                            bundle_edit.putString("customeradrs", customeradrs);
                            bundle_edit.putString("preferdate", preferdate);
                            bundle_edit.putString("prefertime",prefertime);
                            bundle_edit.putString("district", district);
                            bundle_edit.putString("pincode", pincode);
                            bundle_edit.putString("paymentmode", paymentmode);
                            bundle_edit.putString("payment", payment);
                            bundle_edit.putString("modelname", modelname);
                            bundle_edit.putString("bookingrowid", bookingrowid);
                            bundle_edit.putString("landmark", landmark);
                            bundle_edit.putString("techname", techname);
                            bundle_edit.putString("techimg", techimg);
                            intent.putExtras(bundle_edit);
                            startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),"Please Select Payment Method",Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("customer_id", customerId);
                params.put("category",catID);
                params.put("district", dist);
                params.put("pincode_id", pinid);
                params.put("product", prdctid);
                params.put("sub_prdct", PId);
                params.put("srvc_typ", SubproductIDD);
                params.put("cmpny_name", companyName);
                params.put("model_no", modelNo);
                params.put("repair_dt", fulldate);
                params.put("repair_time", time);
                params.put("product_prblm", productIssu);
                params.put("alternt_phn", customenumber);
                params.put("service_adrs", customeaddress);
                params.put("service_landmark", landmark);
                params.put("payment_mode", paymentmode);
                params.put("payment_amnt", paymentamnt);
                Log.e("Payment",customerId+" "+catID+" "+dist+" "
                        +pinid+" "+prdctid+" "+PId+" "+SubproductIDD+" "+companyName+" "+modelNo+" "+fulldate+" "
                        +time+" "+productIssu+" "+customenumber+" "+customeaddress+" "+landmark+" "+paymentmode+" "+customerId+" "+paymentamnt);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton volleySingleton = VolleySingleton.getInstance(getApplicationContext());
        stringRequest.setShouldCache(false);
        volleySingleton.addToRequestQueue(stringRequest);
    }

    public void radiobutton(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.RADIOBUTTON,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // progressBar.setVisibility(View.GONE);
                        Log.e("fetchProfile", response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            array = obj.getJSONArray("pay_mode");
                            for (int i =0; i<array.length(); i++){
                                JSONObject object = array.getJSONObject(i);
                                String id = object.getString("id");
                                String name = object.getString("name");
                                Log.e("Payment_Mode",id+" "+name);
                                offersList.add(new OffersModel(
                                        object.getString("id"),
                                        object.getString("name")
                                ));
                                arrlist.addAll(offersList);
                            }
                            OffersRecyclerViewAdapter adapter = new OffersRecyclerViewAdapter( offersList,getApplicationContext());
                            offers_lst.setAdapter(adapter);
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
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton volleySingleton = VolleySingleton.getInstance(getApplicationContext());
        stringRequest.setShouldCache(false);
        volleySingleton.addToRequestQueue(stringRequest);

    }

    public class OffersRecyclerViewAdapter extends RecyclerView.Adapter<OffersRecyclerViewAdapter.ViewHolder> {

        private List<OffersModel> offersList;
        private Context context;

        private int lastSelectedPosition = -1;

        public OffersRecyclerViewAdapter(List<OffersModel> offersListIn, Context ctx) {
            offersList = offersListIn;
            context = ctx;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                       int viewType) {

            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.offer_amount, parent, false);

            ViewHolder viewHolder =
                    new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder,
                                     int position) {
            OffersModel offersModel = offersList.get(position);
            holder.offerName.setText(offersModel.getFname());
            holder.fId.setText(offersModel.getFiD());
            holder.selectionState.setText(offersModel.getFname());
            holder.selectionState.setChecked(lastSelectedPosition == position);
        }
        @Override
        public int getItemCount() {
            return offersList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView offerName,fId;
            public RadioButton selectionState;
            public ViewHolder(View view) {
                super(view);
                offerName = (TextView) view.findViewById(R.id.offer_name);
                fId = (TextView) view.findViewById(R.id.fId);
                selectionState = (RadioButton) view.findViewById(R.id.offer_select);
                selectionState.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        lastSelectedPosition = getAdapterPosition();
                        notifyDataSetChanged();
//                        Toast.makeText(OffersRecyclerViewAdapter.this.context,
//                                "selected offer is " + fId.getText(),
//                                Toast.LENGTH_LONG).show();
                        paymentmode = fId.getText().toString();

                    }
                });
            }
        }
    }
//    @Override
//    public void onBackPressed() {
//        Intent intent_info = new Intent(getApplicationContext(),HomeNavigationDrawerActivity.class);
//        startActivity(intent_info);
//        overridePendingTransition(R.anim.slide_up_info,R.anim.no_change);
//    }

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
