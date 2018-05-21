package kr.hs.dgsw.flow.view.ticket.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import kr.hs.dgsw.flow.R;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.ViewHolder> {
    public static final String KEY_TYPE = "type";
    public static final String KEY_IN_DATE_TIME = "in_date_time";
    public static final String KEY_OUT_DATE_TIME = "out_date_time";
    public static final String KEY_REASON = "reason";

    private Context mContext;

    private ArrayList<HashMap<String, String>> mOutList;

    public TicketAdapter(Context context, ArrayList<HashMap<String, String>> outList) {
        if (outList == null) {
            throw new IllegalArgumentException("outList: null error");
        }
        mContext = context;
        mOutList = outList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ticket, null);
        return new ViewHolder(view);
    }

    public void add(HashMap<String, String> outMap) {
        mOutList.add(outMap);
    }

    @Override
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HashMap<String, String> outItem = mOutList.get(position);

        holder.mType.setText(outItem.get(KEY_TYPE));
        holder.mOutDateTime.setText(outItem.get(KEY_IN_DATE_TIME));
        holder.mInDateTime.setText(outItem.get(KEY_OUT_DATE_TIME));
        holder.mReason.setText(outItem.get(KEY_REASON));
    }

    @Override
    public int getItemCount() {
        return mOutList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mType;
        private TextView mOutDateTime;
        private TextView mInDateTime;
        private TextView mReason;
        private CardView mCardView;

        private ViewHolder(View itemView) {
            super(itemView);
            mType = itemView.findViewById(R.id.ticket_item_type);
            mOutDateTime = itemView.findViewById(R.id.ticket_item_out_date_time);
            mInDateTime = itemView.findViewById(R.id.ticket_item_in_date_time);
            mReason = itemView.findViewById(R.id.ticket_item_reason);
            mCardView = itemView.findViewById(R.id.ticket_item);
        }
    }
}
