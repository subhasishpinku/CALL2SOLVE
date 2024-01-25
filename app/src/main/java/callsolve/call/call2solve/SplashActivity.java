package callsolve.call.call2solve;

import android.content.Intent;
import android.os.Handler;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.flashscreen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // This method will be executed once the timer is over
                Intent i = new Intent(SplashActivity.this, FirstPageScreen.class);
                startActivity(i);
                finish();
            }
        }, 2000);
    }
}
