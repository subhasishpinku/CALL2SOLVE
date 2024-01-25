package callsolve.call.call2solve;

import android.os.Bundle;
import android.view.WindowManager;
import android.webkit.WebView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class TramandConditionActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.tram_and_condition);
        WebView mywebview = (WebView) findViewById(R.id.webView);
        mywebview.loadUrl("https://call2solv.com/cal2solv/Page/terms_condition1");
    }
}
