package kr.hs.dgsw.flow.util.retrofit.model.signin;

import com.google.gson.annotations.SerializedName;

import kr.hs.dgsw.flow.util.retrofit.model.BaseResponseBody;

public class LoginResponseBody extends BaseResponseBody {

    @SerializedName("data")
    private ResponseData data;

    public LoginResponseBody(int status, String message, ResponseData data) {
        super(status, message);
        this.data = data;
    }

    public ResponseData getData() {
        return data;
    }

    public void setData(ResponseData data) {
        this.data = data;
    }
}
