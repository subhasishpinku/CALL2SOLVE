package callsolve.call.call2solve;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import callsolve.call.call2solve.AdapterActivity.ViewPagerAdapter;
import callsolve.call.call2solve.SetgetActivity.DistricViewSetGet;
import callsolve.call.call2solve.SetgetActivity.sliderImgProduct;
import callsolve.call.call2solve.URL.URLs;

public class ItemViewActivity extends AppCompatActivity {
    ViewPager viewPager;
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;
    List<sliderImgProduct> sliderImg;
    Timer timer;
    int page = 0;
    int page1 = 6;
    String Itemname,prdctid,catID,productname;
    Toolbar toolbar;
 //   ViewPager viewPager;
 //   LinearLayout sliderDotspanel;
 //   private int dotscount;
  //  private ImageView[] dots;
  //  Timer timer;
   // int page = 0;
     RecyclerView recyclerView;
     List<DistricViewSetGet> districViewSetGets;
     LinearLayout lvrecycler,lvv;
     TextView serviceTv;
     AlertDialog.Builder builder;
    private AlertDialog alertDialog = null;
    int listview = -1;
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();
    ArrayList<DistricViewSetGet> arrlist = new ArrayList<DistricViewSetGet>();
    String dist,districtv,disName;
    String imgUrl;
    TextView nofounId;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.itenview_activity);
        Intent intent = getIntent();
        Itemname = intent.getStringExtra("Itemname");
        prdctid = intent.getStringExtra("prdctid");
        catID = intent.getStringExtra("catID");
        productname = intent.getStringExtra("productname");
        Log.e("Itemname",Itemname+" "+prdctid+" "+catID+" "+productname);
        initToolBar();
        viewPager = (ViewPager)findViewById(R.id.viewPager);
        sliderDotspanel = (LinearLayout)findViewById(R.id.SliderDots);
//        viewPager = (ViewPager)findViewById(R.id.viewPager);
//        sliderDotspanel = (LinearLayout)findViewById(R.id.SliderDots);
//        ViewPagerAdapterHome viewPagerAdapterHome = new ViewPagerAdapterHome(getApplicationContext());
//        viewPager.setAdapter(viewPagerAdapterHome);
//        dotscount = viewPagerAdapterHome.getCount();
//        dots = new ImageView[dotscount];
//        for(int i = 0; i < dotscount; i++){
//            dots[i] = new ImageView(getApplicationContext());
//            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//            params.setMargins(8, 0, 8, 0);
//            sliderDotspanel.addView(dots[i], params);
//        }
//
//        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
//
//        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//
//                for(int i = 0; i< dotscount; i++){
//                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
//                }
//
//                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
    //    pageSwitcher(5);
