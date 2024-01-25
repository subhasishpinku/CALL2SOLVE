package callsolve.call.call2solve;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import callsolve.call.call2solve.ApplicationActivity.Application;
import callsolve.call.call2solve.SetgetActivity.ProfileSetGet;
import callsolve.call.call2solve.SetgetActivity.User;
import callsolve.call.call2solve.SharePreferance.SharePreferanceSaveData;
import callsolve.call.call2solve.SharePreferance.SharedPrefManager;
import callsolve.call.call2solve.SharePreferance.SharedPrefManagerProfile;
import callsolve.call.call2solve.URL.URLs;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

public class ProfileFragment extends Fragment implements Spinner.OnItemSelectedListener,OnBackClickListener, View.OnClickListener{
    private static final String TAG = "IMAGEUPLOAD";
    String customerId;
    EditText usernameId,contractID,emailID,addressID,stateId,disID,pincodeID;
    String customername,customerphnno,customeremail,customeraddress,customerdistrict,customerdistrictname,customerpincode,customerimage;
  // CircularImageView imageview_account_profile;
    CircularImageView imageview_account_profile;
    Button savedetaildID;
    Spinner spID;
    private JSONArray array;
    ArrayList<String> Disname;
    String  DisId;
    String DName,DId;
    private int PICK_IMAGE_REQUEST = 1;
    private static final int STORAGE_PERMISSION_CODE = 123;
    private Bitmap bitmap;
    private Uri filePath;
    String filePathImg;
    String file_extn = "pinku";
    String url;
    SharedPreferences sp;
    String PROFILE;
    String PROFILEE = "1";
    int screenWidth, screenHeight ;
    FloatingActionButton floatingActionButton,cameraID;
    public static final String UPLOAD_KEY = "tmp_name";
    private File actualImage;
   // Context context = null;
    private static final int Image_Capture_Code = 2;
    private BackButtonHandlerInterface backButtonHandler;
    boolean doubleBackToExitPressedOnce = false;
    private Context context;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        backButtonHandler = (BackButtonHandlerInterface) activity;
        backButtonHandler.addBackClickListener(this);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.profilefragment, container, false);
        usernameId = (EditText)rootView.findViewById(R.id.usernameId);
        contractID = (EditText)rootView.findViewById(R.id.contractID);
        emailID = (EditText)rootView.findViewById(R.id.emailID);
        addressID = (EditText)rootView.findViewById(R.id.addressID);
     // disID = (EditText)rootView.findViewById(R.id.disID);
        pincodeID = (EditText)rootView.findViewById(R.id.pincodeID);
        savedetaildID =(Button) rootView.findViewById(R.id.savedetaildID);
        savedetaildID.setOnClickListener(this);
        imageview_account_profile=   (CircularImageView)rootView.findViewById(R.id.imageview_account_profile);
        floatingActionButton = (FloatingActionButton) rootView.findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(this);
        cameraID = (FloatingActionButton)rootView.findViewById(R.id.cameraID);
        cameraID.setOnClickListener(this);
        spID =(Spinner)rootView.findViewById(R.id.spID);
        spID.setOnItemSelectedListener(this);
        User user = SharedPrefManager.getInstance(getContext()).getUser();
        customerId = String.valueOf(user.getCustomerid());
        Log.e("CustomerID", customerId);
        FetchProfile(customerId);
        sp = getActivity().getSharedPreferences(SharePreferanceSaveData.PROFILE, MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("PROFILE","0");
        edit.commit();
        Disname = new ArrayList<>();
        loadcatagory(customerdistrictname);
        requestStoragePermission();
       // deleteCache(context);
        return rootView;
    }

    @SuppressLint("UnsupportedChromeOsCameraSystemFeature")
    private void takePhotoFromCamera() {
        if (getActivity().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, Image_Capture_Code);
        } else {

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
                            Log.e("Profiledata_Type",customername+" "+customerphnno+" "+customeremail+" "+customeraddress+" "+customerdistrict+" "+customerpincode+" "+customerimage);
                          //  SharedPrefManagerProfile.getInstance(getContext()).clear();
//                            SharedPrefManagerProfile userProfile = SharedPrefManagerProfile.getInstance(getContext());
//                            userProfile.profileSetGet().setCustomername(customername);
//                            userProfile.profileSetGet().setCustomerimage(customerimage);
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
                            SharedPrefManagerProfile.getInstance(getContext()).userProfile(profileSetGet);
                            usernameId.setText(customername);
                            contractID.setText(customerphnno);
                            emailID.setText(customeremail);
                            addressID.setText(customeraddress);
//                          disID.setText(customerdistrictname);
                            pincodeID.setText(customerpincode);
                            ImageLoader imageLoader = Application.getInstance().getImageLoader();
                            imageview_account_profile.setImageUrl(customerimage,imageLoader);
//                            Glide.with(getContext())
//                                    .load(customerimage)
//                                    .into(imageview_account_profile);
                            loadcatagory(customerdistrictname);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
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
        VolleySingleton volleySingleton = VolleySingleton.getInstance(getContext());
        stringRequest.setShouldCache(false);
        volleySingleton.addToRequestQueue(stringRequest);
    }
    public void onActivityResult (int requestCode, int resultCode, Intent data){
//        if (resultCode != RESULT_OK) return;
//        String path = "";
//        Log.e("requestCode", String.valueOf(requestCode));
//        if (requestCode == PICK_IMAGE_REQUEST) {
//            Uri mImageCaptureUri = data.getData();
//            Uri selectedImage = data.getData();
//
//            filePathImg = getPath(selectedImage);
//            url = getPath(selectedImage);
////            Log.e("path", url);
//            path = getRealPathFromURI (mImageCaptureUri);
//            if (path == null) {
//                path = mImageCaptureUri.getPath(); //from File Manager
//            }
//           // url = path;
//        //    Log.e("path", url);
//            // Log.e("file_extn", file_extn);
//           // imageview_account_profile.setImageBitmap(getBitmap(path));
//        }
//        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
//            filePath = data.getData();
//            try {
//                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
//                imageview_account_profile.setImageBitmap(bitmap);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            try {
                actualImage = FileUtil.from(getContext(),data.getData());
              //  Log.e("Path", String.valueOf(actualImage));
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), Uri.fromFile(actualImage));
                imageview_account_profile.setImageBitmap(bitmap);
              //  Uri tempUri = getImageUri(getContext(), bitmap);
               // File finalFile = new File(getRealPathFromURII(tempUri));
              //  actualImage = finalFile;
                String fileImage = String.valueOf(actualImage);
                file_extn = fileImage.substring(fileImage.lastIndexOf("/") + 1);
                //Log.e("file_extnn", file_extn);
                // resizeAndCompressImageBeforeSend(context,fileImage,fileImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
