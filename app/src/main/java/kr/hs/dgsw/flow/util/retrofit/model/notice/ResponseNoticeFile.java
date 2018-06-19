package kr.hs.dgsw.flow.util.retrofit.model.notice;

import com.google.gson.annotations.SerializedName;

public class ResponseNoticeFile {
    private int idx;

    @SerializedName("upload_name")
    private String uploadName;

    @SerializedName("upload_dir")
    private String uploadDir;

    @SerializedName("notice_idx")
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

    public String getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }

    public int getNoticeIdx() {
        return noticeIdx;
    }

    public void setNoticeIdx(int noticeIdx) {
        this.noticeIdx = noticeIdx;
    }
}
