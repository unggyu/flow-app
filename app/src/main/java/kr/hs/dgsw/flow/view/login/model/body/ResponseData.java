package kr.hs.dgsw.flow.view.login.model.body;

import com.google.gson.annotations.SerializedName;

public class ResponseData {
    @SerializedName("token")
    private String token;

    @SerializedName("user")
    private ResponseUser user;

    public ResponseData(String token, ResponseUser user) {
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public ResponseUser getUser() {
        return user;
    }

    public void setUser(ResponseUser user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "{" +
                "token='" + token + '\'' +
                ", user=" + user.toString() +
                '}';
    }
}
