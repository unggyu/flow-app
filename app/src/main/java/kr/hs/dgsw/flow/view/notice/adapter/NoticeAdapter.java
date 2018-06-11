package kr.hs.dgsw.flow.view.notice.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import kr.hs.dgsw.flow.R;
import kr.hs.dgsw.flow.view.notice.model.ResponseNoticeItem;

public class NoticeAdapter
        extends RecyclerView.Adapter<NoticeAdapter.ViewHolder>
        implements INoticeAdapterContract.View, INoticeAdapterContract.Model{

    private Context mContext;

    private ArrayList<ResponseNoticeItem> mNoticeList = new ArrayList<>();

    public NoticeAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(mContext)
                .inflate(R.layout.item_notice, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ResponseNoticeItem noticeItem = mNoticeList.get(position);

        holder.mWriter.setText(noticeItem.getWriter());
        holder.mWriteDate.setText(noticeItem.getWriteDate());
        holder.mModifyDate.setText(noticeItem.getModifyDate());
        holder.mContent.setText(noticeItem.getContent());
    }

    @Override
    public int getItemCount() {
        return mNoticeList.size();
    }

    @Override
    public void notifyAdapter() {
        notifyItemChanged(0, mNoticeList.size());
    }

    @Override
    public void addItems(ArrayList<ResponseNoticeItem> noticeList) {
        mNoticeList.addAll(noticeList);
    }

    @Override
    public void clearItems() {
        mNoticeList.clear();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.notice_writer)
        public TextView mWriter;

        @BindView(R.id.notice_write_date)
        public TextView mWriteDate;

        @BindView(R.id.notice_modify_date)
        public TextView mModifyDate;

        @BindView(R.id.notice_content)
        public TextView mContent;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
