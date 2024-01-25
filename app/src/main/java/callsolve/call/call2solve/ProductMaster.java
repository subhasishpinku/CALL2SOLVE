package callsolve.call.call2solve;

import android.app.Activity;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import callsolve.call.call2solve.SetgetActivity.HomeViewSetget;
import callsolve.call.call2solve.SetgetActivity.sliderImgProduct;
import callsolve.call.call2solve.URL.URLs;

public class ProductMaster extends AppCompatActivity {
    ViewPager viewPager;
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;
    List<sliderImgProduct> sliderImg;
    Timer timer;
    int page = 0;
    int page1 = 6;
    Activity activity;
    private final String Itemname[] = {
            "Donut",
            "Eclair",
            "Froyo",
            "Gingerbread",
            "Honeycomb",
            "Ice Cream",
            "Jelly Bean",
            "KitKat",
            "Lollipop",
            "Marshmallow"
    };

    private final String image_urls[] = {
            "http://api.learn2crack.com/android/images/donut.png",
            "http://api.learn2crack.com/android/images/eclair.png",
            "http://api.learn2crack.com/android/images/froyo.png",
            "http://api.learn2crack.com/android/images/ginger.png",
            "http://api.learn2crack.com/android/images/honey.png",
            "http://api.learn2crack.com/android/images/icecream.png",
            "http://api.learn2crack.com/android/images/jellybean.png",
            "http://api.learn2crack.com/android/images/kitkat.png",
            "http://api.learn2crack.com/android/images/lollipop.png",
            "http://api.learn2crack.com/android/images/marshmallow.png"
    };

    List<HomeViewSetget> homeViewSetgets;
    ArrayList<HomeViewSetget> arrlist = new ArrayList<HomeViewSetget>();
    RecyclerView recyclerView;
    String catID,productname;
    Toolbar toolbar;
    TextView bookId;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home);
        Intent intent = getIntent();
        catID = intent.getStringExtra("catID");
        productname = intent.getStringExtra("productname");
        Log.e("catIDShow",catID+" "+productname);
        initToolBar();
        viewPager = (ViewPager)findViewById(R.id.viewPager);
        sliderDotspanel = (LinearLayout)findViewById(R.id.SliderDots);
        bookId = (TextView)findViewById(R.id.bookId);
//        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getApplicationContext());
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
////
        http://www.sanktips.com/2017/10/15/how-to-fetch-images-from-server-to-image-slider-with-viewpager-in-android-studio/
        ///

//        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
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

        recyclerView = (RecyclerView)findViewById(R.id.card_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),4);
        recyclerView.setLayoutManager(layoutManager);

//        ArrayList<HomeViewSetget> homeViewSetgets = prepareData();
//        HomeViewAdapter adapter = new HomeViewAdapter(getContext(),homeViewSetgets);
//        recyclerView.setAdapter(adapter);
        homeViewSetgets = new ArrayList<>();
        bookId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(),ChangeDistricActivity.class);
                startActivity(intent1);
            }
        });

        //adding some items to our list
//        homeViewSetgets.add(
//                new HomeViewSetget("1",
//                        "Ac \n Repair",
//                        R.drawable.ac));
//        homeViewSetgets.add(
//                new HomeViewSetget("2",
//                        "Refrigerator \n Repair",
//                        R.drawable.fridge));
//        homeViewSetgets.add(
//                new HomeViewSetget("3",
//                        "Fan \n Repair",
//                        R.drawable.fan));
//        homeViewSetgets.add(
//                new HomeViewSetget("4",
//                        "Laptop \n Repair",
//                        R.drawable.laptop));
//        homeViewSetgets.add(
//                new HomeViewSetget("5",
//                        "Computer \n Repair",
//                        R.drawable.computer));
//        homeViewSetgets.add(
//                new HomeViewSetget("6",
//                        "Microwave \n Repair",
//                        R.drawable.microwave));
//        homeViewSetgets.add(
//                new HomeViewSetget("7",
//                        "Printer \n Repair",
//                        R.drawable.printer));
//        homeViewSetgets.add(
//                new HomeViewSetget("8",
//                        "Mixergrinder \n Repair",
//                        R.drawable.mixi));
//        homeViewSetgets.add(
//                new HomeViewSetget("9",
//                        "Induction \n Repair",
//                        R.drawable.induction));
        //creating recyclerview adapter
//        HomeViewAdapter adapter = new HomeViewAdapter(getApplicationContext(), homeViewSetgets);
//        recyclerView.setAdapter(adapter);

        ProductMaster();
    }

    public void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(productname);
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

