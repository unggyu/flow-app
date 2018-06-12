package kr.hs.dgsw.flow.view.login.model;

import android.content.Context;

import java.util.regex.Pattern;

import kr.hs.dgsw.flow.data.model.EditData;
import kr.hs.dgsw.flow.data.realm.login.LoginHelper;
import kr.hs.dgsw.flow.data.realm.user.UserHelper;
import kr.hs.dgsw.flow.data.realm.user.model.User;
import kr.hs.dgsw.flow.util.fcm.FlowFirebaseInstanceIDService;
import kr.hs.dgsw.flow.util.retrofit.FlowUtils;
import kr.hs.dgsw.flow.view.login.model.body.LoginRequestBody;
import kr.hs.dgsw.flow.view.login.model.body.LoginResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginData {
    public static final String STR_EMAIL_EMPTY_ERROR = "이메일을 입력해주세요";
    public static final String STR_EMAIL_ERROR = "대소고 이메일 포맷형식으로 입력해주세요";
    public static final String STR_PASSWORD_EMPTY_ERROR = "비밀번호를 입력해주세요";
    public static final String STR_PASSWORD_ERROR = "비밀번호 포맷형식으로 입력해주세요";

    private UserHelper mUserHelper;
    private LoginHelper mLoginHelper;

    private EditData email;
    private EditData password;

    public LoginData(Context context) {
        email = new EditData();
        password = new EditData();

        mUserHelper = new UserHelper(context);
        mLoginHelper = new LoginHelper(context);
    }

    public EditData getEmail() {
        return email;
    }

    public EditData getPassword() {
        return password;
    }

    public LoginRequestBody makeLoginRequestBody() {
        return new LoginRequestBody(email.getData(), password.getData(),
                FlowFirebaseInstanceIDService.getToken());
    }

    public boolean isDataValid() {
        return email.isValid() && password.isValid();
    }

    public boolean isEmailValid(String email) {
        Pattern emailPattern = Pattern.compile("^[a-zA-Z0-9]+@dgsw.hs.kr$");
        return emailPattern.matcher(email).find();
    }

    public boolean isPasswordValid(String password) {
        Pattern passwordPattern = Pattern.compile("^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*()])[a-zA-Z0-9!@#$%^&*()]{8,16}$");
        return passwordPattern.matcher(password).find();
    }

    public void insertOrUpdateUser(String email, String password, String token) {
        if (mUserHelper.getUserByEmail(email) == null) {
            mUserHelper.insertUser(email, password, token);
        } else {
            mUserHelper.updateUser(email, password, token);
        }
    }

    public boolean insertLogin(String email) {
        User user = mUserHelper.getUserByEmail(email);
        if (user != null) {
            mLoginHelper.login(user);
            return true;
        }
        return false;
    }

    public void callSignIn(LoginRequestBody requestBody, LoginCallback callback) {
        Call<LoginResponseBody> call = FlowUtils.getFlowService().signIn(requestBody);
        call.enqueue(new Callback<LoginResponseBody>() {
            @Override
            public void onResponse(Call<LoginResponseBody> call, Response<LoginResponseBody> response) {
                callback.onResponse(response);
            }

            @Override
            public void onFailure(Call<LoginResponseBody> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    public interface LoginCallback {
        void onResponse(Response<LoginResponseBody> response);
        void onFailure(Throwable t);
    }
}
