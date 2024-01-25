package callsolve.call.call2solve;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import callsolve.call.call2solve.SetgetActivity.ItemData;
import callsolve.call.call2solve.SetgetActivity.Product;
import callsolve.call.call2solve.SetgetActivity.WorldPopulation;
import callsolve.call.call2solve.URL.URLs;

public class RatecardAcivity extends AppCompatActivity implements Spinner.OnItemSelectedListener {
    private static final String TAG = "RatecardAcivity";
    TextView disNameId,pincode,chdistricID;
    Spinner sp,spp;
    String[] textArray;
    Integer[] imageArray;
    JSONObject jsonobject;
    JSONArray jsonarray;
    ProgressDialog mProgressDialog;
    ArrayList<String> worldlist;
    ArrayList<WorldPopulation> world;
    ArrayList<String> CountryName;
    ArrayList<String> imageArrayy;
    String[] catagory = { "HOME APPLICATION", "KITCHEN APPLIANCES", "LIFESTYLE APPLIANCES"};
    List<Product> productList;
    ProductAdapterView adapterlist;
    RecyclerView recyclerView;
    int listview = -1;
    String Itemname;
    Toolbar toolbar;
    ImageView disImageId;
    String imageUrl,dist,pinid,districtv,disName;
    String catid,catname,img;
    ArrayList<String> catgoryName;
    ArrayList<Product> arrlist = new ArrayList<Product>();
    ArrayList<String> Productname;
    String ProductId ="";
    private JSONArray array;
    String PName,PId;
    String prductid,prductnm,imgg,totsubprodct;
    String imgUrl;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.ratecard_activity);
        initToolBar();
        Itemname ="Rate Card";
        disNameId = (TextView)findViewById(R.id.disNameId);
        pincode = (TextView)findViewById(R.id.pincode);
        chdistricID = (TextView)findViewById(R.id.chdistricID);
        disImageId =(ImageView)findViewById(R.id.disImageId);
        //  sp = (Spinner)findViewById(R.id.sp);
        spp = (Spinner)findViewById(R.id.spp);
        spp.setOnItemSelectedListener(this);
        if(getIntent().hasExtra("imageurl") && getIntent().hasExtra("dist") && getIntent().hasExtra("pinid") && getIntent().hasExtra("districtv") && getIntent().hasExtra("disName")){
            Log.d(TAG, "getIncomingIntent: found intent extras.");
            imageUrl = getIntent().getStringExtra("imageurl");
            dist = getIntent().getStringExtra("dist");
            pinid = getIntent().getStringExtra("pinid");
            districtv = getIntent().getStringExtra("districtv");
            disName = getIntent().getStringExtra("disName");
            Log.e("DataAll"," "+imageUrl+" "+ dist+" "+pinid+" "+districtv+" "+disName);
            disNameId.setText(disName);
            pincode.setText("("+districtv+")");
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.mipmap.ic_launcher_round)
                    .error(R.mipmap.ic_launcher_round);
            Glide.with(this).load(imageUrl).apply(options).into(disImageId);
            //        Glide.with(getApplicationContext())
//                   .load(imageUrl)
//                  .into(disImageId);
        }
        chdistricID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_info = new Intent(getApplicationContext(),ChangeDistricActivity.class);
                startActivity(intent_info);
                overridePendingTransition(R.anim.slide_up_info,R.anim.no_change);
            }
        });

//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, catagory);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spp.setAdapter(adapter);
//        spp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
        catgoryName = new ArrayList<>();
        loadcatagory();
        Productname =new ArrayList<>();
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        productList = new ArrayList<>();
        //  recyclerView.setAdapter(adapterlist);
