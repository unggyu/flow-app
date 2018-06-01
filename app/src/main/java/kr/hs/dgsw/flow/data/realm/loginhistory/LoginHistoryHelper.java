package kr.hs.dgsw.flow.data.realm.loginhistory;

import android.content.Context;

import java.util.Date;

import io.realm.RealmResults;
import kr.hs.dgsw.flow.data.realm.RealmHelper;
import kr.hs.dgsw.flow.data.realm.loginhistory.model.LoginHistory;
import kr.hs.dgsw.flow.data.realm.user.model.User;

public class LoginHistoryHelper extends RealmHelper{
    public LoginHistoryHelper(Context context) {
        super(context);
    }

    public int getSize() {
        return getRealm().where(LoginHistory.class).findAll().size();
    }

    public User getLastLoggedInUser() {
        getRealm().beginTransaction();
        RealmResults<LoginHistory> loginHistories = getRealm().where(LoginHistory.class).findAll();
        getRealm().commitTransaction();
        return loginHistories.size() == 0 ? null : loginHistories.last().getUser();
    }

    public void insertLoggedInUser(User user) {
        getRealm().executeTransaction(realm -> {
            LoginHistory loginHistory =
                    realm.createObject(LoginHistory.class, getNextId(LoginHistory.class));
            loginHistory.setLoginDate(new Date());
            loginHistory.setUser(user);
        });
    }

    public void deleteAll() {
        RealmResults<LoginHistory> loginHistories = getRealm().where(LoginHistory.class).findAll();
        getRealm().beginTransaction();
        loginHistories.deleteAllFromRealm();
        getRealm().commitTransaction();
    }
}