//            if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
//                bitmap = (Bitmap) data.getExtras().get("data");
//                imageview_account_profile.setImageBitmap(bitmap);
//                // setPic();
//                //  galleryAddPic();
//                Log.e("Camera"," "+bitmap);
//            }
        }
        if (requestCode == Image_Capture_Code) {
            if (resultCode == RESULT_OK) {
                bitmap = (Bitmap) data.getExtras().get("data");
                imageview_account_profile.setImageBitmap(bitmap);
                Uri tempUri = getImageUri(getContext(), bitmap);
                File finalFile = new File(getRealPathFromURII(tempUri));
                //Log.e("IMGPATH"," "+finalFile);
                url = String.valueOf(finalFile);
                actualImage = finalFile;
                Log.e("path", String.valueOf(actualImage));
                String fileImage = String.valueOf(actualImage);
                file_extn = fileImage.substring(fileImage.lastIndexOf("/") + 1);
                //Log.e("file_extn", file_extn);
                // resizeAndCompressImageBeforeSend(context,fileImage,fileImage);
            } else if (resultCode == RESULT_CANCELED) {

            }
        }
    }
//    public static String resizeAndCompressImageBeforeSend(Context context,String actualImage,String fileName){
//        final int MAX_IMAGE_SIZE = 700 * 1024;
//        final BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = true;
//        BitmapFactory.decodeFile(actualImage,options);
//        options.inSampleSize = calculateInSampleSize(options, 800, 800);
//        options.inJustDecodeBounds = false;
//        options.inPreferredConfig= Bitmap.Config.ARGB_8888;
//        Bitmap bmpPic = BitmapFactory.decodeFile(actualImage,options);
//        int compressQuality = 100; // quality decreasing by 5 every loop.
//        int streamLength;
//        do{
//            ByteArrayOutputStream bmpStream = new ByteArrayOutputStream();
//            Log.d("compressBitmap", "Quality: " + compressQuality);
//            bmpPic.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpStream);
//            byte[] bmpPicByteArray = bmpStream.toByteArray();
//            streamLength = bmpPicByteArray.length;
//            compressQuality -= 5;
//            Log.d("compressBitmap", "Size: " + streamLength/1024+" kb");
//        }while (streamLength >= MAX_IMAGE_SIZE);
//        try {
//            Log.d("compressBitmap","cacheDir: "+context.getCacheDir());
//            FileOutputStream bmpFile = new FileOutputStream(context.getCacheDir()+fileName);
//            bmpPic.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpFile);
//            bmpFile.flush();
//            bmpFile.close();
//        } catch (Exception e) {
//            Log.e("compressBitmap", "Error on saving file");
//        }
//     return  context.getCacheDir()+fileName;
//    }
//    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
//        String debugTag = "MemoryInformation";
//        final int height = options.outHeight;
//        final int width = options.outWidth;
//        Log.d(debugTag,"image height: "+height+ "---image width: "+ width);
//        int inSampleSize = 1;
//        if (height > reqHeight || width > reqWidth) {
//            final int halfHeight = height / 2;
//            final int halfWidth = width / 2;
//            while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth) {
//                inSampleSize *= 2;
//            }
//        }
//        Log.d(debugTag,"inSampleSize: "+inSampleSize);
//        return inSampleSize;
//    }
//public Bitmap decodeFile(String path) {
//    try {
//        // Decode image size
//        BitmapFactory.Options o = new BitmapFactory.Options();
//        o.inJustDecodeBounds = true;
//        BitmapFactory.decodeFile(path, o);
//        // The new size we want to scale to
//        final int REQUIRED_SIZE = 70;
//
//        // Find the correct scale value. It should be the power of 2.
//        int scale = 1;
//        while (o.outWidth / scale / 2 >= REQUIRED_SIZE && o.outHeight / scale / 2 >= REQUIRED_SIZE)
//            scale *= 2;
//
//        // Decode with inSampleSize
//        BitmapFactory.Options o2 = new BitmapFactory.Options();
//        o2.inSampleSize = scale;
//        return BitmapFactory.decodeFile(path, o2);
//    } catch (Throwable e) {
//        e.printStackTrace();
//    }
//    return null;
//
//}

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
    public String getRealPathFromURII(Uri uri) {
        String path = "";
        if (getContext().getContentResolver() != null) {
            Cursor cursor = getContext().getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                path = cursor.getString(idx);
                cursor.close();
            }
        }
        return path;
    }
