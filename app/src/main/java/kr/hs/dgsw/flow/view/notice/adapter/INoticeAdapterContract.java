package kr.hs.dgsw.flow.view.notice.adapter;

import java.util.ArrayList;

import kr.hs.dgsw.flow.view.notice.listener.OnItemClickListener;
import kr.hs.dgsw.flow.util.retrofit.model.notice.ResponseNoticeItem;

public interface INoticeAdapterContract {
    interface View {
        void notifyAdapter();
        void setOnClickListener(OnItemClickListener clickListener);
    }

    interface Model {
        void addItems(ArrayList<ResponseNoticeItem> noticeList);
        void clearItems();
        ResponseNoticeItem getItem(int position);
    }
}
