package com.team.s.sapp.util;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class AppUtil {

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void showKeyboard(Activity activity, View view) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);

        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        view.requestFocus();
    }
}
