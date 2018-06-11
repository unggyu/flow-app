package kr.hs.dgsw.flow.data.realm.login.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import kr.hs.dgsw.flow.data.realm.user.model.User;

public class Login extends RealmObject {
    @PrimaryKey
    private int id;
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
