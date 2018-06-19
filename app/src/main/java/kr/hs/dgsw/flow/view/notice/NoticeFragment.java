package kr.hs.dgsw.flow.view.notice;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import kr.hs.dgsw.flow.R;
import kr.hs.dgsw.flow.view.main.MainActivity;
import kr.hs.dgsw.flow.view.notice.adapter.NoticeAdapter;
import kr.hs.dgsw.flow.view.notice.presenter.INoticeContract;
import kr.hs.dgsw.flow.view.notice.presenter.NoticePresenterImpl;
import kr.hs.dgsw.flow.view.noticedetails.NoticeDetailsActivity;

public class NoticeFragment extends Fragment implements INoticeContract.View {

    @BindView(R.id.notice_swipe_refresh_layout)
    public SwipeRefreshLayout mRefreshView;

    @BindView(R.id.notice_progress)
    public ProgressBar mProgressView;

    @BindView(R.id.notice_recycler)
    public RecyclerView mRecyclerView;

    @BindView(R.id.notice_none)
    public TextView mNoneNoticeView;

    private INoticeContract.Presenter mPresenter;

    private NoticeAdapter mNoticeAdapter;

    private static NoticeFragment mNoticeFragment;

    private OnFragmentInteractionListener mListener;

    public static synchronized NoticeFragment getInstance() {
        if (mNoticeFragment == null) {
            mNoticeFragment = new NoticeFragment();
        }
        return mNoticeFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notice, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter = new NoticePresenterImpl(getContext(), this);

        mNoticeAdapter = new NoticeAdapter(getContext());
        mPresenter.setNoticeAdapterView(mNoticeAdapter);
        mPresenter.setNoticeAdapterModel(mNoticeAdapter);

        mPresenter.loadItems(true);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(
                getContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mNoticeAdapter);

        mRefreshView.setOnRefreshListener(() -> {
            mPresenter.loadItems(true);
            mRefreshView.setRefreshing(false);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        // 앱 타이틀 바 제목 변경
        ((MainActivity) getActivity()).setActionBarTitle("공지");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void showMessageToast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
    }

    @Override
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mRefreshView.setVisibility(show ? View.GONE : View.VISIBLE);
            mRefreshView.animate().setDuration(shortAnimTime).alpha(show ? 0 : 1)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mRefreshView.setVisibility(show ? View.GONE : View.VISIBLE);
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
            mRefreshView.setVisibility(show ? View.GONE : View.VISIBLE);
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        }
        mNoneNoticeView.setVisibility(show ? View.GONE : View.VISIBLE);
    }

    @Override
    public void showNoneNotice(final boolean show) {
        mNoneNoticeView.setVisibility(show ? View.VISIBLE : View.GONE);
        mRefreshView.setVisibility(show ? View.GONE : View.VISIBLE);
    }

    @Override
    public void navigateToNoticeDetails(int noticeIdx) {
        Intent intent = new Intent(getContext(), NoticeDetailsActivity.class);
        intent.putExtra("idx", noticeIdx);
        startActivity(intent);
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
