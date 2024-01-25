package callsolve.call.call2solve;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

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
import java.util.Timer;
import java.util.TimerTask;

import callsolve.call.call2solve.AdapterActivity.ServiceChangeRate;
import callsolve.call.call2solve.AdapterActivity.TramAndConditionTextAdapter;
import callsolve.call.call2solve.AdapterActivity.ViewPagerRatecardAdapter;
import callsolve.call.call2solve.DatabaseActivity.DatabaseHelper;
import callsolve.call.call2solve.SetgetActivity.ServiceChargesetget;
import callsolve.call.call2solve.SetgetActivity.Sliderproduct;
import callsolve.call.call2solve.SetgetActivity.TramAndConditionText;
import callsolve.call.call2solve.SetgetActivity.User;
import callsolve.call.call2solve.SharePreferance.SharedPrefManager;
import callsolve.call.call2solve.SharePreferance.SharedPrefManagerProfile;
import callsolve.call.call2solve.URL.URLs;

public class RateChargeActivity extends AppCompatActivity implements View.OnClickListener{
    String Itemname;
    Toolbar toolbar;
    ViewPager viewPager;
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;
    List<Sliderproduct> sliderImg;
    Timer timer;
    int page = 0;
    List<ServiceChargesetget> serviceChargesetgets;
    ServiceChangeRate serviceChangeRate;
    RecyclerView recyclerView;
    TextView tramcondition;
    String imageUrl,dist,pinid,prdctid,disName,catID,productname;
    private static final String TAG = "RateChargeActivity";
    ArrayList<ServiceChargesetget> arrlist = new ArrayList<ServiceChargesetget>();
    TextView title;
    RecyclerView rcvv;
    List<TramAndConditionText> tramAndConditionTexts;
    TramAndConditionTextAdapter tramAndConditionTextAdapter;
    ArrayList<TramAndConditionText> arrlist2 = new ArrayList<TramAndConditionText>();
    RelativeLayout placeOrderID;
    DatabaseHelper db;
    String cusTID,customerId,districtv;
    String cusId ="0";
    String booksmallid,bookid,msg,custid;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.ratechage_activity);
        Log.e("PPDUC","1");
        getIncomingIntent();
        initToolBar();
        db =new DatabaseHelper(getApplicationContext());
        viewPager = (ViewPager)findViewById(R.id.viewPager);
    //    tramcondition = (TextView)findViewById(R.id.tramcondition);
        sliderDotspanel = (LinearLayout)findViewById(R.id.SliderDots);
        title = (TextView)findViewById(R.id.title);
        placeOrderID = (RelativeLayout)findViewById(R.id.placeOrderID);
        placeOrderID.setOnClickListener(this);
