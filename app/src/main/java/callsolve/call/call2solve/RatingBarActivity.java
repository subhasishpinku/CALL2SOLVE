package callsolve.call.call2solve;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import callsolve.call.call2solve.SetgetActivity.ProfileSetGet;
import callsolve.call.call2solve.SetgetActivity.User;
import callsolve.call.call2solve.SharePreferance.SharedPrefManager;
import callsolve.call.call2solve.SharePreferance.SharedPrefManagerProfile;
import callsolve.call.call2solve.URL.URLs;

public class RatingBarActivity extends AppCompatActivity implements View.OnClickListener{
    Toolbar toolbar;
    RatingBar ratingBar;
    EditText reviewId;
    String recodId,jobId;
    Button conbookingId;
    TextView nameId,modelId,tvAmountID,tvNormalID,tvTimeID,tvexperID,btnJobID,manufacter,TnameId,TechId;
    String rcdid,bookid,prdctname,prdcticon,modelno,cmpanynm,chrgtypname,srvcchrg,srvcdate,orderon;
    ImageView imgID,PimgId;
    String customername,customerphnno,customeremail,customeraddress,customerdistrict,customerdistrictname,customerpincode,customerimage,customerId;
    String reatvalue;
    LinearLayout lv1,lv2,lv3,lv4,lv5,lv6,lv7;
    RadioGroup satisfiygpId,behGpId,techontimeGPId,techc2SUniGPID,techresponsiveGPID,techHowbookGPID,techfromwherdidknoGPID;
    RadioButton exsatId,goodsatId,avgsatId,poorsatId;
    RadioButton behextrenId,behVerywellId,behsomewellId,behNotwellId,behNotatwellId;
    RadioButton techyesId,technoId;
    RadioButton techuniYId,techUniNoId;
    RadioButton techextresId,techveryresponId,techsomewhatresId,technoresId,technotallresId;
    RadioButton techthomobiappId,techthouthwebId,techcallHelpNumberId;
    RadioButton techfrienId,techfromfedbackId,techHordingleftId,techyutubId,techotherId;
    String satisfaction,techbehave,ontime,calluniform,responsive,through,from;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.ratingbar_activity);
        ratingBar = (RatingBar)findViewById(R.id.ratingBar);
        reviewId =(EditText) findViewById(R.id.reviewId);
        conbookingId = (Button)findViewById(R.id.conbookingId);
        conbookingId.setOnClickListener(this);
        nameId = (TextView)findViewById(R.id.nameId);
        modelId = (TextView)findViewById(R.id.modelId);
        tvAmountID = (TextView)findViewById(R.id.tvAmountID);
        tvNormalID = (TextView)findViewById(R.id.tvNormalID);
        tvTimeID = (TextView)findViewById(R.id.tvTimeID);
        tvexperID = (TextView)findViewById(R.id.tvexperID);
        btnJobID = (TextView)findViewById(R.id.btnJobID);
        imgID = (ImageView)findViewById(R.id.imgID);
        PimgId = (ImageView)findViewById(R.id.PimgId);
        manufacter = (TextView)findViewById(R.id.manufacter);
        TnameId = (TextView)findViewById(R.id.TnameId);
        TechId = (TextView)findViewById(R.id.TechId);

        satisfiygpId =(RadioGroup)findViewById(R.id.satisfiygpId);
        behGpId =(RadioGroup)findViewById(R.id.behGpId);
        techontimeGPId =(RadioGroup)findViewById(R.id.techontimeGPId);
        techc2SUniGPID =(RadioGroup)findViewById(R.id.techc2SUniGPID);
        techresponsiveGPID =(RadioGroup)findViewById(R.id.techresponsiveGPID);
        techHowbookGPID =(RadioGroup)findViewById(R.id.techHowbookGPID);
        techfromwherdidknoGPID =(RadioGroup)findViewById(R.id.techfromwherdidknoGPID);
        ///////////////////////////////////////////
        exsatId =(RadioButton)findViewById(R.id.exsatId);
        goodsatId =(RadioButton)findViewById(R.id.goodsatId);
        avgsatId =(RadioButton)findViewById(R.id.avgsatId);
        poorsatId =(RadioButton)findViewById(R.id.poorsatId);
        behextrenId =(RadioButton)findViewById(R.id.behextrenId);
        behVerywellId =(RadioButton)findViewById(R.id.behVerywellId);
        behsomewellId =(RadioButton)findViewById(R.id.behsomewellId);

        behNotwellId =(RadioButton)findViewById(R.id.behNotwellId);
        behNotatwellId =(RadioButton)findViewById(R.id.behNotatwellId);
        techyesId =(RadioButton)findViewById(R.id.techyesId);
        technoId =(RadioButton)findViewById(R.id.technoId);
        techuniYId =(RadioButton)findViewById(R.id.techuniYId);
        techUniNoId =(RadioButton)findViewById(R.id.techUniNoId);
        techextresId =(RadioButton)findViewById(R.id.techextresId);

        techveryresponId =(RadioButton)findViewById(R.id.techveryresponId);
        techsomewhatresId =(RadioButton)findViewById(R.id.techsomewhatresId);
        technoresId =(RadioButton)findViewById(R.id.technoresId);
        technotallresId =(RadioButton)findViewById(R.id.technotallresId);
        techthomobiappId =(RadioButton)findViewById(R.id.techthomobiappId);
        techthouthwebId =(RadioButton)findViewById(R.id.techthouthwebId);
        techcallHelpNumberId =(RadioButton)findViewById(R.id.techcallHelpNumberId);

        techfrienId =(RadioButton)findViewById(R.id.techfrienId);
        techfromfedbackId =(RadioButton)findViewById(R.id.techfromfedbackId);
        techHordingleftId =(RadioButton)findViewById(R.id.techHordingleftId);
        techyutubId =(RadioButton)findViewById(R.id.techyutubId);
        techotherId =(RadioButton)findViewById(R.id.techotherId);

        lv1 = (LinearLayout)findViewById(R.id.lv1);
        lv2 = (LinearLayout)findViewById(R.id.lv2);
        lv3 = (LinearLayout)findViewById(R.id.lv3);
        lv4 = (LinearLayout)findViewById(R.id.lv4);
        lv5 = (LinearLayout)findViewById(R.id.lv5);
        lv6 = (LinearLayout)findViewById(R.id.lv6);
        lv7 = (LinearLayout)findViewById(R.id.lv7);
        Intent intent = getIntent();
        recodId = intent.getStringExtra("recodId");
        jobId = intent.getStringExtra("jobId");
        Log.e("Vieww",recodId+" "+jobId);
        User user = SharedPrefManager.getInstance(getApplicationContext()).getUser();
        customerId = String.valueOf(user.getCustomerid());
        Log.e("CustomerID", customerId);
        initToolBar();
        //How much are you satisfied with our service ?
        satisfiygpId.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId== R.id.exsatId){
                    satisfaction = "Excellent";
                    Log.e("SHOWDATAVALUE",satisfaction);
                }
                else if (checkedId== R.id.goodsatId){
                    satisfaction = "Good";
                    Log.e("SHOWDATAVALUE",satisfaction);
                }
                else if (checkedId== R.id.avgsatId){
                    satisfaction = "Average";
                    Log.e("SHOWDATAVALUE",satisfaction);
                }
                else if (checkedId== R.id.poorsatId){
                    satisfaction = "Poor";
                    Log.e("SHOWDATAVALUE",satisfaction);
                }
                else {

                }

            }
        });
