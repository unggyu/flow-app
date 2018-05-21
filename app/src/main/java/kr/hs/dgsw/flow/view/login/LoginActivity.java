package kr.hs.dgsw.flow.view.login;

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
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import butterknife.OnTextChanged;
import kr.hs.dgsw.flow.R;
import kr.hs.dgsw.flow.view.login.presenter.ILoginContract;
import kr.hs.dgsw.flow.view.login.presenter.LoginPresenterImpl;
import kr.hs.dgsw.flow.view.main.MainActivity;
import kr.hs.dgsw.flow.view.register.RegisterActivity;

public class LoginActivity extends AppCompatActivity implements ILoginContract.View {

    private ILoginContract.Presenter mPresenter;

    @BindView(R.id.login_progress)
    public ProgressBar mProgressView;

    @BindView(R.id.login_email_layout)
    public TextInputLayout mEmailLayout;

    @BindView(R.id.login_email)
    public TextInputEditText mEmailView;

    @BindView(R.id.login_password_layout)
    public TextInputLayout mPasswordLayout;

    @BindView(R.id.login_password)
    public TextInputEditText mPasswordView;

    @BindView(R.id.login_form)
    public View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        mPresenter = new LoginPresenterImpl(this, getApplicationContext());
    }

    @OnTextChanged(value = R.id.login_email, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void afterEmailInput(Editable editable) {
        mPresenter.validateEmail(editable.toString());
    }

    @OnTextChanged(value = R.id.login_password, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void afterPasswordInput(Editable editable) {
        mPresenter.validatePassword(editable.toString());
    }

    @OnEditorAction(R.id.login_password)
    public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            mPresenter.validateLoginFields();
            return true;
        }
        return false;
    }

    @OnClick(R.id.login_button)
    public void onLoginButtonClick(View view) {
        mPresenter.validateLoginFields();
    }

    @OnClick(R.id.login_sign_up_link)
    public void onSignUpLinkClick(View view) {
        navigateToRegister();
    }

    @Override
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(show ? 0 : 1)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
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
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        }
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
    public void setEmailErrorEnabled(final boolean enabled) {
        mEmailLayout.setErrorEnabled(enabled);
    }

    @Override
    public void setPasswordErrorEnabled(final boolean enabled) {
        mPasswordLayout.setErrorEnabled(enabled);
    }

    @Override
    public void showMessageToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void navigateToRegister() {
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        this.startActivity(intent);
    }

    @Override
    public void navigateToMain() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        this.startActivity(intent);
        this.finish();
    }
}
