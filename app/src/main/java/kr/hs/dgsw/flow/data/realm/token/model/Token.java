package kr.hs.dgsw.flow.data.realm.token.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Token extends RealmObject {
    @PrimaryKey
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
