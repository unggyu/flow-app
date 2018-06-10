package kr.hs.dgsw.flow.view.notice.adapter;

import java.util.ArrayList;

import kr.hs.dgsw.flow.view.notice.model.ResponseNoticeItem;

public interface INoticeAdapterContract {
    interface View {
        void notifyAdapter();
    }

    interface Model {
        void addItems(ArrayList<ResponseNoticeItem> noticeList);
        void clearItems();
    }
}
