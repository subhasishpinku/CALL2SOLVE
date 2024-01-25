package callsolve.call.call2solve.SharePreferance;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import callsolve.call.call2solve.NavigationDrawerActivity;
import callsolve.call.call2solve.SetgetActivity.ProfileSetGet;


public class SharedPrefManagerProfile {
    private static final String SHARED_PREF_NAME = "call2solve";
    private static final String KEY_USERNAME = "keyusername";
    private static final String KEY_USERPHONE = "keyuserphone";
    private static final String KEY_EMAIL = "keyemail";
    private static final String KEY_ADDRESS = "keyaddress";
    private static final String KEY_DISTRIC = "keydistric";
    private static final String KEY_DISTRICNAME = "keydistricname";
    private static final String KEY_PINCODE = "keypincode";
    private static final String KEY_CUSTOMERIMAGE = "keyimage";

    private static SharedPrefManagerProfile mInstance;
    private static Context mCtx;

    private SharedPrefManagerProfile(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManagerProfile getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManagerProfile(context);
        }
        return mInstance;
    }

    public void userProfile(ProfileSetGet profileSetGet) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USERNAME, profileSetGet.getCustomername());
        editor.putString(KEY_USERPHONE, profileSetGet.getCustomerphnno());
        editor.putString(KEY_EMAIL, profileSetGet.getCustomeremail());
        editor.putString(KEY_ADDRESS, profileSetGet.getCustomeraddress());
        editor.putString(KEY_DISTRIC, profileSetGet.getCustomerdistrict());
        editor.putString(KEY_PINCODE, profileSetGet.getCustomerpincode());
        editor.putString(KEY_CUSTOMERIMAGE, profileSetGet.getCustomerimage());
        editor.apply();
    }

    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, null) != null;
    }

    public ProfileSetGet profileSetGet() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new ProfileSetGet(
                sharedPreferences.getString(KEY_USERNAME, null),
                sharedPreferences.getString(KEY_USERPHONE, null),
                sharedPreferences.getString(KEY_EMAIL, null),
                sharedPreferences.getString(KEY_ADDRESS, null),
                sharedPreferences.getString(KEY_DISTRIC, null),
                sharedPreferences.getString(KEY_DISTRICNAME, null),
                sharedPreferences.getString(KEY_PINCODE, null),
                sharedPreferences.getString(KEY_CUSTOMERIMAGE, null)
        );
    }
    public String getUsername(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, null);
    }
    public void clear(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }

    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        mCtx.startActivity(new Intent(mCtx, NavigationDrawerActivity.class));
    }


}
