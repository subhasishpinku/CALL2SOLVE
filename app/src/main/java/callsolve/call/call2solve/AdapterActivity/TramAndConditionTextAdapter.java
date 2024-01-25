package callsolve.call.call2solve.AdapterActivity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import callsolve.call.call2solve.R;
import callsolve.call.call2solve.SetgetActivity.TramAndConditionText;


public class TramAndConditionTextAdapter extends RecyclerView.Adapter<TramAndConditionTextAdapter.ViewHolder> {
    private Context mCtx;
    private List<TramAndConditionText> tramAndConditionTexts;
    public TramAndConditionTextAdapter(Context mCtx, List<TramAndConditionText> tramAndConditionTexts) {
        this.mCtx = mCtx;
        this.tramAndConditionTexts = tramAndConditionTexts;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tramandcondition, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TramAndConditionText tramAndConditionText = tramAndConditionTexts.get(position);
        holder.tramcondition.setText(tramAndConditionText.getTermcndtn());

    }
    @Override
    public int getItemCount() {
        return tramAndConditionTexts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tramcondition;
        ImageView imageID;
        public ViewHolder(View itemView) {
            super(itemView);
            tramcondition = (TextView)itemView.findViewById(R.id.tramcondition);

        }
    }
}
