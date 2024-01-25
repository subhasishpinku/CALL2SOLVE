package callsolve.call.call2solve;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.FontRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import callsolve.call.call2solve.SetgetActivity.OrderSetGet;
import callsolve.call.call2solve.URL.URLs;

public class TrackingActivityLocation extends AppCompatActivity {
    Toolbar toolbar;
    String recodId,jobId,techids;
    RecyclerView recyclerView;
    List<Locationsetget> locationsetgets;
    LocationAdapter adapter;
    ArrayList<Locationsetget> arrlist = new ArrayList<Locationsetget>();
    String url;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tracking_activity_location);
        Intent intent = getIntent();
        recodId = intent.getStringExtra("recodId");
        jobId = intent.getStringExtra("jobId");
        techids = intent.getStringExtra("techids");
        Log.e("Trackingdetails",recodId+" "+jobId+" "+techids);
        initToolBar();
        recyclerView =(RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        locationsetgets = new ArrayList<>();
//        locationsetgets.add(
//                new Locationsetget(
//                        "20.5252",
//                        "22.552",
//                        "10:30",
//                        "Sodhpur Location", "20/03/2020","12345487752452"));
//        locationsetgets.add(
//                new Locationsetget(
//                        "20.5252",
//                        "22.552",
//                        "10:30",
//                        "Sodhpur Location", "20/03/2020","12345487752452"));
       // LocationAdapter adapter = new LocationAdapter(this, locationsetgets);
       // recyclerView.setAdapter(adapter);
        trackinglocation(jobId,techids);
    }
    public void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Tracking Details");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back_icon);
        toolbar.setNavigationOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent_info = new Intent(getApplicationContext(),Viewdetails.class);
                        Bundle bundle_edit  =   new Bundle();
                        bundle_edit.putString("recodId",recodId);
                        bundle_edit.putString("jobId",jobId);
                        intent_info.putExtras(bundle_edit);
                        intent_info.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent_info);
                        overridePendingTransition(R.anim.slide_up_info,R.anim.no_change);
                    }});

    }
    public void trackinglocation(final String jobId,final String techids){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.TRACKINGLOCATION,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // progressBar.setVisibility(View.GONE);
                        Log.e("RECDID", response);
                        try {
                            JSONArray array = new JSONArray(response);
                            Log.e("TRACKING", " "+array);
                            for (int i= 0; i<array.length(); i++){
                                JSONObject jsonObject = array.getJSONObject(i);
                                String jobid = jsonObject.getString("job_id");
                                String techid  = jsonObject.getString("tech_id");
                                String date = jsonObject.getString("date");
                                String time = jsonObject.getString("time");
                                String latitude = jsonObject.getString("latitude");
                                String longitude = jsonObject.getString("longitude");
                                String actualloc = jsonObject.getString("actual_loc");
                                String status = jsonObject.getString("status");

                                locationsetgets.add(new Locationsetget(
                                        jsonObject.getString("job_id"),
                                        jsonObject.getString("tech_id"),
                                        jsonObject.getString("date"),
                                        jsonObject.getString("time"),
                                        jsonObject.getString("latitude"),
                                        jsonObject.getString("longitude"),
                                        jsonObject.getString("actual_loc"),
                                        jsonObject.getString("status")
                                ));
                                arrlist.addAll(locationsetgets);
                                adapter = new LocationAdapter(getApplicationContext(), locationsetgets);
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
                params.put("job_id", jobId);
                params.put("tech_id", techids);
                Log.e("techidData",techids);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton volleySingleton = VolleySingleton.getInstance(getApplicationContext());
        stringRequest.setShouldCache(false);
        volleySingleton.addToRequestQueue(stringRequest);
    }
    public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationViewHolder> {
        private Context mCtx;
        private List<Locationsetget> locationsetgets;
        public LocationAdapter(Context mCtx, List<Locationsetget> locationsetgets) {
            this.mCtx = mCtx;
            this.locationsetgets = locationsetgets;
        }

        @Override
        public LocationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            //inflating and returning our view holder
            LayoutInflater inflater = LayoutInflater.from(mCtx);
            View view = inflater.inflate(R.layout.location_show, null);
            return new LocationViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final LocationViewHolder holder, int position) {
            Locationsetget locationsetget = locationsetgets.get(position);
            holder.timeId.setText(locationsetget.getTime());
            holder.locationId.setText(locationsetget.getActualloc());
            holder.latId.setText(locationsetget.getLatitude());
            holder.langId.setText(locationsetget.getLongitude());
            holder.viewmapId.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                String latitude = holder.latId.getText().toString();
                String langitude = holder.langId.getText().toString();
                Log.e("Call2solveLocation"," "+latitude+" "+langitude+" ");
                url = "http://maps.google.com/maps?daddr=" + latitude + "," + langitude;
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
                }
            });


        }
        @Override
        public int getItemCount() {
            return locationsetgets.size();
        }
        class LocationViewHolder extends RecyclerView.ViewHolder {
            TextView odtypeID, timeId, locationId,latId,langId;
            ImageView viewmapId;
            public LocationViewHolder(View itemView) {
                super(itemView);
                odtypeID = itemView.findViewById(R.id.odtypeID);
                timeId = itemView.findViewById(R.id.timeId);
                locationId = itemView.findViewById(R.id.locationId);
                viewmapId = itemView.findViewById(R.id.viewmapId);
                latId = itemView.findViewById(R.id.latId);
                langId = itemView.findViewById(R.id.langId);
            }
        }
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
