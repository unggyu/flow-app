package kr.hs.dgsw.flow.view.notice.model;

public class ResponseNoticeFile {
    private int idx;
    private String uploadName;
    private int noticeIdx;

    public ResponseNoticeFile(int idx, String uploadName, int noticeIdx) {
        this.idx = idx;
        this.uploadName = uploadName;
        this.noticeIdx = noticeIdx;
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public String getUploadName() {
        return uploadName;
    }

    public void setUploadName(String uploadName) {
        this.uploadName = uploadName;
    }

    public int getNoticeIdx() {
        return noticeIdx;
    }

    public void setNoticeIdx(int noticeIdx) {
        this.noticeIdx = noticeIdx;
    }
}
