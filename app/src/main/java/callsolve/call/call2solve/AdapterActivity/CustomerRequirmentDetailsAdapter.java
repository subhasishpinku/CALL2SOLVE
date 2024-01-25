package callsolve.call.call2solve.AdapterActivity;//package com.example.call2solve.AdapterActivity;
//import android.content.Context;
//import android.graphics.Color;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//import com.example.call2solve.R;
//import com.example.call2solve.SetgetActivity.CustomerRequirmentDetailsSetGet;
//import java.util.List;
//public class CustomerRequirmentDetailsAdapter  extends RecyclerView.Adapter<CustomerRequirmentDetailsAdapter.ViewHolder> {
//    private Context mCtx;
//    private List<CustomerRequirmentDetailsSetGet> customerRequirmentDetailsSetGets;
//    public CustomerRequirmentDetailsAdapter(Context mCtx, List<CustomerRequirmentDetailsSetGet> customerRequirmentDetailsSetGets) {
//        this.mCtx = mCtx;
//        this.customerRequirmentDetailsSetGets = customerRequirmentDetailsSetGets;
//    }
//
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_requirmentde, parent, false);
//        return new ViewHolder(view);
//    }
//    @Override
//    public void onBindViewHolder(final ViewHolder holder, int position) {
//        CustomerRequirmentDetailsSetGet customerRequirmentDetailsSetGet = customerRequirmentDetailsSetGets.get(position);
//        holder.dateId.setText(customerRequirmentDetailsSetGet.getDate());
//        holder.monthId.setText(customerRequirmentDetailsSetGet.getMonth());
//        holder.dateId.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                holder.lvColorID.setBackgroundColor(mCtx.getResources().getColor(android.R.color.holo_red_light));
//            }
//        });
//    }
//    @Override
//    public int getItemCount() {
//        return customerRequirmentDetailsSetGets.size();
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//        private TextView dateId,monthId;
//        private LinearLayout lvColorID;
//        public ViewHolder(View view) {
//            super(view);
//            dateId = (TextView) view.findViewById(R.id.dateId);
//            monthId = (TextView) view.findViewById(R.id.monthId);
//            lvColorID = (LinearLayout) view.findViewById(R.id.lvColorID);
//        }
//    }
//}
