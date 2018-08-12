package com.team.s.sapp.fragment.main.chat;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.daimajia.swipe.util.Attributes;
import com.team.s.sapp.MainActivity;
import com.team.s.sapp.R;
import com.team.s.sapp.adapter.main.chat.BoxAdapter;
import com.team.s.sapp.dialog.NumberPickerDialog;
import com.team.s.sapp.fragment.main.MainFragment;
import com.team.s.sapp.inf.DialogNumberPickerListener;
import com.team.s.sapp.model.Box;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ChatFragment extends Fragment {

    @BindView(R.id.tv_header)
    TextView tvHeader;
    Unbinder unbinder;
    @BindView(R.id.rv_box)
    RecyclerView rvBox;
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
    @BindView(R.id.tv_age)
    TextView tvAge;
    @BindView(R.id.tv_from)
    TextView tvFrom;
    @BindView(R.id.tv_to)
    TextView tvTo;
    @BindView(R.id.tv_category)
    TextView tvCategory;
    @BindView(R.id.group_view_call)
    Group groupViewCall;
    @BindView(R.id.tv_from_age)
    TextView tvFromAge;
    @BindView(R.id.tv_to_age)
    TextView tvToAge;
    @BindView(R.id.tv_value_category)
    TextView tvValueCategory;

    private ArrayList<Box> boxArrayList;
    BoxAdapter boxAdapter;
    LinearLayoutManager linearLayoutManager;
    MainFragment mainFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        unbinder = ButterKnife.bind(this, view);
        if (MainActivity.mainActivity.isLiveMainFragment())
            mainFragment = MainActivity.mainActivity.getMainFragment();
        //temp
        boxArrayList = new ArrayList<>();

        for (int i = 0; i < 15; i++) {
            boxArrayList.add(new Box());
        }
        initRecyclerView(boxArrayList);
        return view;
    }

    public void initRecyclerView(ArrayList<Box> arrayList) {

        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        boxAdapter = new BoxAdapter(arrayList, getContext());
        boxAdapter.setMode(Attributes.Mode.Single);
        rvBox.setLayoutManager(linearLayoutManager);
        rvBox.setAdapter(boxAdapter);
    }

    //Change view layout message to call or in contrast
    //Đổi view tin nhắn sang gọi điện hoặc ngược lại
    public void setChangeLayout() {

        if (rvBox.getVisibility() == View.VISIBLE) {
            rvBox.setVisibility(View.GONE);
            groupViewCall.setVisibility(View.VISIBLE);
            tvHeader.setText("Gọi cho người lạ");
            if (mainFragment != null)
                mainFragment.setChangeIconFab(false);
        } else {
            rvBox.setVisibility(View.VISIBLE);
            groupViewCall.setVisibility(View.GONE);
            tvHeader.setText("Tin nhắn");
            if (mainFragment != null)
                mainFragment.setChangeIconFab(true);
        }
    }

    //Event click fab button
    @OnClick({R.id.tv_from_age,R.id.tv_to_age,R.id.tv_value_category})
    public void onClickFabButton(View view) {

        NumberPickerDialog numberPickerDialog = new NumberPickerDialog(getContext(), 1,80,null);

        switch (view.getId()){

            case R.id.tv_from_age:

                numberPickerDialog.setDialogNumberPickerListener(new DialogNumberPickerListener() {
                    @Override
                    public void onClickYes(int value) {
                        tvFromAge.setText(String.valueOf(value)+" tuổi");
                    }
                });
                numberPickerDialog.show();
                break;

            case R.id.tv_to_age:
                numberPickerDialog.setDialogNumberPickerListener(new DialogNumberPickerListener() {
                    @Override
                    public void onClickYes(int value) {
                        tvFromAge.setText(String.valueOf(value)+" tuổi");
                    }
                });
                numberPickerDialog.show();
                break;

            case R.id.tv_value_category:
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
