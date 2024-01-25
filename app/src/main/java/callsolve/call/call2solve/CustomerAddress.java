package callsolve.call.call2solve;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

import callsolve.call.call2solve.SetgetActivity.ProfileSetGet;
import callsolve.call.call2solve.SetgetActivity.User;
import callsolve.call.call2solve.SharePreferance.SharedPrefManager;
import callsolve.call.call2solve.SharePreferance.SharedPrefManagerProfile;
import callsolve.call.call2solve.URL.URLs;

public class CustomerAddress extends AppCompatActivity {
    TextView NameId,addId,contractId,districId,pincodeId;
    CheckBox checkID;
    EditText cusNameId,contractID,stateID,cityID,landId;
    EditText addressID;
    Toolbar toolbar;
    Button conbookingId;
    String imageUrl,dist,pinid,prdctid,disName,Itemname,catID,productName,PId,subproductName,companyName,modelNo,time,productIssu;
    String customerId,customername,customerphnno,customeremail,customeraddress,customerdistrict,customerdistrictname,customerpincode,customerimage,fulldate,districtv,SubproductIDD;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.customeraddress);
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
        fulldate  = intent.getStringExtra("fulldate");
        districtv = intent.getStringExtra("districtv");
        SubproductIDD = intent.getStringExtra("SubproductIDD");
        Log.e("CusTAddress"," "+catID+" "+dist+" "+disName+" "+pinid+" "+Itemname+" "
                +prdctid+" "+productName+" "+PId+" "+subproductName+" "+imageUrl+" "+companyName+" "+modelNo+" "+time+ " "+productIssu+" "+fulldate+" "+districtv+" "+SubproductIDD);
        NameId = (TextView)findViewById(R.id.NameId);
        addId = (TextView)findViewById(R.id.addId);
        contractId = (TextView)findViewById(R.id.contractId);
        checkID = (CheckBox)findViewById(R.id.checkID);
        conbookingId = (Button)findViewById(R.id.conbookingId);
        cusNameId = (EditText)findViewById(R.id.cusNameId);
        contractID =(EditText)findViewById(R.id.contractID);
        addressID =(EditText) findViewById(R.id.addressID);
       // stateID =(EditText)findViewById(R.id.stateID);
       // cityID = (EditText)findViewById(R.id.cityID);
        districId = (TextView)findViewById(R.id.districId);
        pincodeId =(TextView) findViewById(R.id.pincodeId);
        landId =(EditText)findViewById(R.id.landId);
        pincodeId.setText(districtv);
        districId.setText(disName);
        ProfileSetGet profile = SharedPrefManagerProfile.getInstance(getApplicationContext()).profileSetGet();
        String  pincode = String.valueOf(profile.getCustomerpincode());
        Log.e("PINCODE", pincode);
        if (districtv.equals(pincode)){
            checkID.setVisibility(View.VISIBLE);
        }
        else {

        }

        checkID.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked==true){
                   // cusNameId.setText(customername);
                    addressID.setText(customeraddress);
                  //  contractID.setText(customerphnno);
                }
                if (isChecked==false){
                 //   cusNameId.setText("");
                    addressID.setText("");
                    //contractID.setText("");
                }

            }
        });
        conbookingId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   final String customername = cusNameId.getText().toString().trim();
                final String customenumber= contractID.getText().toString().trim();
                final String customeaddress= addressID.getText().toString().trim();
              //  final String state = stateID.getText().toString().trim();
              //  final String city = cityID.getText().toString().trim();
                final String distric = districId.getText().toString().trim();
                final String pincode = pincodeId.getText().toString().trim();
                final String landmark = landId.getText().toString().trim();
//                if (TextUtils.isEmpty(customername)) {
//                    cusNameId.setError("Please enter CustomerName");
//                    cusNameId.requestFocus();
//                    return;
//                }
                if (TextUtils.isEmpty(customenumber)) {
                    contractID.setError("Please enter Contract Number");
                    contractID.requestFocus();
                    return;
                }
                if (customenumber.length() < 10){
                    contractID.setError("Invalid Contact");
                    contractID.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(customeaddress)) {
                    addressID.setError("Please enter Address");
                    addressID.requestFocus();
                    return;
                }
//                if (TextUtils.isEmpty(state)) {
//                    stateID.setError("Please enter State");
//                    stateID.requestFocus();
//                    return;
//                }
                if (TextUtils.isEmpty(landmark)) {
                    landId.setError("Please enter LandMark");
                    landId.requestFocus();
                    return;
                }
//                if (TextUtils.isEmpty(city)) {
//                    stateID.setError("Please enter City");
//                    stateID.requestFocus();
//                    return;
//                }
                if (TextUtils.isEmpty(distric)) {
                    districId.setError("Please enter Distric");
                    districId.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(pincode)) {
                    pincodeId.setError("Please enter Pincode");
                    pincodeId.requestFocus();
                    return;
                }
                Intent intent = new Intent(getApplicationContext(), CustomerInformation_Activity.class);
                Bundle bundle_edit = new Bundle();
                bundle_edit.putString("catID", catID);
                bundle_edit.putString("dist", dist);
                bundle_edit.putString("disName", disName);
                bundle_edit.putString("pinid", pinid);
                bundle_edit.putString("Itemname", Itemname);
                bundle_edit.putString("prdctid", prdctid);
                bundle_edit.putString("productName", productName);
                bundle_edit.putString("PId",PId);
                bundle_edit.putString("subproductName", subproductName);
                bundle_edit.putString("imageUrl", imageUrl);
                bundle_edit.putString("companyName", companyName);
                bundle_edit.putString("modelNo",modelNo);
                bundle_edit.putString("time", time);
                bundle_edit.putString("productIssu", productIssu);
                bundle_edit.putString("customername", customername);
                bundle_edit.putString("customenumber", customenumber);
                bundle_edit.putString("customeaddress", customeaddress);
               // bundle_edit.putString("state", state);
             //   bundle_edit.putString("city", city);
                bundle_edit.putString("distric", distric);
                bundle_edit.putString("pincode", pincode);
                bundle_edit.putString("landmark", landmark);
                bundle_edit.putString("fulldate",fulldate);
                bundle_edit.putString("districtv",districtv);
                bundle_edit.putString("SubproductIDD",SubproductIDD);
                intent.putExtras(bundle_edit);
                startActivity(intent);
            }
        });
        User user = SharedPrefManager.getInstance(getApplicationContext()).getUser();
        customerId = String.valueOf(user.getCustomerid());
        Log.e("CustomerID", customerId);
        FetchProfile(customerId);
    }

    public void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Customer Details");
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
    public void FetchProfile(final String customerId){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.FETCHPROFILE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // progressBar.setVisibility(View.GONE);
                        Log.e("fetchProfile", response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            customername = obj.getString("customer_name");
                            customerphnno = obj.getString("customer_phn_no");
                            customeremail = obj.getString("customer_email");
                            customeraddress = obj.getString("customer_address");
                            customerdistrict = obj.getString("customer_district");
                            customerdistrictname = obj.getString("customer_district_name");
                            customerpincode = obj.getString("customer_pincode");
                            customerimage = obj.getString("customer_image");
                            NameId.setText(customername);
                            addId.setText(customeraddress);
                            contractId.setText(customerphnno);
                            Log.e("AddressData",customername+" "+customerphnno+" "+customeremail+" "+customeraddress+" "+customerdistrict+" "+customerpincode+" "+customerimage);
                            if (districtv.equals(customerpincode)){
                                checkID.setVisibility(View.VISIBLE);
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
