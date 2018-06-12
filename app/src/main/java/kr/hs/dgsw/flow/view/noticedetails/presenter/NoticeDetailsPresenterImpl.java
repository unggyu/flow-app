package kr.hs.dgsw.flow.view.noticedetails.presenter;

import android.content.Context;
import android.support.annotation.NonNull;

import java.text.ParseException;

import kr.hs.dgsw.flow.data.realm.login.LoginHelper;
import kr.hs.dgsw.flow.util.Utils;
import kr.hs.dgsw.flow.util.retrofit.FlowUtils;
import kr.hs.dgsw.flow.view.notice.model.ResponseNoticeItem;
import kr.hs.dgsw.flow.view.noticedetails.model.body.NoticeDetailsResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NoticeDetailsPresenterImpl implements INoticeDetailsContract.Presenter {

    private LoginHelper mLoginHelper;

    private INoticeDetailsContract.View mView;

    public NoticeDetailsPresenterImpl(INoticeDetailsContract.View view, Context context, int noticeIdx) {
        mView = view;

        if (noticeIdx == -1) {
            mView.showMessageToast("공지 인덱스 오류");
            return;
        }

        mLoginHelper = new LoginHelper(context);

        // 선택된 공지를 서버로 부터 불러와 띄어줌
        loadNotice(noticeIdx);
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

        String writeDate = Utils.dateFormat(noticeItem.getWriteDate());
        mView.showWriteDate(writeDate);
        if (!noticeItem.getWriteDate().equals(noticeItem.getModifyDate())) {
            // 만약에 쓴 시간과 수정한 시간이 다르다면 수정한 시간을 표시
            String modifyDate = Utils.dateFormat(noticeItem.getModifyDate());
            mView.showModifyDate(modifyDate + " 수정됨");
        }
        mView.showContent(noticeItem.getContent());
    }
}
