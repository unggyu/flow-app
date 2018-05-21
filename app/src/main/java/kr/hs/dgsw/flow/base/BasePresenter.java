package kr.hs.dgsw.flow.base;

import android.support.annotation.NonNull;

/**
 * Created by Administrator on 2018-04-01.
 */

public interface BasePresenter<T> {
    void setView(@NonNull T view);
    void onDestroy();
}
