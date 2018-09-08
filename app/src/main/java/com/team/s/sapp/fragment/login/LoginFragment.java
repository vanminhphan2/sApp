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
import com.team.s.sapp.api.ApiClient;
import com.team.s.sapp.api.RegisterApi;
import com.team.s.sapp.model.Profile;
import com.team.s.sapp.model.Result;
import com.team.s.sapp.util.AnimateUtils;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.team.s.sapp.util.AnimateUtils.ANIM_DURATION_3;
import static com.team.s.sapp.util.AnimateUtils.goneGroupViewAnim;
import static com.team.s.sapp.util.AnimateUtils.visibleGroupViewAnim;
import static com.team.s.sapp.util.AppUtil.hideKeyboard;

public class LoginFragment extends Fragment {

    public static String TAG = LoginFragment.class.getSimpleName();
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
    MainActivity mainActivity;
    @BindView(R.id.btnBackToLogin)
    Button btnBackToLogin;
    @BindView(R.id.btnBackToGetCode)
    Button btnBackToGetCode;

    private FirebaseAuth auth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks verificationcallback;
    private PhoneAuthProvider.ForceResendingToken resendingToken;
    private String verifyCode;
    private String phoneRegister;
    View[] groupViewGetCodes, groupViewConfirmCodes, groupViewLogins;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        unbinder = ButterKnife.bind(this, view);
        auth = FirebaseAuth.getInstance();
        mainActivity = MainActivity.mainActivity;

