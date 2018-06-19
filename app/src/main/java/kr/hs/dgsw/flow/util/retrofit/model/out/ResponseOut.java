package kr.hs.dgsw.flow.util.retrofit.model.out;

import com.google.gson.annotations.SerializedName;

public class ResponseOut {

    @SerializedName("accept")
    private int accept;

    @SerializedName("idx")
    private int idx;

    @SerializedName("start_time")
    private String startTime;

    @SerializedName("end_time")
    private String endTime;

    @SerializedName("reason")
    private String reason;

    @SerializedName("class_idx")
    private int classIdx;

    @SerializedName("student_email")
    private String studentEmail;

    public ResponseOut(int accept, int idx, String startTime, String endTime, String reason, int classIdx, String studentEmail) {
        this.accept = accept;
        this.idx = idx;
        this.startTime = startTime;
        this.endTime = endTime;
        this.reason = reason;
        this.classIdx = classIdx;
        this.studentEmail = studentEmail;
    }

    public int getAccept() {
        return accept;
    }

    public void setAccept(int accept) {
        this.accept = accept;
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getClassIdx() {
        return classIdx;
    }

    public void setClassIdx(int classIdx) {
        this.classIdx = classIdx;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }
}
