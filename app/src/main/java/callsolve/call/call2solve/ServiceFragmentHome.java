package callsolve.call.call2solve;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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

import callsolve.call.call2solve.AdapterActivity.ServiceAdapter;
import callsolve.call.call2solve.SetgetActivity.ServiceSetGet;
import callsolve.call.call2solve.URL.URLs;

public class ServiceFragmentHome extends Fragment implements OnBackClickListener{
    RecyclerView rcv;
    List<ServiceSetGet> serviceSetGetList;
    ServiceAdapter adapter;
    ImageView serviceImageID;
    TextView srvice;
    ArrayList<ServiceSetGet> arrlist = new ArrayList<ServiceSetGet>();
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
        View rootView = inflater.inflate(R.layout.service, container, false);
        serviceImageID = (ImageView)rootView.findViewById(R.id.serviceImageID);
        srvice = (TextView)rootView.findViewById(R.id.srvice);
        rcv = (RecyclerView)rootView.findViewById(R.id.rcv);
        rcv.setHasFixedSize(true);
        rcv.setLayoutManager(new LinearLayoutManager(getContext()));
        serviceSetGetList = new ArrayList<>();
        rcv.setAdapter(adapter);
//        serviceSetGetList.add(
//                new ServiceSetGet(
//                        "Home appliances Repair Services",
//                        "We offer services to all home appliances within slot-time at a reasonable cost. Our experts are certified and experienced in troubleshooting all appliances like Air conditioner, Washing machine, Refrigerator, Chimney, Geyser, Gas stove, Gas hub, Air Cooler, Water purifier, TVs, Microwave oven. We provide best of its class service and repairs genuine spare parts are replaced when old ones get damaged. We also provide skilled technicians to install uninstall all the appliances safely.",
//                        R.mipmap.simg));
//        serviceSetGetList.add(
//                new ServiceSetGet(
//                        "Computer services",
//                        "Our goal is customer satisfaction while offering high quality professional repair services at a cost thats affordable and competitive. Whether your computer is not starting, making noise, getting too hot, giving slow performance, Mr. Right provides quality workmanship through its Pan-India network of skilled and verified computer repair professionals. We offer a comprehensive range of computer repair services including laptop not working, laptop getting too hot, laptop display problem, laptop running slow, printer or scanner repair, motherboard repair, BIOS problem, software or anti-virus installation for almost all top brands like Apple, Lenovo, Dell, Asus, HP, Acer.",
//                        R.mipmap.simgg));
//        serviceSetGetList.add(
//                new ServiceSetGet(
//                        "Mobile Repair Services",
//                        "We offer a comprehensive range of mobile repair services including mobile not working, mobile getting too hot, mobile display problem, mobile batter replacement, mobile camera not working, motherboard repair replacement, OS installation, charging port problem, Wi-fi or Bluetooth problem; pretty much any kind of repair work required on your smartphone and normal phones of all most all top brands of mobiles, like Samsung, Apple, Xiaomi, Oppo, OnePlus, Vivo, Asus, Motorola, Nokia, LG etc.",
//                        R.mipmap.simggg));
        SERVICE();
        return rootView;
    }

    public void SERVICE(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.SERVICE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("ratecheck"," "+response);
                        try {
                            JSONArray array = new JSONArray(response);
                            Log.e("array", " "+array);
                            for (int i = 0; i<array.length(); i++) {
                                JSONObject jsonObject = array.getJSONObject(i);
                                String srvc = jsonObject.getString("srvc");
                                String bnrimg = jsonObject.getString("bnr_img");
                                srvice.setText(srvc);
                                Glide.with(getContext())
                                        .load(bnrimg)
                                        .into(serviceImageID);
                                JSONArray jsonArray = jsonObject.getJSONArray("service_img");
                                for (int j =0; j<jsonArray.length(); j++){
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(j);
                                    String srvccatgry = jsonObject1.getString("srvc_catgry");
                                    String srvcdesc = jsonObject1.getString("srvc_desc");
                                    String img = jsonObject1.getString("img");
                                    Log.e("service"," "+srvccatgry+" "+srvcdesc+" "+img);
                                    serviceSetGetList.add(new ServiceSetGet(
                                            jsonObject1.getString("srvc_catgry"),
                                            jsonObject1.getString("srvc_desc"),
                                            jsonObject1.getString("img")
                                    ));
                                    arrlist.addAll(serviceSetGetList);
                                }
                                adapter = new ServiceAdapter(getContext(), serviceSetGetList);
                                rcv.setAdapter(adapter);
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
