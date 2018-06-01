package kr.hs.dgsw.flow.data.realm.loginhistory.model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import kr.hs.dgsw.flow.data.realm.user.model.User;

public class LoginHistory extends RealmObject {
    @PrimaryKey
    private int id;
    private User user;
    private Date loginDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }
}
