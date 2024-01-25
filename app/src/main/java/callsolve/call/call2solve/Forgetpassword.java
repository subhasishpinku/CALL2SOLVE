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
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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

import callsolve.call.call2solve.ImeiActivity.TelephonyInfo;
import callsolve.call.call2solve.URL.URLs;

public class Forgetpassword extends AppCompatActivity {
    EditText useID,emailId;
    RelativeLayout loginID;
    static final Integer PHONESTATS = 0x1;
    String imei,androidid;
    public static boolean isMultiSimEnabled = false;
    public static List<SubscriptionInfo> subInfoList;
    private SubscriptionManager subscriptionManager;
    public static ArrayList<String> numbers;
    String imeiSIM1 ="0";
    String imeiSIM2 ="0";
    private int checkedPermission = PackageManager.PERMISSION_DENIED;
    public static final int REQUEST_CODE_PHONE_STATE_READ = 100;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.forgetpassword);
        useID = (EditText)findViewById(R.id.useID);
        emailId = (EditText)findViewById(R.id.emailId);
        loginID = (RelativeLayout) findViewById(R.id.loginID);
        //askForPermission(Manifest.permission.READ_PHONE_STATE, PHONESTATS);
        numbers = new ArrayList<String>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            subscriptionManager = SubscriptionManager.from(getApplicationContext());
        }
        loginID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = useID.getText().toString().trim();
                final String email = emailId.getText().toString().trim();
                int usernamee = useID.getText().length();
                int emaill = emailId.getText().length();
                if (usernamee>0 && emaill>0){
                    Toast.makeText(getApplicationContext(), "Please Enter Any One Option", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (usernamee>0) {
                        if (usernamee < 10) {
                            useID.setError("Please enter Valid Mobile No");
                            useID.requestFocus();
                            return;
                        }
                        else {
                            ForgetPassword(username,email);
                        }
                    }
                    else {
                        if (emaill>0) {
                            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                                emailId.setError("Enter a valid email");
                                emailId.requestFocus();
                                return;
                            } else {
                                ForgetPassword(username,email);
                            }
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Please Enter Your Valid Mobile Number Or Email", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

            }
        });
    }
    public void ForgetPassword(final String username, final String email){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_FORGETPASSWORD,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //   progressBar.setVisibility(View.GONE);
                        Log.e("response", response);
                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);
                            String msg = obj.getString("msg");
                            if (msg.equals("true")){

                                Toast.makeText(getApplicationContext(), "User Valid", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "User Not Valid", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                       // Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("mob_no", username);
                params.put("email_id", email);
                params.put("imei1",imeiSIM1);
                //  params.put("imei2",imeiSIM2);
                Log.e("logValue",username+" "+email+" "+imeiSIM1);
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton volleySingleton = VolleySingleton.getInstance(getApplicationContext());
        stringRequest.setShouldCache(false);
        volleySingleton.addToRequestQueue(stringRequest);

    }

    private void askForPermission(String permission, Integer requestCode) {
        if (ContextCompat.checkSelfPermission(Forgetpassword.this, permission) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(Forgetpassword.this, permission)) {
                ActivityCompat.requestPermissions(Forgetpassword.this, new String[]{permission}, requestCode);
            } else {
                ActivityCompat.requestPermissions(Forgetpassword.this, new String[]{permission}, requestCode);
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
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
}
