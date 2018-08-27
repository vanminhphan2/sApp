package com.team.s.sapp.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.TextView;

import com.team.s.sapp.R;

public class BackDialog extends Dialog {

    DialogListener dialogListener;

    public void setDialogListener(DialogListener dialogListener) {
        this.dialogListener = dialogListener;
    }

    public BackDialog(Context context, String title) {
        super(context);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.dialog_back);
        setTitle("Title...");

        TextView text = (TextView) findViewById(R.id.tv_title);
        TextView tvNo = (TextView) findViewById(R.id.btn_cancel);
        TextView tvYes = (TextView) findViewById(R.id.btn_ok);
        text.setText(title);

        tvNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dismiss();
            }
        });
        tvYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {

                dialogListener.onClickYes();
                dismiss();
            }
        });
    }

}
