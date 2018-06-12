package kr.hs.dgsw.flow.view.meal;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.hs.dgsw.flow.R;
import kr.hs.dgsw.flow.view.main.MainActivity;
import kr.hs.dgsw.flow.view.meal.custom.MealButton;
import kr.hs.dgsw.flow.view.meal.presenter.IMealContract;
import kr.hs.dgsw.flow.view.meal.custom.MealDateTextView;
import kr.hs.dgsw.flow.view.meal.model.Enum.MealType;
import kr.hs.dgsw.flow.view.meal.presenter.MealPresenterImpl;

public class MealFragment extends Fragment implements IMealContract.View {

    private static MealFragment mMealFragment;

    /**
     * 프래그먼트 상호작용 리스너
     */
    private OnFragmentInteractionListener mListener;

    @BindView(R.id.meal_progress)
    public ProgressBar mProgressView;

    @BindView(R.id.meal_main_layout)
    public View mMainView;

    @BindView(R.id.meal_date_text_view)
    public MealDateTextView mDateTextView;

    @BindView(R.id.meal_text_view)
    public TextView mTextView;

    private IMealContract.Presenter mPresenter;

    public static synchronized MealFragment getInstance() {
        if (mMealFragment == null) {
            mMealFragment = new MealFragment();
        }
        return mMealFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meal, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
    public void onStart() {
        super.onStart();
        if (mPresenter == null) {
            mPresenter = new MealPresenterImpl(this);
        } else {
            mPresenter.loadViewState();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle("급식");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @OnClick(R.id.meal_date_pick_button)
    public void onMealDataPickButtonClick(View view) {
        mPresenter.onDatePickButtonClick();
    }

    @OnClick({ R.id.meal_breakfast_button,
               R.id.meal_lunch_button,
               R.id.meal_dinner_button })
    public void onMealButtonClick(View view) {
        MealType mealType = ((MealButton) view).getMealType();
        mPresenter.onMealButtonClick(mealType);
    }

    @Override
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mMainView.setVisibility(show ? View.GONE : View.VISIBLE);
            mMainView.animate().setDuration(shortAnimTime).alpha(show ? 0 : 1)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mMainView.setVisibility(show ? View.GONE : View.VISIBLE);
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
            mMainView.setVisibility(show ? View.GONE : View.VISIBLE);
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public void showDate(int year, int month, int day, MealType mealType) {
        mDateTextView.setMealDate(year, month, day, mealType);
    }

    @Override
    public void showMeal(String meal) {
        mTextView.setText(meal);
    }

    @Override
    public void showDatePickerDialog(int defaultYear, int defaultMonth, int defaultDay) {
        new DatePickerDialog(getContext(), (view, year, month, day) ->
                mPresenter.onDatePickerDialogDateSet(year, month, day),
                defaultYear, defaultMonth, defaultDay
        ).show();
    }

    @Override
    public void showMessageToast(String msg) {
        try {
            Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Log.d("#showMessageToast", "ToastError");
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}

