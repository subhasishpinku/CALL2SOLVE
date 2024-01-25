package callsolve.call.call2solve;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
public class InvoiceActivity extends AppCompatActivity implements View.OnClickListener{
    Toolbar toolbar;
    WebView webView;
    WebChromeClient ChromeView;
  //  public static final String url ="http://wbmdfcscholarship.in/aikya_app/";
 // public static final String url ="https://call2solv.com/cal2solv/Page/download_invoice?b_id=";
  public static final String url ="https://www.call2solv.com/cal2solv/Page/download_invoices?b_id=";
    String recodId,jobId;
    RelativeLayout screenshootId;
    RelativeLayout L1;
    ImageView image;
    WebView myWebview;
    boolean isHomePage = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invoice_activity);
        screenshootId =(RelativeLayout)findViewById(R.id.screenshootId);
        screenshootId.setOnClickListener(this);
        L1 = (RelativeLayout) findViewById(R.id.L1);
        initToolBar();
        Intent intent = getIntent();
        recodId = intent.getStringExtra("recodId");
        jobId = intent.getStringExtra("jobId");
        Log.e("Invoice",recodId+" "+jobId);
        webView = (WebView) findViewById(R.id.webView);
//        webView.setWebViewClient(new myWebClient());
//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.loadUrl(url+recodId);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.contains("android_asset")) {
                    isHomePage = false;
                    return false;
                }
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
                return true;
            }
        });

        webView.loadUrl(url+recodId);
    }
    public void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Invoice Details");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back_icon);
        toolbar.setNavigationOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // finish();
                        Intent intent = new Intent(getApplicationContext(),HomeNavigationDrawerActivity.class);
                        startActivity(intent);
                    }
                }
        );
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.screenshootId:
                Intent i = new Intent(android.content.Intent.ACTION_VIEW);
                i.setData(Uri.parse(url+recodId));
                startActivity(i);
                break;
                default:
        }
    }

//    public class myWebClient extends WebViewClient
//    {
//        @Override
//        public void onPageStarted(WebView view, String url, Bitmap favicon) {
//            // TODO Auto-generated method stub
//            super.onPageStarted(view, url, favicon);
//        }
//
//        @Override
//        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            // TODO Auto-generated method stub
//
//            view.loadUrl(url);
//            return true;
//
//        }
//    }
//    // To handle &quot;Back&quot; key press event for WebView to go back to previous screen.
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event)
//    {
//        if ((keyCode == KeyEvent.KEYCODE_BACK)) webView.canGoBack();
//        {
//            webView.goBack();
//            return true;
//        }
//        // return super.onKeyDown(keyCode, event);
//    }
//    @Override
//    public void onBackPressed() {
//        if (webView.canGoBack()) {
//            webView.goBack();
//        } else {
//            super.onBackPressed();
//
//        }
//    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (Integer.parseInt(android.os.Build.VERSION.SDK) > 5
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            Log.d("CDA", "onKeyDown Called");
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onBackPressed() {
        // if not home page go to home page.
        if(isHomePage == false){
            webView.loadUrl(url+recodId);
            isHomePage = true;

            // if home page exit app.
            Log.d("CDA", "onBackPressed");
        }else {
            Log.d("CDA", "onBackPressed Called");
            Intent intent_info = new Intent(getApplicationContext(),HomeNavigationDrawerActivity.class);
            startActivity(intent_info);
            overridePendingTransition(R.anim.slide_up_info,R.anim.no_change);
        }
    }
}