//        ViewPagerRatecardAdapter viewPagerAdapter = new ViewPagerRatecardAdapter(getApplicationContext());
//        viewPager.setAdapter(viewPagerAdapter);
//        dotscount = viewPagerAdapter.getCount();
//        dots = new ImageView[dotscount];
//        for(int i = 0; i < dotscount; i++){
//            dots[i] = new ImageView(getApplicationContext());
//            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//            params.setMargins(8, 0, 8, 0);
//            sliderDotspanel.addView(dots[i], params);
//        }
//////
//        http://www.sanktips.com/2017/10/15/how-to-fetch-images-from-server-to-image-slider-with-viewpager-in-android-studio/
//        ///
//        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
        sliderImg = new ArrayList<>();
        sliderImg.clear();
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for(int i = 0; i< dotscount; i++){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        sendRequest();
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        serviceChargesetgets = new ArrayList<>();
        recyclerView.setAdapter(serviceChangeRate);
        rcvv = (RecyclerView)findViewById(R.id.rcvv);
        rcvv.setHasFixedSize(true);
        rcvv.setLayoutManager(new LinearLayoutManager(this));
        tramAndConditionTexts =new ArrayList<>();
        rcvv.setAdapter(tramAndConditionTextAdapter);
        tramcondi();
//        serviceChargesetgets.add(
//                new ServiceChargesetget(
//                        "Service Change of Window Air Conditioner",
//                        "350.00","500.00","550.00"));
//        serviceChargesetgets.add(
//                new ServiceChargesetget(
//                        "Service Charge of split Air Conditioner",
//                        "425.00","750.00","1,250.00"));
//        serviceChangeRate = new ServiceChangeRate(this, serviceChargesetgets);
//        recyclerView.setAdapter(serviceChangeRate);
        pageSwitcher(5);

        Cursor res = db.getcusId();
        if(res.getCount() == 0) {
            Toast.makeText(getApplicationContext(),"please Login First",Toast.LENGTH_SHORT).show();
          //  Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
          //  startActivity(intent);
            return;
        }
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()) {
             cusTID =  res.getString(0);
             cusId =  res.getString(1);
            Log.e("DBPROclose", cusTID+" "+cusId);
        }
        User user = SharedPrefManager.getInstance(getApplicationContext()).getUser();
        customerId = String.valueOf(user.getCustomerid());
        Log.e("CustomerID", customerId+" "+cusId);
        CheckRating(customerId);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.placeOrderID:
                if (cusId.equals(customerId)) {
                    if (msg.equals("true")) {
                        if (customerId.equals(custid)) {
                            Intent intent = new Intent(getApplicationContext(), RatingBarActivity.class);
                            Bundle bundle_edit = new Bundle();
                            bundle_edit.putString("recodId", booksmallid);
                            bundle_edit.putString("jobId", bookid);
                            intent.putExtras(bundle_edit);
                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_up_info, R.anim.no_change);
                        }
                    }
                    else if(msg.equals("false")){
                        //senDataAnotherPage();
                        PLACEORDER();
                    }
                    else {

                    }
                }
                else {
                    Toast.makeText(getApplicationContext(),"please Login First",Toast.LENGTH_SHORT).show();
                    SharedPrefManagerProfile.getInstance(getApplicationContext()).clear();
                    //SharedPrefManagerProfile.getInstance(getApplicationContext()).logout();
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
            default:
        }
    }
    public void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(Itemname);
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


    private void getIncomingIntent(){
        Log.d(TAG, "getIncomingIntent: checking for incoming intents.");
        if(getIntent().hasExtra("imageurl") && getIntent().hasExtra("dist") && getIntent().hasExtra("pinid") && getIntent().hasExtra("prdctid") && getIntent().hasExtra("disName") && getIntent().hasExtra("Itemname") && getIntent().hasExtra("catID") && getIntent().hasExtra("productname") && getIntent().hasExtra("districtv")){
            Log.d(TAG, "getIncomingIntent: found intent extras.");
            imageUrl = getIntent().getStringExtra("imageurl");
            dist = getIntent().getStringExtra("dist");
            pinid = getIntent().getStringExtra("pinid");
            prdctid = getIntent().getStringExtra("prdctid");
            disName = getIntent().getStringExtra("disName");
            Itemname = getIntent().getStringExtra("Itemname");
            catID = getIntent().getStringExtra("catID");
            productname = getIntent().getStringExtra("productname");
            districtv   = getIntent().getStringExtra("districtv");
            Log.e("Data"," "+imageUrl+" "+ dist+" "+pinid+" "+prdctid+" "+disName+" "+Itemname+" "+catID+" "+productname+" "+districtv);
            PINCODECHECK();
            //serviceChage();

        }
    }
    public void PINCODECHECK(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.RATE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("ratecheckrate"," "+response);
                        try {
                            JSONArray array = new JSONArray(response);
                            Log.e("arrayrate", " "+array);
                            for (int i = 0; i<array.length(); i++){
                                JSONObject jsonObject = array.getJSONObject(i);
                                String subprductnm = jsonObject.getString("sub_prduct_nm");
                                title.setText(subprductnm);
                                JSONArray array1 = jsonObject.getJSONArray("charge");
                                for (int j = 0; j<array1.length(); j++){
                                    JSONObject jsonObject1 = array1.getJSONObject(j);
                                    String chargename = jsonObject1.getString("charge_name");
                                    String mainrate = jsonObject1.getString("main_rate");
                                    String ofrrate = jsonObject1.getString("ofr_rate");
                                    Log.e("RateData",chargename+" "+mainrate+" "+ofrrate+ " "+subprductnm);
                                    serviceChargesetgets.add(new ServiceChargesetget(
                                            jsonObject.getString("sub_prduct_nm"),
                                            jsonObject1.getString("charge_name"),
                                            jsonObject1.getString("main_rate"),
                                            jsonObject1.getString("ofr_rate")
                                     ));
                                     arrlist.addAll(serviceChargesetgets);
                                     serviceChangeRate = new ServiceChangeRate(getApplicationContext(), serviceChargesetgets);
                                     recyclerView.setAdapter(serviceChangeRate);
                                }
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
                params.put("dist",dist);
                params.put("pin_id", pinid);
                params.put("prdct_id", prdctid);
                Log.e("ratecount",dist+" "+pinid+" "+prdctid);
                return params;

            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
        VolleySingleton volleySingleton = VolleySingleton.getInstance(this);
        stringRequest.setShouldCache(false);
        volleySingleton.addToRequestQueue(stringRequest);
    }
    public void sendRequest(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.PRODUCT_SILIDER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //   progressBar.setVisibility(View.GONE);
                        Log.e("res"," "+response);
//                        for(int i = 0; i < response.length(); i++){
//
//                            SliderUtils sliderUtils = new SliderUtils();
//
//                            try {
//                                JSONObject jsonObject = response.getJSONObject(i);
//
//                                sliderUtils.setSliderImageUrl(jsonObject.getString("image_url"));
//
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//
//                          //  sliderImg.add(sliderUtils);
//
//                        }
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i<jsonArray.length(); i++){
                                JSONObject Object = jsonArray.getJSONObject(i);
                                String img = Object.getString("img");
                                Log.e("Img"," "+img);
                                Sliderproduct sliderUtils = new Sliderproduct();
                                sliderUtils.setSliderImageUrl(img);
                                 sliderImg.add(sliderUtils);
                            }
                          //  ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(sliderImg, getApplicationContext());
                        //    viewPager.setAdapter(viewPagerAdapter);
                           ViewPagerRatecardAdapter viewPagerAdapter = new ViewPagerRatecardAdapter(sliderImg,getApplicationContext());
                            viewPager.setAdapter(viewPagerAdapter);
                            dotscount = viewPagerAdapter.getCount();
                            dots = new ImageView[dotscount];

                            for(int i = 0; i < dotscount; i++){

                                dots[i] = new ImageView(getApplicationContext());
                                dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));

                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                                params.setMargins(8, 0, 8, 0);

                                sliderDotspanel.addView(dots[i], params);

                            }

                            dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
                            pageSwitcher(5);

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
                params.put("p_id",prdctid);
                return params;

            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
        VolleySingleton volleySingleton = VolleySingleton.getInstance(this);
        stringRequest.setShouldCache(false);
        volleySingleton.addToRequestQueue(stringRequest);
    }
    public void pageSwitcher(int seconds) {
        timer = new Timer(); // At this line a new Thread will be created
        timer.scheduleAtFixedRate(new RemindTask(), 0, seconds * 1000); // delay
        // in
        // milliseconds
    }
    class RemindTask extends TimerTask {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (page > 5) { // In my case the number of pages are 5
                        timer.cancel();
                        LsttoFirst();
                    } else {
                        viewPager.setCurrentItem(page++);
                    }
                }
            });
        }
    }
    private void LsttoFirst(){
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                int currentPage = viewPager.getCurrentItem();
                if (currentPage == page-6) {

                    currentPage = 0;

                } else {
                    currentPage--;

                }
                viewPager.setCurrentItem(currentPage, true);

                handler.postDelayed(this, 5000);

            }
        };

        handler.postDelayed(Update, 500);
    }
    public void tramcondi(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.TRAMANDCONDITION,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("text"," "+response);
                        try {
                            JSONArray array = new JSONArray(response);
                            Log.e("arraytext", " "+array);
                            for (int i = 0; i<array.length(); i++){
                                JSONObject jsonObject = array.getJSONObject(i);
                                String name = jsonObject.getString("name");
                                Log.e("tttext_y", " "+name);
                                tramAndConditionTexts.add(new TramAndConditionText(
                                        jsonObject.getString("name")
                                ));
                                arrlist2.addAll(tramAndConditionTexts);
                            }
                            tramAndConditionTextAdapter = new TramAndConditionTextAdapter(getApplicationContext(), tramAndConditionTexts);
                            rcvv.setAdapter(tramAndConditionTextAdapter);
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
        //VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
        VolleySingleton volleySingleton = VolleySingleton.getInstance(this);
        stringRequest.setShouldCache(false);
        volleySingleton.addToRequestQueue(stringRequest);


    }
    public void  PLACEORDER(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.PLACEORDER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("PLACEORDER"," "+response);
                        try {
                            JSONObject object = new JSONObject(response);
                            Log.e("PLACEORDEROBJ2", " "+object);
                            String status = object.getString("status");
                            String msg = object.getString("msg");
                            if (status.equals("1")){
                                senDataAnotherPage();
                                Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();

                            }
                            else if (status.equals("0")){
                                Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
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
                params.put("dist",dist);
                params.put("pin", districtv);
                params.put("prdct_id", prdctid);
                Log.e("PLACE2",dist+" "+districtv+" "+prdctid);
                return params;

            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
        VolleySingleton volleySingleton = VolleySingleton.getInstance(this);
        stringRequest.setShouldCache(false);
        volleySingleton.addToRequestQueue(stringRequest);

    }
    public void senDataAnotherPage(){
        Intent intent = new Intent(getApplicationContext(), CustomerRequirmentDetailsActivity.class);
        Bundle bundle_edit = new Bundle();
        bundle_edit.putString("imageUrl", imageUrl);
        bundle_edit.putString("dist", dist);
        bundle_edit.putString("pinid", pinid);
        bundle_edit.putString("prdctid", prdctid);
        bundle_edit.putString("disName", disName);
        bundle_edit.putString("Itemname", Itemname);
        bundle_edit.putString("catID", catID);
        bundle_edit.putString("productname", productname);
        bundle_edit.putString("districtv",districtv);
        intent.putExtras(bundle_edit);
        startActivity(intent);
    }
    private void CheckRating(final String customerId){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.RATECHECK,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // progressBar.setVisibility(View.GONE);
                        Log.e("getreat", response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            booksmallid = obj.getString("book_small_id");
                            bookid = obj.getString("book_id");
                            msg = obj.getString("msg");
                            custid = obj.getString("cust_id");
                            Log.e("GETRATE", booksmallid+" "+bookid+" "+msg);
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