//        lvrecycler.setVisibility(View.GONE);
        serviceTv =(TextView)findViewById(R.id.serviceTv);
        lvrecycler =(LinearLayout)findViewById(R.id.lvrecycler);
        lvv = (LinearLayout)findViewById(R.id.lvv);
        recyclerView = (RecyclerView)findViewById(R.id.card_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),4);
        recyclerView.setLayoutManager(layoutManager);
        districViewSetGets = new ArrayList<>();
        sliderImg = new ArrayList<>();
        sliderImg.clear();
        sendRequest();
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
//        districViewSetGets.add(
//                new DistricViewSetGet(
//                        "Alipurduar",
//                        R.mipmap.alipurduar));
//        districViewSetGets.add(
//                new DistricViewSetGet(
//                        "Bankura",
//                        R.mipmap.bankura));
//        districViewSetGets.add(
//                new DistricViewSetGet(
//                        "paschim Bardhaman",
//                        R.mipmap.s_bardhaman));
//        districViewSetGets.add(
//                new DistricViewSetGet(
//                        "Purba Bardhaman",
//                        R.mipmap.e_bardhaman));
//        districViewSetGets.add(
//                new DistricViewSetGet(
//                        "Birbhum",
//                        R.mipmap.birbhum));
//        districViewSetGets.add(
//                new DistricViewSetGet(
//                        "Coochbehar",
//                        R.mipmap.coochbehar));
//        districViewSetGets.add(
//                new DistricViewSetGet(
//                        "Darjeeling",
//                        R.mipmap.darjeeling));
//        districViewSetGets.add(
//                new DistricViewSetGet(
//                        "Uttar Dinajpur",
//                        R.mipmap.uttardinajpur));
//        districViewSetGets.add(
//                new DistricViewSetGet(
//                        "Dakshin Dinajpur",
//                        R.mipmap.dakshindinajpur));
//        districViewSetGets.add(
//                new DistricViewSetGet(
//                        "Howrah",
//                        R.mipmap.howrah));
//        districViewSetGets.add(
//                new DistricViewSetGet(
//                        "Jalpaiguri",
//                        R.mipmap.jalpaiguri));
//        districViewSetGets.add(
//                new DistricViewSetGet(
//                        "Jhargram",
//                        R.mipmap.jhargram));
//
//        districViewSetGets.add(
//                new DistricViewSetGet(
//                        "Kolkata",
//                        R.mipmap.kolkata));
//        districViewSetGets.add(
//                new DistricViewSetGet(
//                        "Kalimpong",
//                        R.mipmap.kalimpong));
//        districViewSetGets.add(
//                new DistricViewSetGet(
//                        "Malda",
//                        R.mipmap.malda));
//
//        districViewSetGets.add(
//                new DistricViewSetGet(
//                        "Paschim Medinipur",
//                        R.mipmap.south_medinipur));
//
//        districViewSetGets.add(
//                new DistricViewSetGet(
//                        "Purba Midnapur",
//                        R.mipmap.purbamidnapur));
//        districViewSetGets.add(
//                new DistricViewSetGet(
//                        "Murshidabad",
//                        R.mipmap.murshidabad));
//        districViewSetGets.add(
//                new DistricViewSetGet(
//                        "Nadia",
//                        R.mipmap.nadia));
//        districViewSetGets.add(
//                new DistricViewSetGet(
//                        "North 24 Parganas",
//                        R.mipmap.n_24_pgs));
//        districViewSetGets.add(
//                new DistricViewSetGet(
//                        "South 24 Parganas",
//                        R.mipmap.s_24_pgs));
//        districViewSetGets.add(
//                new DistricViewSetGet(
//                        "Purulia",
//                        R.mipmap.purulia));
        //creating recyclerview adapter

    //    initImageBitmaps();

        DISTRICMASTER();
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
//    public void pageSwitcher(int seconds) {
//        timer = new Timer(); // At this line a new Thread will be created
//        timer.scheduleAtFixedRate(new ItemViewActivity.RemindTask(), 0, seconds * 1000); // delay
//        // in
//        // milliseconds
//    }
//    class RemindTask extends TimerTask {
//        @Override
//        public void run() {
//            runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        if (page > 5) { // In my case the number of pages are 5
//                            timer.cancel();
//                            LsttoFirst();
//                        } else {
//                            viewPager.setCurrentItem(page++);
//                        }
//                    }
//                });
//        }
//    }
//
//    private void LsttoFirst(){
//        final Handler handler = new Handler();
//        final Runnable Update = new Runnable() {
//            public void run() {
//                int currentPage = viewPager.getCurrentItem();
//                if (currentPage == page-6) {
//
//                    currentPage = 0;
//
//                } else {
//                    currentPage--;
//
//                }
//                viewPager.setCurrentItem(currentPage, true);
//
//                handler.postDelayed(this, 5000);
//
//            }
//        };
//
//        handler.postDelayed(Update, 500);
//
//    }


