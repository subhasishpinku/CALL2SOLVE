package callsolve.call.call2solve;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
import callsolve.call.call2solve.SetgetActivity.ProfileSetGet;
import callsolve.call.call2solve.SetgetActivity.User;
import callsolve.call.call2solve.SharePreferance.SharedPrefManager;
import callsolve.call.call2solve.SharePreferance.SharedPrefManagerProfile;
import callsolve.call.call2solve.URL.URLs;
public class MyorderFragment extends Fragment implements View.OnClickListener,OnBackClickListener{
    RecyclerView recyclerView;
    List<OrderSetGet> orderSetGets;
    private JSONObject object;
    String customerId;
    ArrayList<OrderSetGet> arrlist = new ArrayList<OrderSetGet>();
    OrderViewAdapter adapter;
    String customername,customerphnno,customeremail,customeraddress,customerdistrict,customerdistrictname,customerpincode,customerimage;
    RelativeLayout priviousId;
    private JSONArray jsonArrayy;
    LinearLayout orderViewId;
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
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.myorder_fragment, container, false);
        orderViewId =(LinearLayout)rootView.findViewById(R.id.orderViewId);
        recyclerView = (RecyclerView)rootView.findViewById(R.id.recyclerView);
        priviousId = (RelativeLayout)rootView.findViewById(R.id.priviousId);
        priviousId.setOnClickListener(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        orderSetGets = new ArrayList<>();
        User user = SharedPrefManager.getInstance(getContext()).getUser();
        customerId = String.valueOf(user.getCustomerid());
        Log.e("CustomerID", customerId);
        FetchProfile(customerId);
        Curren_privesorder(customerId);

//        orderSetGets.add(
//                new OrderSetGet("Current Order Status","JOBID: 123456789",R.drawable.fridge,
//                        "Hitachi Air Conditioner","Model No : A/C 45896456",R.drawable.potrait,"500",
//                        "Normal Service Charges",
//                        "Mon, 27th Jun 2019, 12:00 P.M.",
//                        "Expected Date &amp; Time to get service","Wed, 27th May 2019","700"
//                         ));
//
//        orderSetGets.add(
//                new OrderSetGet("Previous Order Status","JOBID: 123456789",R.drawable.fridge,
//                        "Hitachi Air Conditioner","Model No : A/C 45896456",R.drawable.potrait,"500",
//                        "Normal Service Charges",
//                        "Mon, 27th Jun 2019, 12:00 P.M.",
//                        "Expected Date &amp; Time to get service","Wed, 27th May 2019","700"
//                ));
//        OrderViewAdapter adapter = new OrderViewAdapter(getContext(), orderSetGets);
//
//        //setting adapter to recyclerview
//        recyclerView.setAdapter(adapter);
        return rootView;
    }
    public void Curren_privesorder(final String customerId){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.CURRENPRIVEUS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("CURRENTPRIVUES"," "+response);
                        try {
                            object = new JSONObject(response);
                            JSONArray jsonArray = object.getJSONArray("current");
                            Log.e("ARRAY", " "+jsonArray);
                            for (int i = 0; i<jsonArray.length(); i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                orderViewId.setVisibility(View.GONE);
                                String recordid = jsonObject.getString("record_id");
                                String jobid = jsonObject.getString("job_id");
                                String productname = jsonObject.getString("product_name");
                                String producticon = jsonObject.getString("product_icon");
                                String productmodelno = jsonObject.getString("product_model_no");
                                String chrgname = jsonObject.getString("chrg_name");
                                String srvcchrg = jsonObject.getString("srvc_chrg");
                                String srvcdatetime = jsonObject.getString("srvc_date_time");
                                String orderon = jsonObject.getString("order_on");
                                String totchrg = jsonObject.getString("tot_chrg");
                                Log.e("Current_job", recordid+" "+jobid+" "+productname+" "+producticon+" "
                                        +productmodelno+" "+chrgname+" "+srvcchrg+" "+srvcdatetime+" "+orderon+" "+totchrg);

                                orderSetGets.add(new OrderSetGet(
                                        jsonObject.getString("record_id"),
                                        jsonObject.getString("job_id"),
                                        jsonObject.getString("product_name"),
                                        jsonObject.getString("product_icon"),
                                        jsonObject.getString("product_model_no"),
                                        jsonObject.getString("chrg_name"),
                                        jsonObject.getString("srvc_chrg"),
                                        jsonObject.getString("srvc_date_time"),
                                        jsonObject.getString("order_on"),
                                        jsonObject.getString("tot_chrg")
                                ));
                                arrlist.addAll(orderSetGets);
                                adapter = new OrderViewAdapter(getContext(), orderSetGets);
                                recyclerView.setAdapter(adapter);
                            }
//                            jsonArrayy = object.getJSONArray("previous");
//                            Log.e("PriArray"," "+jsonArrayy);
//                            for (int j =0; j<jsonArrayy.length(); j++){
//                                JSONObject object = jsonArrayy.getJSONObject(j);
//                                String recordid = object.getString("record_id");
//                                String jobid = object.getString("job_id");
//                                String productname = object.getString("product_name");
//                                String producticon = object.getString("product_icon");
//                                String productmodelno = object.getString("product_model_no");
//                                String chrgname = object.getString("chrg_name");
//                                String srvcchrg = object.getString("srvc_chrg");
//                                String srvcdatetime = object.getString("srvc_date_time");
//                                String orderon = object.getString("order_on");
//                                String totchrg = object.getString("tot_chrg");
//                                Log.e("privious_job", recordid+" "+jobid+" "+productname+" "+producticon+" "
//                                        +productmodelno+" "+chrgname+" "+srvcchrg+" "+srvcdatetime+" "+orderon+" "+totchrg);
//                            }

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
                params.put("c_id", customerId);
                Log.e("CusID",customerId);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
        VolleySingleton volleySingleton = VolleySingleton.getInstance(getContext());
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
                            JSONObject obj = new JSONObject(response);
                            customername = obj.getString("customer_name");
                            customerphnno = obj.getString("customer_phn_no");
                            customeremail = obj.getString("customer_email");
                            customeraddress = obj.getString("customer_address");
                            customerdistrict = obj.getString("customer_district");
                            customerdistrictname = obj.getString("customer_district_name");
                            customerpincode = obj.getString("customer_pincode");
                            customerimage = obj.getString("customer_image");
                            Log.e("Profiledata",customername+" "+customerphnno+" "+customeremail+" "+customeraddress+" "+customerdistrict+" "+customerpincode+" "+customerimage);
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
                            SharedPrefManagerProfile.getInstance(getContext()).userProfile(profileSetGet);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
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
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.priviousId:
                Intent intent_info = new Intent(getContext(),PriviousorderActivity.class);
                startActivity(intent_info);
                getActivity().overridePendingTransition(R.anim.slide_up_info,R.anim.no_change);
                break;
                default:
        }
    }

    public class OrderViewAdapter extends RecyclerView.Adapter<OrderViewAdapter.ProductViewHolder> {


        //this context we will use to inflate the layout
        private Context mCtx;

        //we are storing all the products in a list
        private List<OrderSetGet> orderSetGets;

        //getting the context and product list with constructor
        public OrderViewAdapter(Context mCtx, List<OrderSetGet> orderSetGets) {
            this.mCtx = mCtx;
            this.orderSetGets = orderSetGets;
        }
        @Override
        public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            //inflating and returning our view holder
            LayoutInflater inflater = LayoutInflater.from(mCtx);
            View view = inflater.inflate(R.layout.myorder_fragment_view, null);
            return new ProductViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ProductViewHolder holder, int position) {
            //getting the product of the specified position
            OrderSetGet orderSetGet = orderSetGets.get(position);
          //  holder.imgID.setImageDrawable(mCtx.getResources().getDrawable(orderSetGet.getImgid()));
            Glide.with(mCtx)
                    .load(orderSetGet.getProducticon())
                    .into(holder.imgID);
//            Glide.with(mCtx)
//                    .load(customerimage)
//                    .into(holder.PimgId);
            String model = "Model No :" +" "+orderSetGet.getProductmodelno();
            String jobid = "JOB ID :"+" " +orderSetGet.getJobid();
            holder.nameId.setText(String.valueOf(orderSetGet.getProductname()));
            holder.modelId.setText(model);
            holder.tvAmountID.setText(String.valueOf(orderSetGet.getSrvcchrg()));
            holder.tvNormalID.setText(String.valueOf(orderSetGet.getSrvcdatetime()));
            holder.oderOnID.setText(String.valueOf(orderSetGet.getOrderon()));
            holder.amountID.setText(String.valueOf(orderSetGet.getTotchrg()));
            holder.recordid.setText(String.valueOf(orderSetGet.getRecordid()));
            holder.jobid.setText(String.valueOf(orderSetGet.getJobid()));
            holder.btnJobID.setText(jobid);
            holder.btnViewID.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String recodId = holder.recordid.getText().toString();
                    String jobId = holder.jobid.getText().toString();
                    Intent intent = new Intent(mCtx, Viewdetails.class);
                    Bundle bundle_edit  =   new Bundle();
                    bundle_edit.putString("recodId",recodId);
                    bundle_edit.putString("jobId",jobId);
                    intent.putExtras(bundle_edit);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mCtx.startActivity(intent);
                }
           });
