package com.team.s.sapp.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import com.team.s.sapp.R;
import com.team.s.sapp.inf.DialogNumberPickerListener;

public class LoadingDialog extends Dialog {

    public LoadingDialog(Context context) {
        super(context);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.dialog_loading);
    }
}