//        productList.add(
//                new Product(
//                        "AC REPAIR",
//                        R.mipmap.acgrey));
//        productList.add(
//                new Product(
//                        "ELECTRIC WATER PUMP REPAIR",
//                        R.mipmap.waterpumpcon));
//        productList.add(
//                new Product(
//                        "WINDOW FAN REPAIR",
//                        R.mipmap.windowfanicon));
//        productList.add(
//                new Product(
//                        "REFRIGERATOR REPAIR",
//                        R.mipmap.fridgegrey));
//        productList.add(
//                new Product(
//                        "MICROWAVE REPAIR",
//                        R.mipmap.microwavegrey));
//        productList.add(
//                new Product(
//                        "WASHING MACHINES REPAIR",
//                        R.mipmap.washingmachineicon));
//        productList.add(
//                new Product(
//                        "AIR COOLER REPAIR",
//                        R.mipmap.coolericon));
//        productList.add(
//                new Product(
//                        "AIR PURIFIER REPAIR",
//                        R.mipmap.airpurifiericon));
//
//        productList.add(
//                new Product(
//                        "WATER HEATER REPAIR",
//                        R.mipmap.waterheatericon));
//        productList.add(
//                new Product(
//                        "VACUME CLEANER REPAIR",
//                        R.mipmap.vacumecleanericon));
//        productList.add(
//                new Product(
//                        "WATER PURIFIER REPAIR",
//                        R.mipmap.waterpurifiericon));
//        adapterlist = new ProductAdapter(this, productList);
//        recyclerView.setAdapter(adapterlist);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext()
                , recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                listview = position;
                if (listview == 0) {
                    //   Intent intent = new Intent(getApplicationContext(),RateChargeActivity.class);
                    //  startActivity(intent);
                }

            }
            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }


    public void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(Itemname);
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
//    public class SpinnerAdapter extends ArrayAdapter<String> {
//        ArrayList<String> countryName;
//        private Context ctx;
//        private String[] contentArray;
//    //    private ArrayList<String> imageArray;
//        private Integer[] imageArray;
//        private List<WorldPopulation> WorldPopulation;
//
//
//        public SpinnerAdapter(Context context, int resource, String[] objects,
//                              Integer[] imageArray) {
//            super(context,  R.layout.spinername, R.id.disnameID, objects);
//            this.ctx = context;
//            this.contentArray = objects;
//            this.imageArray = imageArray;
//        }
//
////        public SpinnerAdapter(Context context, int resource, ArrayList<String> countryName, ArrayList<String> imageArrayy) {
////            super(context,  R.layout.spinername, R.id.disnameID, countryName);
////            this.ctx = context;
////            this.countryName = countryName;
////           this.imageArray = imageArrayy;
////        }
//
//
//        @Override
//        public View getDropDownView(int position, View convertView, ViewGroup parent) {
//            return getCustomView(position, convertView, parent);
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            return getCustomView(position, convertView, parent);
//        }
//
//        public View getCustomView(int position, View convertView, ViewGroup parent) {
//
//            LayoutInflater inflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            View row = inflater.inflate(R.layout.spinername, parent, false);
//
//            TextView textView = (TextView) row.findViewById(R.id.disnameID);
//            textView.setText(contentArray[position]);
//
//            ImageView imageView = (ImageView)row.findViewById(R.id.ivFlag);
//          //  imageView.setImageResource(Integer.parseInt(imageArrayy.get(position)));
//            imageView.setImageResource(imageArray[position]);
//
//            return row;
//        }
//    }

