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
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import callsolve.call.call2solve.AdapterActivity.HomeItemViewAdapter;
import callsolve.call.call2solve.AdapterActivity.ViewPagerHomeItemAdapter;
import callsolve.call.call2solve.SetgetActivity.HomeitemViewCatagorySetGet;
import callsolve.call.call2solve.SetgetActivity.SliderUtils;
import callsolve.call.call2solve.SetgetActivity.User;
import callsolve.call.call2solve.SetgetActivity.YoutubeVideo;
import callsolve.call.call2solve.SharePreferance.SharedPrefManager;
import callsolve.call.call2solve.URL.URLs;
public class HomeItemFragmentHome extends Fragment implements OnBackClickListener{
    ViewPager viewPager;
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;
    RecyclerView recyclerView,rcv;
    List<HomeitemViewCatagorySetGet> homeitemViewCatagorySetGets;
    HomeItemViewAdapter homeItemViewAdapter;
    List<YoutubeVideo> youtubeVideos;
    VideoAdapter videoAdapter;
    RequestQueue rq;
    List<SliderUtils> sliderImg;
    ViewPagerHomeItemAdapter viewPagerAdapter;
    ArrayList<HomeitemViewCatagorySetGet> arrlist = new ArrayList<HomeitemViewCatagorySetGet>();
    ArrayList<YoutubeVideo> list = new ArrayList<YoutubeVideo>();
    String request_url = "https://call2solv.com/cal2solv-service/home_bnr.php";
    Timer timer;
    int page = 0;
    ProgressBar progressBar;
    RelativeLayout footer;
    TextView serviceTv,bookId;
    private Context context;
    String customerId;
    String booksmallid,bookid,msg,custid;
    private BackButtonHandlerInterface backButtonHandler;
    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        backButtonHandler = (BackButtonHandlerInterface) activity;
        backButtonHandler.addBackClickListener(this);
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragmenthome_item, container, false);
        User user = SharedPrefManager.getInstance(getContext()).getUser();
        customerId = String.valueOf(user.getCustomerid());
        CheckRating(customerId);
        rq = CustomVolleyRequest.getInstance(getContext()).getRequestQueue();
        sliderImg = new ArrayList<>();
        viewPager = (ViewPager)rootView.findViewById(R.id.viewPager);
        sliderDotspanel = (LinearLayout) rootView.findViewById(R.id.SliderDots);
        progressBar =(ProgressBar)rootView.findViewById(R.id.progressBar);
        footer =(RelativeLayout)rootView.findViewById(R.id.footer);
        bookId = (TextView)rootView.findViewById(R.id.bookId);
        serviceTv = (TextView)rootView.findViewById(R.id.serviceTv);
        serviceTv.setVisibility(View.GONE);
        footer.setVisibility(View.GONE);
        sliderImg.clear();
        sendRequest();
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                for(int i = 0; i< dotscount; i++){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.non_active_dot));
                }
                dots[position].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.active_dot));
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        recyclerView = (RecyclerView)rootView.findViewById(R.id.card_recycler_view);
        rcv = (RecyclerView)rootView.findViewById(R.id.rcv);
        homeitemViewCatagorySetGets = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(),3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(homeItemViewAdapter);
        youtubeVideos = new ArrayList<>();
        rcv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rcv.setAdapter(videoAdapter);
        bookId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (msg.equals("true")) {
                    if (customerId.equals(custid)) {
                        Intent intent = new Intent(getContext(), RatingBarActivity.class);
                        Bundle bundle_edit = new Bundle();
                        bundle_edit.putString("recodId", booksmallid);
                        bundle_edit.putString("jobId", bookid);
                        intent.putExtras(bundle_edit);
                        startActivity(intent);
                        getActivity().overridePendingTransition(R.anim.slide_up_info, R.anim.no_change);
                    }
                }
                else if(msg.equals("false")){
                    Intent intent_info = new Intent(getContext(), ChangeDistricActivity.class);
                    startActivity(intent_info);
                    getActivity().overridePendingTransition(R.anim.slide_up_info, R.anim.no_change);
                }
                else {

                }
            }
        });
        loadcatagory();
        youtubvider();
        deleteCache(context);
        return rootView;
    }
    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) { e.printStackTrace();}
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if (dir != null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }
    public void sendRequest(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.HOME_SILIDER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.GONE);
                        footer.setVisibility(View.VISIBLE);
                        serviceTv.setVisibility(View.VISIBLE);
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
                                SliderUtils sliderUtils = new SliderUtils();
                                sliderUtils.setSliderImageUrl(img);
                                sliderImg.add(sliderUtils);
                            }
                            viewPagerAdapter = new ViewPagerHomeItemAdapter(sliderImg, getContext());
                            viewPager.setAdapter(viewPagerAdapter);
                            dotscount = viewPagerAdapter.getCount();
                            dots = new ImageView[dotscount];
                            for(int i = 0; i < dotscount; i++){
                                dots[i] = new ImageView(getContext());
                                dots[i].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.non_active_dot));
                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                params.setMargins(8, 0, 8, 0);
                                sliderDotspanel.addView(dots[i], params);
                            }
                            dots[0].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.active_dot));
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
        VolleySingleton volleySingleton = VolleySingleton.getInstance(getContext());
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

            if (getActivity() != null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (page > 5) { // In my case the number of pages are 5
                            timer.cancel();
                            //  LsttoFirst();
                        } else {
                            //play store error
                            viewPager.setCurrentItem(page++);
                        }
                    }
                });
            }

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
    public void loadcatagory(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.CATAGORY,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("catagory"," "+response);
                        try {
                            JSONArray array = new JSONArray(response);
                            Log.e("array", " "+array);
                            for (int i = 0; i<array.length(); i++){
                                JSONObject jsonObject = array.getJSONObject(i);
                                Log.e("obj", " "+jsonObject);
                                String catid  = jsonObject.getString("cat_id");
                                String catname = jsonObject.getString("alias");
                                String  img = jsonObject.getString("img");
                                Log.e("showview", catid+" "+catname+" "+img);
                                homeitemViewCatagorySetGets.add(new HomeitemViewCatagorySetGet(
                                        jsonObject.getString("cat_id"),
                                        jsonObject.getString("alias"),
                                        jsonObject.getString("img")
                                ));
                                arrlist.addAll(homeitemViewCatagorySetGets);
                                homeItemViewAdapter = new HomeItemViewAdapter(getContext(), homeitemViewCatagorySetGets);
                                recyclerView.setAdapter(homeItemViewAdapter);
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
        VolleySingleton volleySingleton = VolleySingleton.getInstance(getContext());
        stringRequest.setShouldCache(false);
        volleySingleton.addToRequestQueue(stringRequest);

    }

    public void youtubvider(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.YOUTUBVIDE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("catagory"," "+response);
                        try {
                            JSONArray array = new JSONArray(response);
                            Log.e("array", " "+array);
                            for (int i =0; i<array.length(); i++){
                                JSONObject jsonObject = array.getJSONObject(i);
                                String vlink = jsonObject.getString("v_link");
                                String[] bits = vlink.split("/");
                                String lastOne = bits[bits.length-1];
                                Log.e("Link", lastOne);
                                youtubeVideos.add(new YoutubeVideo(jsonObject.getString("v_link")));
                                list.addAll(youtubeVideos);
                                videoAdapter = new VideoAdapter(getContext(), youtubeVideos);
                                rcv.setAdapter(videoAdapter);
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
        VolleySingleton volleySingleton = VolleySingleton.getInstance(getContext());
        stringRequest.setShouldCache(false);
        volleySingleton.addToRequestQueue(stringRequest);
    }

    public class VideoAdapter  extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {
        private Context mCtx;
        private List<YoutubeVideo> youtubeVideos;
        public VideoAdapter(Context mCtx, List<YoutubeVideo> youtubeVideos) {
            this.mCtx = mCtx;
            this.youtubeVideos = youtubeVideos;
        }
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.videilodad, parent, false);
            return new ViewHolder(view);

        }
        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            YoutubeVideo youtubeVideo = youtubeVideos.get(position);
            holder.webview.loadUrl(youtubeVideo.getVlink());
        }
        @Override
        public int getItemCount() {
            return youtubeVideos.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            WebView webview;
            public ViewHolder(View view) {
                super(view);
                webview = view.findViewById(R.id.webview);
                webview.getSettings().setJavaScriptEnabled(true);
                webview.setWebChromeClient(new WebChromeClient());
            }
        }
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
        VolleySingleton volleySingleton = VolleySingleton.getInstance(getContext());
        stringRequest.setShouldCache(false);
        volleySingleton.addToRequestQueue(stringRequest);

    }
    @Override
    public void onStart() {
        super.onStart();
    }
    @Override
    public void onResume() {
        super.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
    }
    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    @Override
    public void onDetach() {
        super.onDetach();
        backButtonHandler.removeBackClickListener(this);
        backButtonHandler = null;
    }
    @Override
    public boolean onBackClick() {
        Toast.makeText(getContext(), "Do not exit", Toast.LENGTH_SHORT).show();
        return true;
    }

}
