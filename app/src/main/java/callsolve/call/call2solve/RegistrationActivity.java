package callsolve.call.call2solve;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import callsolve.call.call2solve.ImeiActivity.TelephonyInfo;
import callsolve.call.call2solve.URL.URLs;

public class RegistrationActivity extends AppCompatActivity implements Spinner.OnItemSelectedListener{
    Toolbar toolbar;
    EditText useID,contractID,mailID,passID,addressID,pincodeID;
    TextView barcodescreenId;
    Spinner spID;
    RelativeLayout regsId;
    private JSONArray array;
    ArrayList<String> Disname;
    String  DisId;
    String DName,DId;
    static final Integer PHONESTATS = 0x1;
    String imei,androidid;
    public static boolean isMultiSimEnabled = false;
    public static List<SubscriptionInfo> subInfoList;
    private SubscriptionManager subscriptionManager;
    public static ArrayList<String> numbers;
    String imeiSIM1 = "0";
    String imeiSIM2 = "0";
    ImageView scannerId;
    private int checkedPermission = PackageManager.PERMISSION_DENIED;
    public static final int REQUEST_CODE_PHONE_STATE_READ = 100;
    private IntentIntegrator qrScan;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.registration_activity);
        useID = (EditText)findViewById(R.id.useID);
        contractID = (EditText)findViewById(R.id.contractID);
        mailID = (EditText)findViewById(R.id.mailID);
        passID = (EditText)findViewById(R.id.passID);
        addressID = (EditText)findViewById(R.id.addressID);
        pincodeID = (EditText)findViewById(R.id.pincodeID);
        barcodescreenId = (TextView)findViewById(R.id.barcodescreenId);
        regsId = (RelativeLayout)findViewById(R.id.regsId);
        spID =(Spinner)findViewById(R.id.spID);
        scannerId = (ImageView)findViewById(R.id.scannerId);
        qrScan = new IntentIntegrator(this);
        spID.setOnItemSelectedListener(this);
        //askForPermission(Manifest.permission.READ_PHONE_STATE, PHONESTATS);
        regsId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  askForPermission(Manifest.permission.READ_PHONE_STATE, PHONESTATS);
                numbers = new ArrayList<String>();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                    subscriptionManager = SubscriptionManager.from(getApplicationContext());
                }
                if (DName.equals("Select District")){
                    Toast.makeText(getApplicationContext(),"Please Select District", Toast.LENGTH_LONG).show();
                }
                else {
                    registerUser(DId);
                }

            }
        });
        initToolBar();
        Disname = new ArrayList<>();
        loadcatagory();
        numbers = new ArrayList<String>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            subscriptionManager = SubscriptionManager.from(getApplicationContext());
        }
        scannerId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qrScan.initiateScan();
            }
        });
    }
    public void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Registration");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }
        );
    }
    public void loadcatagory(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.DISTRICMASTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("districAllData"," "+response);
                        try {
                            array = new JSONArray(response);
                            Log.e("array", " "+array);
                            for (int i = 0; i<array.length(); i++){
                                JSONObject jsonObject = array.getJSONObject(i);
                                Log.e("obj", " "+jsonObject);
                                String distid = jsonObject.getString("dist_id");
                                String distnm = jsonObject.getString("dist_nm");
                                String distimg = jsonObject.getString("dist_img");
                                Log.e("ShowData", distid+" "+distnm+" "+distimg);
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
                return params;

            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton volleySingleton = VolleySingleton.getInstance(this);
        stringRequest.setShouldCache(false);
        volleySingleton.addToRequestQueue(stringRequest);

    }
    private void getdis(JSONArray j){
        for(int i=0;i<j.length();i++){
            try {
                JSONObject json = j.getJSONObject(i);
                Disname.add(json.getString(URLs.TAG_DIS_NAME));
                Log.e("DisName"," "+Disname);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        spID.setAdapter(new ArrayAdapter<String>(RegistrationActivity.this, android.R.layout.simple_spinner_dropdown_item, Disname));
    }
    private String getID(int position){
        try {
            JSONObject json = array.getJSONObject(position);
            DisId = json.getString(URLs.TAG__DIS_DI);
            Log.e("DisId"," "+DisId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return DisId;
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        DId = getID(position);
       // registerUser(DId);
        DName = spID.getSelectedItem().toString();
        //Toast.makeText(adapterView.getContext(),DName+" "+" "+DId, Toast.LENGTH_LONG).show();
//        if (spID.getSelectedItem().toString().trim().equals("Select District")) {
//            Toast.makeText(getApplicationContext(),"Please Select District", Toast.LENGTH_LONG).show();
//
//        }
//        else {
//
//        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void registerUser(final String DId) {
        final String username = useID.getText().toString().trim();
        final String contract = contractID.getText().toString().trim();
        final String email = mailID.getText().toString().trim();
        //final String password = passID.getText().toString().trim();
        final String address = addressID.getText().toString().trim();
        final String pincode = pincodeID.getText().toString().trim();
        final String scanner = barcodescreenId.getText().toString().trim();
//        final String gender = ((RadioButton) findViewById(radioGroupGender.getCheckedRadioButtonId())).getText().toString();
//
//        //first we will do the validations

        if (TextUtils.isEmpty(username)) {
            useID.setError("Please enter username");
            useID.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(contract)) {
            contractID.setError("Please enter Contact");
            contractID.requestFocus();
            return;
        }
        if (contract.length() < 10){
            contractID.setError("Invalid Contact");
            contractID.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(email)) {
            mailID.setError("Please enter your email");
            mailID.requestFocus();
            return;
        }

//        if (TextUtils.isEmpty(scanner)) {
//            barcodescreenId.setError("Please QR Code Scanne");
//            barcodescreenId.requestFocus();
//            return;
//        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mailID.setError("Enter a valid email");
            mailID.requestFocus();
            return;
        }

//        if (TextUtils.isEmpty(password)) {
//            passID.setError("Enter a password");
//            passID.requestFocus();
//            return;
//        }
        if (TextUtils.isEmpty(address)) {
            addressID.setError("Enter a Address");
            addressID.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(pincode)) {
            pincodeID.setError("Enter a Pincode");
            pincodeID.requestFocus();
            return;
        }
        if (pincode.length()<6) {
            pincodeID.setError("Invalid Pincode");
            pincodeID.requestFocus();
            return;
        }
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_REGISTER,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //   progressBar.setVisibility(View.GONE);
                            Log.e("response", response);
                            try {
                                //converting response to json object
                                JSONObject obj = new JSONObject(response);
                                String msg = obj.getString("msg");
                                if (msg.equals("success")){
                                    //   Toast.makeText(getApplicationContext(), obj.getString("msg"), Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                                    startActivity(intent);
                                    Toast.makeText(getApplicationContext(), "Thank You for Registering with us. Your user Id & Password will be sent to your registered contact number shortly.", Toast.LENGTH_SHORT).show();
                                }
                                else if(msg.equals("error")){
                                    Toast.makeText(getApplicationContext(), "User Already Register", Toast.LENGTH_SHORT).show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("customer_name", username);
                    params.put("customer_phn_no", contract);
                    params.put("customer_email", email);
                    params.put("customer_address", address);
                    params.put("customer_dist", DId);
                    params.put("customer_pincode", pincode);
                    params.put("imei1",imeiSIM1);
                    params.put("imei2",imeiSIM2);
                    params.put("bar_code_scanned",scanner);
                    Log.e("Regs", username+" "+contract+" "+email+" "+address+" "+DId+" "+pincode+" "+imeiSIM1+" "+imeiSIM2+" "+scanner);
                    return params;
                }
            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            VolleySingleton volleySingleton = VolleySingleton.getInstance(getApplicationContext());
            stringRequest.setShouldCache(false);
            volleySingleton.addToRequestQueue(stringRequest);
    }

    private void askForPermission(String permission, Integer requestCode) {
        if (ContextCompat.checkSelfPermission(RegistrationActivity.this, permission) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(RegistrationActivity.this, permission)) {
                ActivityCompat.requestPermissions(RegistrationActivity.this, new String[]{permission}, requestCode);
            } else {
                ActivityCompat.requestPermissions(RegistrationActivity.this, new String[]{permission}, requestCode);
            }
        } else {
            imei = getImeiNumber();
            Log.e("IMEII",imei);
            getClientPhoneNumber();
            androidid=getAndroidId();
            //Toast.makeText(this,permission + " is already granted.", Toast.LENGTH_SHORT).show();
            AllEmai();
        }
    }
    private void AllEmai(){
        TelephonyInfo telephonyInfo = TelephonyInfo.getInstance(this);
        imeiSIM1 = telephonyInfo.getImsiSIM1();
        imeiSIM2 = telephonyInfo.getImsiSIM2();
        imei = imeiSIM1+","+imeiSIM2;
        Log.e("IMEI",imei);
        boolean isSIM1Ready = telephonyInfo.isSIM1Ready();
        boolean isSIM2Ready = telephonyInfo.isSIM2Ready();
        boolean isDualSIM = telephonyInfo.isDualSIM();

    }
    @SuppressLint("MissingPermission")
    private String getImeiNumber() {
        final TelephonyManager telephonyManager= (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            return telephonyManager.getImei();
        }
        else {
            return telephonyManager.getDeviceId();
        }
    }
    @SuppressLint({"NewApi", "MissingPermission"})
    private void getClientPhoneNumber() {
        try{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                subInfoList = subscriptionManager.getActiveSubscriptionInfoList();
            }
            if (subInfoList.size() > 1)
            {
                isMultiSimEnabled = true;
            }
            for (SubscriptionInfo subscriptionInfo : subInfoList)
            {
                numbers.add(subscriptionInfo.getNumber());
            }
        }catch (Exception e)
        {
        }
    }
    private String getAndroidId() {
        androidid = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        Log.e("TAG",Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ALLOWED_GEOLOCATION_ORIGINS));
        Log.e("TAG",Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.DEFAULT_INPUT_METHOD));
        return androidid;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                //if qr contains data
                try {

                    JSONObject obj = new JSONObject(result.getContents());
                } catch (JSONException e) {
                    e.printStackTrace();
                    barcodescreenId.setText(result.getContents());
                   // Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(intent);
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
