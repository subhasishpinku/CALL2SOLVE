package callsolve.call.call2solve.AdapterActivity;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import callsolve.call.call2solve.R;
import callsolve.call.call2solve.SetgetActivity.ServiceChargesetget;

public class ServiceChangeRate extends RecyclerView.Adapter<ServiceChangeRate.ProductViewHolder> {
    private Context mCtx;
    private List<ServiceChargesetget> serviceChargesetgets;
    public ServiceChangeRate(Context mCtx, List<ServiceChargesetget> serviceChargesetgets) {
        this.mCtx = mCtx;
        this.serviceChargesetgets = serviceChargesetgets;
    }
    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.servicechage, null);
        return new ProductViewHolder(view);
    }
    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        //getting the product of the specified position
        ServiceChargesetget serviceChargesetgetss = serviceChargesetgets.get(position);
        //binding the data with the viewholder views
        holder.title.setText(serviceChargesetgetss.getTitle());
        holder.nName.setText(serviceChargesetgetss.getName());
        holder.serviceID.setText(serviceChargesetgetss.getNormalchage());
        holder.noffer.setText(serviceChargesetgetss.getOferrate());

        String offer = serviceChargesetgetss.getOferrate();
        if (offer.equals("0")){
            holder.noffer.setVisibility(View.GONE);
        }
        else {
            holder.noffer.setVisibility(View.VISIBLE);
            holder.serviceID.setPaintFlags(holder.serviceID.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG );
            //holder.serviceID.setText(Color.RED);
            holder.serviceID.setTextColor(Color.parseColor("#FF0000"));
        }
        //holder.breakID.setText(serviceChargesetgetss.getBreakdowenchage());
      //  holder.installID.setText(serviceChargesetgetss.getInstallcharge());

    }
    @Override
    public int getItemCount() {
        return serviceChargesetgets.size();
    }
    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView title,serviceID,breakID,installID,noffer,boffer,ioffer,nName,bName,iName;
        public ProductViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            nName = itemView.findViewById(R.id.nName);
            serviceID = itemView.findViewById(R.id.serviceID);
          //  breakID = itemView.findViewById(R.id.breakID);
          //  installID = itemView.findViewById(R.id.installID);
            noffer = itemView.findViewById(R.id.noffer);
           // boffer = itemView.findViewById(R.id.boffer);
           // ioffer = itemView.findViewById(R.id.ioffer);
         //   nName = itemView.findViewById(R.id.nName);
          //  bName = itemView.findViewById(R.id.bName);
           // iName = itemView.findViewById(R.id.iName);
        }
    }
}