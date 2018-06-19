package kr.hs.dgsw.flow.view.register.presenter;

import kr.hs.dgsw.flow.base.BasePresenter;
import kr.hs.dgsw.flow.base.BaseView;
import kr.hs.dgsw.flow.util.retrofit.model.signup.RegisterRequestBody;

public interface IRegisterContract {
    interface View extends BaseView {
        void showProgress(final boolean show);

        void setNameError(String msg);
        void setNumberError(String msg);
        void setCellphoneError(String msg);
        void setEmailError(String msg);
        void setPasswordError(String msg);
        void setConfirmPasswordError(String msg);

        void setNameErrorEnabled(final boolean enabled);
        void setNumberErrorEnabled(final boolean enabled);
        void setCellphoneErrorEnabled(final boolean enabled);
        void setEmailErrorEnabled(final boolean enabled);
        void setPasswordErrorEnabled(final boolean enabled);
        void setConfirmPasswordErrorEnabled(final boolean enabled);

        void navigateToLogin();
    }

    interface Presenter extends BasePresenter<View> {
        void validateName(String name);
        void validateNumber(String number);
        void validateCellphone(String cellphone);
        void validateEmail(String email);
        void validatePassword(String password);
        void validateConfirmPassword(String confirmPassword);

        void validateRegisterFields(String gender, String classNumber);

        void attemptRegister(RegisterRequestBody requestBody);
    }
}
