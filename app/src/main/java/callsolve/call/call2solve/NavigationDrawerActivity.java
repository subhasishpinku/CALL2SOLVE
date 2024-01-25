package callsolve.call.call2solve;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.Toast;
;import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;

import callsolve.call.call2solve.SharePreferance.SharedPrefManagerProfile;

public class NavigationDrawerActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener, View.OnClickListener,
BackButtonHandlerInterface{
    private static String TAG = NavigationDrawerActivity.class.getSimpleName();
    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;
    private SharedPreferences sp;
    ImageButton profileID;
    boolean doubleBackToExitPressedOnce = false;
    private ArrayList<WeakReference<OnBackClickListener>> backClickListenersList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_drawer_main);

        System.out.println("Inside onCreate");

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        profileID  = (ImageButton)findViewById(R.id.profileID);
        profileID.setOnClickListener(this);
//        profileID.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(), "Search action is selected!", Toast.LENGTH_SHORT).show();
//
//            }
//        });

        sp  =   getSharedPreferences(Consts.SP_NAME, Context.MODE_PRIVATE);
        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);
        if (SharedPrefManagerProfile.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, HomeNavigationDrawerActivity.class));
            return;
        }
        // display the first navigation drawer view on app launch
        displayView(Consts.DASBOARD);

    }
    @SuppressLint("MissingPermission")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.profileID:
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
//                ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
//                activityManager.killBackgroundProcesses("callsolve.call.call2solve");
                //android.os.Process.killProcess(android.os.Process.myPid());
               // super.onBackPressed();
                break;
            default:
        }
    }
    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);

        outState.putString("PRE_VAL","RESTRO");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {

        // ignore orientation/keyboard change
        Log.i("tag", "On config changed");
        super.onConfigurationChanged(newConfig);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.loginID) {
            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(intent);
            return true;
        }
        if(id == R.id.RregistrationID){
            Intent intent = new Intent(getApplicationContext(),RegistrationActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }

    private void displayView(int position) {
        Fragment fragment = null;
        String title = getResources().getString(R.string.app_name);
        switch (position) {
            case Consts.PROFILE:

                break;
            case Consts.LOGOUT:
//                SharedPrefManager.getInstance(getApplicationContext()).logout();
//                Intent intent5 = new Intent(this,Login.class);
//                startActivity(intent5);
//                finish();
                break;
            case Consts.DASBOARD:
                title = getResources().getString(R.string.app_name);
                //fragment = new ProductMaster();
                fragment = new HomeItemFragment();
                break;
                case Consts.ABOUTSUS:
                title = getResources().getString(R.string.about_us);
                fragment = new AboutusFragment();
                break;
            case Consts.SERVICE:
                title = getResources().getString(R.string.servies);
                fragment = new ServiceFragment();
                break;
            case Consts.RATECARD:
                title =  getResources().getString(R.string.ratecard);
                fragment = new RatecardFragment();
                break;
            case Consts.MEDIA:
                title =  getResources().getString(R.string.media);
                fragment = new MediaFragment();
                break;
            case Consts.TRAMCONDITION:
               title =  getResources().getString(R.string.tercondition);
               // Intent intent = new Intent(getApplicationContext(),TramandConditionActivity.class);
               // startActivity(intent);
                fragment = new TramandConditionFragment();
                break;
            case Consts.SERVICEPOLICY:
                title =  getResources().getString(R.string.servicepolocy);

                break;
            case Consts.CONTRACTUS:
                title =  getResources().getString(R.string.contractus);
                fragment = new Contractus_Fragment();
                break;
            case Consts.LOGIN:
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                break;
            case Consts.REGES:
                Intent intent1 = new Intent(getApplicationContext(),RegistrationActivity.class);
                startActivity(intent1);
                break;
            default:
                break;
        }
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();
            getSupportActionBar().setTitle(title);

        }
//        if (getSupportFragmentManager().getBackStackEntryCount() > 0){
//            boolean done = getSupportFragmentManager().popBackStackImmediate();
//        }

    }

//    @Override public void onBackPressed() {
//       // Intent intent_info = new Intent(getApplicationContext(),NavigationDrawerActivity.class);
//       // startActivity(intent_info);
//       // overridePendingTransition(R.anim.slide_up_info,R.anim.no_change);
//
//    }

    //    @Override
//    public void onBackPressed() {
//        if (doubleBackToExitPressedOnce) {
//            super.onBackPressed();
//            return;
//        }
//        this.doubleBackToExitPressedOnce = true;
//        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//            doubleBackToExitPressedOnce=false;
////            Intent intent_info = new Intent(getApplicationContext(),NavigationDrawerActivity.class);
////            startActivity(intent_info);
////            overridePendingTransition(R.anim.slide_up_info,R.anim.no_change);
//            }
//        }, 2000);
//    }
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


    @Override
    public void addBackClickListener(OnBackClickListener onBackClickListener) {
        backClickListenersList.add(new WeakReference<>(onBackClickListener));
    }

    @Override
    public void removeBackClickListener(OnBackClickListener onBackClickListener) {
        for (Iterator<WeakReference<OnBackClickListener>> iterator = backClickListenersList.iterator();
             iterator.hasNext();){
            WeakReference<OnBackClickListener> weakRef = iterator.next();
            if (weakRef.get() == onBackClickListener){
                iterator.remove();
            }
        }
    }

    @Override
    public void onBackPressed() {
        if(!fragmentsBackKeyIntercept()){
            super.onBackPressed();
        }
    }

    private boolean fragmentsBackKeyIntercept() {
        boolean isIntercept = false;
        for (WeakReference<OnBackClickListener> weakRef : backClickListenersList) {
            OnBackClickListener onBackClickListener = weakRef.get();
            if (onBackClickListener != null) {
                boolean isFragmIntercept = onBackClickListener.onBackClick();
                if (!isIntercept) isIntercept = isFragmIntercept;
            }
        }
        return isIntercept;
    }
}