//    private ArrayList<HomeViewSetget> prepareData(){
//
//        ArrayList<HomeViewSetget> ItemView = new ArrayList<>();
//        for(int i=0;i<Itemname.length;i++){
//            HomeViewSetget homeViewSetget = new HomeViewSetget();
//            homeViewSetget.setItemname(Itemname[i]);
//            homeViewSetget.setItemimageurl(image_urls[i]);
//            ItemView.add(homeViewSetget);
//        }
//        return ItemView;
//    }
    public void ProductMaster(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.PRODUCTMASTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("productnameissse"," "+response);
                        try {
                            JSONArray array = new JSONArray(response);
                            Log.e("array", " "+array);
                            for (int i = 0; i<array.length(); i++){
                                JSONObject jsonObject = array.getJSONObject(i);
                                String prductid = jsonObject.getString("prduct_id");
                                String prductnm = jsonObject.getString("prduct_nm");
                                String img = jsonObject.getString("img");
                                Log.e("pro", " "+prductid+" "+prductnm+" "+img);
                                homeViewSetgets.add(new HomeViewSetget(
                                        jsonObject.getString("prduct_id"),
                                        jsonObject.getString("prduct_nm"),
                                        jsonObject.getString("img")
                                ));
                                arrlist.addAll(homeViewSetgets);
                                HomeViewAdapter adapter = new HomeViewAdapter(getApplicationContext(), homeViewSetgets);
                                recyclerView.setAdapter(adapter);
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
                params.put("c_id", catID);
                return params;

            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
        VolleySingleton volleySingleton = VolleySingleton.getInstance(this);
        stringRequest.setShouldCache(false);
        volleySingleton.addToRequestQueue(stringRequest);

    }
    public class HomeViewAdapter extends RecyclerView.Adapter<HomeViewAdapter.ViewHolder> {
        // private ArrayList<HomeViewSetget> homeViewSetgets;
        private Context context;
        private List<HomeViewSetget> homeViewSetgets;
        int listview = -1;
//    public HomeViewAdapter(Context context,ArrayList<HomeViewSetget> homeViewSetgets) {
//        this.homeViewSetgets = homeViewSetgets;
//        this.context = context;
//    }

        public HomeViewAdapter(Context context, List<HomeViewSetget> homeViewSetgets) {
            this.homeViewSetgets = homeViewSetgets;
            this.context = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.home_row_view, viewGroup, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder viewHolder, int i) {
            listview = i;
            //HomeViewSetget homeViewSetget = homeViewSetgets.get(i);
            // viewHolder.itemname.setText(homeViewSetgets.get(i).getItemname());
            //  Picasso.with(context).load(homeViewSetgets.get(i).getItemimageurl()).resize(24, 12).into(viewHolder.imageItem);
            HomeViewSetget homeViewSetget = homeViewSetgets.get(i);
            viewHolder.itemname.setText(String.valueOf(homeViewSetget.getItemname()));
            viewHolder.productId.setText(homeViewSetget.getItemId());
            // viewHolder.imageItem.setImageDrawable(context.getResources().getDrawable(homeViewSetget.getItemimageurl()));
            Glide.with(context)
                    .load(homeViewSetget.getItemimageurl())
                    .into(viewHolder.imageItem);

            viewHolder.lv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String Itemname = viewHolder.itemname.getText().toString();
                    String prdctid = viewHolder.productId.getText().toString();
                    Intent intent = new Intent(context, ItemViewActivity.class);
                    Bundle bundle_edit  =   new Bundle();
                    bundle_edit.putString("Itemname",Itemname);
                    bundle_edit.putString("prdctid",prdctid);
                    bundle_edit.putString("catID",catID);
                    bundle_edit.putString("productname",productname);
                    intent.putExtras(bundle_edit);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    //Toast.makeText(context,"NAME:"+" "+Itemname,Toast.LENGTH_SHORT).show();

                }
            });
        }

        @Override
        public int getItemCount() {
            return homeViewSetgets.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            private TextView itemname,productId;
            private ImageView imageItem;
            LinearLayout lv;
            public ViewHolder(View view) {
                super(view);

                itemname = (TextView)view.findViewById(R.id.itemname);
                imageItem = (ImageView) view.findViewById(R.id.imageItem);
                lv = (LinearLayout)view.findViewById(R.id.lv);
                productId = (TextView)view.findViewById(R.id.productId);
            }
        }

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