//How well did our Technician behave ?
        behGpId.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId== R.id.behextrenId){
                    //techbehave = "Extremely well";
                    techbehave = "ew";
                    Log.e("SHOWDATAVALUE1",techbehave);
                }
                else if (checkedId== R.id.behVerywellId){
                   // techbehave = "Very well";
                    techbehave = "vw";
                    Log.e("SHOWDATAVALUE1",techbehave);
                }
                else if (checkedId== R.id.behsomewellId){
                    //techbehave = "Somewhat well";
                    techbehave = "sw";
                    Log.e("SHOWDATAVALUE1",techbehave);
                }
                else if (checkedId== R.id.behNotwellId){
                    //techbehave = "Not so well";
                    techbehave = "naaw";
                    Log.e("SHOWDATAVALUE1",techbehave);
                }
                else if (checkedId== R.id.behNotatwellId){
                    //techbehave = "Not at all well";
                    techbehave = "nw";
                    Log.e("SHOWDATAVALUE1",techbehave);
                }
                else {

                }

            }
        });
        //Was the technician on time as per the commitment ?
        techontimeGPId.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId== R.id.techyesId){
                    ontime = "y";
                    Log.e("SHOWDATAVALUE2",ontime);
                }
                else if (checkedId== R.id.technoId){
                    ontime = "n";
                    Log.e("SHOWDATAVALUE2",ontime);
                }
                else {

                }

            }
        });
        ///Was the technician wearing Call2Solv Uniform ?
        techc2SUniGPID.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId== R.id.techuniYId){
                    calluniform = "y";
                    Log.e("SHOWDATAVALUE3",calluniform);
                }
                else if (checkedId== R.id.techUniNoId){
                    calluniform = "n";
                    Log.e("SHOWDATAVALUE3",calluniform);
                }


            }
        });
        ////How responsive have the Technician been to your questions or concerns about our services ?
        techresponsiveGPID.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId== R.id.techextresId){
                   // responsive = "Extremely responsive";
                    responsive = "er";
                    Log.e("SHOWDATAVALUE4",responsive);
                }
                else if (checkedId== R.id.techveryresponId){
                   // responsive = "Very responsive";
                    responsive = "vr";
                    Log.e("SHOWDATAVALUE4",responsive);
                }

                else if (checkedId== R.id.techsomewhatresId){
                    //responsive = "Somewhat responsive";
                    responsive = "sr";
                    Log.e("SHOWDATAVALUE4",responsive);
                }
                else if (checkedId== R.id.technoresId){
                   // responsive = "Not so responsive";
                    responsive = "nsr";
                    Log.e("SHOWDATAVALUE4",responsive);
                }
                else if (checkedId== R.id.technotallresId){
                  //  responsive = "Not at all responsive";
                    responsive = "naar";
                    Log.e("SHOWDATAVALUE4",responsive);
                }

            }
        });
        ///How did you book your call ?
        techHowbookGPID.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId== R.id.techthomobiappId){
                   // through = "Through mobile App";
                    through = "tma";
                    Log.e("SHOWDATAVALUE5",through);
                }
                else if (checkedId== R.id.techthouthwebId){
                    //through = "Through Call2Solv Website";
                    through = "tcw";
                    Log.e("SHOWDATAVALUE5",through);
                }

                else if (checkedId== R.id.techcallHelpNumberId){
                   // through = "Calling our Call2Solv Heplline Number";
                    through = "cochn";
                    Log.e("SHOWDATAVALUE5",through);
                }


            }
        });
