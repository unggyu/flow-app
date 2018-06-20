package kr.hs.dgsw.flow.view.register.model;

import java.util.regex.Pattern;

import kr.hs.dgsw.flow.data.model.EditData;
import kr.hs.dgsw.flow.util.FlowUtils;
import kr.hs.dgsw.flow.util.retrofit.model.signup.RegisterRequestBody;
import kr.hs.dgsw.flow.util.retrofit.model.signup.RegisterResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterData {
    private static RegisterData registerData;

    private EditData name;
    private EditData gender;
    private EditData classNumber;
    private EditData number;
    private EditData cellphone;
    private EditData email;
    private EditData password;
    private EditData confirmPassword;

    private RegisterData() {
        this.name = new EditData();
        this.gender = new EditData();
        this.classNumber = new EditData();
        this.number = new EditData();
        this.cellphone = new EditData();
        this.email = new EditData();
        this.password = new EditData();
        this.confirmPassword = new EditData();
    }

    public static RegisterData getInstance() {
        if (registerData == null) {
            registerData = new RegisterData();
        }
        return registerData;
    }

    public EditData getName() {
        return name;
    }

    public EditData getGender() {
        return gender;
    }

    public EditData getClassNumber() {
        return classNumber;
    }

    public EditData getNumber() {
        return number;
    }

    public EditData getCellphone() {
        return cellphone;
    }

    public EditData getEmail() {
        return email;
    }

    public EditData getPassword() {
        return password;
    }

    public EditData getConfirmPassword() {
        return confirmPassword;
    }

    public RegisterRequestBody makeRegisterRequestBody() {
        return new RegisterRequestBody(
                email.getData(),
                password.getData(),
                name.getData(),
                gender.getData(),
                cellphone.getData(),
                Integer.parseInt(classNumber.getData()),
                Integer.parseInt(number.getData())
        );
    }

    public boolean isValidData() {
        return (name.isValid()      &&
                gender.isValid()    &&
                number.isValid()    &&
                cellphone.isValid() &&
                email.isValid()     &&
                password.isValid()  &&
                confirmPassword.isValid());
    }

    public boolean isValidNumber(String number) {
        Integer num;
        try {
            num = Integer.parseInt(number);
        } catch (NumberFormatException e) {
            num = null;
        }
        return (num != null) && (0 < num && num <= 20);
    }

    public boolean isValidEmail(String email) {
        Pattern emailPattern = Pattern.compile("^[a-zA-Z0-9]+@dgsw.hs.kr$");
        return emailPattern.matcher(email).find();
    }

    public boolean isValidPassword(String password) {
        Pattern passwordPattern = Pattern.compile("^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*()])[a-zA-Z0-9!@#$%^&*()]{8,16}$");
        return passwordPattern.matcher(password).find();
    }

    public boolean isValidCellphone(String cellphone) {
        Pattern cellphonePattern = Pattern.compile("^[0-9]{11}$");
        return cellphonePattern.matcher(cellphone).find();
    }

    public void callSignUp(RegisterRequestBody requestBody, RegisterCallback callback) {
        Call<RegisterResponseBody> call = FlowUtils.getFlowService().signUp(requestBody);
        call.enqueue(new Callback<RegisterResponseBody>() {
            @Override
            public void onResponse(Call<RegisterResponseBody> call, Response<RegisterResponseBody> response) {
                callback.onResponse(response);
            }

            @Override
            public void onFailure(Call<RegisterResponseBody> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    public interface RegisterCallback {
        void onResponse(Response<RegisterResponseBody> response);
        void onFailure(Throwable t);
    }
}
