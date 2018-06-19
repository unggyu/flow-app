package kr.hs.dgsw.flow.application;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class FlowApplication extends Application {

    private static Application sApplication;

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

    public static NetworkInfo getNetworkInfo() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo;
    }
}