//            Glide.with(mCtx)
//                    .load(orderSetGet.getImgpro())
//                    .into(holder.PimgId);
            //holder.PimgId.setImageDrawable(mCtx.getResources().getDrawable(orderSetGet.getImgpro()));

           // ImageLoader imageLoader = Application.getInstance().getImageLoader();
           // holder.PimgId.setImageUrl(customerimage,imageLoader);


//            if (status.equals("Current Order Status")){
//                holder.btnRateID.setVisibility(View.GONE);
//            }
//            else {
//                holder.btnRateID.setVisibility(View.VISIBLE);
//            }
//
//            holder.btnRateID.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(getContext(),RatingBarActivity.class);
//                    startActivity(intent);
//
//                }
//            });
//
        }
        @Override
        public int getItemCount() {
            return orderSetGets.size();
        }
        class ProductViewHolder extends RecyclerView.ViewHolder {
            TextView currentID, btnJobID, btnRateID, nameId,modelId,tvAmountID,tvNormalID,tvTimeID,btnViewID,oderOnID,amountID,recordid,jobid;
            Button btnTrackID;
            ImageView imgID,PimgId;
          //  CircularImageView PimgId;
            public ProductViewHolder(View itemView) {
                super(itemView);
                currentID = itemView.findViewById(R.id.currentID);
                btnJobID = itemView.findViewById(R.id.btnJobID);
                btnTrackID = itemView.findViewById(R.id.btnTrackID);
                btnRateID = itemView.findViewById(R.id.btnRateID);
                nameId = itemView.findViewById(R.id.nameId);
                modelId = itemView.findViewById(R.id.modelId);
                imgID  =itemView.findViewById(R.id.imgID);
                tvAmountID = itemView.findViewById(R.id.tvAmountID);
                tvNormalID = itemView.findViewById(R.id.tvNormalID);
                tvTimeID = itemView.findViewById(R.id.tvTimeID);
                btnViewID = itemView.findViewById(R.id.btnViewID);
                oderOnID = itemView.findViewById(R.id.oderOnID);
                amountID = itemView.findViewById(R.id.amountID);
                PimgId = itemView.findViewById(R.id.PimgId);
                recordid = itemView.findViewById(R.id.recordid);
                jobid = itemView.findViewById(R.id.jobid);
            }
        }
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
        Intent intent_info = new Intent(getContext(),HomeNavigationDrawerActivity.class);
        startActivity(intent_info);
        getActivity().overridePendingTransition(R.anim.slide_up_info,R.anim.no_change);
        return false;
    }
}