//        public String getRealPathFromURI(Uri contentUri) {
//        String[] proj 		= {MediaStore.Images.Media.DATA};
//        Cursor cursor 		= getActivity().managedQuery( contentUri, proj, null, null,null);
//        if (cursor == null) return null;
//        int column_index 	= cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//        cursor.moveToFirst();
//        return cursor.getString(column_index);
//    }
    public String getPath(Uri uri) {
        Cursor cursor = getContext().getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();
        cursor = getContext().getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
//        cursor.moveToFirst();
//        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
//        cursor.close();
//
//        return path;
        cursor.moveToFirst();
        if(cursor.getCount() > 0){
            String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            cursor.close();
            return path;
        }
        return null;
    }
    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) {

        }
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getContext(), "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getContext(), "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }
//    public void uploadMultipart() {
//        String name = "pink";
//        String path = getPath(filePath);
//        try {
//            String uploadId = UUID.randomUUID().toString();
//            new MultipartUploadRequest(getContext(), uploadId, URLs.UPDATEPROFILE)
//                    .addFileToUpload(path, "tmp_name") //Adding file
//                    .addParameter("name", name) //Adding text parameter to the request
//                    .setNotificationConfig(new UploadNotificationConfig())
//                    .setMaxRetries(2)
//                    .startUpload(); //Starting the upload
//
//        } catch (Exception exc) {
//            Toast.makeText(getContext(), exc.getMessage(), Toast.LENGTH_SHORT).show();
//        }
//    }
        private void showFileChooser() {
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);

    }
    @Override
    public void onClick(View view) {
      switch (view.getId()){
          case R.id.floatingActionButton:
              sp = getActivity().getSharedPreferences(SharePreferanceSaveData.PROFILE, MODE_PRIVATE);
              SharedPreferences.Editor edit = sp.edit();
              edit.putString("PROFILE","1");
              edit.commit();
              showFileChooser();
              break;
          case R.id.cameraID:
              sp = getActivity().getSharedPreferences(SharePreferanceSaveData.PROFILE, MODE_PRIVATE);
              SharedPreferences.Editor editt = sp.edit();
              editt.putString("PROFILE","1");
              editt.commit();
              if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ) {
                  ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 110);
              } else {
                  takePhotoFromCamera();
              }
              break;
          case R.id.savedetaildID:
              sp = getActivity().getSharedPreferences(SharePreferanceSaveData.PROFILE, MODE_PRIVATE);
              PROFILE = sp.getString("PROFILE","");
              Log.e("PsaveDat",PROFILE);
