package callsolve.call.call2solve;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.baoyachi.stepview.VerticalStepView;
import com.bumptech.glide.Glide;
//import com.payu.india.Extras.PayUChecksum;
//import com.payu.india.Extras.PayUSdkDetails;
//import com.payu.india.Model.PayuConfig;
//import com.payu.india.Model.PayuHashes;
//import com.payu.india.Payu.Payu;
//import com.payu.india.Payu.PayuConstants;
//import com.payu.india.Payu.PayuErrors;
//import com.payu.paymentparamhelper.PaymentParams;
//import com.payu.paymentparamhelper.PostData;
//import com.payu.payuui.Activity.PayUBaseActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import callsolve.call.call2solve.ApplicationActivity.Application;
import callsolve.call.call2solve.SetgetActivity.ProfileSetGet;
import callsolve.call.call2solve.SetgetActivity.User;
import callsolve.call.call2solve.SharePreferance.SharedPrefManager;
import callsolve.call.call2solve.SharePreferance.SharedPrefManagerProfile;
import callsolve.call.call2solve.URL.URLs;

public class
Product_DetailsActivity extends AppCompatActivity {
    Toolbar toolbar;
    String msg,bookingid,productname,subproductname,servicename,productissue,companyname,customername,customerphn,customeradrs,preferdate,prefertime,district,pincode,paymentmode,payment,techname,
            techimg,jobid;
    TextView Pname,SubPname,IssuDetailID,CustNameID,CusPhoneID,CusAddressID,preFerDateID,preFerTimeID,paymentmodeID,amountID,totalchargeID;
    VerticalStepView verticalStepView,verticalStepView1;
    int i = 1;
    LinearLayout llv;
    List<String> sources;
    TextView companynameId,modelNoId,servicenameId,issuedId,serviceAddId,alerNoId,landmarkId;
    String modelname,bookingrowid,landmark;
    Button lvvv,invoiceId,payubizId;
    RatingBar ratingBar;
    TextView tmaneId,ratvalue;
    ImageView imgID;
    String rate;
    private String merchantKey, userCredentials;
//    private PaymentParams mPaymentParams;
//    private PayuConfig payuConfig;
    private Spinner environmentSpinner;
//    private PayUChecksum checksum;
    String salt = null;
    String customerphnno,customeremail,customeraddress,customerdistrict,
            customerdistrictname,customerpincode,customerimage,customerId;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.product_detailsactivity);
        initToolBar();
        Intent intent = getIntent();
        msg = intent.getStringExtra("msg");
        bookingid = intent.getStringExtra("bookingid");
        productname = intent.getStringExtra("productname");
        subproductname = intent.getStringExtra("subproductname");
        servicename = intent.getStringExtra("servicename");
        productissue = intent.getStringExtra("productissue");
        companyname = intent.getStringExtra("companyname");
        customername = intent.getStringExtra("customername");
        customerphn = intent.getStringExtra("customerphn");
        customeradrs = intent.getStringExtra("customeradrs");
        preferdate = intent.getStringExtra("preferdate");
        prefertime = intent.getStringExtra("prefertime");
        district = intent.getStringExtra("district");
        pincode = intent.getStringExtra("pincode");
        paymentmode  = intent.getStringExtra("paymentmode");
        payment = intent.getStringExtra("payment");
        modelname = intent.getStringExtra("modelname");
        bookingrowid = intent.getStringExtra("bookingrowid");
        landmark = intent.getStringExtra("landmark");
        techname = intent.getStringExtra("techname");
        techimg = intent.getStringExtra("techimg");
        jobid = intent.getStringExtra("jobid");
        Log.e("ALLDETAILS"," "+msg+" "+bookingid+" "+productname+" "+subproductname
                +" "+servicename+" "+productissue+" "+companyname+" "
                +customername+" "+customerphn+" "+customeradrs+" "+preferdate+" "
                +prefertime+" "+district+" "+pincode+" "+paymentmode+" "+payment+" "+bookingrowid+" "+jobid);
        Pname = (TextView)findViewById(R.id.Pname);
        companynameId = (TextView)findViewById(R.id.companynameId);
        modelNoId = (TextView)findViewById(R.id.modelNoId);
        servicenameId = (TextView)findViewById(R.id.servicenameId);
        issuedId =(TextView)findViewById(R.id.issuedId);
        serviceAddId =(TextView)findViewById(R.id.serviceAddId);
        preFerDateID = (TextView)findViewById(R.id.preFerDateID);
        preFerTimeID = (TextView)findViewById(R.id.preFerTimeID);
        CustNameID = (TextView)findViewById(R.id.CustNameID);
        CusPhoneID = (TextView)findViewById(R.id.CusPhoneID);
        alerNoId = (TextView)findViewById(R.id.alerNoId);
        CusAddressID = (TextView)findViewById(R.id.CusAddressID);
        paymentmodeID = (TextView)findViewById(R.id.paymentmodeID);
        amountID = (TextView)findViewById(R.id.amountID);
        totalchargeID = (TextView)findViewById(R.id.totalchargeID);
        landmarkId = (TextView)findViewById(R.id.landmarkId);
        lvvv = (Button) findViewById(R.id.lvvv);
        invoiceId =(Button)findViewById(R.id.invoiceId);
        payubizId =(Button)findViewById(R.id.payubizId);
        ratingBar = (RatingBar)findViewById(R.id.ratingBar);
        ratvalue =(TextView)findViewById(R.id.ratvalue);
        rate = "5.0";
        ratingBar.setEnabled(false);
        ratingBar.setStepSize(Float.parseFloat("0.5"));
        ratingBar.setNumStars(5);
        ratingBar.setRating(Float.parseFloat(rate));
        ratvalue.setText(""+ratingBar.getRating());
        tmaneId =(TextView)findViewById(R.id.tmaneId);
        imgID =(ImageView)findViewById(R.id.imgID);
        Pname.setText(productname);
        companynameId.setText(companyname);
        modelNoId.setText(modelname);
        servicenameId.setText(servicename);
        issuedId.setText(productissue);
        serviceAddId.setText(customeradrs);
        preFerDateID.setText(preferdate+" "+" "+prefertime);
        CustNameID.setText(customername);
        CusPhoneID.setText(customerphn);
        alerNoId.setText(customerphn);
        CusAddressID.setText(customeradrs);
        paymentmodeID.setText(paymentmode);
        amountID.setText(payment);
        totalchargeID.setText(payment);
        landmarkId.setText(landmark);
        tmaneId.setText(techname);
