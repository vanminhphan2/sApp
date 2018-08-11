package com.team.s.sapp.fragment.login;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.team.s.sapp.MainActivity;
import com.team.s.sapp.R;
import com.team.s.sapp.fragment.main.MainFragment;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class LoginFragment extends Fragment {

    public static String TAG= LoginFragment.class.getSimpleName();
    @BindView(R.id.edtPhone)
    EditText edtPhone;
    @BindView(R.id.edtPass)
    EditText edtPass;
    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.btnRegister)
    Button btnRegister;
    @BindView(R.id.groupViewLogin)
    Group groupViewLogin;
    @BindView(R.id.edtPhoneRegister)
    EditText edtPhoneRegister;
    @BindView(R.id.btnGetCode)
    Button btnGetCode;
    @BindView(R.id.groupViewGetCode)
    Group groupViewGetCode;
    @BindView(R.id.edtCode)
    EditText edtCode;
    @BindView(R.id.btnConfirm)
    Button btnConfirm;
    @BindView(R.id.groupViewConfirmCode)
    Group groupViewConfirmCode;
    Unbinder unbinder;

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks verificationcallback;
    private PhoneAuthProvider.ForceResendingToken resendingToken;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @OnClick({R.id.btnLogin, R.id.btnRegister, R.id.btnGetCode, R.id.btnConfirm})
    void onClickButton(View view) {
        switch (view.getId()) {

            case R.id.btnLogin:

                MainActivity.mainActivity.replaceMainFragment();
                MainActivity.mainActivity.goneLoginFragment();
                break;

            case R.id.btnRegister:
                groupViewLogin.setVisibility(View.GONE);
                groupViewGetCode.setVisibility(View.VISIBLE);
                break;

            case R.id.btnGetCode:
                getCode();
                groupViewGetCode.setVisibility(View.GONE);
                groupViewConfirmCode.setVisibility(View.VISIBLE);
                break;

            case R.id.btnConfirm:
                groupViewConfirmCode.setVisibility(View.GONE);
                groupViewLogin.setVisibility(View.VISIBLE);
                break;
        }
    }

    public void getCode() {
        String phone_number = "+84"+edtPhoneRegister.getText().toString();

        setupVerificationCallback();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phone_number,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                getActivity(),               // Activity (for callback binding)
                verificationcallback);        // OnVerificationStateChangedCallbacks
    }

    private void setupVerificationCallback() {

        verificationcallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                //time out
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

                if (e instanceof FirebaseAuthInvalidCredentialsException)
                {
                    Log.d(TAG,"Invalid Credential: " + e.getLocalizedMessage());
                }
                else if (e instanceof FirebaseTooManyRequestsException)
                {
                    Log.d(TAG,"SMS exceeded: " + e);
                }
            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                resendingToken = forceResendingToken;
            }
        };
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}