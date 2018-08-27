package com.team.s.sapp.fragment.main.chat;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.ArcMotion;
import android.transition.ChangeBounds;
import android.transition.ChangeTransform;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.makeramen.roundedimageview.RoundedImageView;
import com.team.s.sapp.MainActivity;
import com.team.s.sapp.R;
import com.team.s.sapp.adapter.main.chat.MessageAdapter;
import com.team.s.sapp.fragment.utility.PreviewImageFragment;
import com.team.s.sapp.inf.MyOnClickItem;
import com.team.s.sapp.model.Box;
import com.team.s.sapp.model.Message;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ChatBoxFragment extends Fragment implements MyOnClickItem{

    @BindView(R.id.tv_header)
    TextView tvHeader;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.img_user)
    RoundedImageView imgUser;
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.img_call)
    ImageView imgCall;
    @BindView(R.id.rv_chat)
    RecyclerView rvChat;

    LinearLayoutManager linearLayoutManager;
    MessageAdapter mAdapter;
    private ArrayList<Message> messages;
    Unbinder unbinder;

    public static ChatBoxFragment newInstance(Box box) {
        ChatBoxFragment boxChatFragment = new ChatBoxFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("Box", box);
        boxChatFragment.setArguments(bundle);
        return boxChatFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_chatbox, container, false);

        unbinder = ButterKnife.bind(this, view);
        messages = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Message message = new Message();
            if (i == 0) {
                message.setMessImage("https://firebasestorage.googleapis.com/v0/b/fir-app-fcaf6.appspot.com/o/ImageUser%2F2018-08-20%2009%3A28%3A24.png?alt=media&token=e74f7b10-23da-4602-a8a4-85ffc65fa16e");
                message.setRatioImage(1.3333333730697632f);
            } else if(i==3){
                message.setMessImage("https://firebasestorage.googleapis.com/v0/b/fir-app-fcaf6.appspot.com/o/ImageUser%2F2018-08-22%2010%3A55%3A32.png?alt=media&token=cff9af6f-01f4-4870-a5ce-98d7b8925932");
                message.setRatioImage(0.7323076725006104f);
            }else if(i==5){
                message.setMessImage("https://firebasestorage.googleapis.com/v0/b/fir-app-fcaf6.appspot.com/o/ImageUser%2F2018-08-20%2008%3A52%3A53.png?alt=media&token=ce2b29e7-08ad-4403-b382-917af358b184");
                message.setRatioImage(1.7777777910232544f);
            }else if(i==7){
                message.setMessImage("https://firebasestorage.googleapis.com/v0/b/fir-app-fcaf6.appspot.com/o/ImageUser%2F2018-08-22%2011%3A02%3A04.png?alt=media&token=67fc58a1-27b3-4154-bdbf-0d29f133dd9e");
                message.setRatioImage(0.5600000023841858f);
            }else if(i==13){
                message.setMessImage("https://firebasestorage.googleapis.com/v0/b/fir-app-fcaf6.appspot.com/o/ImageUser%2F2018-08-22%2011%3A02%3A45.png?alt=media&token=0c97bc26-0e37-4e95-b4f3-24fc1c6c66a6");
                message.setRatioImage(0.5625f);
            }else if(i==16){
                message.setMessImage("https://firebasestorage.googleapis.com/v0/b/fir-app-fcaf6.appspot.com/o/ImageUser%2F2018-08-22%2011%3A03%3A50.png?alt=media&token=af5cd093-be2d-49a0-9071-52dcbbc1cbfc");
                message.setRatioImage(0.75f);
            }else {
                message.setMessImage("");
                message.setMessText("hello, how are you?");
            }
            if ((i % 5) == 0)
                message.setIdSender("ABC");
            else message.setIdSender("AAA");
            message.setTimeSend("20/01/2019");
            messages.add(message);
        }
        initRecycler(messages);
        return view;
    }

    public void initRecycler(ArrayList<Message> arrayList) {
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.setStackFromEnd(true);
        rvChat.setLayoutManager(linearLayoutManager);
        mAdapter = new MessageAdapter(arrayList, getContext(),this);
        rvChat.setAdapter(mAdapter);

//        mAdapter.setOnClickItem(new MyOnClickItem() {
//            @Override
//            public void onClickItem(View view, Object object, int position) {
//
////                Fragment animalDetailFragment = PreviewImageFragment.newInstance((String) object, ViewCompat.getTransitionName(view));
////                getFragmentManager()
////                        .beginTransaction()
////                        .addSharedElement(view, ViewCompat.getTransitionName(view))
////                        .addToBackStack("TAG")
////                        .replace(R.id.frame_chat, animalDetailFragment)
////                        .commit();
////                MainActivity.mainActivity.showImageTranslation((RoundedImageView) view);
//            }
//        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClickItem(View view, Object object, int position) {
//        Fragment animalDetailFragment = PreviewImageFragment.newInstance((String) object, ViewCompat.getTransitionName(view));
//        getFragmentManager()
//                .beginTransaction()
//                .addSharedElement(view, ViewCompat.getTransitionName(view))
//                .addToBackStack("TAG")
//                .replace(R.id.frame_chat, animalDetailFragment)
//                .commit();

        MainActivity.mainActivity.onClickImg(view, (String) object);
    }
}
