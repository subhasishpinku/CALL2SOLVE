package callsolve.call.call2solve;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import androidx.recyclerview.widget.GridLayoutManager;
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

import callsolve.call.call2solve.SetgetActivity.DistricViewSetGet;
import callsolve.call.call2solve.URL.URLs;


public class ChangeDistricActivity extends AppCompatActivity {
    String Itemname;
    Toolbar toolbar;
    RecyclerView recyclerView;
    List<DistricViewSetGet> districViewSetGets;
    ArrayList<DistricViewSetGet> arrlist = new ArrayList<DistricViewSetGet>();
    AlertDialog.Builder builder;
    private AlertDialog alertDialog = null;
    int listview = -1;
    String dist,districtv,disName;
    String imgUrl;
    TextView nofounId;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.changedistric);
        initToolBar();
        recyclerView = (RecyclerView)findViewById(R.id.card_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),4);
        recyclerView.setLayoutManager(layoutManager);
        districViewSetGets = new ArrayList<>();
        DISTRICMASTER();
    }
    public void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Change District");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //inish();
                        Intent intent = new Intent(getApplicationContext(),NavigationDrawerActivity.class);
                        startActivity(intent);
                    }
                }
        );
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
        VolleySingleton volleySingleton = VolleySingleton.getInstance(getApplicationContext());
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
                                String distid = obj.getString("dist_id");
                                String pincodeid = obj.getString("pincode_id");
                                Log.e("code",distid+" "+pincodeid);
                                Intent intent = new Intent(getApplicationContext(), RatecardAcivity.class);
                                intent.putExtra("imageurl", imgUrl);
                                intent.putExtra("dist", distid);
                                intent.putExtra("pinid", pincodeid);
                                // intent.putExtra("prdctid", "");
                                intent.putExtra("districtv", districtv);
                                intent.putExtra("disName",disName);
                                startActivity(intent);
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
                //  params.put("p_id",prdctid);
                Log.e("Pin",dist+" "+districtv+" ");
                return params;

            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
        VolleySingleton volleySingleton = VolleySingleton.getInstance(getApplicationContext());
        stringRequest.setShouldCache(false);
        volleySingleton.addToRequestQueue(stringRequest);
    }
    public class DistricAdapter extends RecyclerView.Adapter<DistricAdapter.ViewHolder>  {
        private Context context;
        private List<DistricViewSetGet> districViewSetGets;
        int image;
        public DistricAdapter(Context context, List<DistricViewSetGet> districViewSetGets) {
            this.districViewSetGets = districViewSetGets;
            this.context = context;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.distric_row_view, viewGroup, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
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
                   // Toast.makeText(context,"NAME:"+" "+disName,Toast.LENGTH_SHORT).show();
                    builder = new AlertDialog.Builder(v.getContext());
                    final AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    final View loginFormView = getLayoutInflater().inflate(R.layout.diologbox, null);
                    Button closeID = (Button) loginFormView.findViewById(R.id.sub);
                    final EditText etsearch = (EditText)loginFormView.findViewById(R.id.etsearch);
                    nofounId = (TextView)loginFormView.findViewById(R.id.nofounId);
                    //tv.setText(Itemname);
                    toolbar = (Toolbar)loginFormView.findViewById(R.id.toolbar);
                    //  toolbar.setTitle(mImageNames.get(i));
                    toolbar.setTitle(disName);
                    //setSupportActionBar(toolbar);
                    toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
                    toolbar.setNavigationOnClickListener(
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    // finish();
                                }
                            }
                    );
                    closeID.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dist = viewHolder.disId.getText().toString();
                            districtv = etsearch.getText().toString().trim();
                          //  Toast.makeText(context,"NAME:"+" "+districtv+" "+dist, Toast.LENGTH_SHORT).show();
                            PINCODECHECK();
                        }
                    });

                    builder.setView(loginFormView);
                    alertDialog = builder.create();
                    alertDialog.show();
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
            private ImageView imageItem;
            private TextView disId;
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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),NavigationDrawerActivity.class);
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
