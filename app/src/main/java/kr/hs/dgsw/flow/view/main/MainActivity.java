package kr.hs.dgsw.flow.view.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import kr.hs.dgsw.flow.R;
import kr.hs.dgsw.flow.data.realm.login.LoginHelper;
import kr.hs.dgsw.flow.data.realm.out.model.Out;
import kr.hs.dgsw.flow.view.login.LoginActivity;
import kr.hs.dgsw.flow.view.meal.MealFragment;
import kr.hs.dgsw.flow.view.notice.NoticeFragment;
import kr.hs.dgsw.flow.view.out.OutFragment;
import kr.hs.dgsw.flow.view.ticket.TicketActivity;

public class MainActivity extends AppCompatActivity
        implements MealFragment.OnFragmentInteractionListener,
        OutFragment.OnFragmentInteractionListener,
        NoticeFragment.OnFragmentInteractionListener,
        BottomNavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.navigation)
    public BottomNavigationView mNavigationView;

    private LoginHelper mLoginHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        mLoginHelper = new LoginHelper(this);

        // 로그인 내역이 없다면 로그인 액티비티로 이동
        if (!mLoginHelper.isLoggedIn()) {
            navigateToLogin();
            finish();
            return;
        }

        mNavigationView.setOnNavigationItemSelectedListener(this);

        Intent intent = getIntent();
        int defaultItemId;
        String type = intent.getStringExtra("type");
        if (type != null) {
            // 백그라운드 메세징 서비스에서 넘어왔을 때
            if (type.equals("go_out") || type.equals("sleep_out")) {
                // 외출 외박일 때
                Intent outIntent = new Intent(this, Out.class);
                startActivity(outIntent);
            } else if (type.equals("notice")) {
                // 공지일 때
                mNavigationView.setSelectedItemId(R.id.navigation_notifications);
            }
        } else {
            // 포그라운드 메세징 서비스에서 넘어왔을 때
            defaultItemId = intent.getIntExtra("defaultItemId", R.id.navigation_meal);
            mNavigationView.setSelectedItemId(defaultItemId);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.out_history:
                navigateToOutHistory();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * 현재 액티비티의 액션바제목을 바꾼다
     * @param title 바꿀 제목
     */
    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    public void navigateToOutHistory() {
        Intent intent = new Intent(this, TicketActivity.class);
        startActivity(intent);
    }

    public void navigateToLogin() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }

    public void transactionFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_frame, fragment);
        transaction.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
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
    }

    @Override
    public void onFragmentInteraction(Uri uri) { }
}

