package kr.hs.dgsw.flow.data.sqlite.model;

import kr.hs.dgsw.flow.view.out.model.Enum.OutType;

public class OutRow {
    private String userToken;
    private OutType outType;
    private boolean accept;
    private String outDateTime;
    private String inDateTime;
    private String reason;
    private int classNumber;
    private String email;

    public OutRow() { }

    public OutRow(String userToken, OutType outType, boolean accept,
                  String outDateTime, String inDateTime, String reason,
                  int classNumber, String email) {
        this.userToken = userToken;
        this.outType = outType;
        this.accept = accept;
        this.outDateTime = outDateTime;
        this.inDateTime = inDateTime;
        this.reason = reason;
        this.classNumber = classNumber;
        this.email = email;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public OutType getOutType() {
        return outType;
    }

    public void setOutType(OutType outType) {
        this.outType = outType;
    }

    public boolean isAccept() {
        return accept;
    }

    public void setAccept(boolean accept) {
        this.accept = accept;
    }

    public String getOutDateTime() {
        return outDateTime;
    }

    public void setOutDateTime(String outDateTime) {
        this.outDateTime = outDateTime;
    }

    public String getInDateTime() {
        return inDateTime;
    }

    public void setInDateTime(String inDateTime) {
        this.inDateTime = inDateTime;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getClassNumber() {
        return classNumber;
    }

    public void setClassNumber(int classNumber) {
        this.classNumber = classNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
