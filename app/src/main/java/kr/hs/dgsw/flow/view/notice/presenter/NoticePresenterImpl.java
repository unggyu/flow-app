package kr.hs.dgsw.flow.view.notice.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.Arrays;

import kr.hs.dgsw.flow.data.realm.login.LoginHelper;
import kr.hs.dgsw.flow.data.realm.user.model.User;
import kr.hs.dgsw.flow.util.retrofit.FlowUtils;
import kr.hs.dgsw.flow.view.notice.adapter.INoticeAdapterContract;
import kr.hs.dgsw.flow.view.notice.listener.OnItemClickListener;
import kr.hs.dgsw.flow.view.notice.model.NoticeResponseBody;
import kr.hs.dgsw.flow.view.notice.model.ResponseData;
import kr.hs.dgsw.flow.view.notice.model.ResponseNoticeItem;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NoticePresenterImpl implements INoticeContract.Presenter, OnItemClickListener {

    private INoticeContract.View mView;

    private INoticeAdapterContract.View mAdapterView;
    private INoticeAdapterContract.Model mAdapterModel;

    private LoginHelper mLoginHelper;

    public NoticePresenterImpl(Context context, @NonNull INoticeContract.View view) {
        mView = view;
        mLoginHelper = new LoginHelper(context);
    }

    @Override
    public void setView(@NonNull INoticeContract.View view) {
        mView = view;
    }

    @Override
    public void onDestroy() {
        mView = null;
    }

    @Override
    public void setNoticeAdapterView(INoticeAdapterContract.View adapterView) {
        mAdapterView = adapterView;
        mAdapterView.setOnClickListener(this);
    }

    @Override
    public void setNoticeAdapterModel(INoticeAdapterContract.Model adapterModel) {
        mAdapterModel = adapterModel;
    }

    @Override
    public void loadItems(final boolean isClear) {
        User loggedUser = mLoginHelper.getLoggedUser();
        mView.showProgress(true);
        Call<NoticeResponseBody> call = FlowUtils.getFlowService().getNotices(loggedUser.getToken());
        call.enqueue(new Callback<NoticeResponseBody>() {
            @Override
            public void onResponse(Call<NoticeResponseBody> call, Response<NoticeResponseBody> response) {
                mView.showProgress(false);
                if (response.isSuccessful() && response.body().getStatus() == 200) {
                    loadItems(response.body().getData(), isClear);
                } else {
                    mView.showMessageToast(
                            response.body().getStatus() + " error: " +response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<NoticeResponseBody> call, Throwable t) {
                mView.showProgress(false);
                mView.showMessageToast(t.getMessage());
            }
        });
    }

    @Override
    public void loadItems(ResponseData responseData, boolean isClear) {
        if (isClear) {
            mAdapterModel.clearItems();
        }

        ArrayList<ResponseNoticeItem> noticeItems =
                new ArrayList<>(Arrays.asList(responseData.getNoticeItems()));

        mAdapterModel.addItems(noticeItems);
        mAdapterView.notifyAdapter();
        // 데이터가 없을 땐 없다고 텍스트뷰를 띄어줌
        mView.showNoneNotice(noticeItems.size() <= 0);
    }

    /**
     * 리스트의 이이템을 클릭했을 시
     * @param position
     */
    @Override
    public void onItemClick(int position) {
        ResponseNoticeItem noticeItem = mAdapterModel.getItem(position);
        mView.navigateToNoticeDetails(noticeItem.getIdx());
    }
}
