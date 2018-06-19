package kr.hs.dgsw.flow.util.retrofit.model.signin;

import com.google.gson.annotations.SerializedName;

public class LoginResponseBody {

    @SerializedName("status")
    private int status;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private ResponseData data;

    public LoginResponseBody(int status, String message, ResponseData data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResponseData getData() {
        return data;
    }

    public void setData(ResponseData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "LoginResponseBody{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data.toString() +
                '}';
    }
}
