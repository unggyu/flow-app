package kr.hs.dgsw.flow.util.retrofit.model.notice;

import com.google.gson.annotations.SerializedName;

public class ResponseData {
    @SerializedName("list")
    private ResponseNoticeItem[] noticeItems;

    public ResponseData(ResponseNoticeItem[] noticeItems) {
        this.noticeItems = noticeItems;
    }

    public ResponseNoticeItem[] getNoticeItems() {
        return noticeItems;
    }

    public void setNoticeItems(ResponseNoticeItem[] noticeItems) {
        this.noticeItems = noticeItems;
    }
}
