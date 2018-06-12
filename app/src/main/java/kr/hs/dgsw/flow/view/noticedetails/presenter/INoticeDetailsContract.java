package kr.hs.dgsw.flow.view.noticedetails.presenter;

import kr.hs.dgsw.flow.base.BasePresenter;
import kr.hs.dgsw.flow.base.BaseView;

public interface INoticeDetailsContract {
    interface View extends BaseView {
        void showProgress(final boolean show);
        void showWriter(String writer);
        void showWriteDate(String date);
        void showModifyDate(String date);
        void showContent(String content);
    }

    interface Presenter extends BasePresenter<View> {
        void loadNotice(int idx);
    }
}
