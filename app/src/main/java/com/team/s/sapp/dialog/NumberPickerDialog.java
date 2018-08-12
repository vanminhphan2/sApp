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

public class NumberPickerDialog extends Dialog {

    DialogNumberPickerListener dialogNumberPickerListener;

    public void setDialogNumberPickerListener(DialogNumberPickerListener dialogNumberPickerListener) {
        this.dialogNumberPickerListener = dialogNumberPickerListener;
    }

    public NumberPickerDialog(Context context, int minValue, int maxValue, String[] strings) {
        super(context);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.dialog_number_picker);

        final NumberPicker numberPicker = (NumberPicker) findViewById(R.id.number_picker);
        Button btn_ok = (Button) findViewById(R.id.btn_ok);
        Button btn_cancle = (Button) findViewById(R.id.btn_cancel);

        numberPicker.setMaxValue(maxValue);
        numberPicker.setMinValue(minValue);
        if (strings != null) {
            numberPicker.setDisplayedValues(strings);
            numberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        }

        btn_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dismiss();
            }
        });
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {

                dialogNumberPickerListener.onClickYes(numberPicker.getValue());
                dismiss();
            }
        });
    }

}
