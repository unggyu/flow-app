package kr.hs.dgsw.flow.view.out;

import android.app.DatePickerDialog;
import android.app.NotificationManager;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.honorato.multistatetogglebutton.MultiStateToggleButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import kr.hs.dgsw.flow.R;
import kr.hs.dgsw.flow.util.fcm.FlowMessagingService;
import kr.hs.dgsw.flow.view.main.MainActivity;
import kr.hs.dgsw.flow.view.out.custom.OutRadioButton;
import kr.hs.dgsw.flow.view.out.model.Enum.OutType;
import kr.hs.dgsw.flow.view.out.presenter.IOutContract;
import kr.hs.dgsw.flow.view.out.presenter.OutPresenterImpl;

public class OutFragment extends Fragment implements IOutContract.View {

    private static OutFragment mOutFragment;

    private OnFragmentInteractionListener mListener;

    private IOutContract.Presenter mPresenter;

    @BindView(R.id.out_state_button)
    public MultiStateToggleButton mOutStateButton;

    @BindView(R.id.out_out_date_time_text)
    public TextView mOutDateTimeView;

    @BindView(R.id.out_in_date_time_text)
    public TextView mInDateTimeView;

    @BindView(R.id.out_reason_layout)
    public TextInputLayout mReasonLayout;

    public static synchronized OutFragment getInstance() {
        if (mOutFragment == null) {
            mOutFragment = new OutFragment();
        }
        return mOutFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_out, container, false);

        ButterKnife.bind(this, view);

        mOutStateButton.setElements(R.array.out, 0);
        mOutStateButton.setOnValueChangedListener((value ->
            mPresenter.onOutStateButtonValueChanged(OutType.values()[value])));

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mPresenter == null) {
            mPresenter = new OutPresenterImpl(this, getContext());
        } else {
            // presenter가 이미 존재 한다면 뷰에 다시 뿌려줌
            mPresenter.loadViewState();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle("외출/외박");
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
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @OnClick(R.id.out_out_date_button)
    public void onOutDateButtonClick(View view) {
        mPresenter.onOutDateButtonClick();
    }

    @OnClick(R.id.out_out_time_button)
    public void onOutTimeButtonClick(View view) {
        mPresenter.onOutTimeButtonClick();
    }

    @OnClick(R.id.out_in_date_button)
    public void onInDateButtonClick(View view) {
        mPresenter.onInDateButtonClick();
    }

    @OnClick(R.id.out_in_time_button)
    public void onInTimeButtonClick(View view) {
        mPresenter.onInTimeButtonClick();
    }

    @OnClick(R.id.out_apply_button)
    public void onApplyButtonClick(View view) {
        mPresenter.applyOut();
    }

    @OnTextChanged(value = R.id.out_reason_text, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void afterReasonTextInput(Editable editable) {
        if (mPresenter != null) {
            mPresenter.validReason(editable.toString());
        }
    }

    @Override
    public void showMessageToast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showOutDateTime(String dateTime) {
        mOutDateTimeView.setText(dateTime);
    }

    @Override
    public void showInDateTime(String dateTime) {
        mInDateTimeView.setText(dateTime);
    }

    @Override
    public void showOutDatePickerDialog(int defaultYear, int defaultMonth, int defaultDay) {
        new DatePickerDialog(getContext(), (view, year, month, dayOfMonth) ->
                mPresenter.onOutDateSet(year, month, dayOfMonth),
                defaultYear, defaultMonth, defaultDay
        ).show();
    }

    @Override
    public void showOutTimePickerDialog(int defaultHour, int defaultMinute) {
        new TimePickerDialog(getContext(), (view, hour, minute) ->
                mPresenter.onOutTimeSet(hour, minute),
                defaultHour, defaultMinute, true
        ).show();
    }

    @Override
    public void showInDatePickerDialog(int defaultYear, int defaultMonth, int defaultDay) {
        new DatePickerDialog(getContext(), (view, year, month, dayOfMonth) ->
                mPresenter.onInDateSet(year, month, dayOfMonth),
                defaultYear, defaultMonth, defaultDay
        ).show();
    }

    @Override
    public void showInTimePickerDialog(int defaultHour, int defaultMinute) {
        new TimePickerDialog(getContext(), (view, hour, minute) ->
                mPresenter.onInTimeSet(hour, minute),
                defaultHour, defaultMinute, true
        ).show();
    }

    @Override
    public void setReasonError(String msg) {
        mReasonLayout.setError(msg);
    }

    @Override
    public void setReasonErrorEnabled(boolean enabled) {
        mReasonLayout.setErrorEnabled(enabled);
    }

    @Override
    public void focusReason() {
        mReasonLayout.requestFocus();
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