/////From where did you know about Call2Solv ?
        techfromwherdidknoGPID.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId== R.id.techfrienId){
                   // from = "Through Friends or Relatives";
                    from = "tfor";
                    Log.e("SHOWDATAVALUE6",from);
                }
                else if (checkedId== R.id.techfromfedbackId){
                   // from = "From Facebook";
                    from = "ff";
                    Log.e("SHOWDATAVALUE6",from);
                }
                else if (checkedId== R.id.techHordingleftId){
                  //  from = "Hoardings & Leaflets";
                    from = "hl";
                    Log.e("SHOWDATAVALUE6",from);
                }
                else if (checkedId== R.id.techyutubId){
                    from = "y";
                    Log.e("SHOWDATAVALUE6",from);
                }
                else if (checkedId== R.id.techotherId){
                    from = "o";
                    Log.e("SHOWDATAVALUE6",from);
                }
            }});
        FetchProfile(customerId);
        REATINGBER(recodId);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                reviewId.setText(String.valueOf(v));
                switch ((int) ratingBar.getRating()) {
                    case 1:
                        reviewId.setText("Very bad");
                        break;
                    case 2:
                        reviewId.setText("Need some improvement");
                        break;
                    case 3:
                        reviewId.setText("Good");
                        break;
                    case 4:
                        reviewId.setText("Great");
                        break;
                    case 5:
                        reviewId.setText("Awesome. I love it");
                        break;
                    default:
                        reviewId.setText("");
                }
            }
        });
    }
    @Override
    public void onClick(View view) {
       switch (view.getId()){
           case R.id.conbookingId:
               final String review = reviewId.getText().toString().trim();
               if (TextUtils.isEmpty(review)) {
                   reviewId.setError("Please enter Review");
                   reviewId.requestFocus();
                   return;
               }
               if (review.equals("Very bad")){
                   reatvalue = "1";

                   if (exsatId.isChecked() || goodsatId.isChecked() || avgsatId.isChecked() || poorsatId.isChecked()){
                        if(behextrenId.isChecked() || behVerywellId.isChecked() || behsomewellId.isChecked() || behNotwellId.isChecked() || behNotatwellId.isChecked()){
                            if (techyesId.isChecked() || technoId.isChecked()){
                                if (techuniYId.isChecked() || techUniNoId.isChecked()) {
                                    if (techextresId.isChecked() || techveryresponId.isChecked() || techsomewhatresId.isChecked() || technoresId.isChecked() || technotallresId.isChecked()) {
                                        if (techthomobiappId.isChecked() || techthouthwebId.isChecked() || techcallHelpNumberId.isChecked() ) {
                                            if (techfrienId.isChecked() || techfromfedbackId.isChecked() || techHordingleftId.isChecked() || techyutubId.isChecked() || techotherId.isChecked()) {
                                                AFTERREATINGBAR(recodId,reatvalue,review);
                                            }
                                            else {
                                                Toast.makeText(getApplicationContext(),"Select  From where did you know about Call2Solv ",Toast.LENGTH_SHORT).show();

                                            }
                                        }
                                      else {
                                            Toast.makeText(getApplicationContext(),"Select  How did you book your call ",Toast.LENGTH_SHORT).show();

                                        }

                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(),"Select  How responsive have the Technician been to your questions or concerns about our services",Toast.LENGTH_SHORT).show();

                                    }
                                }
                                else {
                                    Toast.makeText(getApplicationContext(),"Select  Was the technician wearing Call2Solv Uniform",Toast.LENGTH_SHORT).show();

                                }

                            }
                            else {
                                Toast.makeText(getApplicationContext(),"Select Was the technician on time as per the commitment ",Toast.LENGTH_SHORT).show();

                            }
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Select How much are you satisfied with our serviceHow well did our Technician behave",Toast.LENGTH_SHORT).show();

                        }
                   }
                   else {
                       Toast.makeText(getApplicationContext(),"Select How much are you satisfied with our service",Toast.LENGTH_SHORT).show();
                   }

               }
               else if (review.equals("Need some improvement")){
                   reatvalue = "2";
                   //AFTERREATINGBAR(recodId,reatvalue,review);
                   if (exsatId.isChecked() || goodsatId.isChecked() || avgsatId.isChecked() || poorsatId.isChecked()){
                       if(behextrenId.isChecked() || behVerywellId.isChecked() || behsomewellId.isChecked() || behNotwellId.isChecked() || behNotatwellId.isChecked()){
                           if (techyesId.isChecked() || technoId.isChecked()){
                               if (techuniYId.isChecked() || techUniNoId.isChecked()) {
                                   if (techextresId.isChecked() || techveryresponId.isChecked() || techsomewhatresId.isChecked() || technoresId.isChecked() || technotallresId.isChecked()) {
                                       if (techthomobiappId.isChecked() || techthouthwebId.isChecked() || techcallHelpNumberId.isChecked() ) {
                                           if (techfrienId.isChecked() || techfromfedbackId.isChecked() || techHordingleftId.isChecked() || techyutubId.isChecked() || techotherId.isChecked()) {
                                               AFTERREATINGBAR(recodId,reatvalue,review);
                                           }
                                           else {
                                               Toast.makeText(getApplicationContext(),"Select  From where did you know about Call2Solv ",Toast.LENGTH_SHORT).show();

                                           }
                                       }
                                       else {
                                           Toast.makeText(getApplicationContext(),"Select  How did you book your call ",Toast.LENGTH_SHORT).show();

                                       }

                                   }
                                   else {
                                       Toast.makeText(getApplicationContext(),"Select  How responsive have the Technician been to your questions or concerns about our services",Toast.LENGTH_SHORT).show();

                                   }
                               }
                               else {
                                   Toast.makeText(getApplicationContext(),"Select  Was the technician wearing Call2Solv Uniform",Toast.LENGTH_SHORT).show();

                               }

                           }
                           else {
                               Toast.makeText(getApplicationContext(),"Select Was the technician on time as per the commitment ",Toast.LENGTH_SHORT).show();

                           }
                       }
                       else {
                           Toast.makeText(getApplicationContext(),"Select How much are you satisfied with our serviceHow well did our Technician behave",Toast.LENGTH_SHORT).show();

                       }
                   }
                   else {
                       Toast.makeText(getApplicationContext(),"Select How much are you satisfied with our service",Toast.LENGTH_SHORT).show();
                   }
               }
               else if (review.equals("Good")){
                   reatvalue = "3";
                  // AFTERREATINGBAR(recodId,reatvalue,review);
                   if (exsatId.isChecked() || goodsatId.isChecked() || avgsatId.isChecked() || poorsatId.isChecked()){
                       if(behextrenId.isChecked() || behVerywellId.isChecked() || behsomewellId.isChecked() || behNotwellId.isChecked() || behNotatwellId.isChecked()){
                           if (techyesId.isChecked() || technoId.isChecked()){
                               if (techuniYId.isChecked() || techUniNoId.isChecked()) {
                                   if (techextresId.isChecked() || techveryresponId.isChecked() || techsomewhatresId.isChecked() || technoresId.isChecked() || technotallresId.isChecked()) {
                                       if (techthomobiappId.isChecked() || techthouthwebId.isChecked() || techcallHelpNumberId.isChecked() ) {
                                           if (techfrienId.isChecked() || techfromfedbackId.isChecked() || techHordingleftId.isChecked() || techyutubId.isChecked() || techotherId.isChecked()) {
                                               AFTERREATINGBAR(recodId,reatvalue,review);
                                           }
                                           else {
                                               Toast.makeText(getApplicationContext(),"Select  From where did you know about Call2Solv ",Toast.LENGTH_SHORT).show();

                                           }
                                       }
                                       else {
                                           Toast.makeText(getApplicationContext(),"Select  How did you book your call ",Toast.LENGTH_SHORT).show();

                                       }

                                   }
                                   else {
                                       Toast.makeText(getApplicationContext(),"Select  How responsive have the Technician been to your questions or concerns about our services",Toast.LENGTH_SHORT).show();

                                   }
                               }
                               else {
                                   Toast.makeText(getApplicationContext(),"Select  Was the technician wearing Call2Solv Uniform",Toast.LENGTH_SHORT).show();

                               }

                           }
                           else {
                               Toast.makeText(getApplicationContext(),"Select Was the technician on time as per the commitment ",Toast.LENGTH_SHORT).show();

                           }
                       }
                       else {
                           Toast.makeText(getApplicationContext(),"Select How much are you satisfied with our serviceHow well did our Technician behave",Toast.LENGTH_SHORT).show();

                       }
                   }
                   else {
                       Toast.makeText(getApplicationContext(),"Select How much are you satisfied with our service",Toast.LENGTH_SHORT).show();
                   }
               }
               else if (review.equals("Great")){
                   reatvalue = "4";
                  // AFTERREATINGBAR(recodId,reatvalue,review);
                   if (exsatId.isChecked() || goodsatId.isChecked() || avgsatId.isChecked() || poorsatId.isChecked()){
                       if(behextrenId.isChecked() || behVerywellId.isChecked() || behsomewellId.isChecked() || behNotwellId.isChecked() || behNotatwellId.isChecked()){
                           if (techyesId.isChecked() || technoId.isChecked()){
                               if (techuniYId.isChecked() || techUniNoId.isChecked()) {
                                   if (techextresId.isChecked() || techveryresponId.isChecked() || techsomewhatresId.isChecked() || technoresId.isChecked() || technotallresId.isChecked()) {
                                       if (techthomobiappId.isChecked() || techthouthwebId.isChecked() || techcallHelpNumberId.isChecked() ) {
                                           if (techfrienId.isChecked() || techfromfedbackId.isChecked() || techHordingleftId.isChecked() || techyutubId.isChecked() || techotherId.isChecked()) {
                                               AFTERREATINGBAR(recodId,reatvalue,review);
                                           }
                                           else {
                                               Toast.makeText(getApplicationContext(),"Select  From where did you know about Call2Solv ",Toast.LENGTH_SHORT).show();

                                           }
                                       }
                                       else {
                                           Toast.makeText(getApplicationContext(),"Select  How did you book your call ",Toast.LENGTH_SHORT).show();

                                       }

                                   }
                                   else {
                                       Toast.makeText(getApplicationContext(),"Select  How responsive have the Technician been to your questions or concerns about our services",Toast.LENGTH_SHORT).show();

                                   }
                               }
                               else {
                                   Toast.makeText(getApplicationContext(),"Select  Was the technician wearing Call2Solv Uniform",Toast.LENGTH_SHORT).show();

                               }

                           }
                           else {
                               Toast.makeText(getApplicationContext(),"Select Was the technician on time as per the commitment ",Toast.LENGTH_SHORT).show();

                           }
                       }
                       else {
                           Toast.makeText(getApplicationContext(),"Select How much are you satisfied with our serviceHow well did our Technician behave",Toast.LENGTH_SHORT).show();

                       }
                   }
                   else {
                       Toast.makeText(getApplicationContext(),"Select How much are you satisfied with our service",Toast.LENGTH_SHORT).show();
                   }
               }
               else if (review.equals("Awesome. I love it")){
                   reatvalue = "5";
                   //AFTERREATINGBAR(recodId,reatvalue,review);
                   if (exsatId.isChecked() || goodsatId.isChecked() || avgsatId.isChecked() || poorsatId.isChecked()){
                       if(behextrenId.isChecked() || behVerywellId.isChecked() || behsomewellId.isChecked() || behNotwellId.isChecked() || behNotatwellId.isChecked()){
                           if (techyesId.isChecked() || technoId.isChecked()){
                               if (techuniYId.isChecked() || techUniNoId.isChecked()) {
                                   if (techextresId.isChecked() || techveryresponId.isChecked() || techsomewhatresId.isChecked() || technoresId.isChecked() || technotallresId.isChecked()) {
                                       if (techthomobiappId.isChecked() || techthouthwebId.isChecked() || techcallHelpNumberId.isChecked() ) {
                                           if (techfrienId.isChecked() || techfromfedbackId.isChecked() || techHordingleftId.isChecked() || techyutubId.isChecked() || techotherId.isChecked()) {
                                               AFTERREATINGBAR(recodId,reatvalue,review);
                                           }
                                           else {
                                               Toast.makeText(getApplicationContext(),"Select  From where did you know about Call2Solv ",Toast.LENGTH_SHORT).show();

                                           }
                                       }
                                       else {
                                           Toast.makeText(getApplicationContext(),"Select  How did you book your call ",Toast.LENGTH_SHORT).show();

                                       }

                                   }
                                   else {
                                       Toast.makeText(getApplicationContext(),"Select  How responsive have the Technician been to your questions or concerns about our services",Toast.LENGTH_SHORT).show();

                                   }
                               }
                               else {
                                   Toast.makeText(getApplicationContext(),"Select  Was the technician wearing Call2Solv Uniform",Toast.LENGTH_SHORT).show();

                               }

                           }
                           else {
                               Toast.makeText(getApplicationContext(),"Select Was the technician on time as per the commitment ",Toast.LENGTH_SHORT).show();

                           }
                       }
                       else {
                           Toast.makeText(getApplicationContext(),"Select How much are you satisfied with our serviceHow well did our Technician behave",Toast.LENGTH_SHORT).show();

                       }
                   }
                   else {
                       Toast.makeText(getApplicationContext(),"Select How much are you satisfied with our service",Toast.LENGTH_SHORT).show();
                   }
               }
               break;
               default:
       }
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
                            SharedPrefManagerProfile.getInstance(getApplicationContext()).userProfile(profileSetGet);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
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
        VolleySingleton volleySingleton = VolleySingleton.getInstance(getApplicationContext());
        stringRequest.setShouldCache(false);
        volleySingleton.addToRequestQueue(stringRequest);
    }
    public void REATINGBER(final String recodId){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.REATINGBAR,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // progressBar.setVisibility(View.GONE);
                        Log.e("REATINGBAR", response);
                        try {
                            JSONObject object = new JSONObject(response);
                            Log.e("REATING", " "+object);
                            rcdid = object.getString("rcd_id");
                            bookid = object.getString("book_id");
                            prdctname = object.getString("prdct_name");
                            prdcticon = object.getString("prdct_icon");
                            modelno = object.getString("model_no");
                            cmpanynm = object.getString("cmpany_nm");
                            chrgtypname = object.getString("chrg_typ_name");
                            srvcchrg = object.getString("srvc_chrg");
                            srvcdate = object.getString("srvc_date");
                            orderon = object.getString("order_on");
                            String techinicanname = object.getString("tech_name");
                            String techcode = object.getString("tech_code");
                            String techphn = object.getString("tech_phn");
                            String techimg = object.getString("tech_img");
                            Log.e("REAT", " "+rcdid+" "+bookid+" "+prdctname+" "+prdcticon+" "+modelno+" "
                                    +cmpanynm+" "+chrgtypname+" "+srvcchrg+" "+srvcdate+" "+orderon);
                          //  AFTERREATINGBAR(recodId,review);
                            btnJobID.setText("JOB ID :"+bookid);
                            nameId.setText(prdctname);
                            Glide.with(getApplicationContext())
                                    .load(prdcticon)
                                    .into(imgID);
                            modelId.setText("Model No :"+" "+modelno);
                            Glide.with(getApplicationContext())
                                    .load(techimg)
                                    .into(PimgId);
                            tvAmountID.setText(srvcchrg);
                            tvNormalID.setText(" Service Type :"+"  "+chrgtypname);
                            tvTimeID.setText("Date & Time :"+" "+srvcdate);
                            tvexperID.setText(orderon);
                            manufacter.setText("Manufacturer :"+" "+cmpanynm);
                            TnameId.setText("Technician Name :"+" "+techinicanname);
                            TechId.setText("Technician ID :"+" "+techcode);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("b_id", recodId);
                Log.e("RECD",recodId);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton volleySingleton = VolleySingleton.getInstance(getApplicationContext());
        stringRequest.setShouldCache(false);
        volleySingleton.addToRequestQueue(stringRequest);
    }
    public void AFTERREATINGBAR(final String recodId,final String reatvalue,final String review){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.AFTERREATINGBAR,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // progressBar.setVisibility(View.GONE);
                        Log.e("REATINGBAR1", response);
                        try {
                            JSONObject object = new JSONObject(response);
                            Log.e("REATING1", " "+object);
                            String msg = object.getString("msg");
                            Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                            Intent intent_info = new Intent(getApplicationContext(),HomeNavigationDrawerActivity.class);
                            startActivity(intent_info);
                            overridePendingTransition(R.anim.slide_up_info,R.anim.no_change);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("b_id", recodId);
                params.put("book_rate", reatvalue);
                params.put("book_remark", review);

                params.put("satisfaction", satisfaction);
                params.put("tech_behave", techbehave);
                params.put("on_time", ontime);
                params.put("call_uniform", calluniform);
                params.put("responsive", responsive);
                params.put("through", through);
                params.put("from", from);
                Log.e("SHOWDATAVALUE6",recodId+" "
                        +reatvalue+" "+review+" "+satisfaction+" "+techbehave+" "
                        +ontime+" "+calluniform+" "+responsive+" "+through+" "+from);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton volleySingleton = VolleySingleton.getInstance(getApplicationContext());
        stringRequest.setShouldCache(false);
        volleySingleton.addToRequestQueue(stringRequest);
    }
    public void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Rating & Reviews");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }
        );
    }



}
