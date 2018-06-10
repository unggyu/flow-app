package kr.hs.dgsw.flow.view.notice.model;

import com.google.gson.annotations.SerializedName;

public class ResponseNoticeItem {
    private int idx;
    private String content;
    private String writer;
    private String writeDate;
    private String modifyDate;

    @SerializedName("NoticeFiles")
    private ResponseNoticeFile[] noticeFiles;

    public ResponseNoticeItem(int idx, String content, String writer, String writeDate, String modifyDate, ResponseNoticeFile[] noticeFiles) {
        this.idx = idx;
        this.content = content;
        this.writer = writer;
        this.writeDate = writeDate;
        this.modifyDate = modifyDate;
        this.noticeFiles = noticeFiles;
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getWriteDate() {
        return writeDate;
    }

    public void setWriteDate(String writeDate) {
        this.writeDate = writeDate;
    }

    public String getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(String modifyDate) {
        this.modifyDate = modifyDate;
    }

    public ResponseNoticeFile[] getNoticeFiles() {
        return noticeFiles;
    }

    public void setNoticeFiles(ResponseNoticeFile[] noticeFiles) {
        this.noticeFiles = noticeFiles;
    }
}
