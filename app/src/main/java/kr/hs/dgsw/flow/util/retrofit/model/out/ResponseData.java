package kr.hs.dgsw.flow.util.retrofit.model.out;

import com.google.gson.annotations.SerializedName;

public class ResponseData {

    @SerializedName("go_out")
    private ResponseOut goOut;

    @SerializedName("sleep_out")
    private ResponseOut sleepOut;

    public ResponseData(ResponseOut goOut, ResponseOut sleepOut) {
        this.goOut = goOut;
        this.sleepOut = sleepOut;
    }

    public ResponseOut getGoOut() {
        return goOut;
    }

    public void setGoOut(ResponseOut goOut) {
        this.goOut = goOut;
    }

    public ResponseOut getSleepOut() {
        return sleepOut;
    }

    public void setSleepOut(ResponseOut sleepOut) {
        this.sleepOut = sleepOut;
    }
}
