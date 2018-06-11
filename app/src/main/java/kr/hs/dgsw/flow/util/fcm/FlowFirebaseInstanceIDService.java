package kr.hs.dgsw.flow.util.fcm;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class FlowFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static String token = FirebaseInstanceId.getInstance().getToken();

    @Override
    public void onTokenRefresh() {
        token = FirebaseInstanceId.getInstance().getToken();
    }

    public static String getToken() {
        return token;
    }
}
