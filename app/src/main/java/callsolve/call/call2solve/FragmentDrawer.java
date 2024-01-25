package callsolve.call.call2solve;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.android.volley.toolbox.ImageLoader;

import callsolve.call.call2solve.ApplicationActivity.Application;


public class
FragmentDrawer extends Fragment {

    private static String TAG = FragmentDrawer.class.getSimpleName();

    private TextView  dashboardId,aboutus,servies,ratecard,media,teramcondi,servicepolicy,contractus,loginId,rgesID;
    private CircularImageView profileImageView;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private View containerView;
    private FragmentDrawerListener drawerListener;
    private SharedPreferences sp;
    String imageUrl,username,desig,email;
    public FragmentDrawer() {

    }
    public void setDrawerListener(FragmentDrawerListener listener) {
        this.drawerListener = listener;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        sp  =   this.getActivity().getSharedPreferences(Consts.SP_NAME, Context.MODE_PRIVATE);
        View layout = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        profileImageView=   (CircularImageView) layout.findViewById(R.id.profileImageView);
        dashboardId         =   (TextView) layout.findViewById(R.id.dashboardId);
        aboutus    = (TextView)layout.findViewById(R.id.aboutus);
        servies = (TextView)layout.findViewById(R.id.servies);
        ratecard = (TextView)layout.findViewById(R.id.ratecard);
        media = (TextView)layout.findViewById(R.id.media);
        teramcondi = (TextView)layout.findViewById(R.id.teramcondi);
        servicepolicy = (TextView)layout.findViewById(R.id.servicepolicy);
        contractus = (TextView)layout.findViewById(R.id.contractus);
        loginId = (TextView)layout.findViewById(R.id.loginId);
        rgesID = (TextView)layout.findViewById(R.id.rgesID);
        String imageUrl =   sp.getString("USERPHOTO","https://gshandicraftfashion.com/wp-content/themes/sw_chamy/assets/img/no-thumbnail.png");
        System.out.println("Image Tag- "+imageUrl);
        ImageLoader imageLoader = Application.getInstance().getImageLoader();
        profileImageView.setImageUrl(imageUrl,imageLoader);

        /*// If you are using normal ImageView
        imageLoader.get(imageUrl, new ImageLoader.ImageListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Image Load Error: " + error.getMessage());
                profileImageView.setImageResource(R.drawable.ic_profile);
            }

            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
                if (response.getBitmap() != null) {
                    // load image into imageview
                    profileImageView.setImageBitmap(response.getBitmap());
                }
            }
        });*/

//        nameTextView.setText(sp.getString("NAME","Name"));
//        timeTextView.setText("For "+sp.getString("CUR_TIME","Time"));
//        String[]    tokenSession    =   sp.getString("SESSION","Session # 0").split("#");
//        outletTextView.setText("For "+tokenSession[0]);

//        profileId.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                drawerListener.onDrawerItemSelected(view, Consts.PROFILE);
//                mDrawerLayout.closeDrawer(containerView);
//            }
//        });

//        logoutId.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                drawerListener.onDrawerItemSelected(view, Consts.LOGOUT);
//                mDrawerLayout.closeDrawer(containerView);
//            }
//        });

        dashboardId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerListener.onDrawerItemSelected(view, Consts.DASBOARD);
                mDrawerLayout.closeDrawer(containerView);
            }
        });

        aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerListener.onDrawerItemSelected(view, Consts.ABOUTSUS);
                mDrawerLayout.closeDrawer(containerView);
            }
        });
        servies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerListener.onDrawerItemSelected(view, Consts.SERVICE);
                mDrawerLayout.closeDrawer(containerView);
            }
        });
        ratecard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerListener.onDrawerItemSelected(view, Consts.RATECARD);
                mDrawerLayout.closeDrawer(containerView);
            }
        });
        media.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerListener.onDrawerItemSelected(view, Consts.MEDIA);
                mDrawerLayout.closeDrawer(containerView);
            }
        });
        teramcondi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerListener.onDrawerItemSelected(view, Consts.TRAMCONDITION);
                mDrawerLayout.closeDrawer(containerView);
            }
        });
        servicepolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerListener.onDrawerItemSelected(view, Consts.SERVICEPOLICY);
                mDrawerLayout.closeDrawer(containerView);
            }
        });
        contractus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerListener.onDrawerItemSelected(view, Consts.CONTRACTUS);
                mDrawerLayout.closeDrawer(containerView);
            }
        });
        loginId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerListener.onDrawerItemSelected(view, Consts.LOGIN);
                mDrawerLayout.closeDrawer(containerView);
            }
        });
        rgesID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerListener.onDrawerItemSelected(view, Consts.REGES);
                mDrawerLayout.closeDrawer(containerView);
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
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                toolbar.setAlpha(1 - slideOffset / 2);
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

    public interface FragmentDrawerListener {
        public void onDrawerItemSelected(View view, int position);
    }


}
