package kr.hs.dgsw.flow.application;

import android.app.Application;

public class FlowApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Foreground.init(this);
    }
}