//    private void initImageBitmaps(){
//        mImageUrls.add("https://gskt.in/call2solv/images/district/alipurduar.png");
//        mNames.add("Alipurduar");
//
//        mImageUrls.add("https://gskt.in/call2solv/images/district/bankura.png");
//        mNames.add("Bankura");
//
//        mImageUrls.add("https://gskt.in/call2solv/images/district/s_bardhaman.png");
//        mNames.add("paschim Bardhaman");
//
//        mImageUrls.add("https://gskt.in/call2solv/images/district/e_bardhaman.png");
//        mNames.add("Purba Bardhaman");
//
//
//        mImageUrls.add("https://gskt.in/call2solv/images/district/birbhum.png");
//        mNames.add("Birbhum");
//
//        mImageUrls.add("https://gskt.in/call2solv/images/district/coochbehar.png");
//        mNames.add("Coochbehar");
//
//
//        mImageUrls.add("https://gskt.in/call2solv/images/district/darjeeling.png");
//        mNames.add("Darjeeling");
//
//        mImageUrls.add("https://gskt.in/call2solv/images/district/uttar-dinajpur.png");
//        mNames.add("Uttar Dinajpur");
//
//        mImageUrls.add("https://gskt.in/call2solv/images/district/dakshin-dinajpur.png");
//        mNames.add("Dakshin Dinajpur");
//
//        mImageUrls.add("https://gskt.in/call2solv/images/district/hoogly.png");
//        mNames.add("Hoogly");
//
//        mImageUrls.add("https://gskt.in/call2solv/images/district/howrah.png");
//        mNames.add("Howrah");
//
//        mImageUrls.add("https://gskt.in/call2solv/images/district/jalpaiguri.png");
//        mNames.add("Jalpaiguri");
//
//        mImageUrls.add("https://gskt.in/call2solv/images/district/jhargram.png");
//        mNames.add("Jhargram");
//
//        mImageUrls.add("https://gskt.in/call2solv/images/district/kolkata.png");
//        mNames.add("Kolkata");
//
//        mImageUrls.add("https://gskt.in/call2solv/images/district/kalimpong.png");
//        mNames.add("Kalimpong");
//
//        mImageUrls.add("https://gskt.in/call2solv/images/district/malda.png");
//        mNames.add("Malda");
//
//        mImageUrls.add("https://gskt.in/call2solv/images/district/south_medinipur.png");
//        mNames.add("Paschim Medinipur");
//
//        mImageUrls.add("https://gskt.in/call2solv/images/district/purba-midnapur.png");
//        mNames.add("Purba Midnapur");
//
//        mImageUrls.add("https://gskt.in/call2solv/images/district/murshidabad.png");
//        mNames.add("Murshidabad");
//
//        mImageUrls.add("https://gskt.in/call2solv/images/district/nadia.png");
//        mNames.add("Nadia");
//
//        mImageUrls.add("https://gskt.in/call2solv/images/district/n_24_pgs.png");
//        mNames.add("North 24 Parganas");
//
//        mImageUrls.add("https://gskt.in/call2solv/images/district/s_24_pgs.png");
//        mNames.add("South 24 Parganas");
//
//        mImageUrls.add("https://gskt.in/call2solv/images/district/purulia.png");
//        mNames.add("Purulia");
//      //  DistricAdapter adapter = new DistricAdapter(getApplicationContext(), mNames,mImageUrls);
//      //  recyclerView.setAdapter(adapter);
//
//    }
    public class DistricAdapter extends RecyclerView.Adapter<DistricAdapter.ViewHolder>  {
        private Context context;
       private List<DistricViewSetGet> districViewSetGets;
       int image;
       public DistricAdapter(Context context, List<DistricViewSetGet> districViewSetGets) {
           this.districViewSetGets = districViewSetGets;
           this.context = context;
       }

//        private ArrayList<String> mImageNames = new ArrayList<>();
//        private ArrayList<String> mImages = new ArrayList<>();
//        private Context mContext;
//
//        public DistricAdapter(Context context, ArrayList<String> imageNames, ArrayList<String> images ) {
//            mImageNames = imageNames;
//            mImages = images;
//            mContext = context;
//        }
        @NonNull
        @Override
        public DistricAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.distric_row_view, viewGroup, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final DistricAdapter.ViewHolder viewHolder, final int i) {
            listview = i;
            final DistricViewSetGet districViewSetGet = districViewSetGets.get(i);
            viewHolder.itemname.setText(String.valueOf(districViewSetGet.getItemname()));
            viewHolder.disId.setText(String.valueOf(districViewSetGet.getDistid()));
           // viewHolder.imageItem.setImageDrawable(context.getResources().getDrawable(districViewSetGet.getItemimageurl()));
            imgUrl = districViewSetGet.getItemimageurl();
            Glide.with(context)
                    .asBitmap()
                    .load(districViewSetGet.getItemimageurl())
                    .into(viewHolder.imageItem);

//            Glide.with(mContext)
//                    .asBitmap()
//                    .load(mImages.get(i))
//                    .into(viewHolder.imageItem);

                  viewHolder.lv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    disName = viewHolder.itemname.getText().toString();
                    dist = viewHolder.disId.getText().toString();
                //    Toast.makeText(mContext, mImageNames.get(i), Toast.LENGTH_SHORT).show();
                  // Toast.makeText(context,"NAME:"+" "+Itemname+" "+dist,Toast.LENGTH_SHORT).show();
                    builder = new AlertDialog.Builder(v.getContext());
                    final AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    final View loginFormView = getLayoutInflater().inflate(R.layout.diologbox, null);
                    Button closeID = (Button) loginFormView.findViewById(R.id.sub);
                    final EditText etsearch = (EditText)loginFormView.findViewById(R.id.etsearch);
                    nofounId = (TextView)loginFormView.findViewById(R.id.nofounId);
                    //tv.setText(Itemname);
                    toolbar = (Toolbar)loginFormView.findViewById(R.id.toolbar);
                  //  toolbar.setTitle(mImageNames.get(i));
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
                    closeID.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dist = viewHolder.disId.getText().toString();
                            districtv = etsearch.getText().toString().trim();
                            //Toast.makeText(context,"NAME:"+" "+districtv+" "+dist, Toast.LENGTH_SHORT).show();
                            PINCODECHECK();
                        }
                    });

                    builder.setView(loginFormView);
                    alertDialog = builder.create();
                    alertDialog.show();

