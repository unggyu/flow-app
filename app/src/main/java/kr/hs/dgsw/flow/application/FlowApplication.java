package kr.hs.dgsw.flow.application;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import kr.hs.dgsw.flow.application.listener.OnPendingNotificationCountChanged;

public class FlowApplication extends Application {

    private static Application sApplication;

    private static int sPendingNotificationCount = 0;

    private static OnPendingNotificationCountChanged sOnPendingNotificationCountChangedListener;

    @Override
    public void onCreate() {
        super.onCreate();
        Foreground.init(this);
        sApplication = this;
    }

    public static Application getApplication() {
        return sApplication;
    }

    public static Context getContext() {
        return getApplication().getApplicationContext();
    }

    public static int getPendingNotificationCount() {
        return sPendingNotificationCount;
    }

    public static void setPendingNotificationCount(int count, final boolean notify) {
        sPendingNotificationCount = count;
        if (sOnPendingNotificationCountChangedListener != null && notify) {
            sOnPendingNotificationCountChangedListener.onPendingNotificationCountChanged(count);
        }
    }

    public static void setOnPendingNotificationCountChangedListener(
            OnPendingNotificationCountChanged onPendingNotificationCountChangedListener) {
        sOnPendingNotificationCountChangedListener = onPendingNotificationCountChangedListener;
    }

    public static NetworkInfo getNetworkInfo() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo;
    }

    /**
     * return {
     *     true: 연결됨
     *     false: 연결 안됨
     * }
     * @return
     */
    public static boolean getConnectivityStatus() {
        NetworkInfo info = getNetworkInfo();
        return (info != null) && info.isConnected();
    }
}
