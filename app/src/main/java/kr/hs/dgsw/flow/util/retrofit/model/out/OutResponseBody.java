package kr.hs.dgsw.flow.util.retrofit.model.out;

import com.google.gson.annotations.SerializedName;

import kr.hs.dgsw.flow.util.retrofit.model.BaseResponseBody;

public class OutResponseBody extends BaseResponseBody {

    @SerializedName("data")
    private ResponseData data;

    public OutResponseBody(int status, String message, ResponseData data) {
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