//                if (PROFILEE.equals(PROFILE)){
//
//                }
//                else {
//                    Toast.makeText(getContext(),"Select Profile Image",Toast.LENGTH_SHORT).show();
//                }
              UpdateProfile(actualImage);
          break;
          default:
      }
    }
    public void UpdateProfile(File imagePath){
         final String username = usernameId.getText().toString().trim();
         final String contract = contractID.getText().toString().trim();
         final String email = emailID.getText().toString().trim();
         final String address = addressID.getText().toString().trim();
//         final String districID = disID.getText().toString().trim();
         final String pincode = pincodeID.getText().toString().trim();
         if (TextUtils.isEmpty(username)) {
             usernameId.setError("Please enter username");
             usernameId.requestFocus();
             return;
         }
         if (TextUtils.isEmpty(contract)) {
             contractID.setError("Please enter Contract");
             contractID.requestFocus();
             return;
         }
        if (contract.length() < 10){
            contractID.setError("Invalid Contact");
            contractID.requestFocus();
            return;
            }
         if (TextUtils.isEmpty(email)) {
             emailID.setError("Please enter your email");
             emailID.requestFocus();
             return;
         }
         if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
             emailID.setError("Enter a valid email");
             emailID.requestFocus();
             return;
         }
         if (TextUtils.isEmpty(address)) {
             addressID.setError("Enter a Address");
             addressID.requestFocus();
             return;
         }
         if (TextUtils.isEmpty(pincode)) {
             pincodeID.setError("Enter a Pincode");
             pincodeID.requestFocus();
             return;
         }
        if (pincode.length() < 6){
            pincodeID.setError("Invalid Pincode");
            pincodeID.requestFocus();
            return;
        }
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.UPDATEPROFILE,
//                 new Response.Listener<String>() {
//                     @Override
//                     public void onResponse(String response) {
//                         // progressBar.setVisibility(View.GONE);
//                         Log.e("udateProfile", response);
//                         try {
//                             JSONObject obj = new JSONObject(response);
//
//                         } catch (JSONException e) {
//                             e.printStackTrace();
//                         }
//                     }
//                 },
//                 new Response.ErrorListener() {
//                     @Override
//                     public void onErrorResponse(VolleyError error) {
//                         Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
//                     }
//                 }) {
//             @Override
//             protected Map<String, String> getParams() throws AuthFailureError {
//                 Map<String, String> params = new HashMap<>();
//                 params.put("customer_name", username);
//                 params.put("customer_phn_no", contract);
//                 params.put("customer_email", email);
//                 params.put("customer_address", address);
//                 params.put("customer_dist", DId);
//                 params.put("customer_pincode", pincode);
//                 uploadMultipart();
//                 Log.e("Data", username+ " "+contract+" "+email+" "+address+" "+DId+" "+pincode);
//                 return params;
//             }
//         };
//         stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//         VolleySingleton volleySingleton = VolleySingleton.getInstance(getContext());
//         stringRequest.setShouldCache(false);
//         volleySingleton.addToRequestQueue(stringRequest);

        Log.e("DATT",imagePath+" "+file_extn+" "+customerId+" "+username+" "+contract+" "+email+" "+address+" "+DId+" "+pincode);
        AndroidNetworking.upload(URLs.UPDATEPROFILE)
                .addMultipartFile(UPLOAD_KEY,imagePath)
                .addMultipartParameter("name",file_extn)
                .addMultipartParameter("customer_id",customerId)
                .addMultipartParameter("customer_name",username)
                .addMultipartParameter("customer_phn_no",contract)
                .addMultipartParameter("customer_email",email)
                .addMultipartParameter("customer_address",address)
                .addMultipartParameter("customer_dist",DId)
                .addMultipartParameter("customer_pincode",pincode)
                .setTag("uploadTest")
                .setPriority(Priority.HIGH)
                .build()
                .setUploadProgressListener(new UploadProgressListener() {
                    @Override
                    public void onProgress(long bytesUploaded, long totalBytes) {
                        // do anything with progress
                        Log.e(TAG, "onProgress: called..." );
                       // Intent intent = new Intent(getContext(),HomeNavigationDrawerActivity.class);
                       // startActivity(intent);
                    }
                })
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        Log.e(TAG, "onResponse: "+response );
                        String res = String.valueOf(response);
                        Log.e("rex",res);
                        try {
                            JSONObject obj =new JSONObject(res);
                            String msg = obj.getString("msg");
                            if (msg.equals("success")){
                                Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getContext(),HomeNavigationDrawerActivity.class);
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                            }
                           } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Log.e(TAG, "onError: "+error.getErrorDetail() );
                    }
                });

