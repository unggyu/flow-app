package kr.hs.dgsw.flow.data.realm.token;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import kr.hs.dgsw.flow.data.realm.RealmHelper;
import kr.hs.dgsw.flow.data.realm.token.model.Token;

public class TokenHelper extends RealmHelper {

    public TokenHelper(Context context) {
        super(context);
    }

    /**
     * 토큰 추가
     * @param token 토큰 문자열
     */
    public void insert(String token) {
        Token tokenObj = new Token();
        tokenObj.setToken(token);

        Realm realm = getRealm();

        realm.beginTransaction();
        realm.copyToRealm(tokenObj);
        realm.commitTransaction();
    }

    public String getLast() {
        RealmResults<Token> tokens = getRealm().where(Token.class).findAll();
        if (tokens.size() == 0) {
            return null;
        }

        return tokens.last().getToken();
    }
}
