package callsolve.call.call2solve;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class EntersideActivity extends AppCompatActivity implements View.OnClickListener {
    RelativeLayout bookId,bookIdd;
   // FloatingActionButton  bookId,bookIdd;
    Toolbar toolbar;
    String currentVersion;
    CoordinatorLayout coordinatorLayoutt;
    private int inter = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enterside_activity);
        bookId =(RelativeLayout) findViewById(R.id.bookId);
        bookIdd =(RelativeLayout)findViewById(R.id.bookIdd);
        bookId.setOnClickListener(this);
        bookIdd.setOnClickListener(this);
        bookId.setVisibility(View.GONE);
        bookIdd.setVisibility(View.VISIBLE);
       // coordinatorLayoutt = (CoordinatorLayout)findViewById(R.id.coordinatorLayoutt);
        bookIdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
            }
        });
        if (isNetworkAvailable()){
            try {
                currentVersion = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

            new GetVersionCode().execute();
        }
        else {
            bookIdd.setVisibility(View.VISIBLE);
            final Snackbar snackBar = Snackbar.make(findViewById(android.R.id.content), "No internet connection!", Snackbar.LENGTH_LONG);
            snackBar.setAction("RETRY", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Call your action method here
                    snackBar.dismiss();
                    finish();
                    startActivity(getIntent());
                }
            });
            snackBar.setActionTextColor(Color.RED);
            View sbView = snackBar.getView();
            TextView textView = (TextView) sbView.findViewById(com.google.android.material.R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);
            snackBar.show();
        }
        new  checkconne().execute();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bookId:
                if (isNetworkAvailable()){
                    try {

                        currentVersion = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
                    } catch (PackageManager.NameNotFoundException e) {
                        e.printStackTrace();
                    }

                    new GetVersionCode().execute();
                }
                else {

                }
                break;
            case R.id.bookIdd:
                if (isNetworkAvailable()){
                    Intent intent_info = new Intent(getApplicationContext(), NavigationDrawerActivity.class);
                    startActivity(intent_info);
                    overridePendingTransition(R.anim.slide_up_info, R.anim.no_change);
                }
                else {
                    final Snackbar snackBar = Snackbar.make(findViewById(android.R.id.content), "No internet connection!", Snackbar.LENGTH_LONG);
                    snackBar.setAction("RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Call your action method here
                            snackBar.dismiss();
                            finish();
                            startActivity(getIntent());
                        }
                    });
                    snackBar.setActionTextColor(Color.RED);
                    View sbView = snackBar.getView();
                    TextView textView = (TextView) sbView.findViewById(com.google.android.material.R.id.snackbar_text);
                    textView.setTextColor(Color.YELLOW);
                    snackBar.show();
                }
                break;
                default:
        }
    }

    public boolean isNetworkAvailable() {
        boolean connect=false;
        ConnectivityManager conMgr =  (ConnectivityManager)getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
        if (netInfo == null){
            connect=false;
        }else{
            connect= true;
        }
        return connect;
    }
    class GetVersionCode extends AsyncTask<Void, String, String> {
        @Override
        protected String doInBackground(Void... voids) {

            String newVersion = null;

            try {
                Document document = Jsoup.connect("https://play.google.com/store/apps/details?id=" + EntersideActivity.this.getPackageName()  + "&hl=en")
                        .timeout(1000)
                        .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                        .referrer("http://www.google.com")
                        .get();
                if (document != null) {
                    Elements element = document.getElementsContainingOwnText("Current Version");
                    for (Element ele : element) {
                        if (ele.siblingElements() != null) {
                            Elements sibElemets = ele.siblingElements();
                            for (Element sibElemet : sibElemets) {
                                newVersion = sibElemet.text();
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return newVersion;

        }
        @Override
        protected void onPostExecute(String onlineVersion) {
            super.onPostExecute(onlineVersion);
            if (onlineVersion != null && !onlineVersion.isEmpty()) {
                if (Float.valueOf(currentVersion) < Float.valueOf(onlineVersion)) {
                    if (onlineVersion.equals(currentVersion)) {

                    } else {
                        bookId.setVisibility(View.VISIBLE);
                        bookIdd.setVisibility(View.GONE);
                        AlertDialog alertDialog = new AlertDialog.Builder( EntersideActivity.this).create();
                        alertDialog.setTitle("Update");
                        alertDialog.setIcon(R.mipmap.logo1);
                        alertDialog.setMessage("New Update is available");
                        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Update", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + EntersideActivity.this.getPackageName())));
                                    Log.e("U","U");
                                } catch (android.content.ActivityNotFoundException anfe) {
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + EntersideActivity.this.getPackageName())));
                                    Log.e("U","U1");
                                }
                            }
                        });
                        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                        alertDialog.show();
                    }
                }
                else {
                    bookId.setVisibility(View.GONE);
                    bookIdd.setVisibility(View.VISIBLE);
                    //Intent intent_info = new Intent(getApplicationContext(), NavigationDrawerActivity.class);
                   // startActivity(intent_info);
                   // overridePendingTransition(R.anim.slide_up_info, R.anim.no_change);
                }
            }
            Log.e("update", "Current version " + currentVersion + "playstore version "+" " + onlineVersion);

        }
    }
    class checkconne extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }
        @Override
        protected String doInBackground(String... args) {
            int kk=0;
            try {
                HttpURLConnection urlc = (HttpURLConnection)
                        (new URL("http://clients3.google.com/generate_204")
                                .openConnection());
                urlc.setRequestProperty("User-Agent", "Android");
                urlc.setRequestProperty("Connection", "close");
                urlc.setConnectTimeout(1500);
                urlc.connect();
                kk= urlc.getResponseCode();
            } catch (IOException e) {

                Log.e("qweqwe", "Error checking internet connection", e);
            }
            inter=kk;
            return null;
        }
        @Override
        protected void onPostExecute(String file_url) {
            if (inter == 204){
               // Toast.makeText(EntersideActivity.this, "is connected", Toast.LENGTH_LONG).show();

            }else{
                //Toast.makeText(EntersideActivity.this, "No internet connection", Toast.LENGTH_LONG).show();
                bookIdd.setVisibility(View.VISIBLE);
                final Snackbar snackBar = Snackbar.make(findViewById(android.R.id.content), "No internet connection!", Snackbar.LENGTH_LONG);
                snackBar.setAction("RETRY", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Call your action method here
                        snackBar.dismiss();
                        finish();
                        startActivity(getIntent());
                    }
                });
                snackBar.setActionTextColor(Color.RED);
                View sbView = snackBar.getView();
                TextView textView = (TextView) sbView.findViewById(com.google.android.material.R.id.snackbar_text);
                textView.setTextColor(Color.YELLOW);
                snackBar.show();

            }


        }
    }
}
