package kr.hs.dgsw.flow.view.ticket.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import kr.hs.dgsw.flow.R;

public class TicketAdapter
        extends RecyclerView.Adapter<TicketAdapter.ViewHolder> {
    public static final String KEY_TYPE = "type";
    public static final String KEY_STATUS = "status";
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
        View view = LayoutInflater
                .from(mContext)
                .inflate(R.layout.item_ticket, parent, false);
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
        holder.mStatus.setText(outItem.get(KEY_STATUS));
        holder.mOutDateTime.setText(outItem.get(KEY_IN_DATE_TIME));
        holder.mInDateTime.setText(outItem.get(KEY_OUT_DATE_TIME));
        holder.mReason.setText(outItem.get(KEY_REASON));
    }

    @Override
    public int getItemCount() {
        return mOutList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ticket_item_type)
        public TextView mType;

        @BindView(R.id.ticket_item_status)
        public TextView mStatus;

        @BindView(R.id.ticket_item_out_date_time)
        public TextView mOutDateTime;

        @BindView(R.id.ticket_item_in_date_time)
        public TextView mInDateTime;

        @BindView(R.id.ticket_item_reason)
        public TextView mReason;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
