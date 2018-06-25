package kr.hs.dgsw.flow.view.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import kr.hs.dgsw.flow.R;
import kr.hs.dgsw.flow.application.FlowApplication;
import kr.hs.dgsw.flow.application.listener.OnPendingNotificationCountChanged;
import kr.hs.dgsw.flow.data.realm.login.LoginHelper;
import kr.hs.dgsw.flow.data.realm.out.OutHelper;
import kr.hs.dgsw.flow.view.login.LoginActivity;
import kr.hs.dgsw.flow.view.meal.MealFragment;
import kr.hs.dgsw.flow.view.notice.NoticeFragment;
import kr.hs.dgsw.flow.view.out.OutFragment;
import kr.hs.dgsw.flow.view.out.model.Enum.OutType;
import kr.hs.dgsw.flow.view.ticket.TicketActivity;

public class MainActivity extends AppCompatActivity
        implements MealFragment.OnFragmentInteractionListener,
        OutFragment.OnFragmentInteractionListener,
        NoticeFragment.OnFragmentInteractionListener,
        AHBottomNavigation.OnTabSelectedListener,
        OnPendingNotificationCountChanged {

    @BindView(R.id.navigation)
    public AHBottomNavigation mNavigationView;

    private LoginHelper mLoginHelper;
    private OutHelper mOutHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        mLoginHelper = new LoginHelper(this);
        mOutHelper = new OutHelper(this);

        // 로그인 내역이 없다면 로그인 액티비티로 이동
        if (!mLoginHelper.isLoggedIn()) {
            navigateToLogin();
            finish();
            return;
        }

        FlowApplication.setOnPendingNotificationCountChangedListener(this);

        AHBottomNavigationItem mealItem = new AHBottomNavigationItem(
                R.string.title_meal, R.drawable.ic_restaurant_black, R.color.colorPrimary);
        AHBottomNavigationItem outItem = new AHBottomNavigationItem(
                R.string.title_out, R.drawable.ic_dashboard_black_24dp, R.color.colorPrimary);
        AHBottomNavigationItem noticeItem = new AHBottomNavigationItem(
                R.string.title_notifications, R.drawable.ic_notifications_black_24dp, R.color.colorPrimary);

        mNavigationView.addItem(mealItem);
        mNavigationView.addItem(outItem);
        mNavigationView.addItem(noticeItem);

        mNavigationView.setOnTabSelectedListener(this);

        int pendingNotificationCount = FlowApplication.getPendingNotificationCount();
        mNavigationView.setNotification(pendingNotificationCount, 2);

        Intent intent = getIntent();
        String type = intent.getStringExtra("type");
        if (type != null) {
            // 백그라운드 메세징 서비스에서 넘어왔을 때
            OutType outType;
            switch (type) {
                case "go_out":
                case "sleep_out":
                    // 외출/외박인 경우 DB업뎃 후 액티비티 이동
                    outType = type.equals("go_out") ? OutType.SHORT : OutType.LONG;

                    int outIdx = Integer.parseInt(intent.getStringExtra("idx"));
                    // 응답이 오면 승인이니 승인으로 업데이트
                    mOutHelper.updateOutStatus(outType, outIdx, 1);

                    Intent outIntent = new Intent(this, TicketActivity.class);
                    startActivity(outIntent);
                    break;
                case "notice":
                    // 공지인 경우 해당 프래그먼트로 이동
                    mNavigationView.setCurrentItem(2);
                    break;
            }
        } else {
            // 포그라운드 메시징 서비스에서 넘어왔을 때
            int item = intent.getIntExtra("defaultItem", 0);
            mNavigationView.setCurrentItem(item);
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
            case R.id.logout:
                logout();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        int item = intent.getIntExtra("defaultItem", 0);
        mNavigationView.setCurrentItem(item);
    }

    /**
     * 현재 액티비티의 액션바제목을 바꾼다
     * @param title 바꿀 제목
     */
    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    public void logout() {
        mLoginHelper.logout();
        navigateToLogin();
        finish();
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
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_frame, fragment)
                .commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) { }

    @Override
    public boolean onTabSelected(int position, boolean wasSelected) {
        Fragment selectedFragment = null;
        switch (position) {
            case 0:
                selectedFragment = MealFragment.getInstance();
                break;
            case 1:
                selectedFragment = OutFragment.getInstance();
                break;
            case 2:
                selectedFragment = NoticeFragment.getInstance();

                // 푸시 알림 카운트 초기화
                FlowApplication.setPendingNotificationCount(0, true);
                break;
        }
        transactionFragment(selectedFragment);

        return true;
    }

    @Override
    public void onPendingNotificationCountChanged(int count) {
        new Thread() {
            public void run() {
                Message msg = pendingNotificationHandler.obtainMessage();
                Bundle bundle = new Bundle();
                bundle.putInt("count", count);
                msg.setData(bundle);
                pendingNotificationHandler.sendMessage(msg);
            }
        }.start();
    }

    @SuppressLint("HandlerLeak")
    final Handler pendingNotificationHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (mNavigationView.getCurrentItem() != 2) {
                mNavigationView.setNotification(msg.getData().getInt("count"), 2);
            } else {
                // 현재 공지 페이지일 때
                FlowApplication.setPendingNotificationCount(0, false);
                mNavigationView.setNotification(0, 2);
            }
        }
    };
}

