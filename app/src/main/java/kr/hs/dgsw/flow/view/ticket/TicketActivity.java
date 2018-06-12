package kr.hs.dgsw.flow.view.ticket;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kr.hs.dgsw.flow.R;
import kr.hs.dgsw.flow.data.realm.login.LoginHelper;
import kr.hs.dgsw.flow.data.realm.out.OutHelper;
import kr.hs.dgsw.flow.data.realm.out.model.Out;
import kr.hs.dgsw.flow.data.realm.user.model.User;
import kr.hs.dgsw.flow.view.ticket.adapter.TicketAdapter;
public class TicketActivity extends AppCompatActivity {

    @BindView(R.id.ticket_progress)
    public ProgressBar mProgressView;

    @BindView(R.id.ticket_none_out)
    public TextView mNoneOutView;

    @BindView(R.id.ticket_recycler_view)
    public RecyclerView mRecyclerView;

    private TicketAdapter mTicketAdapter;

    private LoginHelper mLoginHelper;
    private OutHelper mOutHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);

        ButterKnife.bind(this);

        mLoginHelper = new LoginHelper(this);
        mOutHelper = new OutHelper(this);

        User loggedInUser = mLoginHelper.getLoggedUser();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        showProgress(true);
        // 외출/외박 기록을 현재 로그인 되어있는 회원의 이메일을 이용하여 그 회원의 기록만 가져온다
        List<Out> outList = mOutHelper.getOutsByEmail(loggedInUser.getEmail());
        // 최신순으로 볼 수 있도록 리스트를 뒤집는다
        Collections.reverse(outList);
        mTicketAdapter = new TicketAdapter(this);
        mTicketAdapter.addItems((ArrayList<Out>) outList);
        mRecyclerView.setAdapter(mTicketAdapter);
        showProgress(false);

        // 기록이 없다면 없다고 표시
        if (mTicketAdapter.getItemCount() <= 0) {
            showNoneOut(true);
        } else {
            showNoneOut(false);
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mRecyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
            mRecyclerView.animate().setDuration(shortAnimTime).alpha(show ? 0 : 1)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mRecyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
                        }
                    });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(show ? 1 : 0)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                        }
                    });

        } else {
            mRecyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        }
        mNoneOutView.setVisibility(show ? View.GONE : View.VISIBLE);
    }

    public void showNoneOut(final boolean show) {
        mNoneOutView.setVisibility(show ? View.VISIBLE : View.GONE);
        mRecyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
    }
}