//        Payu.setInstance(this);
//        PayUSdkDetails payUSdkDetails = new PayUSdkDetails();
//        //Toast.makeText(this, "Build No: " + payUSdkDetails.getSdkBuildNumber() + "\n Build Type: " + payUSdkDetails.getSdkBuildType() + " \n Build Flavor: " + payUSdkDetails.getSdkFlavor() + "\n Application Id: " + payUSdkDetails.getSdkApplicationId() + "\n Version Code: " + payUSdkDetails.getSdkVersionCode() + "\n Version Name: " + payUSdkDetails.getSdkVersionName(), Toast.LENGTH_LONG).show();
//        Log.e("PAYUBIZ"," "+payUSdkDetails.getSdkBuildNumber() +
//                "\n Build Type: " + payUSdkDetails.getSdkBuildType() +
//                " \n Build Flavor: " + payUSdkDetails.getSdkFlavor() +
//                "\n Application Id: " + payUSdkDetails.getSdkApplicationId() +
//                "\n Version Code: " + payUSdkDetails.getSdkVersionCode() +
//                "\n Version Name: " + payUSdkDetails.getSdkVersionName());
        Glide.with(getApplicationContext())
                                    .load(techimg)
                                    .into(imgID);
        llv = (LinearLayout)findViewById(R.id.llv);
        llv.setVisibility(View.GONE);
        verticalStepView = (VerticalStepView)findViewById(R.id.Verticalstepview);
        verticalStepView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String aa =  sources.get(1);
                llv.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(),sources.get(1)+" ",Toast.LENGTH_SHORT).show();

            }
        });
        sources = new ArrayList<>();
        sources.add("Generate Call");
        sources.add("Telephonic Confermation");
        sources.add("Assign Technician");
        sources.add("Receive Job");
        sources.add("Track Our Technician");
        sources.add("On Job");
        sources.add("End Job");
        sources.add("Invoice");
        verticalStepView.setStepsViewIndicatorComplectingPosition(sources.size()-i)
                .reverseDraw(false)
                .setStepViewTexts(sources)
                .setLinePaddingProportion(0.85f)
                .setStepsViewIndicatorCompletedLineColor(Color.parseColor("#008000"))
                .setStepViewComplectedTextColor(Color.parseColor("#000000"))
                .setStepViewUnComplectedTextColor(ContextCompat.getColor(this,R.color.red))
                .setStepsViewIndicatorUnCompletedLineColor(Color.parseColor("#FFFFFF"))
                .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(this, R.drawable.ic_check_circle_black_24dp))
                .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(this,R.drawable.attention))
                .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(this,R.drawable.default_icon));

        lvvv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_info = new Intent(getApplicationContext(),TrackingActivity.class);
                Bundle bundle_edit  =   new Bundle();
                bundle_edit.putString("recodId",bookingrowid);
                bundle_edit.putString("jobId",bookingid);
                intent_info.putExtras(bundle_edit);
                intent_info.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent_info);
                overridePendingTransition(R.anim.slide_up_info,R.anim.no_change);
            }
        });
        invoiceId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_info = new Intent(getApplicationContext(),InvoiceActivity.class);
                Bundle bundle_edit  =   new Bundle();
                bundle_edit.putString("recodId",bookingrowid);
                bundle_edit.putString("jobId",bookingid);
                intent_info.putExtras(bundle_edit);
                intent_info.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent_info);
                overridePendingTransition(R.anim.slide_up_info,R.anim.no_change);

            }
        });
        payubizId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //navigateToBaseActivity();
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
                            Log.e("Profiledata_Type",
                                    customername+" "
                                            +customerphnno+" "
                                            +customeremail+" "
                                            +customeraddress+" "
                                            +customerdistrict+" "
                                            +customerdistrictname+" "
                                            +customerpincode+" "
                                            +customerimage);

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
                Log.e("CusID",customerId);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton volleySingleton = VolleySingleton.getInstance(getApplicationContext());
        stringRequest.setShouldCache(false);
        volleySingleton.addToRequestQueue(stringRequest);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == PayuConstants.PAYU_REQUEST_CODE) {
//            if (data != null) {
//                Log.e("PAUBIZRESULT"," "+"Payu's Data : " + data.getStringExtra("payu_response")
//                        + "\n\n\n Merchant's Data: " + data.getStringExtra("result"));
//
//                new AlertDialog.Builder(this)
//                        .setCancelable(false)
//                        .setMessage("Payu's Data : " + data.getStringExtra("payu_response") + "\n\n\n Merchant's Data: " + data.getStringExtra("result"))
//                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int whichButton) {
//                                dialog.dismiss();
//                            }
//                        }).show();
//
//            } else {
//                Toast.makeText(this, getString(R.string.could_not_receive_data), Toast.LENGTH_LONG).show();
//            }
//        }
    }

