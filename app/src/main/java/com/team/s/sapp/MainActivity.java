package com.team.s.sapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.team.s.sapp.fragment.login.LoginFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        replaceLoginFragment();
    }

    private void replaceLoginFragment() {

        LoginFragment loginFragment = new LoginFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame, loginFragment)
                .commitAllowingStateLoss();
    }
}
