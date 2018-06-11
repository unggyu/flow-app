package kr.hs.dgsw.flow.data.realm.login;

import android.content.Context;

import kr.hs.dgsw.flow.data.realm.RealmHelper;
import kr.hs.dgsw.flow.data.realm.login.model.Login;
import kr.hs.dgsw.flow.data.realm.user.model.User;

/**
 * 이 DB는 로그인 되어있는 유저를 관리합니다.
 */
public class LoginHelper extends RealmHelper {
    public LoginHelper(Context context) {
        super(context);
    }

    public boolean isLoggedIn() {
        return getRealm().where(Login.class).findAll().size() > 0;
    }

    public User getLoggedUser() {
        Login logged = getRealm().where(Login.class).findFirst();
        return logged != null ? logged.getUser() : null;
    }

    public void Login(User user) {
        getRealm().executeTransaction(realm -> {
            realm.where(Login.class).findAll().deleteAllFromRealm();
            realm.createObject(Login.class, 1).setUser(user);
        });
    }
}