//    public void navigateToBaseActivity() {
//
//
//         merchantKey="CM60Sb";
//      //  merchantKey = ((EditText) findViewById(R.id.editTextMerchantKey)).getText().toString();
//      //  String amount = ((EditText) findViewById(R.id.editTextAmount)).getText().toString();
//      //  String email = ((EditText) findViewById(R.id.editTextEmail)).getText().toString();
//        String email = customeremail;
//      // String value = environmentSpinner.getSelectedItem().toString();
//       String value = "Production";
//        int environment;
//        String TEST_ENVIRONMENT = getResources().getString(R.string.test);
//        if (value.equals(TEST_ENVIRONMENT))
//            environment = PayuConstants.STAGING_ENV;
//        else
//            environment = PayuConstants.PRODUCTION_ENV;
//        userCredentials = merchantKey + ":" + email;
//        Log.e("PROITEMVALUE","PRODUCTION"+" "+environment+" "+userCredentials);
//
//        //TODO Below are mandatory params for hash genetation
//        mPaymentParams = new PaymentParams();
//        /**
//         * For Test Environment, merchantKey = please contact mobile.integration@payu.in with your app name and registered email id
//
//         */
//        mPaymentParams.setKey(merchantKey);
//        mPaymentParams.setAmount(payment);
//        mPaymentParams.setProductInfo(servicename);
//        mPaymentParams.setFirstName(customername);
//        mPaymentParams.setEmail(customeremail);
//        mPaymentParams.setPhone(customerphnno);
//     //   mPaymentParams.setTxnId("" + System.currentTimeMillis());
//        mPaymentParams.setTxnId(bookingid);
//
//        /**
//         * Surl --> Success url is where the transaction response is posted by PayU on successful transaction
//         * Furl --> Failre url is where the transaction response is posted by PayU on failed transaction
//         */
//        // mPaymentParams.setSurl(" https://www.fitternity.com/paymentsuccessandroid");
//        mPaymentParams.setSurl("https://payu.herokuapp.com/success");
//        mPaymentParams.setFurl("https://payu.herokuapp.com/failure");
//        //  mPaymentParams.setFurl("https://www.fitternity.com/paymentsuccessandroid");
//        mPaymentParams.setNotifyURL(mPaymentParams.getSurl());  //for lazy pay
//
//        /*
//         * udf1 to udf5 are options params where you can pass additional information related to transaction.
//         * If you don't want to use it, then send them as empty string like, udf1=""
//         * */
//        mPaymentParams.setUdf1("udf1");
//        mPaymentParams.setUdf2("udf2");
//        mPaymentParams.setUdf3("udf3");
//        mPaymentParams.setUdf4("udf4");
//        mPaymentParams.setUdf5("udf5");
//
//        /**
//         * These are used for store card feature. If you are not using it then user_credentials = "default"
//         * user_credentials takes of the form like user_credentials = "merchant_key : user_id"
//         * here merchant_key = your merchant key,
//         * user_id = unique id related to user like, email, phone number, etc.
//         * */
//        mPaymentParams.setUserCredentials(userCredentials);
//
//        //TODO Pass this param only if using offer key
//        // mPaymentParams.setOfferKey("YONOYSF@6445");
//
//        //TODO Sets the payment environment in PayuConfig object
//        payuConfig = new PayuConfig();
//        payuConfig.setEnvironment(environment);
//        //TODO It is recommended to generate hash from server only. Keep your key and salt in server side hash generation code.
//        // generateHashFromServer(mPaymentParams);
//
//        /**
//         * Below approach for generating hash is not recommended. However, this approach can be used to test in PRODUCTION_ENV
//         * if your server side hash generation code is not completely setup. While going live this approach for hash generation
//         * should not be used.
//         * */
//        if(environment== PayuConstants.STAGING_ENV){
//            //  salt = "eCwWELxi";
//            salt = "65GdodrX";
//        }else {
//            //Production Env
//            // salt = "1b1b0";
//            // salt = "QBl78dtK";
//            // salt = "Xd4VtaFd";
////            salt = "13p0PXZk";
//            salt = "F0rtl9nF";
//
//        }
////        String salt = "eCwWELxi";
//        // String salt = "13p0PXZk";
//        // String salt = "1b1b0";
//
//        //  String salt = "mF1Rgbi5";
//        //generateHashFromSDK(mPaymentParams, salt,bookingid);
//
//    }
//    public void generateHashFromSDK(PaymentParams mPaymentParams, String salt,String bookingid) {
//        PayuHashes payuHashes = new PayuHashes();
//        PostData postData = new PostData();
//
//        // payment Hash;
//        checksum = null;
//        checksum = new PayUChecksum();
//        checksum.setAmount(mPaymentParams.getAmount());
//        checksum.setKey(mPaymentParams.getKey());
//        checksum.setTxnid(mPaymentParams.getTxnId());
//      //  checksum.setTxnid(bookingid);
//        checksum.setEmail(mPaymentParams.getEmail());
//        checksum.setSalt(salt);
//        checksum.setProductinfo(mPaymentParams.getProductInfo());
//        checksum.setFirstname(mPaymentParams.getFirstName());
//        checksum.setUdf1(mPaymentParams.getUdf1());
//        checksum.setUdf2(mPaymentParams.getUdf2());
//        checksum.setUdf3(mPaymentParams.getUdf3());
//        checksum.setUdf4(mPaymentParams.getUdf4());
//        checksum.setUdf5(mPaymentParams.getUdf5());
//
//        postData = checksum.getHash();
//        if (postData.getCode() == PayuErrors.NO_ERROR) {
//            payuHashes.setPaymentHash(postData.getResult());
//        }
//
//        // checksum for payemnt related details
//        // var1 should be either user credentials or default
//        String var1 = mPaymentParams.getUserCredentials() == null ? PayuConstants.DEFAULT : mPaymentParams.getUserCredentials();
//        String key = mPaymentParams.getKey();
//
//        if ((postData = calculateHash(key, PayuConstants.PAYMENT_RELATED_DETAILS_FOR_MOBILE_SDK, var1, salt)) != null && postData.getCode() == PayuErrors.NO_ERROR) // Assign post data first then check for success
//            payuHashes.setPaymentRelatedDetailsForMobileSdkHash(postData.getResult());
//        //vas
//        if ((postData = calculateHash(key, PayuConstants.VAS_FOR_MOBILE_SDK, PayuConstants.DEFAULT, salt)) != null && postData.getCode() == PayuErrors.NO_ERROR)
//            payuHashes.setVasForMobileSdkHash(postData.getResult());
//
//        // getIbibocodes
//        if ((postData = calculateHash(key, PayuConstants.GET_MERCHANT_IBIBO_CODES, PayuConstants.DEFAULT, salt)) != null && postData.getCode() == PayuErrors.NO_ERROR)
//            payuHashes.setMerchantIbiboCodesHash(postData.getResult());
//
//        if (!var1.contentEquals(PayuConstants.DEFAULT)) {
//            // get user card
//            if ((postData = calculateHash(key, PayuConstants.GET_USER_CARDS, var1, salt)) != null && postData.getCode() == PayuErrors.NO_ERROR) // todo rename storedc ard
//                payuHashes.setStoredCardsHash(postData.getResult());
//            // save user card
//            if ((postData = calculateHash(key, PayuConstants.SAVE_USER_CARD, var1, salt)) != null && postData.getCode() == PayuErrors.NO_ERROR)
//                payuHashes.setSaveCardHash(postData.getResult());
//            // delete user card
//            if ((postData = calculateHash(key, PayuConstants.DELETE_USER_CARD, var1, salt)) != null && postData.getCode() == PayuErrors.NO_ERROR)
//                payuHashes.setDeleteCardHash(postData.getResult());
//            // edit user card
//            if ((postData = calculateHash(key, PayuConstants.EDIT_USER_CARD, var1, salt)) != null && postData.getCode() == PayuErrors.NO_ERROR)
//                payuHashes.setEditCardHash(postData.getResult());
//        }
//
//        if (mPaymentParams.getOfferKey() != null) {
//            postData = calculateHash(key, PayuConstants.OFFER_KEY, mPaymentParams.getOfferKey(), salt);
//            if (postData.getCode() == PayuErrors.NO_ERROR) {
//                payuHashes.setCheckOfferStatusHash(postData.getResult());
//            }
//        }
//
//        if (mPaymentParams.getOfferKey() != null && (postData = calculateHash(key, PayuConstants.CHECK_OFFER_STATUS, mPaymentParams.getOfferKey(), salt)) != null && postData.getCode() == PayuErrors.NO_ERROR) {
//            payuHashes.setCheckOfferStatusHash(postData.getResult());
//        }
//
//        // we have generated all the hases now lest launch sdk's ui
//        launchSdkUI(payuHashes);
//    }
//    private PostData calculateHash(String key, String command, String var1, String salt) {
//        checksum = null;
//        checksum = new PayUChecksum();
//        checksum.setKey(key);
//        checksum.setCommand(command);
//        checksum.setVar1(var1);
//        checksum.setSalt(salt);
//        return checksum.getHash();
//    }
//    public void launchSdkUI(PayuHashes payuHashes) {
//
//        Intent intent = new Intent(getApplicationContext(), PayUBaseActivity.class);
//        intent.putExtra(PayuConstants.PAYU_CONFIG, payuConfig);
//        intent.putExtra(PayuConstants.PAYMENT_PARAMS, mPaymentParams);
//        intent.putExtra(PayuConstants.SALT,salt);
//        intent.putExtra("bookingid", bookingid);
//        intent.putExtra(PayuConstants.PAYU_HASHES, payuHashes);
//        startActivityForResult(intent, PayuConstants.PAYU_REQUEST_CODE);
//      //  overridePendingTransition(R.anim.slide_up_info,R.anim.no_change);
//
//        Log.e("DATAPAUBIZ", " "+payuConfig+" "+mPaymentParams+" "+salt+" "+payuHashes);
//    }
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
