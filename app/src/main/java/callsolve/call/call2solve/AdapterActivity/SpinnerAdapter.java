package callsolve.call.call2solve.AdapterActivity;//package com.example.call2solve.AdapterActivity;
//
//import android.app.Activity;
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.bumptech.glide.Glide;
//import com.example.call2solve.R;
//import com.example.call2solve.SetgetActivity.ItemData;
//
//import java.util.ArrayList;
//
//public class SpinnerAdapter extends ArrayAdapter<ItemData> {
//    int groupid;
//    Activity context;
//    Context applicationContext;
//    ArrayList<ItemData> list;
//    LayoutInflater inflater;
////        public SpinnerAdapter(Activity context, int groupid, int id, ArrayList<ItemData>
////                list){
////            super(context,id,list);
////            this.list=list;
////            inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
////            this.groupid=groupid;
//     //   }
//
//
//
//    public SpinnerAdapter(Context applicationContext, int groupid, int id, ArrayList<ItemData> list) {
//        super(applicationContext,id,list);
//        this.list=list;
//        inflater=(LayoutInflater)applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        this.groupid=groupid;
//    }
//
//    public View getView(int position, View convertView, ViewGroup parent ){
//        View itemView=inflater.inflate(groupid,parent,false);
//        ImageView imageView=(ImageView)itemView.findViewById(R.id.img);
//      //  imageView.setImageResource(list.get(position).getImageId());
//
//               Glide.with(applicationContext)
//                   .load(list.get(position).getImageId())
//                  .into(imageView);
//        TextView textView=(TextView)itemView.findViewById(R.id.txt);
//        textView.setText(list.get(position).getText());
//        return itemView;
//    }
//
//    public View getDropDownView(int position, View convertView, ViewGroup
//            parent){
//        return getView(position,convertView,parent);
//
//    }
//}