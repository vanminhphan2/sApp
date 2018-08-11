package com.team.s.sapp.fragment.main.chat;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daimajia.swipe.util.Attributes;
import com.team.s.sapp.R;
import com.team.s.sapp.adapter.main.chat.BoxAdapter;
import com.team.s.sapp.model.Box;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ChatFragment extends Fragment {

    @BindView(R.id.tv_header)
    TextView tvHeader;
    Unbinder unbinder;
    @BindView(R.id.rv_box)
    RecyclerView rvBox;

    private ArrayList<Box> boxArrayList;
    BoxAdapter boxAdapter;
    LinearLayoutManager linearLayoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        unbinder = ButterKnife.bind(this, view);

        //temp
        boxArrayList= new ArrayList<>();

        for (int i=0; i<15;i++){
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