//         class UploadImage extends AsyncTask<Bitmap,Void,String> {
//                ProgressDialog loading;
//                 RequestHandler rh = new RequestHandler();
//                 @Override
//                 protected void onPreExecute() {
//                     super.onPreExecute();
//                     try {
//                         String uploadId = UUID.randomUUID().toString();
//                         new MultipartUploadRequest(getContext(), uploadId,URLs.UPDATEPROFILE)
//                                 .addFileToUpload(String.valueOf(actualImage), UPLOAD_KEY) //Adding file
//                                 .addParameter("name", file_extn) //Adding text parameter to the request
//                                 .addParameter("customer_id", customerId)
//                                 .addParameter("customer_name", username)
//                                 .addParameter("customer_phn_no", contract)
//                                 .addParameter("customer_email", email)
//                                 .addParameter("customer_address", address)
//                                 .addParameter("customer_dist", DId)
//                                 .addParameter("customer_pincode", pincode)
//                                 .setNotificationConfig(new UploadNotificationConfig())
//                                 .setMaxRetries(2)
//                                 .startUpload(); //Starting the upload
//                         Log.e("up",url+" "+file_extn+" "+username+" "+contract+" "+email+" "+address+" "+DId+" "+pincode);
//                         Intent intent = new Intent(getContext(),HomeNavigationDrawerActivity.class);
//                         startActivity(intent);
//                     } catch (Exception exc) {
//
//                     }
//                 }
//                 @Override
//                 protected void onPostExecute(String s) {
//                     super.onPostExecute(s);
////                   loading.dismiss();
//                     if(s!=null){
//
//                     }else{
//
//                     }
//                 }
//                 @Override
//                 protected String doInBackground(Bitmap... params) {
//                     // Bitmap bitmap = params[0];
//                     // String uploadImage = getStringImage(bitmap);
//                     RequestHandler requestHandler = new RequestHandler();
//                     HashMap<String,String> data = new HashMap<>();
//                     data.put(UPLOAD_KEY, String.valueOf(actualImage));
//                     data.put("name", file_extn);
//                     data.put("customer_id",customerId);
//                     data.put("customer_name", username);
//                     data.put("customer_phn_no", contract);
//                     data.put("customer_email", email);
//                     data.put("customer_address", address);
//                     data.put("customer_dist", DId);
//                     data.put("customer_pincode", pincode);
//                     Log.e("upload",actualImage+" "+file_extn+" "+username+" "+contract+" "+email+" "+address+" "+DId+" "+pincode);
//                   //  String result = rh.sendPostRequest(URLs.UPDATEPROFILE,data);
//                   //  return result;
//                     Intent intent = new Intent(getContext(),HomeNavigationDrawerActivity.class);
//                     startActivity(intent);
//                     return requestHandler.sendPostRequest(URLs.UPDATEPROFILE, data);
//
//                 }
//             }
//             UploadImage ui = new UploadImage();
//             ui.execute(bitmap);
    }
