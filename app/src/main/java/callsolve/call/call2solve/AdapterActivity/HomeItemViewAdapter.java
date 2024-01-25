package callsolve.call.call2solve.AdapterActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import callsolve.call.call2solve.ProductMaster;
import callsolve.call.call2solve.R;
import callsolve.call.call2solve.SetgetActivity.HomeitemViewCatagorySetGet;

public class HomeItemViewAdapter extends RecyclerView.Adapter<HomeItemViewAdapter.ViewHolder> {
    private Context mCtx;
    private List<HomeitemViewCatagorySetGet> homeitemViewCatagorySetGets;
    int listview;
    public HomeItemViewAdapter(Context mCtx, List<HomeitemViewCatagorySetGet> homeitemViewCatagorySetGets) {
        this.mCtx = mCtx;
        this.homeitemViewCatagorySetGets = homeitemViewCatagorySetGets;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item_row_view, parent, false);
        return new ViewHolder(view);

    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        HomeitemViewCatagorySetGet homeitemViewCatagorySetGet = homeitemViewCatagorySetGets.get(position);
        listview = position;
        holder.itemname.setText(String.valueOf(homeitemViewCatagorySetGet.getCatname()));
        holder.catid.setText(String.valueOf(homeitemViewCatagorySetGet.getCatid()));
        //holder.imageItem.setImageDrawable(mCtx.getResources().getDrawable(homeitemViewCatagorySetGet.getImg()));
        Glide.with(mCtx)
                .load(homeitemViewCatagorySetGet.getImg())
                .into(holder.imageItem);
        holder.imageItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String catID = holder.catid.getText().toString();
                String productname = holder.itemname.getText().toString();
                Log.e("CATID", catID);
                Intent intent = new Intent(mCtx, ProductMaster.class);
                Bundle bundle_edit  =   new Bundle();
                bundle_edit.putString("catID",catID);
                bundle_edit.putString("productname",productname);
                intent.putExtras(bundle_edit);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mCtx.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return homeitemViewCatagorySetGets.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView itemname,catid;
        private ImageView imageItem;
        LinearLayout lv;

        public ViewHolder(View view) {
            super(view);
            itemname = (TextView) view.findViewById(R.id.itemname);
            imageItem = (ImageView) view.findViewById(R.id.imageItem);
            catid = (TextView)view.findViewById(R.id.catid);
            lv = (LinearLayout) view.findViewById(R.id.lv);
        }
    }
}
