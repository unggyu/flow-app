package kr.hs.dgsw.flow.view.login.presenter;

import kr.hs.dgsw.flow.base.BasePresenter;
import kr.hs.dgsw.flow.base.BaseView;
import kr.hs.dgsw.flow.view.login.model.body.LoginRequestBody;

public interface ILoginContract {
    interface View extends BaseView {
        void showProgress(final boolean show);

        void setEmailError(String msg);
        void setPasswordError(String msg);

        void setEmailErrorEnabled(final boolean enabled);
        void setPasswordErrorEnabled(final boolean enabled);

        void navigateToRegister();
        void navigateToMain();
    }

    interface Presenter extends BasePresenter<View> {
        void validateEmail(String email);
        void validatePassword(String password);

        void validateLoginFields();

        void attemptLogin(LoginRequestBody requestBody);
    }
}