//    public static void deleteCache(Context context) {
//        try {
//            File dir = context.getCacheDir();
//            deleteDir(dir);
//        } catch (Exception e) { e.printStackTrace();}
//    }
//
//    public static boolean deleteDir(File dir) {
//        if (dir != null && dir.isDirectory()) {
//            String[] children = dir.list();
//            for (int i = 0; i < children.length; i++) {
//                boolean success = deleteDir(new File(dir, children[i]));
//                if (!success) {
//                    return false;
//                }
//            }
//            return dir.delete();
//        } else if (dir != null && dir.isFile()) {
//            return dir.delete();
//        } else {
//            return false;
//        }
//    }
    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
    public void loadcatagory(final String customerdistrictname){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.DISTRICMASTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("districAllData"," "+response);

                        try {
                            array = new JSONArray(response);
                            Log.e("array", " "+array);
                            for (int i = 0; i<array.length(); i++){
                                JSONObject jsonObject = array.getJSONObject(i);
                                Log.e("obj", " "+jsonObject);
                                String distid = jsonObject.getString("dist_id");
                                String distnm = jsonObject.getString("dist_nm");
                                String distimg = jsonObject.getString("dist_img");
                                Log.e("ShowData", distid+" "+distnm+" "+distimg);
                            }
                            getdis(array,customerdistrictname);

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
        VolleySingleton volleySingleton = VolleySingleton.getInstance(getContext());
        stringRequest.setShouldCache(false);
        volleySingleton.addToRequestQueue(stringRequest);
    }
    private void getdis(JSONArray j,final String customerdistrictname){
        for(int i=0;i<j.length();i++){
            try {
                JSONObject json = j.getJSONObject(i);
                Disname.add(json.getString(URLs.TAG_DIS_NAME));
                Log.e("DisName"," "+Disname);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
       // spID.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, Disname));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, Disname);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spID.setAdapter(adapter);
        String compareValue = customerdistrictname;
           // Log.e("DistricName",customerdistrictname);
        System.out.println("DISTRICNAME"+customerdistrictname);
        if (compareValue != null) {
            int spinnerPosition = adapter.getPosition(compareValue);
            spID.setSelection(spinnerPosition);
        }
    }
    private String getID(int position){
        try {
            JSONObject json = array.getJSONObject(position);
            DisId = json.getString(URLs.TAG__DIS_DI);
            Log.e("DisId"," "+DisId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return DisId;
    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        DId = getID(position);
        DName = spID.getSelectedItem().toString();
        // Toast.makeText(adapterView.getContext(),DName+" "+" "+DId, Toast.LENGTH_LONG).show();
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
    @Override
    public void onDetach() {
        super.onDetach();
        backButtonHandler.removeBackClickListener(this);
        backButtonHandler = null;
    }
    @Override
    public boolean onBackClick() {
        Intent intent_info = new Intent(getContext(),HomeNavigationDrawerActivity.class);
        startActivity(intent_info);
        getActivity().overridePendingTransition(R.anim.slide_up_info,R.anim.no_change);
        return false;
    }

}
