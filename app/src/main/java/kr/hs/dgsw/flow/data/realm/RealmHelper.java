package kr.hs.dgsw.flow.data.realm;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class RealmHelper {
    private Realm mRealm;

    public RealmHelper(Context context) {
        Realm.init(context);
        RealmConfiguration configuration =
                new RealmConfiguration.Builder()
                        .deleteRealmIfMigrationNeeded()
                        .build();
        mRealm = Realm.getInstance(configuration);
    }

    public Realm getRealm() {
        return mRealm;
    }

    public void close() {
        mRealm.close();
    }

    /**
     * auto_increment처럼 사용하기 위한 메서드
     * 다음 id를 반환
     * @param clazz
     * @return
     */
    public int getNextId(Class clazz) {
        Number currentIdNum = mRealm.where(clazz).max("id");
        int nextId;
        if (currentIdNum == null) {
            nextId = 1;
        } else {
            nextId = currentIdNum.intValue() + 1;
        }
        return nextId;
    }
}
