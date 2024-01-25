package callsolve.call.call2solve.SharePreferance;

import android.content.Context;
import android.content.SharedPreferences;

import callsolve.call.call2solve.SetgetActivity.User;


public class SharedPrefManager {

    //the constants
    private static final String SHARED_PREF_NAME = "call2solve";
    private static final String KEY_CUSTOMERID = "keycustomerid";

    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private SharedPrefManager(Context context) {
        mCtx = context;
    }
    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }
    public void CustomerID(User user) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_CUSTOMERID, user.getCustomerid());
        editor.apply();
    }
    public User getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getString(KEY_CUSTOMERID, null)
        );
    }


}
