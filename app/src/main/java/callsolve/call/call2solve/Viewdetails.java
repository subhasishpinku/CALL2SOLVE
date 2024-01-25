package callsolve.call.call2solve;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.baoyachi.stepview.VerticalStepView;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import callsolve.call.call2solve.URL.URLs;

public class Viewdetails extends AppCompatActivity {
    Toolbar toolbar;
    VerticalStepView verticalStepView,verticalStepView1;
    int i = 1;
    LinearLayout llv;
    List<String> sources;
    String recodId,jobId;
    TextView Pname,companynameId,modelNoId,
            servicenameId,issuedId,serviceAddId,
            preFerDateID,preFerTimeID,CustNameID,
            CusPhoneID,alerNoId,CusAddressID,paymentmodeID,
            amountID,totalchargeID,landmarkId,cdateId,ctimeId,cresonId;
    String flagstatus,prdctname,modelno,subproname,issuedtl,cname,cphn,cadrs,srvcdate,srvctime,payamnt,paymode,companyname,servcphn,servcadrs,landmark,techname,techcode,techphn,techimg,techids,canceldate,canceltime,cancelreason,status;
    Button lvvv,invoiceId,trackingID;
    TextView tmaneId,ratvalue;
    ImageView imgID;
    RatingBar ratingBar;
    String rate;
    CardView PcardId,CancardId;
    LinearLayout lvv,lvcancelId;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_details_activity);
        Intent intent = getIntent();
        recodId = intent.getStringExtra("recodId");
        jobId = intent.getStringExtra("jobId");
        Log.e("Vieww",recodId+" "+jobId);
        FetchProfile(recodId);
        initToolBar();
        Pname = (TextView)findViewById(R.id.Pname);
        companynameId = (TextView)findViewById(R.id.companynameId);
        modelNoId = (TextView)findViewById(R.id.modelNoId);
        servicenameId = (TextView)findViewById(R.id.servicenameId);
        issuedId = (TextView)findViewById(R.id.issuedId);
        serviceAddId = (TextView)findViewById(R.id.serviceAddId);
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
        cdateId = (TextView)findViewById(R.id.cdateId);
        ctimeId = (TextView)findViewById(R.id.ctimeId);
        cresonId = (TextView)findViewById(R.id.cresonId);
        llv = (LinearLayout)findViewById(R.id.llv);
        lvvv = (Button) findViewById(R.id.lvvv);
        invoiceId =(Button)findViewById(R.id.invoiceId);
        trackingID = (Button)findViewById(R.id.trackingID);
        tmaneId =(TextView)findViewById(R.id.tmaneId);
        imgID =(ImageView)findViewById(R.id.imgID);
        ratingBar =(RatingBar)findViewById(R.id.ratingBar);
        ratvalue =(TextView)findViewById(R.id.ratvalue);
        PcardId =(CardView)findViewById(R.id.PcardId);
        lvv =(LinearLayout)findViewById(R.id.lvv);
        lvcancelId = (LinearLayout)findViewById(R.id.lvcancelId);
        CancardId = (CardView)findViewById(R.id.CancardId);
        rate = "5.0";
        ratingBar.setEnabled(false);
        ratingBar.setStepSize(Float.parseFloat("0.5"));
        ratingBar.setNumStars(5);
        ratingBar.setRating(Float.parseFloat(rate));
        ratvalue.setText(""+ratingBar.getRating());
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
                bundle_edit.putString("recodId",recodId);
                bundle_edit.putString("jobId",jobId);
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
                bundle_edit.putString("recodId",recodId);
                bundle_edit.putString("jobId",jobId);
                intent_info.putExtras(bundle_edit);
                intent_info.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent_info);
                overridePendingTransition(R.anim.slide_up_info,R.anim.no_change);

            }
        });
        trackingID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (techids.equals("NA")){
                    Toast.makeText(getApplicationContext(),"No Technician Allotment",Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent_info = new Intent(getApplicationContext(), TrackingActivityLocation.class);
                    Bundle bundle_edit = new Bundle();
                    bundle_edit.putString("recodId", recodId);
                    bundle_edit.putString("jobId", jobId);
                    bundle_edit.putString("techids", techids);
                    intent_info.putExtras(bundle_edit);
                    intent_info.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent_info);
                    overridePendingTransition(R.anim.slide_up_info, R.anim.no_change);
                }
            }
        });
    }
    public void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("View Details");
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
    public void FetchProfile(final String recodId){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.BOOKINGVIEW,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // progressBar.setVisibility(View.GONE);
                        Log.e("RECDID", response);
                        try {
                            JSONObject object = new JSONObject(response);
                            Log.e("RECDARRAY", " "+object);
                                flagstatus = object.getString("flag_status");
                                prdctname = object.getString("prdct_name");
                                modelno = object.getString("model_no");
                                subproname = object.getString("sub_pro_name");
                                issuedtl = object.getString("issue_dtl");
                                cname = object.getString("c_name");
                                cphn = object.getString("c_phn");
                                cadrs = object.getString("c_adrs");
                                srvcdate = object.getString("srvc_date");
                                srvctime = object.getString("srvc_time");
                                payamnt = object.getString("pay_amnt");
                                paymode = object.getString("pay_mode");
                                companyname = object.getString("company_name");
                                servcphn = object.getString("servc_phn");
                                servcadrs = object.getString("servc_adrs");
                                landmark = object.getString("land_mark");
                                techname = object.getString("tech_name");
                                techcode = object.getString("tech_code");
                                techphn = object.getString("tech_phn");
                                techimg = object.getString("tech_img");
                                techids = object.getString("tech_ids");
                                ////////////////////////////////
                               canceldate = object.getString("cancel_date");
                               canceltime = object.getString("cancel_time");
                               cancelreason = object.getString("cancel_reason");
                               status = object.getString("status");
                               Log.e("VIEWDITAILS" +
                                        "", prdctname+" "+modelno+" "+subproname+" "+issuedtl+" "
                                        +cname+" "+cphn+" "+cadrs+" "+srvcdate+" "+srvctime
                                       +" "+payamnt+" "+paymode+" "
                                       +canceldate+" "+canceltime+" "+cancelreason);

                            Pname.setText(prdctname);
                            companynameId.setText(companyname);
                            modelNoId.setText(modelno);
                            servicenameId.setText(subproname);
                            issuedId.setText(issuedtl);
                            serviceAddId.setText(servcadrs);
                            preFerDateID.setText(srvcdate+" "+srvctime);
                            preFerTimeID.setText(srvctime);
                            CustNameID.setText(cname);
                            CusPhoneID.setText(cphn);
                            alerNoId.setText(servcphn);
                            CusAddressID.setText(cadrs);
                            paymentmodeID.setText(paymode);
                            amountID.setText(payamnt);
                            totalchargeID.setText(payamnt);
                            landmarkId.setText(landmark);
                            tmaneId.setText(techname);
                            Glide.with(getApplicationContext())
                                    .load(techimg)
                                    .into(imgID);
                            if (status.equals("0")){
                                PcardId.setVisibility(View.VISIBLE);
                                CancardId.setVisibility(View.GONE);
                            }
                            if (status.equals("1")){
                                PcardId.setVisibility(View.GONE);
                                CancardId.setVisibility(View.VISIBLE);
                            }
                                    cdateId.setText(canceldate);
                                    ctimeId.setText(canceltime);
                                    cresonId.setText(cancelreason);
                            if (flagstatus.equals("1")){
                                trackingID.setVisibility(View.VISIBLE);
                            }
                            else {
                                trackingID.setVisibility(View.GONE);

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
                params.put("rcd_id", recodId);
                Log.e("RECD",recodId);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton volleySingleton = VolleySingleton.getInstance(getApplicationContext());
        stringRequest.setShouldCache(false);
        volleySingleton.addToRequestQueue(stringRequest);
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),HomeNavigationDrawerActivity.class);
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
