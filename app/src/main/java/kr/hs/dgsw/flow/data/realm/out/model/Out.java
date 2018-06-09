package kr.hs.dgsw.flow.data.realm.out.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import kr.hs.dgsw.flow.data.realm.user.model.User;

public class Out extends RealmObject {
    @PrimaryKey
    private int id;
    private int outType; // 0: OutType.SHORT, 1: OutType.LONG
    private int status; // 0: 대기중, 1: 승인됨, 2: 거절됨
    private String outDateTime;
    private String inDateTime;
    private String reason;
    private User user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOutType() {
        return outType;
    }

    public void setOutType(int outType) {
        this.outType = outType;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
