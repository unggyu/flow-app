package kr.hs.dgsw.flow.data.realm.user;

import android.content.Context;

import kr.hs.dgsw.flow.data.realm.RealmHelper;
import kr.hs.dgsw.flow.data.realm.user.model.User;

public class UserHelper extends RealmHelper {
    public UserHelper(Context context) {
        super(context);
    }

    public User getUserByEmail(String email) {
        User user = getRealm()
                .where(User.class)
                .equalTo("email", email)
                .findFirst();
        return user;
    }

    public void insertUser(String email, String password, String token) {
        getRealm().executeTransaction(realm -> {
            User user = realm.createObject(User.class, getNextId(User.class));
            user.setEmail(email);
            user.setPassword(password);
            user.setToken(token);
        });
    }

    public void updateUser(String email, String password, String token) {
        getRealm().executeTransaction(realm -> {
            User updateUser = realm
                    .where(User.class)
                    .equalTo("email", email)
                    .findFirst();
            updateUser.setEmail(email);
            updateUser.setPassword(password);
            updateUser.setToken(token);
        });
    }
}
