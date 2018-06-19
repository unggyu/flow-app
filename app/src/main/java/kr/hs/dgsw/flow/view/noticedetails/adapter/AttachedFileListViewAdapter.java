package kr.hs.dgsw.flow.view.noticedetails.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import kr.hs.dgsw.flow.R;
import kr.hs.dgsw.flow.util.retrofit.model.notice.ResponseNoticeFile;
import kr.hs.dgsw.flow.view.noticedetails.listener.OnDownloadButtonClickListener;

public class AttachedFileListViewAdapter extends BaseAdapter implements IAttachedFileListViewAdapter.Model, IAttachedFileListViewAdapter.View {
    private LayoutInflater mInflater;
    private ArrayList<ResponseNoticeFile> mData = new ArrayList<>();
    private OnDownloadButtonClickListener mOnDownloadButtonClickListener;

    public AttachedFileListViewAdapter(Context context) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public void addAll(ArrayList<ResponseNoticeFile> data) {
        mData.addAll(data);
    }

    @Override
    public void notifyAdapter() {
        notifyDataSetChanged();
    }

    @Override
    public void setOnDownloadButtonClickListener(OnDownloadButtonClickListener listener) {
        mOnDownloadButtonClickListener = listener;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position).getUploadName();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ResponseNoticeFile item = mData.get(position);

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_attached_file, parent, false);

            // 다운로드 버튼 클릭시 이벤트 발생
            ImageView downloadBtn = convertView.findViewById(R.id.attached_file_download);
            downloadBtn.setOnClickListener((view) -> {
                if (mOnDownloadButtonClickListener != null) {
                    mOnDownloadButtonClickListener
                            .onDownloadButtonClick(item.getUploadDir(), item.getUploadName());
                }
            });
        }

        TextView fileNameView = convertView.findViewById(R.id.attached_file_name);
        fileNameView.setText(item.getUploadName());

        return convertView;
    }
}
