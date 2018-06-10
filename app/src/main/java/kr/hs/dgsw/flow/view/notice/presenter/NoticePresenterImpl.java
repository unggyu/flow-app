package kr.hs.dgsw.flow.view.notice.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

import kr.hs.dgsw.flow.data.realm.loginhistory.LoginHistoryHelper;
import kr.hs.dgsw.flow.data.realm.user.model.User;
import kr.hs.dgsw.flow.util.retrofit.FlowUtils;
import kr.hs.dgsw.flow.view.notice.adapter.INoticeAdapterContract;
import kr.hs.dgsw.flow.view.notice.model.NoticeResponseBody;
import kr.hs.dgsw.flow.view.notice.model.ResponseData;
import kr.hs.dgsw.flow.view.notice.model.ResponseNoticeItem;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NoticePresenterImpl implements INoticeContract.Presenter {

    private INoticeContract.View mView;

    private INoticeAdapterContract.View mAdapterView;
    private INoticeAdapterContract.Model mAdapterModel;

    private LoginHistoryHelper mLoginHistoryHelper;

    public NoticePresenterImpl(Context context, @NonNull INoticeContract.View view) {
        mLoginHistoryHelper = new LoginHistoryHelper(context);
        mView = view;
        loadItems();
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
    }

    @Override
    public void setNoticeAdapterModel(INoticeAdapterContract.Model adapterModel) {
        mAdapterModel = adapterModel;
    }

    @Override
    public void loadItems() {
        User loggedUser = mLoginHistoryHelper.getLastLoggedInUser();
        mView.showProgress(true);
        Call<NoticeResponseBody> call = FlowUtils.getFlowService().getNotices(loggedUser.getToken());
        call.enqueue(new Callback<NoticeResponseBody>() {
            @Override
            public void onResponse(Call<NoticeResponseBody> call, Response<NoticeResponseBody> response) {
                if (response.isSuccessful() && response.body().getStatus() == 200) {
                    loadItems(response.body().getData(), false);
                } else {
                    mView.showMessageToast(
                            response.body().getStatus() + " error: " +response.body().getMessage());
                }
                mView.showProgress(false);
            }

            @Override
            public void onFailure(Call<NoticeResponseBody> call, Throwable t) {
                mView.showMessageToast(t.getMessage());
                mView.showProgress(false);
            }
        });
    }

    @Override
    public void loadItems(ResponseData responseData, boolean isClear) {
        if (isClear) {
            mAdapterModel.clearItems();
        }
        ArrayList<ResponseNoticeItem> noticeItems = new ArrayList<>(Arrays.asList(responseData.getNoticeItems()));
        mAdapterModel.addItems(noticeItems);
        mAdapterView.notifyAdapter();
    }
}
