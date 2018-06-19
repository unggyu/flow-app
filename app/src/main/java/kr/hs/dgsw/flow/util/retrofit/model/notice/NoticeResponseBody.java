package kr.hs.dgsw.flow.util.retrofit.model.notice;

import kr.hs.dgsw.flow.util.retrofit.model.BaseResponseBody;

public class NoticeResponseBody extends BaseResponseBody {
    private ResponseData data;

    public NoticeResponseBody(int status, String message, ResponseData data) {
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
