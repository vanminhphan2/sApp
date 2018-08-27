package com.team.s.sapp.fragment.utility;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.transition.ArcMotion;
import android.transition.ChangeBounds;
import android.transition.ChangeClipBounds;
import android.transition.ChangeImageTransform;
import android.transition.ChangeTransform;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.team.s.sapp.MainActivity;
import com.team.s.sapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class PreviewImageFragment extends Fragment {
//    @BindView(R.id.imageViewB)
//    RoundedImageView imageViewB;
//    Unbinder unbinder;

    //
    private static final String EXTRA_ANIMAL_ITEM = "animal_item";
    private static final String EXTRA_TRANSITION_NAME = "transition_name";

    private int widthItem;

    public static PreviewImageFragment newInstance(String pathImg, String transitionName) {
        PreviewImageFragment previewImageFragment = new PreviewImageFragment();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_ANIMAL_ITEM, pathImg);
        bundle.putString(EXTRA_TRANSITION_NAME, transitionName);
        previewImageFragment.setArguments(bundle);
        return previewImageFragment;
    }

    //
    public static PreviewImageFragment newInstance() {
        return new PreviewImageFragment();
    }

    public int parseDpToPx(int dp) {

        final float scale = getContext().getResources().getDisplayMetrics().density;
        int px = (int) (dp * scale + 0.5f);
        return px;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        postponeEnterTransition();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setSharedElementEnterTransition(TransitionInflater.from(getContext()).inflateTransition(android.R.transition.move));
            setSharedElementReturnTransition(TransitionInflater.from(getContext()).inflateTransition(R.transition.change_image_transform));
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_preview_image, container, false);

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        widthItem = MainActivity.WIDTHDEVICE - parseDpToPx(100);
        String animalItem = getArguments().getString(EXTRA_ANIMAL_ITEM);
        String transitionName = getArguments().getString(EXTRA_TRANSITION_NAME);

        final ImageView imageView = (ImageView) view.findViewById(R.id.imageViewB);
        int newH = (int) (MainActivity.WIDTHDEVICE * 1.3333333730697632f);
        imageView.setLayoutParams(
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, newH));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            imageView.setTransitionName(transitionName);
        }

        Glide.with(getContext())
                .load(animalItem).thumbnail(0.5f)
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL)
                .onlyRetrieveFromCache(true))
                .into(imageView);

        View v = view.findViewById(R.id.parent);
        v.getBackground().setAlpha(0);
    }
}
