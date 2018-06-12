package kr.hs.dgsw.flow.view.noticedetails.model.body;

import kr.hs.dgsw.flow.util.retrofit.model.BaseResponseBody;
import kr.hs.dgsw.flow.view.notice.model.ResponseNoticeItem;

public class NoticeDetailsResponseBody extends BaseResponseBody {
    private ResponseNoticeItem data;

    public NoticeDetailsResponseBody(int status, String message, ResponseNoticeItem data) {
        super(status, message);
        this.data = data;
    }

    public ResponseNoticeItem getData() {
        return data;
    }

    public void setData(ResponseNoticeItem data) {
        this.data = data;
    }
}
