package callsolve.call.call2solve;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

import callsolve.call.call2solve.ApplicationActivity.Application;
import callsolve.call.call2solve.DatabaseActivity.DatabaseHelper;
import callsolve.call.call2solve.SetgetActivity.ProfileSetGet;
import callsolve.call.call2solve.SetgetActivity.User;
import callsolve.call.call2solve.SharePreferance.SharedPrefManager;
import callsolve.call.call2solve.SharePreferance.SharedPrefManagerProfile;
import callsolve.call.call2solve.URL.URLs;

import static callsolve.call.call2solve.DatabaseActivity.DatabaseHelper.TABLE_CUSTOMERID;
import static callsolve.call.call2solve.DatabaseActivity.DatabaseHelper.TABLE_ID;
import static callsolve.call.call2solve.DatabaseActivity.DatabaseHelper.TABLE_NAMECUSTOMER;

public class HomeFragmentDrawer extends Fragment {
    private HomeFragmentDrawerListener drawerListener;
    private SharedPreferences sp;
    private View containerView;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    String imageUrl,username,desig,email;
    private CircularImageView profileImageView;
    private TextView homeID,myProfileID,myorder,aboutus,servies,ratecard,mycash,media,teramcondi,contractus,logoutId,nameTextView;
    String customerName;
    String customerId;
    String customername,customerphnno,customeremail,customeraddress,customerdistrict,customerdistrictname,customerpincode,customerimage;
    String dbid = "1";
    DatabaseHelper helper;
    public HomeFragmentDrawer() {

    }
    public void setDrawerListener(HomeFragmentDrawerListener listener) {
        this.drawerListener = listener;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        sp  =   this.getActivity().getSharedPreferences(Consts.SP_NAME, Context.MODE_PRIVATE);
        View layout = inflater.inflate(R.layout.home_fragment_navigation_drawer, container, false);
        User user = SharedPrefManager.getInstance(getContext()).getUser();
        customerId = String.valueOf(user.getCustomerid());
        Log.e("CustomerID", customerId);
        if (customerId.equals("null")){
            getActivity().finish();
            SharedPrefManagerProfile.getInstance(getContext()).clear();
            SharedPrefManagerProfile.getInstance(getContext()).logout();
        }
        helper = new DatabaseHelper(getContext());
        SQLiteDatabase database = helper.getReadableDatabase();
        database.execSQL( "UPDATE "+TABLE_NAMECUSTOMER +" SET " + TABLE_CUSTOMERID+ " = '"+customerId+"' WHERE "+TABLE_ID+ " = "+dbid);
        FetchProfile(customerId);
      //  ProfileSetGet puser = SharedPrefManagerProfile.getInstance(getContext()).profileSetGet();
       // customerName= String.valueOf(puser.getCustomername());
      //  imageUrl = String.valueOf(puser.getCustomerimage());
       // Log.e("ProfilEName",customerName+" "+imageUrl);
        nameTextView = (TextView)layout.findViewById(R.id.nameTextView);
//        nameTextView.setText(customerName);
        profileImageView=   (CircularImageView) layout.findViewById(R.id.profileImageView);
//        String imageUrl =   sp.getString("USERPHOTO","http://gshandicraftfashion.com/wp-content/themes/sw_chamy/assets/img/no-thumbnail.png");
//        System.out.println("Image Tag- "+imageUrl);
//        ImageLoader imageLoader = Application.getInstance().getImageLoader();
       // profileImageView.setImageUrl(imageUrl,imageLoader);
//        ImageLoader imageLoader = Application.getInstance().getImageLoader();
//        profileImageView.setImageUrl(imageUrl,imageLoader);
        homeID = (TextView) layout.findViewById(R.id.homeID);
        myProfileID =(TextView)layout.findViewById(R.id.myProfileID);
        myorder =(TextView)layout.findViewById(R.id.myorder);
        aboutus =(TextView)layout.findViewById(R.id.aboutus);
        servies = (TextView)layout.findViewById(R.id.servies);
        ratecard = (TextView)layout.findViewById(R.id.ratecard);
        mycash = (TextView)layout.findViewById(R.id.mycash);
        media = (TextView)layout.findViewById(R.id.media);
        teramcondi = (TextView)layout.findViewById(R.id.teramcondi);
        contractus = (TextView)layout.findViewById(R.id.contractus);
        logoutId = (TextView)layout.findViewById(R.id.logoutId);
        homeID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerListener.onDrawerItemSelected(view, Consts.HOME);
                mDrawerLayout.closeDrawer(containerView);
            }
        });
        myProfileID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerListener.onDrawerItemSelected(view, Consts.MYPROFILE);
                mDrawerLayout.closeDrawer(containerView);
            }
        });
        myorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerListener.onDrawerItemSelected(view, Consts.MYORDER);
                mDrawerLayout.closeDrawer(containerView);
            }
        });
        aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerListener.onDrawerItemSelected(view, Consts.MYABOUSUS);
                mDrawerLayout.closeDrawer(containerView);
            }
        });
        servies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerListener.onDrawerItemSelected(view, Consts.MYSERVICE);
                mDrawerLayout.closeDrawer(containerView);
            }
        });
        ratecard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerListener.onDrawerItemSelected(view, Consts.MYRATECARD);
                mDrawerLayout.closeDrawer(containerView);
            }
        });
        mycash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerListener.onDrawerItemSelected(view, Consts.MYRATEUS);
                mDrawerLayout.closeDrawer(containerView);
            }
        });
        media.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerListener.onDrawerItemSelected(view, Consts.MYMEDIA);
                mDrawerLayout.closeDrawer(containerView);
            }
        });
        teramcondi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerListener.onDrawerItemSelected(view, Consts.MYTRAMANDCONDITION);
                mDrawerLayout.closeDrawer(containerView);
            }
        });
        contractus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerListener.onDrawerItemSelected(view, Consts.MYCONTRACT);
                mDrawerLayout.closeDrawer(containerView);
            }
        });
        logoutId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  drawerListener.onDrawerItemSelected(view, Consts.MYLOGOUT);
               // mDrawerLayout.closeDrawer(containerView);
                getActivity().finish();
                SharedPrefManagerProfile.getInstance(getContext()).logout();

            }
        });
        return layout;
    }

    public void setUp(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar) {
        containerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActivity().invalidateOptionsMenu();
                Log.e("onDrawerOpened", "1");
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
                Log.e("onDrawerOpened", "2");
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                toolbar.setAlpha(1 - slideOffset / 2);
                User user = SharedPrefManager.getInstance(getContext()).getUser();
                customerId = String.valueOf(user.getCustomerid());
                Log.e("CustomerID", customerId);
                FetchProfile(customerId);
                Log.e("onDrawerOpened", "3");
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

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
                            nameTextView.setText(customername);
                            ImageLoader imageLoader = Application.getInstance().getImageLoader();
                            profileImageView.setImageUrl(customerimage,imageLoader);
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
    public interface HomeFragmentDrawerListener {
        public void onDrawerItemSelected(View view, int position);
    }
}
