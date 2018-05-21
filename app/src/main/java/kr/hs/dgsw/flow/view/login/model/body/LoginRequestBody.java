package kr.hs.dgsw.flow.view.login.model.body;

import com.google.gson.annotations.SerializedName;

public class LoginRequestBody {

    @SerializedName("email")
    private String email;

    @SerializedName("pw")
    private String password;

    public LoginRequestBody(String email, String password) {
        this.email = email;
        this.password = password;
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

    @Override
    public String toString() {
        return "LoginRequestBody{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
