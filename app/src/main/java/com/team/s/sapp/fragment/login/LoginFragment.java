package com.team.s.sapp.fragment.login;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.team.s.sapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class LoginFragment extends Fragment {

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
                break;

            case R.id.btnRegister:
                break;

            case R.id.btnGetCode:
                break;

            case R.id.btnConfirm:
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}