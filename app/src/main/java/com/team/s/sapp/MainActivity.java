package com.team.s.sapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;

import com.team.s.sapp.fragment.login.LoginFragment;
import com.team.s.sapp.fragment.main.MainFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.frame_main)
    FrameLayout frameMain;
    @BindView(R.id.frame_login)
    FrameLayout frameLogin;

    private LoginFragment loginFragment;
    private MainFragment mainFragment;
    public static MainActivity mainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mainActivity=this;
        replaceLoginFragment();
    }

    private void replaceLoginFragment() {

        loginFragment = new LoginFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_login, loginFragment)
                .commitAllowingStateLoss();
    }

    public void replaceMainFragment() {

        mainFragment = new MainFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_main, mainFragment)
                .commitAllowingStateLoss();
    }
    public void goneLoginFragment(){

        frameLogin.setVisibility(View.GONE);
    }
}
