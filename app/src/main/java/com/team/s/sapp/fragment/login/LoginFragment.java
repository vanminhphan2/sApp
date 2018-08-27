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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.team.s.sapp.MainActivity;
import com.team.s.sapp.R;
import com.team.s.sapp.dialog.LoadingDialog;

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
    LoadingDialog loadingDialog;
    private FirebaseAuth auth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks verificationcallback;
    private PhoneAuthProvider.ForceResendingToken resendingToken;
    private String verifyCode;
    private String phoneRegister;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        unbinder = ButterKnife.bind(this, view);
        auth = FirebaseAuth.getInstance();
        loadingDialog=new LoadingDialog(getContext());
        return view;
    }

    @OnClick({R.id.btnLogin, R.id.btnRegister, R.id.btnGetCode, R.id.btnConfirm})
    void onClickButton(View view) {
        switch (view.getId()) {

            case R.id.btnLogin:

//                MainActivity.mainActivity.replaceMainFragment();
//                MainActivity.mainActivity.replaceEditProfileFragment();
                MainActivity.mainActivity.loadingDialog.show();
                MainActivity.mainActivity.login(edtPhone.getText().toString(),edtPass.getText().toString());

                break;

            case R.id.btnRegister:
                groupViewLogin.setVisibility(View.GONE);
                groupViewGetCode.setVisibility(View.VISIBLE);
                break;

            case R.id.btnGetCode:
                phoneRegister=edtPhoneRegister.getText().toString();
                loadingDialog.show();
                getCode();
                break;

            case R.id.btnConfirm:
                loadingDialog.show();
                verifyCode();
                break;
        }
    }

    public void getCode() {
        phoneRegister=edtPhoneRegister.getText().toString();
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
                Log.e(TAG,"get code FireBase: time out");
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

                if (e instanceof FirebaseAuthInvalidCredentialsException)
                {
                    Log.e(TAG,"Invalid Credential: " + e.getLocalizedMessage());
                }
                else if (e instanceof FirebaseTooManyRequestsException)
                {
                    Log.e(TAG,"SMS exceeded: " + e);
                }
            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                resendingToken = forceResendingToken;
                verifyCode=s;
                loadingDialog.hide();
                groupViewGetCode.setVisibility(View.GONE);
                groupViewConfirmCode.setVisibility(View.VISIBLE);
            }
        };
    }

    public void verifyCode()
    {
        String code = edtCode.getText().toString();
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verifyCode, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful())
                        {
                            Log.e(TAG, "signInWithCredential:success");
                            Toast.makeText(getContext(), "Xác nhận thành công!", Toast.LENGTH_SHORT).show();
                            loadingDialog.hide();
                            groupViewConfirmCode.setVisibility(View.GONE);
                            groupViewLogin.setVisibility(View.VISIBLE);
                            MainActivity.mainActivity.registerPhoneOnFB(phoneRegister);

                        }
                        else {
                            Log.e(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {

                            }
                        }
                    }
                });
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void loginFail() {
        Toast.makeText(getContext(), "Sai tài khoảng hoặc mật khẩu!", Toast.LENGTH_SHORT).show();
    }
}