package kr.hs.dgsw.flow.view.register.presenter;

import android.support.annotation.NonNull;

import kr.hs.dgsw.flow.data.model.EditData;
import kr.hs.dgsw.flow.view.register.model.RegisterData;
import kr.hs.dgsw.flow.view.register.model.body.RegisterRequestBody;
import kr.hs.dgsw.flow.view.register.model.body.RegisterResponseBody;
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
            mView.setNameError(RegisterData.STR_NAME_EMPTY_ERROR);
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
            mView.setNumberError(RegisterData.STR_NUMBER_EMPTY_ERROR);
            return;
        }

        if (!mRegisterData.isValidNumber(number)) {
            numberData.setValid(false);
            mView.setNumberError(RegisterData.STR_NUMBER_ERROR);
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
            mView.setCellphoneError(RegisterData.STR_CELLPHONE_EMPTY_ERROR);
            return;
        }

        if (!mRegisterData.isValidCellphone(cellphone)) {
            cellphoneData.setValid(false);
            mView.setCellphoneError(RegisterData.STR_CELLPHONE_ERROR);
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
            mView.setEmailError(RegisterData.STR_EMAIL_EMPTY_ERROR);
            return;
        }

        if (!mRegisterData.isValidEmail(email)) {
            emailData.setValid(false);
            mView.setEmailError(RegisterData.STR_EMAIL_ERROR);
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
            mView.setPasswordError(RegisterData.STR_PASSWORD_EMPTY_ERROR);
            return;
        }

        if (!mRegisterData.isValidPassword(password)) {
            passwordData.setValid(false);
            mView.setPasswordError(RegisterData.STR_PASSWORD_ERROR);
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
            mView.setConfirmPasswordError(RegisterData.STR_CONFIRM_PASSWORD_EMPTY_ERROR);
            return;
        }

        String password = mRegisterData.getPassword().getData();
        if (!password.equals(confirmPassword)) {
            confirmPasswordData.setValid(false);
            mView.setConfirmPasswordError(RegisterData.STR_CONFIRM_PASSWORD_INCORRECT_ERROR);
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
            mView.showMessageToast(RegisterData.STR_REGISTER_FIELD_ERROR);
        }
    }

    @Override
    public void attemptRegister(RegisterRequestBody requestBody) {
        mView.showProgress(true);

        mRegisterData.callSignUp(requestBody, new RegisterData.RegisterCallback() {
            @Override
            public void onResponse(Response<RegisterResponseBody> response) {
                if (response.isSuccessful()) {
                    mView.showMessageToast(response.body().getMessage());
                    if (response.body().getStatus() == 200) {
                        mView.showProgress(false);
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
