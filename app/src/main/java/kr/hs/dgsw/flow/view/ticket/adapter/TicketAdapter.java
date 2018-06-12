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
import kr.hs.dgsw.flow.data.realm.out.model.Out;
import kr.hs.dgsw.flow.view.out.model.body.ResponseOut;

public class TicketAdapter
        extends RecyclerView.Adapter<TicketAdapter.ViewHolder> {
    private Context mContext;

    private ArrayList<Out> mOutList = new ArrayList<>();

    public TicketAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(mContext)
                .inflate(R.layout.item_ticket, parent, false);
        return new ViewHolder(view);
    }

    public void addItems(ArrayList<Out> outList) {
        mOutList.addAll(outList);
    }

    public void add(Out out) {
        mOutList.add(out);
    }

    @Override
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Out outItem = mOutList.get(position);

        holder.mType.setText(outItem.getOutType() == 0 ? "외출" : "외박");
        holder.mStatus.setText(outItem.getStatus() == 0 ?
                "승인 대기중.." : outItem.getStatus() == 1 ? "승인" : "거절");
        holder.mOutDateTime.setText(outItem.getOutDateTime());
        holder.mInDateTime.setText(outItem.getInDateTime());
        holder.mReason.setText(outItem.getReason());
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
