package callsolve.call.call2solve;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import callsolve.call.call2solve.SharePreferance.SharedPrefManagerProfile;
public class HomeNavigationDrawerActivity extends AppCompatActivity
        implements HomeFragmentDrawer.HomeFragmentDrawerListener, View.OnClickListener,BackButtonHandlerInterface {
    private Toolbar mToolbar;
    private HomeFragmentDrawer drawerFragment;
    ImageButton profileID1;
    private SharedPreferences sp;
    String customerId;
    boolean doubleBackToExitPressedOnce = false;
    private ArrayList<WeakReference<OnBackClickListener>> backClickListenersList = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_navigation_drawer_main);
        System.out.println("Inside onCreate");
        mToolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        profileID1  = (ImageButton)findViewById(R.id.profileID1);
        profileID1.setOnClickListener(this);
        sp  =   getSharedPreferences(Consts.SP_NAME, Context.MODE_PRIVATE);
        drawerFragment = (HomeFragmentDrawer) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer1);
        drawerFragment.setUp(R.id.fragment_navigation_drawer1, (DrawerLayout) findViewById(R.id.drawer_layout1), mToolbar);
        drawerFragment.setDrawerListener(this);
        displayView(Consts.HOME);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.profileID1:
                finish();
                SharedPrefManagerProfile.getInstance(getApplicationContext()).logout();
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
        getMenuInflater().inflate(R.menu.menu_main_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.logoutID) {
            finish();
            SharedPrefManagerProfile.getInstance(getApplicationContext()).logout();
            return true;
        }

//        if(id == R.id.action_search){
//            Toast.makeText(getApplicationContext(), "Search action is selected!", Toast.LENGTH_SHORT).show();
//            return true;
//        }
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
            case Consts.HOME:
                title = getResources().getString(R.string.app_name);
                //fragment = new HomeItemFragment();
                fragment = new HomeItemFragmentHome();
                break;
            case Consts.MYPROFILE:
                title = getResources().getString(R.string.myprofile);
                fragment = new ProfileFragment();
                break;
            case Consts.MYORDER:
                title = getResources().getString(R.string.myorder);
                fragment = new MyorderFragment();
                break;
            case Consts.MYABOUSUS:
                title = getResources().getString(R.string.about_us);
               // fragment = new AboutusFragment();
                fragment = new AboutusFragmentHome();
                break;
            case Consts.MYSERVICE:
                title = getResources().getString(R.string.servies);
                //fragment = new ServiceFragment();
                fragment = new ServiceFragmentHome();
                break;
            case Consts.MYRATECARD:
                title =  getResources().getString(R.string.ratecard);
               // fragment = new RatecardFragment();
                fragment = new RatecardFragmentHome();
                break;
            case Consts.MYRATEUS:
                //title =  getResources().getString(R.string.reatus);
                Intent i = new Intent(android.content.Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://play.google.com/store/apps/details?id=callsolve.call.call2solve&hl=en"));
                startActivity(i);
                break;
            case Consts.MYMEDIA:
                title =  getResources().getString(R.string.media);
                //fragment = new MediaFragment();
                fragment = new MediaFragmentHome();
                break;
            case Consts.MYTRAMANDCONDITION:
                title =  getResources().getString(R.string.tercondition);
                // Intent intent = new Intent(getApplicationContext(),TramandConditionActivity.class);
                // startActivity(intent);
               // fragment = new TramandConditionFragment();
                fragment = new TramandConditionFragmentHome();
                break;
            case Consts.MYCONTRACT:
                title =  getResources().getString(R.string.contractus);
                //fragment = new Contractus_Fragment();
                fragment = new Contractus_FragmentHome();
                break;
            case Consts.MYLOGOUT:
               // title =  getResources().getString(R.string.logout);
//                finish();
//                SharedPrefManagerProfile.getInstance(getApplicationContext()).logout();
//                break;
                default:
                    break;
        }
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body1, fragment);
            fragmentTransaction.commit();
            // set the toolbar title
            getSupportActionBar().setTitle(title);
        }
    }

//    @Override
//    public void onBackPressed() {
//        Intent intent_info = new Intent(getApplicationContext(),HomeNavigationDrawerActivity.class);
//        startActivity(intent_info);
//        overridePendingTransition(R.anim.slide_up_info,R.anim.no_change);
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
