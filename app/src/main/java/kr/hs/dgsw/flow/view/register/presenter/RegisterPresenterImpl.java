package kr.hs.dgsw.flow.view.register.presenter;

import android.support.annotation.NonNull;

import kr.hs.dgsw.flow.R;
import kr.hs.dgsw.flow.application.FlowApplication;
import kr.hs.dgsw.flow.data.model.EditData;
import kr.hs.dgsw.flow.view.register.model.RegisterData;
import kr.hs.dgsw.flow.util.retrofit.model.signup.RegisterRequestBody;
import kr.hs.dgsw.flow.util.retrofit.model.signup.RegisterResponseBody;
import retrofit2.Response;

public class RegisterPresenterImpl implements IRegisterContract.Presenter {

    private IRegisterContract.View mView;

    private RegisterData mRegisterData;

    public RegisterPresenterImpl() {
        this.mRegisterData = RegisterData.getInstance();
    }

    @Override
    public void setView(@NonNull IRegisterContract.View view) {
        this.mView = view;
    }

    @Override
    public void onDestroy() {
        this.mView = null;
    }

    @Override
    public void validateName(String name) {
        name = name.trim();

        EditData nameData = mRegisterData.getName();
        nameData.setData(name);

        if (name.isEmpty()) {
            nameData.setValid(false);
            mView.setNameError(FlowApplication.getContext()
                    .getString(R.string.error_email_empty));
            return;
        }

        nameData.setValid(true);
        mView.setNameErrorEnabled(false);
    }

    @Override
    public void validateNumber(String number) {
        number = number.trim();

        EditData numberData = mRegisterData.getNumber();
        numberData.setData(number);

        if (number.isEmpty()) {
            numberData.setValid(false);
            mView.setNumberError(FlowApplication.getContext()
                    .getString(R.string.error_number_empty));
            return;
        }

        if (!mRegisterData.isValidNumber(number)) {
            numberData.setValid(false);
            mView.setNumberError(FlowApplication.getContext()
                    .getString(R.string.error_number));
            return;
        }

        numberData.setValid(true);
        mView.setNumberErrorEnabled(false);
    }

    @Override
    public void validateCellphone(String cellphone) {
        cellphone = cellphone.trim();

        EditData cellphoneData = mRegisterData.getCellphone();
        cellphoneData.setData(cellphone);

        if (cellphone.isEmpty()) {
            cellphoneData.setValid(false);
            mView.setCellphoneError(FlowApplication.getContext()
                    .getString(R.string.error_cellphone_empty));
            return;
        }

        if (!mRegisterData.isValidCellphone(cellphone)) {
            cellphoneData.setValid(false);
            mView.setCellphoneError(FlowApplication.getContext()
                    .getString(R.string.error_cellphone));
            return;
        }

        cellphoneData.setValid(true);
        mView.setCellphoneErrorEnabled(false);
    }

    @Override
    public void validateEmail(String email) {
        email = email.trim();

        EditData emailData = mRegisterData.getEmail();
        emailData.setData(email);

        if (email.isEmpty()) {
            emailData.setValid(false);
            mView.setEmailError(FlowApplication.getContext()
                    .getString(R.string.error_email_empty));
            return;
        }

        if (!mRegisterData.isValidEmail(email)) {
            emailData.setValid(false);
            mView.setEmailError(FlowApplication.getContext()
                    .getString(R.string.error_email));
            return;
        }

        emailData.setValid(true);
        mView.setEmailErrorEnabled(false);
    }

    @Override
    public void validatePassword(String password) {
        password = password.trim();

        EditData passwordData = mRegisterData.getPassword();
        passwordData.setData(password);

        if (password.isEmpty()) {
            passwordData.setValid(false);
            mView.setPasswordError(FlowApplication.getContext()
                    .getString(R.string.error_password_empty));
            return;
        }

        if (!mRegisterData.isValidPassword(password)) {
            passwordData.setValid(false);
            mView.setPasswordError(FlowApplication.getContext()
                    .getString(R.string.error_password));
            return;
        }

        passwordData.setValid(true);
        mView.setPasswordErrorEnabled(false);
    }

    @Override
    public void validateConfirmPassword(String confirmPassword) {
        confirmPassword = confirmPassword.trim();

        EditData confirmPasswordData = mRegisterData.getConfirmPassword();
        confirmPasswordData.setData(confirmPassword);

        if (confirmPassword.isEmpty()) {
            confirmPasswordData.setValid(false);
            mView.setConfirmPasswordError(FlowApplication.getContext()
                    .getString(R.string.error_confirm_password_empty));
            return;
        }

        String password = mRegisterData.getPassword().getData();
        if (!password.equals(confirmPassword)) {
            confirmPasswordData.setValid(false);
            mView.setConfirmPasswordError(FlowApplication.getContext()
                    .getString(R.string.error_confirm_password_incorrect));
            return;
        }

        confirmPasswordData.setValid(true);
        mView.setConfirmPasswordErrorEnabled(false);
    }

    @Override
    public void validateRegisterFields(String gender, String classNumber) {
        EditData genderData = mRegisterData.getGender();
        genderData.setData(gender);
        genderData.setValid(true);

        EditData classNumberData = mRegisterData.getClassNumber();
        classNumberData.setData(classNumber.replace("반", ""));
        classNumberData.setValid(true);

        if (mRegisterData.isValidData()) {
            RegisterRequestBody requestBody = mRegisterData.makeRegisterRequestBody();
            this.attemptRegister(requestBody);
        } else {
            mView.showMessageToast(FlowApplication.getContext()
                    .getString(R.string.error_register_field));
        }
    }

    @Override
    public void attemptRegister(RegisterRequestBody requestBody) {
        if (!FlowApplication.getConnectivityStatus()) {
            mView.showMessageToast(FlowApplication.getContext()
                    .getString(R.string.error_not_connected_to_internet));
            return;
        }

        mView.showProgress(true);

        mRegisterData.callSignUp(requestBody, new RegisterData.RegisterCallback() {
            @Override
            public void onResponse(Response<RegisterResponseBody> response) {
                mView.showProgress(false);
                if (response.isSuccessful()) {
                    mView.showMessageToast(response.body().getMessage());
                    if (response.body().getStatus() == 200) {
                        mView.navigateToLogin();
                    }
                } else {
                    mView.showMessageToast(response.message());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                mView.showProgress(false);
                mView.showMessageToast(t.getMessage());
            }
        });
    }

}
