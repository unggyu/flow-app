package kr.hs.dgsw.flow.view.login.model.body;

import com.google.gson.annotations.SerializedName;

public class LoginRequestBody {

    @SerializedName("email")
    private String email;

    @SerializedName("pw")
    private String password;

    @SerializedName("registration_token")
    private String registrationToken;

    public LoginRequestBody(String email, String password, String registrationToken) {
        this.email = email;
        this.password = password;
        this.registrationToken = registrationToken;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRegistrationToken() {
        return registrationToken;
    }

    public void setRegistrationToken(String registrationToken) {
        this.registrationToken = registrationToken;
    }

    @Override
    public String toString() {
        return "LoginRequestBody{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", registrationToken='" + registrationToken + '\'' +
                '}';
    }
}
