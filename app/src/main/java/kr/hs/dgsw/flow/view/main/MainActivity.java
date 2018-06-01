package kr.hs.dgsw.flow.view.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import io.realm.Realm;
import kr.hs.dgsw.flow.R;
import kr.hs.dgsw.flow.data.realm.loginhistory.LoginHistoryHelper;
import kr.hs.dgsw.flow.data.realm.loginhistory.model.LoginHistory;
import kr.hs.dgsw.flow.data.realm.token.TokenHelper;
import kr.hs.dgsw.flow.view.login.LoginActivity;
import kr.hs.dgsw.flow.view.meal.MealFragment;
import kr.hs.dgsw.flow.view.notice.NoticeFragment;
import kr.hs.dgsw.flow.view.out.OutFragment;

public class MainActivity extends AppCompatActivity
        implements MealFragment.OnFragmentInteractionListener,
        OutFragment.OnFragmentInteractionListener,
        NoticeFragment.OnFragmentInteractionListener {

    private LoginHistoryHelper mLoginHistoryHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLoginHistoryHelper = new LoginHistoryHelper(this);

        // 로그인 내역이 없다면 로그인 액티비티로 이동
        if (mLoginHistoryHelper.getSize() == 0) {
            navigateToLogin();
            finish();
            return;
        }

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        transactionFragment(MealFragment.getInstance());
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
        Fragment selectedFragment = null;
        switch (item.getItemId()) {
            case R.id.navigation_meal:
                selectedFragment = MealFragment.getInstance();
                break;
            case R.id.navigation_out:
                selectedFragment = OutFragment.getInstance();
                break;
            case R.id.navigation_notifications:
                selectedFragment = NoticeFragment.getInstance();
                break;
        }
        transactionFragment(selectedFragment);

        return true;
    };

    public void transactionFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_frame, fragment);
        transaction.commit();
    }

    public void navigateToLogin() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void onFragmentInteraction(Uri uri) { }
}
