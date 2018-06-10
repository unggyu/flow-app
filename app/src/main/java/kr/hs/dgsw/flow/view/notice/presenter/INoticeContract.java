package kr.hs.dgsw.flow.view.notice.presenter;

import kr.hs.dgsw.flow.base.BasePresenter;
import kr.hs.dgsw.flow.base.BaseView;
import kr.hs.dgsw.flow.view.notice.adapter.INoticeAdapterContract;
import kr.hs.dgsw.flow.view.notice.model.ResponseData;

public interface INoticeContract {
    interface View extends BaseView {
        void showProgress(final boolean show);
    }

    interface Presenter extends BasePresenter<View> {
        void setNoticeAdapterView(INoticeAdapterContract.View adapterView);
        void setNoticeAdapterModel(INoticeAdapterContract.Model adapterModel);

        void loadItems();
        void loadItems(ResponseData responseData, boolean isClear);
    }
}
