package com.team.s.sapp.fragment.account;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.team.s.sapp.MainActivity;
import com.team.s.sapp.R;
import com.team.s.sapp.dialog.NumberPickerDialog;
import com.team.s.sapp.inf.DialogNumberPickerListener;
import com.team.s.sapp.inf.MyOnClickItem;
import com.team.s.sapp.model.Profile;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.text.InputType.TYPE_CLASS_TEXT;

public class EditProfileFragment extends Fragment {


    @BindView(R.id.tv_header)
    TextView tvHeader;
    @BindView(R.id.img_user_edit)
    RoundedImageView imgUserEdit;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.edt_user_name)
    EditText edtUserName;
    @BindView(R.id.tv_gender)
    TextView tvGender;
    @BindView(R.id.tv_male)
    TextView tvMale;
    @BindView(R.id.cb_male)
    CheckBox cbMale;
    @BindView(R.id.tv_female)
    TextView tvFemale;
    @BindView(R.id.cb_female)
    CheckBox cbFemale;
    @BindView(R.id.tv_birth_year)
    TextView tvBirthYear;
    @BindView(R.id.birth_year)
    TextView birthYear;
    @BindView(R.id.tv_input_pass)
    TextView tvInputPass;
    @BindView(R.id.edt_password)
    EditText edtPassword;
    @BindView(R.id.btn_save)
    Button btnSave;
    Unbinder unbinder;
    @BindView(R.id.img_show_pass)
    ImageView imgShowPass;

    private String phoneRegister;
    private Profile profile;

    public static EditProfileFragment newInstance(String phone) {
        EditProfileFragment editProfileFragment = new EditProfileFragment();
        Bundle args = new Bundle();
        args.putString("PHONE_REGISTER", phone);
        editProfileFragment.setArguments(args);
        return editProfileFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        unbinder = ButterKnife.bind(this, view);
        if (getArguments() != null) {
            phoneRegister = getArguments().getString("PHONE_REGISTER", "");
        } else {
            phoneRegister = "";
        }
        setShowPass();
        return view;
    }

    //Event click
    @OnClick({R.id.birth_year, R.id.cb_male, R.id.cb_female, R.id.btn_save, R.id.img_user_edit})
    public void onClick(View view) {

        NumberPickerDialog numberPickerDialog = new NumberPickerDialog(getContext(), 1950, 2011, null);
        switch (view.getId()) {

            case R.id.img_user_edit:
                MainActivity.mainActivity.getSingleImageFromGallery("Bạn muốn chọn ảnh này?");
                MainActivity.mainActivity.setOnClickChooseImg(new MyOnClickItem() {
                    @Override
                    public void onClickItem(View view, Object object, int position) {
                        String pathImg = (String) object;
                        if (pathImg != null) {
                            if (!pathImg.equals("")) {
                                Glide.with(getContext())
                                        .load(pathImg)
                                        .into(imgUserEdit);
                                if(profile==null)
                                    profile=new Profile();
                                profile.setImgUser(pathImg);
                            }
                        }
                    }
                });

                break;

            case R.id.birth_year:

                numberPickerDialog.setDialogNumberPickerListener(new DialogNumberPickerListener() {
                    @Override
                    public void onClickYes(int value) {
                        birthYear.setText(String.valueOf(value));
                    }
                });
                numberPickerDialog.show();
                break;

            case R.id.cb_male:
                cbFemale.setChecked(false);
                cbMale.setChecked(true);
                break;

            case R.id.cb_female:
                cbMale.setChecked(false);
                cbFemale.setChecked(true);
                break;

            case R.id.btn_save:
                MainActivity.mainActivity.loadingDialog.show();
                if (checkProfile()) {
                    if (profile == null)
                        profile = new Profile();
                    setUserProfile();
                    MainActivity.mainActivity.createOrEditUserProfile(profile);
                }
                break;
        }
    }

    //set event show or hide pass when click icon show
    private void setShowPass() {
        imgShowPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtPassword.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                    edtPassword.setInputType(InputType.TYPE_CLASS_TEXT |
                            InputType.TYPE_TEXT_VARIATION_PASSWORD);
                } else {
                    edtPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }
                edtPassword.setSelection(edtPassword.getText().length());
            }
        });
    }

    private void setUserProfile() {

        profile.setInfo(true);
        profile.setUserName(edtUserName.getText().toString());
        if (cbFemale.isChecked())
            profile.setGender(0);
        else profile.setGender(1);
        profile.setPassword(edtPassword.getText().toString());
        if (phoneRegister != null && !phoneRegister.equals("")) {
            profile.setPhone(phoneRegister);
        }
    }

    private boolean checkProfile() {

        if (edtUserName.getText().length() < 3) {
            Toast.makeText(getContext(), "Tên ít nhất 3 ký tự!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!cbFemale.isChecked() && !cbMale.isChecked()) {
            Toast.makeText(getContext(), "Chọn giới tính!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (birthYear.getText().length() < 1) {
            Toast.makeText(getContext(), "Chọn năm sinh!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (edtPassword.getText().length() < 6) {
            Toast.makeText(getContext(), "Password phải ít nhất 6 ký tự", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
