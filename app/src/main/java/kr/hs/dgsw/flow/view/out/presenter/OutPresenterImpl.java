package kr.hs.dgsw.flow.view.out.presenter;

import android.content.Context;
import android.support.annotation.NonNull;

import java.text.ParseException;
import java.util.Calendar;

import kr.hs.dgsw.flow.data.model.EditData;
import kr.hs.dgsw.flow.util.retrofit.FlowUtils;
import kr.hs.dgsw.flow.view.out.model.Enum.OutType;
import kr.hs.dgsw.flow.view.out.model.OutData;
import kr.hs.dgsw.flow.view.out.model.body.OutRequestBody;
import kr.hs.dgsw.flow.view.out.model.body.OutResponseBody;
import kr.hs.dgsw.flow.view.out.model.body.ResponseOut;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OutPresenterImpl implements IOutContract.Presenter {

    private IOutContract.View mView;

    private OutData mOutData;

    public OutPresenterImpl(@NonNull IOutContract.View view, @NonNull Context context, OutType outType) {
        mView = view;

        Calendar cal = Calendar.getInstance();

        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);

        mOutData = new OutData(context, outType,
                year, month, day, hour, minute,
                year, month, day, hour, minute);

        mView.showOutDateTime(mOutData.getOutDateTimeToString());
        mView.showInDateTime(mOutData.getInDateTimeToString());
    }

    @Override
    public void setView(@NonNull IOutContract.View view) {
        mView = view;
    }

    @Override
    public void onDestroy() {
        mView = null;
    }

    @Override
    public void loadViewState() {
        String outDateTime = mOutData.getOutDateTimeToString();
        mView.showOutDateTime(outDateTime);

        String inDateTime = mOutData.getInDateTimeToString();
        mView.showInDateTime(inDateTime);
    }

    @Override
    public void onOutRadioButtonClick(OutType outType) {
        mOutData.setOutType(outType);
    }

    @Override
    public void onOutDateButtonClick() {
        int year = mOutData.getOutYear();
        int month = mOutData.getOutMonth();
        int day = mOutData.getOutDay();

        mView.showOutDatePickerDialog(year, month, day);
    }

    @Override
    public void onOutTimeButtonClick() {
        int hour = mOutData.getOutHour();
        int minute = mOutData.getOutMinute();

        mView.showOutTimePickerDialog(hour, minute);
    }

    @Override
    public void onInDateButtonClick() {
        int year = mOutData.getInYear();
        int month = mOutData.getInMonth();
        int day = mOutData.getInDay();

        mView.showInDatePickerDialog(year, month, day);
    }

    @Override
    public void onInTimeButtonClick() {
        int hour = mOutData.getInHour();
        int minute = mOutData.getInMinute();

        mView.showInTimePickerDialog(hour, minute);
    }

    @Override
    public void onOutDateSet(int year, int month, int dayOfMonth) {
        mOutData.setOutDate(year, month, dayOfMonth);
        mView.showOutDateTime(mOutData.getOutDateTimeToString());
    }

    @Override
    public void onOutTimeSet(int hour, int minute) {
        mOutData.setOutTime(hour, minute);
        mView.showOutDateTime(mOutData.getOutDateTimeToString());
    }

    @Override
    public void onInDateSet(int year, int month, int dayOfMonth) {
        mOutData.setInDate(year, month, dayOfMonth);
        mView.showInDateTime(mOutData.getInDateTimeToString());
    }

    @Override
    public void onInTimeSet(int hour, int minute) {
        mOutData.setInTime(hour, minute);
        mView.showInDateTime(mOutData.getInDateTimeToString());
    }

    @Override
    public void validReason(String reason) {
        reason = reason.trim();

        EditData reasonData = mOutData.getReason();
        reasonData.setData(reason);

        if (reason.isEmpty()) {
            reasonData.setValid(false);
            mView.setReasonError("이유를 입력해주세요");
            return;
        }

        reasonData.setValid(true);
        mView.setReasonErrorEnabled(false);
    }

    @Override
    public void applyOut() {
        if (!mOutData.isLoggedIn()) {
            mView.showMessageToast("로그인 되어있지 않습니다.");
            return;
        }

        if (!mOutData.getReason().isValid()) {
            mView.setReasonError("이유를 입력해주세요");
            mView.focusReason();
            return;
        }

        if (!mOutData.isDateTimeValid()) {
            mView.showMessageToast("날짜를 제대로 선택해주세요");
            return;
        }

        String token = mOutData.getToken();

        OutRequestBody requestBody = new OutRequestBody(
                mOutData.getOutDateTimeToString(),
                mOutData.getInDateTimeToString(),
                mOutData.getReason().getData()
        );

        Call<OutResponseBody> call = null;
        OutType outType = mOutData.getOutType();
        switch (outType) {
            case SHORT:
                call = FlowUtils.getFlowService().outGo(token, requestBody);
                break;
            case LONG:
                call = FlowUtils.getFlowService().outSleep(token, requestBody);
                break;
        }

        if (call != null) {
            call.enqueue(new Callback<OutResponseBody>() {
                @Override
                public void onResponse(Call<OutResponseBody> call, Response<OutResponseBody> response) {
                    if (response.isSuccessful()) {
                        OutResponseBody body = response.body();
                        mView.showMessageToast(body.getMessage());
                        if (body.getStatus() == 200) {
                            // 외출은 goOut에 저장되고 외박은 goSleepOut에 저장되어있음.
                            ResponseOut out = (body.getData().getGoOut() != null) ?
                                    body.getData().getGoOut() : body.getData().getSleepOut();

                            // 외출/외박 데이터 db에 저장
                            try {
                                mOutData.insertOut(out, (body.getData().getGoOut() != null) ?
                                        OutType.SHORT : OutType.LONG);
                            } catch (ParseException e) {
                                e.printStackTrace();
                                mView.showMessageToast("시간 포맷이 다릅니다.");
                            }
                        }
                    } else {
                        mView.showMessageToast(response.code() + " error: " +response.message());
                    }
                }

                @Override
                public void onFailure(Call<OutResponseBody> call, Throwable t) {
                    mView.showMessageToast(t.getMessage());
                }
            });
        }
    }
}