//                String Itemname = viewHolder.itemname.getText().toString();
////                Intent intent = new Intent(context, ItemViewActivity.class);
////                Bundle bundle_edit  =   new Bundle();
////                bundle_edit.putString("Itemname",Itemname);
////                intent.putExtras(bundle_edit);
////                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////                context.startActivity(intent);
//                Toast.makeText(context,"NAME:"+" "+Itemname,Toast.LENGTH_SHORT).show();
                }
            });


        }

        @Override
        public int getItemCount() {
            return districViewSetGets.size();
        //    return mImageNames.size();
        }
        public class ViewHolder extends RecyclerView.ViewHolder{
            private TextView itemname;
            private TextView disId;
            private ImageView imageItem;
            LinearLayout lv;
            public ViewHolder(View view) {
                super(view);

                itemname = (TextView)view.findViewById(R.id.itemname);
                imageItem = (ImageView) view.findViewById(R.id.imageItem);
                lv = (LinearLayout)view.findViewById(R.id.lv);
                disId = (TextView)view.findViewById(R.id.disId);
            }
        }
    }
    public void DISTRICMASTER(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.DISTRICMASTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Districmaster"," "+response);
                        try {
                            JSONArray array = new JSONArray(response);
                            Log.e("array", " "+array);
                            for (int i = 0; i<array.length(); i++){
                                JSONObject jsonObject = array.getJSONObject(i);
                                String distid = jsonObject.getString("dist_id");
                                String distnm = jsonObject.getString("dist_nm");
                                String distimg = jsonObject.getString("dist_img");
                                Log.e("dis", " "+distid+" "+distnm+" "+distimg);
                                if (distnm.equals("Select District")){
                                    Log.e("dissssssss",distnm);

                                }
                                else {
                                    districViewSetGets.add(new DistricViewSetGet(
                                            jsonObject.getString("dist_id"),
                                            jsonObject.getString("dist_nm"),
                                            jsonObject.getString("dist_img")
                                    ));
                                    arrlist.addAll(districViewSetGets);
                                    DistricAdapter adapter = new DistricAdapter(getApplicationContext(), districViewSetGets);
                                    recyclerView.setAdapter(adapter);
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
                return params;

            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
        VolleySingleton volleySingleton = VolleySingleton.getInstance(this);
        stringRequest.setShouldCache(false);
        volleySingleton.addToRequestQueue(stringRequest);


    }
    public void PINCODECHECK(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.PINCODECHECK,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("pincodecheck"," "+response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            Log.e("array", " "+obj);
                            String msg = obj.getString("msg");
                            if(msg.equals("true")){
                                String productound = obj.getString("product_found");
                                if (productound.equals("true")){
                                    String distid = obj.getString("dist_id");
                                    String pincodeid = obj.getString("pincode_id");
                                    String totsubproduct = obj.getString("tot_sub_product");
                                    Log.e("PPDUC",totsubproduct);
                                    if (totsubproduct.equals("1")){
                                        Log.e("code",distid+" "+pincodeid);
                                        Intent intent = new Intent(getApplicationContext(), RateChargeActivity.class);
                                        intent.putExtra("imageurl", imgUrl);
                                        intent.putExtra("dist", distid);
                                        intent.putExtra("pinid", pincodeid);
                                        intent.putExtra("prdctid", prdctid);
                                        intent.putExtra("disName",disName);
                                        intent.putExtra("Itemname",Itemname);
                                        intent.putExtra("catID",catID);
                                        intent.putExtra("productname",productname);
                                        intent.putExtra("districtv",districtv);
                                        startActivity(intent);
                                    }
                                    else {
                                        Intent intent = new Intent(getApplicationContext(), RateChargeActivitynew.class);
                                        intent.putExtra("imageurl", imgUrl);
                                        intent.putExtra("dist", distid);
                                        intent.putExtra("pinid", pincodeid);
                                        intent.putExtra("prdctid", prdctid);
                                        intent.putExtra("disName",disName);
                                        intent.putExtra("Itemname",Itemname);
                                        intent.putExtra("catID",catID);
                                        intent.putExtra("productname",productname);
                                        intent.putExtra("districtv",districtv);
                                        startActivity(intent);
                                   }
                                }
                                else {
                                   // Toast.makeText(getApplicationContext(),"Product Not Found",Toast.LENGTH_SHORT).show();
                                    nofounId.setText("This service will be available very soon in your area.For further information please call 8420208888");
                                }
                            }
                            else {
                                //Toast.makeText(getApplicationContext(),"Invalid Pincode",Toast.LENGTH_SHORT).show();
                                nofounId.setText("This service will be available very soon in your area.For further information please call 8420208888");
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
                params.put("pincode", districtv);
                params.put("p_id",prdctid);
                Log.e("Pin",dist+" "+districtv+" "+prdctid);
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
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.HOME_SILIDER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //   progressBar.setVisibility(View.GONE);
                        Log.e("response"," "+response);
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
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("banner");
                            for (int i = 0; i<jsonArray.length(); i++){
                                JSONObject Object = jsonArray.getJSONObject(i);
                                String img = Object.getString("img");
                                Log.e("Img"," "+img);
                                sliderImgProduct sliderUtils = new sliderImgProduct();
                                sliderUtils.setSliderImageUrl(img);
                                sliderImg.add(sliderUtils);
                            }
                            ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(sliderImg, getApplicationContext());
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