        groupViewGetCodes = new View[]{btnBackToLogin, btnGetCode, edtPhoneRegister};
        groupViewLogins = new View[]{btnLogin, btnRegister, edtPhone, edtPass};
        groupViewConfirmCodes = new View[]{edtCode, btnConfirm,btnBackToGetCode};
        return view;
    }

    @OnClick({R.id.btnLogin, R.id.btnRegister, R.id.btnGetCode, R.id.btnBackToLogin,
            R.id.btnConfirm, R.id.btnBackToGetCode})
    void onClickButton(View view) {
        switch (view.getId()) {

            case R.id.btnLogin:

                mainActivity.showLoadingDialog();
                String phone = edtPhone.getText().toString();
                String pass = edtPass.getText().toString();
                login(phone, pass);

                break;

            case R.id.btnRegister:

                AnimateUtils.startMyAnim(groupViewLogins, mainActivity,
                        ANIM_DURATION_3, R.anim.zoom_out, () -> goneGroupViewAnim(groupViewLogins, groupViewLogin));
                groupViewGetCode.setVisibility(View.VISIBLE);
                AnimateUtils.SlideFadeInRight(groupViewGetCodes, mainActivity,
                        ANIM_DURATION_3, () -> groupViewGetCode.setVisibility(View.VISIBLE));
                break;

            case R.id.btnBackToLogin:

                AnimateUtils.SlideFadeOutRight(groupViewGetCodes, mainActivity, ANIM_DURATION_3,
                        () -> goneGroupViewAnim(groupViewGetCodes, groupViewGetCode));
                AnimateUtils.startMyAnim(groupViewLogins, mainActivity, ANIM_DURATION_3, R.anim.zoom_in,
                        () -> visibleGroupViewAnim(groupViewLogins, groupViewLogin));
                break;

            case R.id.btnGetCode:
                phoneRegister = edtPhoneRegister.getText().toString();
                mainActivity.showLoadingDialog();
                checkPhone(phoneRegister);

                break;

            case R.id.btnBackToGetCode:

                AnimateUtils.SlideFadeOutRight(groupViewConfirmCodes, mainActivity, ANIM_DURATION_3,
                        () -> goneGroupViewAnim(groupViewConfirmCodes, groupViewConfirmCode));
                AnimateUtils.startMyAnim(groupViewGetCodes, mainActivity, ANIM_DURATION_3, R.anim.zoom_in,
                        () -> visibleGroupViewAnim(groupViewGetCodes, groupViewGetCode));
                break;

            case R.id.btnConfirm:
                mainActivity.showLoadingDialog();
                verifyCode();
                break;

        }
    }

    public void getCode() {
        phoneRegister = edtPhoneRegister.getText().toString();
        String phone_number = "+84" + phoneRegister;
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
                Log.e(TAG, "get code FireBase: time out");
//                Toast.makeText(getContext(), "Hết thời gian xác nhận!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    Log.e(TAG, "Invalid Credential: " + e.getLocalizedMessage());
                    Toast.makeText(getContext(), "Số điện thoại không hợp lệ!", Toast.LENGTH_SHORT).show();
                    mainActivity.hideLoadingDialog();
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    Log.e(TAG, "SMS exceeded: " + e);
                    Toast.makeText(getContext(), "Mã xác nhận đã được gửi!", Toast.LENGTH_SHORT).show();
                    mainActivity.hideLoadingDialog();
                }
            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                resendingToken = forceResendingToken;
                verifyCode = s;
                mainActivity.hideLoadingDialog();
                AnimateUtils.startMyAnim(groupViewGetCodes, mainActivity,
                        ANIM_DURATION_3, R.anim.zoom_out, () ->
                                goneGroupViewAnim(groupViewGetCodes, groupViewGetCode));
                groupViewConfirmCode.setVisibility(View.VISIBLE);
                AnimateUtils.SlideFadeInRight(groupViewConfirmCodes, mainActivity,
                        ANIM_DURATION_3, () -> groupViewConfirmCode.setVisibility(View.VISIBLE));

                Toast.makeText(getContext(), "Đã gửi mã xác nhận!", Toast.LENGTH_SHORT).show();
            }
        };
    }

    public void verifyCode() {
        String code = edtCode.getText().toString();
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verifyCode, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            Log.e(TAG, "signInWithCredential:success");
                            Toast.makeText(getContext(), "Xác nhận thành công!", Toast.LENGTH_SHORT).show();
                            mainActivity.hideLoadingDialog();
                            groupViewConfirmCode.setVisibility(View.GONE);
                            groupViewLogin.setVisibility(View.VISIBLE);
                            MainActivity.mainActivity.registerPhoneOnFB(phoneRegister);

                        } else {
                            Log.e(TAG, "signInWithCredential:failure", task.getException());
                            mainActivity.hideLoadingDialog();
                            Toast.makeText(getContext(), "Sai mã!", Toast.LENGTH_SHORT).show();
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

    private void checkPhone(String phone) {
        RegisterApi apiClient = ApiClient.getClient().create(RegisterApi.class);
        Call<Result> call = apiClient.checkPhone(phone.trim());
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {

                Log.e("Rio result register", response.body().getStatus().toString());
                if (response.body().getStatus().equals("success")) {
                    Toast.makeText(getContext(), "Số điện thoại đã được sử dụng!", Toast.LENGTH_SHORT).show();
                    mainActivity.hideLoadingDialog();
                } else {
                    getCode();
                }

            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Log.e("Rio registerByPhone", t.toString());
                mainActivity.hideLoadingDialog();
                Toast.makeText(getContext(), "lỗi kết nối server!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void login(String phone, String password) {
        RegisterApi apiClient = ApiClient.getClient().create(RegisterApi.class);
        Call<Result> call = apiClient.login(phone.trim(), password.trim());
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {

                Log.e("Rio result login", response.body().getStatus().toString());
                if (response.body().getStatus().equals("success")) {
                    Toast.makeText(getContext(), "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                    Profile user = response.body().getUserById();
                    hideKeyboard(mainActivity);
                    MainActivity.mainActivity.loginSuccess(user);
                } else if (response.body().getStatus().equals("incorrect phone")) {
                    mainActivity.hideLoadingDialog();
                    Toast.makeText(getContext(), "Số điện thoại này chưa đăng kí!", Toast.LENGTH_SHORT).show();
                } else if (response.body().getStatus().equals("incorrect password")) {
                    mainActivity.hideLoadingDialog();
                    Toast.makeText(getContext(), "Sai mật khẩu!", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Log.e("Rio login error", t.toString());
                mainActivity.hideLoadingDialog();
                Toast.makeText(getContext(), "lỗi kết nối server!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void pressKeyBack() {
        hideKeyboard(mainActivity);
        mainActivity.hideLoadingDialog();
        mainActivity.exitApp();
    }
}