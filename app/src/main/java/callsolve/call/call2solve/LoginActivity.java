package callsolve.call.call2solve;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
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


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import callsolve.call.call2solve.DatabaseActivity.DatabaseHelper;
import callsolve.call.call2solve.ImeiActivity.TelephonyInfo;
import callsolve.call.call2solve.SetgetActivity.ProfileSetGet;
import callsolve.call.call2solve.SetgetActivity.User;
import callsolve.call.call2solve.SharePreferance.SharedPrefManager;
import callsolve.call.call2solve.SharePreferance.SharedPrefManagerProfile;
import callsolve.call.call2solve.URL.URLs;

public class LoginActivity extends AppCompatActivity {
    Toolbar toolbar;
    EditText useID,passwordID;
    LinearLayout loginID;
    TextView btnLinkToRegisterScreen,forgetpassID;
    String customerid;
    private DatabaseHelper db;
    private CheckBox saveLoginCheckBox;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;
    String customer_email,customerphnno;
    static final Integer PHONESTATS = 0x1;
    String imei,androidid;
    public static boolean isMultiSimEnabled = false;
    public static List<SubscriptionInfo> subInfoList;
    private SubscriptionManager subscriptionManager;
    public static ArrayList<String> numbers;
    String imeiSIM1 = "0";
    String imeiSIM2 = "0";
    private int checkedPermission = PackageManager.PERMISSION_DENIED;
    public static final int REQUEST_CODE_PHONE_STATE_READ = 100;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.login_activity);
        useID =(EditText)findViewById(R.id.useID);
        passwordID = (EditText)findViewById(R.id.passwordID);
        loginID =(LinearLayout)findViewById(R.id.loginID);
        saveLoginCheckBox = (CheckBox)findViewById(R.id.saveLoginCheckBox);
        btnLinkToRegisterScreen = (TextView)findViewById(R.id.btnLinkToRegisterScreen);
        forgetpassID = (TextView)findViewById(R.id.forgetpassID);
        db = new DatabaseHelper(this);
        //askForPermission(Manifest.permission.READ_PHONE_STATE, PHONESTATS);
        numbers = new ArrayList<String>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            subscriptionManager = SubscriptionManager.from(getApplicationContext());
        }
        btnLinkToRegisterScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),RegistrationActivity.class);
                startActivity(intent);
            }
        });
        forgetpassID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Forgetpassword.class);
                startActivity(intent);
            }
        });
        loginID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                askForPermission(Manifest.permission.READ_PHONE_STATE, PHONESTATS);
//                numbers = new ArrayList<String>();
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
//                    subscriptionManager = SubscriptionManager.from(getApplicationContext());
//                }
                login();
            }
        });
        initToolBar();
        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();
        saveLogin = loginPreferences.getBoolean("saveLogin", false);
        if (saveLogin == true) {
            useID.setText(loginPreferences.getString("customer_phn_no", ""));
            passwordID.setText(loginPreferences.getString("pwd", ""));
            saveLoginCheckBox.setChecked(true);
        }
    }
    public void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Login");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(),NavigationDrawerActivity.class);
                        startActivity(intent);
                    }
                }
        );
    }
    public void login(){
        final String username = useID.getText().toString();
        final String password = passwordID.getText().toString();
        //validating inputs
        if (TextUtils.isEmpty(username)) {
            useID.setError("Please enter your username");
            useID.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            passwordID.setError("Please enter your password");
            passwordID.requestFocus();
            return;
        }
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                       // progressBar.setVisibility(View.GONE);
                        Log.e("ResponseLogin", response);
                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);
                            String msg = obj.getString("msg");
                            String msgg = obj.getString("Message");
                            customerid = obj.getString("customer_id");
                            Log.e("LoginData", msg+" "+customerid+ msgg);
                            Cursor res = db.getcusId();
                            if(res.getCount() == 0) {
                                Log.e("ErrorDDB","Nothing found");
                                db.addcusId(customerid);
                                return;
                            }
                            else {

                            }
                            //if no error in response
                            if (msg.equals("1")) {
                               Toast.makeText(getApplicationContext(), obj.getString("Message"), Toast.LENGTH_SHORT).show();
                                User user = new User(
                                        obj.getString("customer_id")
                                );
                                SharedPrefManager.getInstance(getApplicationContext()).CustomerID(user);
                                FetchProfile(customerid);

                            }
                            else{
                                Toast.makeText(getApplicationContext(), obj.getString("Message"), Toast.LENGTH_SHORT).show();

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(getApplicationContext(), "UserName Password Not Valid", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_name", username);
                params.put("user_pwd", password);
                params.put("imei1",imeiSIM1);
                params.put("imei2",imeiSIM2);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton volleySingleton = VolleySingleton.getInstance(getApplicationContext());
        stringRequest.setShouldCache(false);
        volleySingleton.addToRequestQueue(stringRequest);
    }
    public void FetchProfile(final String customerId){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.FETCHPROFILE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // progressBar.setVisibility(View.GONE);
                        Log.e("fetchProfile", response);
                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);
                            String customername = obj.getString("customer_name");
                            customerphnno = obj.getString("customer_phn_no");
                            customer_email = obj.getString("customer_email");
                            String customeraddress = obj.getString("customer_address");
                            String customerdistrict = obj.getString("customer_district");
                            String customerdistrictname = obj.getString("customer_district_name");
                            String customerpincode = obj.getString("customer_pincode");
                            String  customerimage = obj.getString("customer_image");
                            Log.e("Profiledata",customername+" "+customerphnno+" "+customer_email+" "+customeraddress+" "+customerdistrict+" "+customerdistrictname+" "+" "+customerpincode+" "+customerimage);
                            if (saveLoginCheckBox.isChecked()) {
                                loginPrefsEditor.putBoolean("saveLogin", true);
                                loginPrefsEditor.putString("customer_phn_no", customerphnno);
                                loginPrefsEditor.putString("pwd", passwordID.getText().toString());
                                loginPrefsEditor.commit();
                            } else {
                                loginPrefsEditor.clear();
                                loginPrefsEditor.commit();
                            }
                            ProfileSetGet profileSetGet = new ProfileSetGet(
                                    obj.getString("customer_name"),
                                    obj.getString("customer_phn_no"),
                                    obj.getString("customer_email"),
                                    obj.getString("customer_address"),
                                    obj.getString("customer_district"),
                                    obj.getString("customer_district_name"),
                                    obj.getString("customer_pincode"),
                                    obj.getString("customer_image")
                            );
                            SharedPrefManagerProfile.getInstance(getApplicationContext()).userProfile(profileSetGet);
                            Intent intent = new Intent(getApplicationContext(),HomeNavigationDrawerActivity.class);
                            startActivity(intent);

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
                params.put("customer_id", customerId);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton volleySingleton = VolleySingleton.getInstance(getApplicationContext());
        stringRequest.setShouldCache(false);
        volleySingleton.addToRequestQueue(stringRequest);
    }
    private void askForPermission(String permission, Integer requestCode) {
        if (ContextCompat.checkSelfPermission(LoginActivity.this, permission) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this, permission)) {
                ActivityCompat.requestPermissions(LoginActivity.this, new String[]{permission}, requestCode);
            } else {
                ActivityCompat.requestPermissions(LoginActivity.this, new String[]{permission}, requestCode);
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
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),EntersideActivity.class);
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
