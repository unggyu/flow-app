package kr.hs.dgsw.flow.view.noticedetails.presenter;

import kr.hs.dgsw.flow.base.BasePresenter;
import kr.hs.dgsw.flow.base.BaseView;
import kr.hs.dgsw.flow.view.noticedetails.adapter.IAttachedFileListViewAdapter;

public interface INoticeDetailsContract {
    interface View extends BaseView {
        void showProgress(final boolean show);
        void showWriter(String writer);
        void showWriteDate(String date);
        void showModifyDate(String date);
        void showContent(String content);
        void showAttachedFileToggleButton(final boolean show);
        void showAttachedFileListView(final boolean show);
    }

    interface Presenter extends BasePresenter<View> {
        void setAttachedFileListViewAdapterView(IAttachedFileListViewAdapter.View adapterView);
        void setAttachedFileListViewAdapterModel(IAttachedFileListViewAdapter.Model adapterModel);

        void onAttachedFileToggleButtonCheckedChanged(boolean isChecked);

        void loadNotice(int idx);
    }
}