//    private class DownloadJSON extends AsyncTask<Void, Void, Void> {
//
//        @Override
//        protected Void doInBackground(Void... params) {
//            // Locate the WorldPopulation Class
//            world = new ArrayList<WorldPopulation>();
//            // Create an array to populate the spinner
//            worldlist = new ArrayList<String>();
//            // JSON file URL address
//            jsonobject = JSONfunctions.getJSONfromURL("https://www.androidbegin.com/tutorial/jsonparsetutorial.txt");
//
//            try {
//                // Locate the NodeList name
//                jsonarray = jsonobject.getJSONArray("worldpopulation");
//                for (int i = 0; i < jsonarray.length(); i++) {
//                    jsonobject = jsonarray.getJSONObject(i);
//                    WorldPopulation worldpop = new WorldPopulation();
//                    worldpop.setRank(jsonobject.optString("rank"));
//                    worldpop.setCountry(jsonobject.optString("country"));
//                    worldpop.setPopulation(jsonobject.optString("population"));
//                    worldpop.setSetFlag(jsonobject.optInt("flag"));
//                    world.add(worldpop);
//                    worldlist.add(jsonobject.optString("country"));
//                }
//            } catch (Exception e) {
//                Log.e("Error", e.getMessage());
//                e.printStackTrace();
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void args) {
//            // Locate the spinner in activity_main.xml
//            Spinner mySpinner = (Spinner) findViewById(R.id.sp);
////            mySpinner.setAdapter(new ArrayAdapter<String>(RatecardAcivity.this,
////                            android.R.layout.simple_spinner_dropdown_item,
////                            worldlist));
////            mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
////
////                        @Override
////                        public void onItemSelected(AdapterView<?> arg0,
////                                                   View arg1, int position, long arg3) {
////                            // TODO Auto-generated method stub
////                            // Locate the textviews in activity_main.xml
////                            TextView txtrank = (TextView) findViewById(R.id.rank);
////                            TextView txtcountry = (TextView) findViewById(R.id.country);
////                            TextView txtpopulation = (TextView) findViewById(R.id.population);
////
////                            // Set the text followed by the position
////                            txtrank.setText("Rank : "
////                                    + world.get(position).getRank());
////                            txtcountry.setText("Country : "
////                                    + world.get(position).getCountry());
////                            txtpopulation.setText("Population : "
////                                    + world.get(position).getPopulation());
////                        }
////
////                        @Override
////                        public void onNothingSelected(AdapterView<?> arg0) {
////                            // TODO Auto-generated method stub
////                        }
////                    });
//
//
//        }
//    }

    public void data(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://www.androidbegin.com/tutorial/jsonparsetutorial.txt",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //  progressBar.setVisibility(View.GONE);
                        Log.e("response", response);

                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);
                            JSONArray array = obj.getJSONArray("worldpopulation");
                            Log.e("worldpopulation"," "+array);
                            for (int i = 0;i<array.length();i++){
                                JSONObject jsonObject = array.getJSONObject(i);
                                String rank = jsonObject.getString("rank");
                                String country = jsonObject.getString("country");
                                String population = jsonObject.getString("population");
                                int flag = jsonObject.getInt("flag");
                                Log.e("data"," "+rank+" "+country+" "+population+" "+flag);
                                WorldPopulation worldPopulation = new WorldPopulation();
                                worldPopulation.setRank(rank);
                                worldPopulation.setCountry(country);
                                worldPopulation.setRank(population);
                                worldPopulation.setSetFlag(flag);
                                CountryName.add(country);
                                imageArrayy.add(String.valueOf(flag));
                                //  SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), R.layout.spinername, CountryName, imageArrayy);
                                //  sp.setAdapter(adapter);
                            }

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
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    public void loadcatagory(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.CATAGORY,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("catagory"," "+response);

                        try {
                            array = new JSONArray(response);
                            Log.e("array", " "+array);
                            for (int i = 0; i<array.length(); i++){
                                JSONObject jsonObject = array.getJSONObject(i);
                                Log.e("obj", " "+jsonObject);
                                catid  = jsonObject.getString("cat_id");
                                catname = jsonObject.getString("cat_name");
                                img = jsonObject.getString("img");
                                // Integer Immgg = Integer.parseInt(img);
                                Log.e("RateShowView", catid+" "+catname+" "+img);
                                catgoryName.add(catname);
                                //  ArrayList<ItemData> list=new ArrayList<>();
                                // list.add(new ItemData(catname,img));
                                // SpinnerAdapter adapter=new SpinnerAdapter(getApplicationContext(), R.layout.spinner_layout,R.id.txt,list);
                                //  spp.setAdapter(adapter);
                                //      spp.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, catgoryName));
                            }
                            getdis(array);

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
                return params;

            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
        VolleySingleton volleySingleton = VolleySingleton.getInstance(this);
        stringRequest.setShouldCache(false);
        volleySingleton.addToRequestQueue(stringRequest);

    }
    private void getdis(JSONArray j){
        for(int i=0;i<j.length();i++){
            try {
                JSONObject json = j.getJSONObject(i);
                Productname.add(json.getString(URLs.TAG_NAME));
                Log.e("pname"," "+Productname);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        spp.setAdapter(new ArrayAdapter<String>(RatecardAcivity.this, android.R.layout.simple_spinner_dropdown_item, Productname));
    }

    private String getID(int position){
        try {
            JSONObject json = array.getJSONObject(position);
            ProductId = json.getString(URLs.TAG_ID);
            Log.e("pId"," "+ProductId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ProductId;
    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        PId = getID(position);
        ProductMaster(PId);
        productList.clear();
        PName = spp.getSelectedItem().toString();
        //Toast.makeText(adapterView.getContext(),PName+" "+" "+PId, Toast.LENGTH_LONG).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void ProductMaster(final String PId){
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.PRODUCTMASTER,
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.PINCODEWISECATAGORY,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("product"," "+response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            Log.e("array", " "+array);
                            JSONArray array = obj.getJSONArray("list");
                            for (int i = 0; i<array.length(); i++){
                                JSONObject jsonObject = array.getJSONObject(i);
                                prductid = jsonObject.getString("prduct_id");
                                prductnm = jsonObject.getString("prduct_nm");
                                imgg = jsonObject.getString("img");
                                totsubprodct = jsonObject.getString("tot_sub_prodct");
                                Log.e("procount", " "+prductid+" "+prductnm+" "+img+" "+totsubprodct);
                                productList.add(new Product(
                                        jsonObject.getString("prduct_id"),
                                        jsonObject.getString("prduct_nm"),
                                        jsonObject.getString("img"),
                                        jsonObject.getString("tot_sub_prodct")
                                ));
                                arrlist.addAll(productList);
                            }
                            adapterlist = new ProductAdapterView(getApplicationContext(), productList);
                           recyclerView.setAdapter(adapterlist);
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
               // params.put("c_id", PId);
             //   Log.e("Pid",PId);
                params.put("dist",dist);
                params.put("pin_id",pinid);
                params.put("category_id",PId);
                Log.e("Pid",dist+" "+pinid+" "+PId);
                return params;

            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
        VolleySingleton volleySingleton = VolleySingleton.getInstance(this);
        stringRequest.setShouldCache(false);
        volleySingleton.addToRequestQueue(stringRequest);

    }
    public class ProductAdapterView extends RecyclerView.Adapter<ProductAdapterView.ProductViewHolder> {
        private Context mCtx;
        private List<Product> productList;
        String name;
        public ProductAdapterView(Context mCtx, List<Product> productList) {
            this.mCtx = mCtx;
            this.productList = productList;
        }
        @Override
        public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            //inflating and returning our view holder
            LayoutInflater inflater = LayoutInflater.from(mCtx);
            View view = inflater.inflate(R.layout.product_list, null);
            return new ProductViewHolder(view);
        }
        @Override
        public void onBindViewHolder(final ProductViewHolder holder, int position) {
            //getting the product of the specified position
            final Product product = productList.get(position);
            //binding the data with the viewholder views
            holder.producatnameID.setText(product.getItemname());
            // holder.productImage.setImageDrawable(mCtx.getResources().getDrawable(product.getItemimageurl()));
            holder.pid.setText(product.getPrductid());
            imgUrl = product.getItemimageurl();
            Glide.with(mCtx)
                    .load(product.getItemimageurl())
                    .into(holder.productImage);
            holder.lv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    name = holder.producatnameID.getText().toString();
                    String prdctid = holder.pid.getText().toString();
                   // String imgUrl = image.getText().toString();
                    Toast.makeText(mCtx,name+" "+prdctid,Toast.LENGTH_SHORT).show();
                    if (totsubprodct.equals("1")){
                       // Log.e("code",distid+" "+pincodeid);
                        Intent intent = new Intent(getApplicationContext(), RateChargeActivity.class);
//                        intent.putExtra("imageurl", imgUrl);
                        intent.putExtra("imageurl", product.getItemimageurl());
                        intent.putExtra("dist", dist);
                       // intent.putExtra("pinid", prdctid);
                        intent.putExtra("pinid",pinid);
                        intent.putExtra("prdctid", prdctid);
                        intent.putExtra("disName",disName);
                        intent.putExtra("Itemname",name);
                        intent.putExtra("catID",PId);
                        intent.putExtra("productname",PName);
                        intent.putExtra("districtv",districtv);
                        startActivity(intent);
                    }
                    else {
                        Intent intent = new Intent(getApplicationContext(), RateChargeActivitynew.class);
                        //intent.putExtra("imageurl", imgUrl);
                        intent.putExtra("imageurl", product.getItemimageurl());
                        intent.putExtra("dist", dist);
                       // intent.putExtra("pinid", prdctid);
                        intent.putExtra("pinid",pinid);
                        intent.putExtra("prdctid", prdctid);
                        intent.putExtra("disName",disName);
                        intent.putExtra("Itemname",name);
                        intent.putExtra("catID",PId);
                        intent.putExtra("productname",PName);
                        intent.putExtra("districtv",districtv);
                        startActivity(intent);
                    }


                }
            });
        }
        @Override
        public int getItemCount() {
            return productList.size();
        }
        class ProductViewHolder extends RecyclerView.ViewHolder {

            TextView producatnameID,pid;
            ImageView productImage;
            LinearLayout lv;

            public ProductViewHolder(View itemView) {
                super(itemView);

                producatnameID = itemView.findViewById(R.id.producatnameID);
                productImage = itemView.findViewById(R.id.productImage);
                pid = itemView.findViewById(R.id.pid);
                lv = itemView.findViewById(R.id.lv);
            }
        }
    }


    public class SpinnerAdapter extends ArrayAdapter<ItemData> {
        int groupid;
        Activity context;
        Context applicationContext;
        ArrayList<ItemData> list;
        LayoutInflater inflater;
//        public SpinnerAdapter(Activity context, int groupid, int id, ArrayList<ItemData>
//                list){
//            super(context,id,list);
//            this.list=list;
//            inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            this.groupid=groupid;
        //   }



        public SpinnerAdapter(Context applicationContext, int groupid, int id, ArrayList<ItemData> list) {
            super(applicationContext,id,list);
            this.list=list;
            inflater=(LayoutInflater)applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.groupid=groupid;
        }

        public View getView(int position, View convertView, ViewGroup parent ){
            View itemView=inflater.inflate(groupid,parent,false);
            ImageView imageView=(ImageView)itemView.findViewById(R.id.img);
            //  imageView.setImageResource(list.get(position).getImageId());

            Glide.with(getApplicationContext())
                    .load(list.get(position).getImageId())
                    .into(imageView);
            TextView textView=(TextView)itemView.findViewById(R.id.txt);
            textView.setText(list.get(position).getText());
            return itemView;
        }

        public View getDropDownView(int position, View convertView, ViewGroup
                parent){
            return getView(position,convertView,parent);

        }
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }
        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

    @Override
    public void onBackPressed() {
        Intent intent_info = new Intent(getApplicationContext(),HomeNavigationDrawerActivity.class);
        startActivity(intent_info);
        overridePendingTransition(R.anim.slide_up_info,R.anim.no_change);
    }

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
}