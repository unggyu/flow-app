package kr.hs.dgsw.flow.view.register;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import kr.hs.dgsw.flow.R;
import kr.hs.dgsw.flow.view.login.LoginActivity;
import kr.hs.dgsw.flow.view.register.presenter.IRegisterContract;
import kr.hs.dgsw.flow.view.register.presenter.RegisterPresenterImpl;

public class RegisterActivity extends AppCompatActivity implements IRegisterContract.View {

    private IRegisterContract.Presenter mPresenter;

    @BindView(R.id.register_name_layout)
    public TextInputLayout mNameLayout;

    @BindView(R.id.register_name)
    public TextInputEditText mNameView;

    @BindView(R.id.register_email_layout)
    public TextInputLayout mEmailLayout;

    @BindView(R.id.register_email)
    public TextInputEditText mEmailView;

    @BindView(R.id.register_password_layout)
    public TextInputLayout mPasswordLayout;

    @BindView(R.id.register_password)
    public TextInputEditText mPasswordView;

    @BindView(R.id.register_confirm_password_layout)
    public TextInputLayout mConfirmPasswordLayout;

    @BindView(R.id.register_confirm_password)
    public TextInputEditText mConfirmPasswordView;

    @BindView(R.id.register_number_layout)
    public TextInputLayout mNumberLayout;

    @BindView(R.id.register_number)
    public TextInputEditText mNumberView;

    @BindView(R.id.register_cellphone_layout)
    public TextInputLayout mCellphoneLayout;

    @BindView(R.id.register_cellphone)
    public TextInputEditText mCellphoneView;

    @BindView(R.id.register_gender)
    public RadioGroup mGenderView;

    @BindView(R.id.register_class_spinner)
    public Spinner mClassView;

    @BindView(R.id.register_progress)
    public ProgressBar mProgressView;

    @BindView(R.id.register_form)
    public View mRegisterFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ButterKnife.bind(this);

        this.mPresenter = new RegisterPresenterImpl();
        mPresenter.setView(this);
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDestroy();
        super.onDestroy();
    }

    @OnTextChanged(value = R.id.register_name, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void afterNameInput(Editable editable) {
        mPresenter.validateName(editable.toString());
    }

    @OnTextChanged(value = R.id.register_number, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void afterNumberInput(Editable editable) {
        mPresenter.validateNumber(editable.toString());
    }

    @OnTextChanged(value = R.id.register_email, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void afterEmailInput(Editable editable) {
        mPresenter.validateEmail(editable.toString());
    }

    @OnTextChanged(value = R.id.register_cellphone, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void afterCellphoneInput(Editable editable) {
        mPresenter.validateCellphone(editable.toString());
    }

    @OnTextChanged(value = R.id.register_password, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void afterPasswordInput(Editable editable) {
        mPresenter.validatePassword(editable.toString());
    }

    @OnTextChanged(value = R.id.register_confirm_password, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void afterConfirmPasswordInput(Editable editable) {
        mPresenter.validateConfirmPassword(editable.toString());
    }

    @OnClick(R.id.register_button)
    public void onRegisterButtonClick(View view) {
        mPresenter.validateRegisterFields(
                ((RadioButton) findViewById(mGenderView.getCheckedRadioButtonId())).getText().toString(),
                mClassView.getSelectedItem().toString());
    }

    @Override
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mRegisterFormView.animate().setDuration(shortAnimTime).alpha(show ? 0 : 1)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
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
            mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public void setNameError(String msg) {
        mNameLayout.setError(msg);
    }

    @Override
    public void setNumberError(String msg) {
        mNumberLayout.setError(msg);
    }

    @Override
    public void setCellphoneError(String msg) {
        mCellphoneLayout.setError(msg);
    }

    @Override
    public void setEmailError(String msg) {
        mEmailLayout.setError(msg);
    }

    @Override
    public void setPasswordError(String msg) {
        mPasswordLayout.setError(msg);
    }

    @Override
    public void setConfirmPasswordError(String msg) {
        mConfirmPasswordLayout.setError(msg);
    }

    @Override
    public void setNameErrorEnabled(boolean enabled) {
        mNameLayout.setErrorEnabled(enabled);
    }

    @Override
    public void setNumberErrorEnabled(boolean enabled) {
        mNumberLayout.setErrorEnabled(enabled);
    }

    @Override
    public void setCellphoneErrorEnabled(boolean enabled) {
        mCellphoneLayout.setErrorEnabled(enabled);
    }

    @Override
    public void setEmailErrorEnabled(boolean enabled) {
        mEmailLayout.setErrorEnabled(enabled);
    }

    @Override
    public void setPasswordErrorEnabled(boolean enabled) {
        mPasswordLayout.setErrorEnabled(enabled);
    }

    @Override
    public void setConfirmPasswordErrorEnabled(boolean enabled) {
        mConfirmPasswordLayout.setErrorEnabled(enabled);
    }

    @Override
    public void showMessageToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void navigateToLogin() {
        this.finish();
    }
}
