package kr.hs.dgsw.flow.view.noticedetails.adapter;

import java.util.ArrayList;

import kr.hs.dgsw.flow.util.retrofit.model.notice.ResponseNoticeFile;
import kr.hs.dgsw.flow.view.noticedetails.listener.OnDownloadButtonClickListener;

public interface IAttachedFileListViewAdapter {
    interface View {
        void notifyAdapter();
        void setOnDownloadButtonClickListener(OnDownloadButtonClickListener listener);
    }

    interface Model {
        void addAll(ArrayList<ResponseNoticeFile> data);
    }
}
