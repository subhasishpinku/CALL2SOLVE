package callsolve.call.call2solve.AdapterActivity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import callsolve.call.call2solve.R;
import callsolve.call.call2solve.SetgetActivity.ServiceSetGet;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ViewHolder> {
    private Context mCtx;
    private List<ServiceSetGet> serviceSetGetList;
    public ServiceAdapter(Context mCtx, List<ServiceSetGet> serviceSetGetList) {
        this.mCtx = mCtx;
        this.serviceSetGetList = serviceSetGetList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_list_service, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ServiceSetGet serviceSetGet = serviceSetGetList.get(position);
        holder.servicenameID.setText(serviceSetGet.getServicename());
        holder.servicetextID.setText(serviceSetGet.getServicetext());
      //  holder.imageID.setImageDrawable(mCtx.getResources().getDrawable(serviceSetGet.getImage()));
        Glide.with(mCtx)
                .load(serviceSetGet.getImage())
                .into(holder.imageID);
    }
    @Override
    public int getItemCount() {
        return serviceSetGetList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
       TextView servicenameID,servicetextID;
       ImageView imageID;
        public ViewHolder(View itemView) {
            super(itemView);
            servicenameID = itemView.findViewById(R.id.servicenameID);
            servicetextID = itemView.findViewById(R.id.servicetextID);
            imageID = itemView.findViewById(R.id.imageID);

        }
    }
}