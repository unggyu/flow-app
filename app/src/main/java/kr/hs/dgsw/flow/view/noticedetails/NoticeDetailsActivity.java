package kr.hs.dgsw.flow.view.noticedetails;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import kr.hs.dgsw.flow.R;
import kr.hs.dgsw.flow.view.noticedetails.presenter.INoticeDetailsContract;
import kr.hs.dgsw.flow.view.noticedetails.presenter.NoticeDetailsPresenterImpl;

public class NoticeDetailsActivity extends AppCompatActivity implements INoticeDetailsContract.View {

    @BindView(R.id.notice_details_scroll)
    public ScrollView mScrollView;

    @BindView(R.id.notice_details_progress)
    public ProgressBar mProgressView;

    @BindView(R.id.notice_details_writer)
    public TextView mWriterView;

    @BindView(R.id.notice_details_write_date)
    public TextView mWriteDateView;

    @BindView(R.id.notice_details_modify_date)
    public TextView mModifyDateView;

    @BindView(R.id.notice_details_content)
    public TextView mContentView;

    private INoticeDetailsContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_details);

        ButterKnife.bind(this);

        int noticeIdx = getIntent().getIntExtra("idx", -1);
        mPresenter = new NoticeDetailsPresenterImpl(this, this, noticeIdx);
    }

    @Override
    public void showMessageToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mScrollView.setVisibility(show ? View.GONE : View.VISIBLE);
            mScrollView.animate().setDuration(shortAnimTime).alpha(show ? 0 : 1)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mScrollView.setVisibility(show ? View.GONE : View.VISIBLE);
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
            mScrollView.setVisibility(show ? View.GONE : View.VISIBLE);
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public void showWriter(String writer) {
        mWriterView.setText(writer);
    }

    @Override
    public void showWriteDate(String date) {
        mWriteDateView.setText(date);
    }

    @Override
    public void showModifyDate(String date) {
        mModifyDateView.setVisibility(View.VISIBLE);
        mModifyDateView.setText(date);
    }

    @Override
    public void showContent(String content) {
        mContentView.setText(content);
    }
}
