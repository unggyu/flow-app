package kr.hs.dgsw.flow.data.realm.out;

import android.content.Context;

import java.util.List;

import io.realm.RealmResults;
import kr.hs.dgsw.flow.data.realm.RealmHelper;
import kr.hs.dgsw.flow.data.realm.out.model.Out;
import kr.hs.dgsw.flow.data.realm.user.model.User;

public class OutHelper extends RealmHelper {
    public OutHelper(Context context) {
        super(context);
    }

    public int getSize() {
        return getRealm().where(Out.class).findAll().size();
    }

    public List<Out> getAll() {
        RealmResults<Out> outs = getRealm().where(Out.class).findAll();
        return getRealm().copyFromRealm(outs);
    }

    /**
     * param email에 해당하는 외출/외박 기록을 불러온다.
     * @param email
     * @return
     */
    public List<Out> getOutsByEmail(String email) {
        RealmResults<Out> outs = getRealm()
                .where(Out.class)
                .equalTo("user.email", email)
                .findAll();
        return getRealm().copyFromRealm(outs);
    }

    public void insertOut(User user,
                          int serverIdx,
                          int outType,
                          String outDateTime,
                          String inDateTime,
                          String reason) {
        getRealm().executeTransaction(realm -> {
            Out out = realm.createObject(Out.class, getNextId(Out.class));
            out.setUser(user);
            out.setServerIdx(serverIdx);
            out.setOutType(outType);
            out.setStatus(0);
            out.setOutDateTime(outDateTime);
            out.setInDateTime(inDateTime);
            out.setReason(reason);
        });
    }

    public void updateOutStatusByServerIdx(int serverIdx, int status) {
        getRealm().executeTransaction(realm -> {
            Out out = getRealm()
                    .where(Out.class)
                    .equalTo("serverIdx", serverIdx)
                    .findFirst();
            out.setStatus(status);
        });
    }

    public void deleteAll() {
        getRealm().executeTransaction(realm -> {
            realm.where(Out.class).findAll().deleteAllFromRealm();
        });
    }
}
