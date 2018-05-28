package kr.hs.dgsw.flow.data.realm.token;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import kr.hs.dgsw.flow.data.realm.token.model.Token;

public class TokenHelper {
    private Realm mRealm;

    public TokenHelper(Context context) {
        Realm.init(context);
        RealmConfiguration configuration =
                new RealmConfiguration.Builder()
                        .deleteRealmIfMigrationNeeded()
                        .build();
        mRealm = Realm.getInstance(configuration);
    }

    public void destroy() {
        mRealm.close();
    }

    /**
     * 토큰 추가
     * @param token 토큰 문자열
     */
    public void insert(String token) {
        Token tokenObj = new Token();
        tokenObj.setToken(token);

        mRealm.beginTransaction();
        mRealm.copyToRealm(tokenObj);
        mRealm.commitTransaction();
    }

    public String getLast() {
        RealmResults<Token> tokens = mRealm.where(Token.class).findAll();
        if (tokens.size() == 0) {
            return null;
        }

        return tokens.last().getToken();
    }
}
