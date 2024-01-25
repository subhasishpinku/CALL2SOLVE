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
import callsolve.call.call2solve.SetgetActivity.Fetchproductsetget;

public class FetchproductAdpter extends RecyclerView.Adapter<FetchproductAdpter.ProductViewHolder> {
    private Context mCtx;
    private List<Fetchproductsetget> fetchproductsetgets;
    public FetchproductAdpter(Context mCtx, List<Fetchproductsetget> fetchproductsetgets) {
        this.mCtx = mCtx;
        this.fetchproductsetgets = fetchproductsetgets;
    }
    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.fetchproduct, null);
        return new ProductViewHolder(view);
    }
    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        Fetchproductsetget fetchproductsetget = fetchproductsetgets.get(position);
        holder.chargename.setText(fetchproductsetget.getChargename());
        holder.mainrate.setText(fetchproductsetget.getMainrate());
        holder.ofrrate.setText(fetchproductsetget.getOfrrate());
        String offer = fetchproductsetget.getOfrrate();
        if (offer.equals("0")){
            holder.ofrrate.setVisibility(View.GONE);
        }
        else {
            holder.ofrrate.setVisibility(View.VISIBLE);
            holder.mainrate.setPaintFlags(holder.mainrate.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG );
           // holder.mainrate.setText(Color.GREEN);
            holder.mainrate.setTextColor(Color.parseColor("#FF0000"));
        }

    }
    @Override
    public int getItemCount() {
        return fetchproductsetgets.size();
    }
    class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView chargename,mainrate,ofrrate;
        public ProductViewHolder(View itemView) {
            super(itemView);
            chargename = itemView.findViewById(R.id.chargename);
            mainrate = itemView.findViewById(R.id.mainrate);
            ofrrate = itemView.findViewById(R.id.ofrrate);

        }
    }

    public void clear() {
        int size = fetchproductsetgets.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                fetchproductsetgets.remove(0);
            }

            notifyItemRangeRemoved(0, size);
        }
    }
}