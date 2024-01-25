package callsolve.call.call2solve.AdapterActivity;//package com.example.call2solve.AdapterActivity;
//
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v7.widget.RecyclerView;
//        import android.view.LayoutInflater;
//        import android.view.View;
//        import android.view.ViewGroup;
//        import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.bumptech.glide.Glide;
//import com.example.call2solve.ItemViewActivity;
//import com.example.call2solve.R;
//import com.example.call2solve.SetgetActivity.HomeViewSetget;
//
//import java.util.List;
//
//public class HomeViewAdapter extends RecyclerView.Adapter<HomeViewAdapter.ViewHolder> {
//   // private ArrayList<HomeViewSetget> homeViewSetgets;
//    private Context context;
//    private List<HomeViewSetget> homeViewSetgets;
//    int listview = -1;
////    public HomeViewAdapter(Context context,ArrayList<HomeViewSetget> homeViewSetgets) {
////        this.homeViewSetgets = homeViewSetgets;
////        this.context = context;
////    }
//
//    public HomeViewAdapter(Context context, List<HomeViewSetget> homeViewSetgets) {
//        this.homeViewSetgets = homeViewSetgets;
//        this.context = context;
//    }
//
//    @Override
//    public HomeViewAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
//        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.home_row_view, viewGroup, false);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(final HomeViewAdapter.ViewHolder viewHolder, int i) {
//        listview = i;
//        //HomeViewSetget homeViewSetget = homeViewSetgets.get(i);
//       // viewHolder.itemname.setText(homeViewSetgets.get(i).getItemname());
//      //  Picasso.with(context).load(homeViewSetgets.get(i).getItemimageurl()).resize(24, 12).into(viewHolder.imageItem);
//        HomeViewSetget homeViewSetget = homeViewSetgets.get(i);
//        viewHolder.itemname.setText(String.valueOf(homeViewSetget.getItemname()));
//        viewHolder.productId.setText(homeViewSetget.getItemId());
//       // viewHolder.imageItem.setImageDrawable(context.getResources().getDrawable(homeViewSetget.getItemimageurl()));
//        Glide.with(context)
//                .load(homeViewSetget.getItemimageurl())
//                .into(viewHolder.imageItem);
//
//       viewHolder.lv.setOnClickListener(new View.OnClickListener() {
//           @Override
//           public void onClick(View v) {
//               String Itemname = viewHolder.itemname.getText().toString();
//               String prdctid = viewHolder.productId.getText().toString();
//               Intent intent = new Intent(context, ItemViewActivity.class);
//               Bundle bundle_edit  =   new Bundle();
//               bundle_edit.putString("Itemname",Itemname);
//               bundle_edit.putString("prdctid",prdctid);
//               intent.putExtras(bundle_edit);
//               intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//               context.startActivity(intent);
//               Toast.makeText(context,"NAME:"+" "+Itemname,Toast.LENGTH_SHORT).show();
//           }
//       });
//    }
//
//    @Override
//    public int getItemCount() {
//        return homeViewSetgets.size();
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder{
//        private TextView itemname,productId;
//        private ImageView imageItem;
//        LinearLayout lv;
//        public ViewHolder(View view) {
//            super(view);
//
//            itemname = (TextView)view.findViewById(R.id.itemname);
//            imageItem = (ImageView) view.findViewById(R.id.imageItem);
//            lv = (LinearLayout)view.findViewById(R.id.lv);
//            productId = (TextView)view.findViewById(R.id.productId);
//        }
//    }
//
//}