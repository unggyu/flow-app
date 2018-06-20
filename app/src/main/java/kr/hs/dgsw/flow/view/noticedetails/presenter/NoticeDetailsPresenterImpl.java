package kr.hs.dgsw.flow.view.noticedetails.presenter;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.View;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;

import kr.hs.dgsw.flow.data.realm.login.LoginHelper;
import kr.hs.dgsw.flow.util.FlowUtils;
import kr.hs.dgsw.flow.util.retrofit.model.notice.ResponseNoticeFile;
import kr.hs.dgsw.flow.util.retrofit.model.notice.ResponseNoticeItem;
import kr.hs.dgsw.flow.util.retrofit.model.notice.NoticeDetailsResponseBody;
import kr.hs.dgsw.flow.view.noticedetails.adapter.IAttachedFileListViewAdapter;
import kr.hs.dgsw.flow.view.noticedetails.listener.OnDownloadButtonClickListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NoticeDetailsPresenterImpl implements INoticeDetailsContract.Presenter, OnDownloadButtonClickListener {

    private LoginHelper mLoginHelper;

    private Context mContext;

    private INoticeDetailsContract.View mView;

    private IAttachedFileListViewAdapter.View mAdapterView;
    private IAttachedFileListViewAdapter.Model mAdapterModel;

    public NoticeDetailsPresenterImpl(INoticeDetailsContract.View view, Context context) {
        mView = view;
        mContext = context;
        mLoginHelper = new LoginHelper(context);
    }

    @Override
    public void setView(@NonNull INoticeDetailsContract.View view) {
        mView = view;
    }

    @Override
    public void onDestroy() {
        mView = null;
    }

    @Override
    public void setAttachedFileListViewAdapterView(IAttachedFileListViewAdapter.View adapterView) {
        mAdapterView = adapterView;
        mAdapterView.setOnDownloadButtonClickListener(this);
    }

    @Override
    public void setAttachedFileListViewAdapterModel(IAttachedFileListViewAdapter.Model adapterModel) {
        mAdapterModel = adapterModel;
    }

    @Override
    public void onAttachedFileToggleButtonCheckedChanged(boolean isChecked) {
        mView.showAttachedFileListView(isChecked);
    }

    @Override
    public void loadNotice(int idx) {
        mView.showProgress(true);
        Call<NoticeDetailsResponseBody> call =
                FlowUtils.getFlowService().getNotice(mLoginHelper.getLoggedUser().getToken(), idx);
        call.enqueue(new Callback<NoticeDetailsResponseBody>() {
            @Override
            public void onResponse(Call<NoticeDetailsResponseBody> call, Response<NoticeDetailsResponseBody> response) {
                mView.showProgress(false);
                if (response.isSuccessful()) {
                    NoticeDetailsResponseBody body = response.body();
                    if (body.getStatus() == 200) {
                        try {
                            onSuccessful(body.getData());
                        } catch (ParseException e) {
                            e.printStackTrace();
                            mView.showMessageToast("Date포맷팅 실패");
                        }
                    } else {
                        mView.showMessageToast(body.getStatus() + " error: " + body.getMessage());
                    }
                } else {
                    mView.showMessageToast(response.code() + " error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<NoticeDetailsResponseBody> call, Throwable t) {
                mView.showProgress(false);
                mView.showMessageToast(t.getMessage());
            }
        });
    }

    private void onSuccessful(@NonNull ResponseNoticeItem noticeItem) throws ParseException {
        mView.showWriter(noticeItem.getWriter());

        String writeDate = FlowUtils.dateFormat(noticeItem.getWriteDate());
        mView.showWriteDate(writeDate);
        if (!noticeItem.getWriteDate().equals(noticeItem.getModifyDate())) {
            // 만약에 쓴 시간과 수정한 시간이 다르다면 수정한 시간을 표시
            String modifyDate = FlowUtils.dateFormat(noticeItem.getModifyDate());
            mView.showModifyDate(modifyDate + " 수정됨");
        }
        mView.showContent(noticeItem.getContent());

        // ArrayList로 변환
        ArrayList<ResponseNoticeFile> noticeFiles =
                new ArrayList<>(Arrays.asList(noticeItem.getNoticeFiles()));

        // 어뎁터에 첨부파일들 삽입
        mAdapterModel.addAll(noticeFiles);
        mAdapterView.notifyAdapter();

        // 첨부파일이 존재할 때만 첨부파일 버튼 보이게
        mView.showAttachedFileToggleButton(noticeFiles.size() > 0);
    }

    @Override
    public void onDownloadButtonClick(String url, String fileName) {
        DownloadManager downloadManager =
                (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);

        Uri uri = Uri.parse(FlowUtils.BASE_URL + "/" + url);

        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        // 다운로드 진행
        downloadManager.enqueue(request);
    }
}
