package kr.hs.dgsw.flow.view.out.presenter;

import kr.hs.dgsw.flow.base.BasePresenter;
import kr.hs.dgsw.flow.base.BaseView;
import kr.hs.dgsw.flow.view.out.model.Enum.OutType;
import kr.hs.dgsw.flow.util.retrofit.model.out.ResponseOut;

public interface IOutContract {
    interface View extends BaseView {
        void showOutDateTime(String dateTime);
        void showInDateTime(String dateTime);
        void showOutDatePickerDialog(int defaultYear, int defaultMonth, int defaultDay);
        void showOutTimePickerDialog(int defaultHour, int defaultMinute);
        void showInDatePickerDialog(int defaultYear, int defaultMonth, int defaultDay);
        void showInTimePickerDialog(int defaultHour, int defaultMinute);

        void setReasonError(String msg);
        void setReasonErrorEnabled(final boolean enabled);

        void focusReason();

        void startOutMessagingService();
    }

    interface Presenter extends BasePresenter<View> {
        void loadViewState();

        void onOutRadioButtonClick(OutType outType);
        void onOutDateButtonClick();
        void onOutTimeButtonClick();
        void onInDateButtonClick();
        void onInTimeButtonClick();
        void onOutDateSet(int year, int month, int dayOfMonth);
        void onOutTimeSet(int hour, int minute);
        void onInDateSet(int year, int month, int dayOfMonth);
        void onInTimeSet(int hour, int minute);
        void validReason(String reason);

        void applyOut();
        void onSuccess(ResponseOut out, OutType outType);
    }
}
