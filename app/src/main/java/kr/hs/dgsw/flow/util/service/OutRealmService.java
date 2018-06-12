package kr.hs.dgsw.flow.util.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import kr.hs.dgsw.flow.data.realm.out.OutHelper;
import kr.hs.dgsw.flow.view.out.model.Enum.OutType;

/**
 * fcm 서비스에서는 realm을 사용할 수 없으므로 IntentService에서 대신 realm을 사용한다
 */
public class OutRealmService extends IntentService {
    public OutRealmService() {
        super("OutRealmService");
    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public OutRealmService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        OutHelper outHelper = new OutHelper(this);

        String type = intent.getStringExtra("type");
        OutType outType = type.equals("go_out") ? OutType.SHORT : OutType.LONG;
        int outIdx = intent.getIntExtra("serverIdx", -1);
        // 서버에서 메세지가 오면 승인이 된것이기 때문에 승인으로 변경
        outHelper.updateOutStatus(outType, outIdx, 1);
        // realm을 사용하기 위해서만 존재하기 때문에 소멸
        stopSelf();
    }
}
