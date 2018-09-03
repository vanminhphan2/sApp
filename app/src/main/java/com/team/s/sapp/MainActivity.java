package com.team.s.sapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.transition.ArcMotion;
import android.transition.ChangeBounds;
import android.transition.ChangeClipBounds;
import android.transition.ChangeImageTransform;
import android.transition.ChangeTransform;
import android.transition.Fade;
import android.transition.TransitionInflater;
import android.transition.TransitionSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.makeramen.roundedimageview.RoundedImageView;
import com.team.s.sapp.activity.ImageViewerActivity;
import com.team.s.sapp.dialog.BackDialog;
import com.team.s.sapp.dialog.DialogListener;
import com.team.s.sapp.dialog.LoadingDialog;
import com.team.s.sapp.fragment.account.EditProfileFragment;
import com.team.s.sapp.fragment.login.LoginFragment;
import com.team.s.sapp.fragment.main.MainFragment;
import com.team.s.sapp.fragment.main.chat.ChatBoxFragment;
import com.team.s.sapp.fragment.utility.GalleryFragment;
import com.team.s.sapp.fragment.utility.PreviewImageFragment;
import com.team.s.sapp.inf.MyOnClickItem;
import com.team.s.sapp.model.Box;
import com.team.s.sapp.model.Message;
import com.team.s.sapp.model.Profile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    static public int WIDTHDEVICE = 0;
    private static final String ALLOWED_CHARACTERS = "0123456789qwertyuiopasdfghjklzxcvbnm";
    private static final int PERMISION_READ_EXTERNAL_STORAGE = 1;

    @BindView(R.id.frame_main)
    FrameLayout frameMain;
    @BindView(R.id.frame_login)
    FrameLayout frameLogin;
    @BindView(R.id.frame_edit_profile)
    FrameLayout frame_edit_profile;

    private ChatBoxFragment chatBoxFragment;
    private LoginFragment loginFragment;
    private MainFragment mainFragment;
    private EditProfileFragment editProfileFragment;
    public static MainActivity mainActivity;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference userRef;
    private DatabaseReference boxRef;
    public StorageReference storageRef;
    private FirebaseStorage storage;

    public Profile user;
    private ArrayList<Box> listBoxFireBase;
    public String pathImg;

    private MyOnClickItem onClickChooseImg;
    public LoadingDialog loadingDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        WIDTHDEVICE = Resources.getSystem().getDisplayMetrics().widthPixels;
        mainActivity = this;
        checkPermision();

        firebaseDatabase = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        userRef = firebaseDatabase.getReference("Users");
        boxRef = firebaseDatabase.getReference("Boxs");
        loadingDialog = new LoadingDialog(this);
        replaceLoginFragment();
//        replaceChatFragment();

    }

    public void showDialog() {
        if (loadingDialog != null && !loadingDialog.isShowing()) {
            loadingDialog.show();
        }
    }

    public void hideDialog() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.hide();
        }
    }

    public void onClickImg(View v, String linkImg) {

        Intent intent = new Intent(MainActivity.this, ImageViewerActivity.class);
        intent.putExtra("LINK_IMAGE", linkImg);
        ActivityOptionsCompat options = ActivityOptionsCompat
                .makeSceneTransitionAnimation(MainActivity.this,
                        v, "image");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            startActivity(intent, options.toBundle());
        }

    }

    private void replaceLoginFragment() {

        loginFragment = new LoginFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_login, loginFragment)
                .commitAllowingStateLoss();
    }

    private void replaceChatFragment() {

        chatBoxFragment = new ChatBoxFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_chat, chatBoxFragment)
                .commitAllowingStateLoss();
    }

    public void replaceMainFragment(Profile profile) {

        mainFragment = MainFragment.newInstance(profile);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_main, mainFragment)
                .commitAllowingStateLoss();
        if (loadingDialog.isShowing())
            loadingDialog.hide();
    }

    public void replaceEditProfileFragment() {

        editProfileFragment = new EditProfileFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_edit_profile, editProfileFragment)
                .commitAllowingStateLoss();
    }

    public void replaceEditProfileFragment(String phone) {

        editProfileFragment = EditProfileFragment.newInstance(phone);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_edit_profile, editProfileFragment)
                .commitAllowingStateLoss();
    }

    public void removeMainFragment() {

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frame_main);
        if (fragment != null && fragment instanceof MainFragment) {
            getSupportFragmentManager().beginTransaction().remove(fragment).commitAllowingStateLoss();
            frameMain.setVisibility(View.GONE);
        }
    }

    public void removeEditProfileFragment() {

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frame_edit_profile);
        if (fragment != null && fragment instanceof EditProfileFragment) {
            getSupportFragmentManager().beginTransaction().remove(fragment).commitAllowingStateLoss();
            frame_edit_profile.setVisibility(View.GONE);
        }
    }

    public void removeLoginFragment() {

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frame_login);
        if (fragment != null && fragment instanceof LoginFragment) {
            getSupportFragmentManager().beginTransaction().remove(fragment).commitAllowingStateLoss();
            frameLogin.setVisibility(View.GONE);
        }
    }

    public boolean isLiveMainFragment() {
        if (mainFragment != null)
            return true;
        else return false;
    }

    public MainFragment getMainFragment() {
        return mainFragment;
    }

    public ArrayList<Box> getBoxsFirebase() {

        boxRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listBoxFireBase = new ArrayList<>();
                Box box = new Box();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    box = postSnapshot.getValue(Box.class);
                    ArrayList<Message> messageArrayList = new ArrayList<>();
                    for (DataSnapshot dataSnapshot1 : postSnapshot.child("message").getChildren())
                        messageArrayList.add(dataSnapshot1.getValue(Message.class));
                    box.setMessages(messageArrayList);
                    listBoxFireBase.add(box);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
        return listBoxFireBase;
    }

    public void registerPhoneOnFB(String phone) {

        userRef.child(phone).child("info").setValue(false);
        replaceEditProfileFragment(phone);
        removeLoginFragment();
    }

    public void finishRegister(Profile profile) {

//        String idUser = getRandomString(5);
//        profile.setId(idUser);
        user = profile;
//        uploadImageToFireBase(profile.getImgUser(),idUser);
//        userRef.child(idUser).setValue(profile);
        replaceMainFragment(user);
        removeEditProfileFragment();
    }

    //Create a string with number param was input
    public static String getRandomString(final int sizeOfRandomString) {
        final Random random = new Random();
        final StringBuilder sb = new StringBuilder(sizeOfRandomString);
        for (int i = 0; i < sizeOfRandomString; ++i)
            sb.append(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length())));
        return sb.toString();
    }

    public void getSingleImageFromGallery(final String tittle) {

        final String[] img = {""};
        final GalleryFragment galleryFragment = new GalleryFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frm_gallery, galleryFragment)
                .commitAllowingStateLoss();

        galleryFragment.setOnClick(new MyOnClickItem() {
            @Override
            public void onClickItem(View view, Object object, int position) {
                getSupportFragmentManager().beginTransaction().remove(galleryFragment).commitAllowingStateLoss();
            }
        });
        galleryFragment.setOnClickImage(new MyOnClickItem() {
            @Override
            public void onClickItem(final View view, Object object, int position) {

                final String str = (String) object;
                BackDialog backDialog = new BackDialog(MainActivity.this, tittle);
                backDialog.setDialogListener(new DialogListener() {
                    @Override
                    public void onClickYes() {

                        img[0] = str;
                        onClickChooseImg.onClickItem(null, img[0], 0);
                        getSupportFragmentManager().beginTransaction().remove(galleryFragment).commitAllowingStateLoss();
                    }
                });
                backDialog.show();
            }
        });
    }

    public void setOnClickChooseImg(MyOnClickItem onClickChooseImg) {
        this.onClickChooseImg = onClickChooseImg;
    }

    public String getPathImg() {
        return pathImg;
    }

    public void setPathImg(String pathImg) {
        this.pathImg = pathImg;
    }

    private void checkPermision() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                if (shouldShowRequestPermissionRationale(
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                }
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISION_READ_EXTERNAL_STORAGE);

            }
        }
    }

    public void login(final String phone, final String pass) {

//        final Profile[] profile = {new Profile()};
//        final Profile[] userLogin = new Profile[1];
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

//                userLogin[0] = new Profile();
                String strPhone = "", strPass = "";
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    strPhone = postSnapshot.child("phone").getValue(String.class);
                    strPass = postSnapshot.child("password").getValue(String.class);
                    if (phone.equals(strPhone) && pass.equals(strPass))
                        user = postSnapshot.getValue(Profile.class);
                }
                if (user != null)
                    loginSuccess();
                else loginFail();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void loginSuccess() {
        replaceMainFragment(user);
        removeLoginFragment();
        Toast.makeText(this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
    }

    private void loginFail() {

        LoginFragment fragment = (LoginFragment) getSupportFragmentManager().findFragmentById(R.id.frame_login);
        if (fragment != null && fragment instanceof LoginFragment) {
            fragment.loginFail();
        }
        if (loadingDialog.isShowing())
            loadingDialog.hide();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISION_READ_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(this, "Đã cấp quyền!", Toast.LENGTH_LONG).show();
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    Toast.makeText(this, "Chưa cấp quyền!", Toast.LENGTH_LONG).show();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
        }
    }

    private void uploadImageToFireBase(String pathImg, final String idUser) {
        Bitmap bitmapImg = null;
        Float ratioImg = 0f;
        try {
            bitmapImg = modifyOrientation(readBitmapAndScale(pathImg), pathImg);
            Float a = Float.valueOf(bitmapImg.getHeight());
            Float b = Float.valueOf(bitmapImg.getWidth());
            ratioImg = a / b;
        } catch (IOException e) {
            e.printStackTrace();
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmapImg.compress(Bitmap.CompressFormat.PNG, 10, baos);
        byte[] data = baos.toByteArray();

        String nameImg = getDateNow() + ".png";
        UploadTask uploadTask;
        final StorageReference ref = storageRef.child("ImageUser").child(nameImg);
        uploadTask = ref.putBytes(data);

        final Float finalRatioImg = ratioImg;
        user.setRatioImage(ratioImg);
        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
//                    Toast.makeText(getContext(), "failll!!", Toast.LENGTH_SHORT).show();
                return ref.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    user.setImgUser(String.valueOf(task.getResult()));
                    userRef.child(idUser).child("imgUser").setValue(String.valueOf(task.getResult()));
                    userRef.child(idUser).child("ratioImage").setValue(finalRatioImg);
                    return;
                } else {
                    // Handle failures
                    // ...
                }
            }
        });
    }

    public static Bitmap modifyOrientation(Bitmap bitmap, String image_absolute_path) throws IOException {
        ExifInterface ei = new ExifInterface(image_absolute_path);
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotate(bitmap, 90);

            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotate(bitmap, 180);

            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotate(bitmap, 270);

            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                return flip(bitmap, true, false);

            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                return flip(bitmap, false, true);

            default:
                return bitmap;
        }
    }

    public static Bitmap flip(Bitmap bitmap, boolean horizontal, boolean vertical) {
        Matrix matrix = new Matrix();
        matrix.preScale(horizontal ? -1 : 1, vertical ? -1 : 1);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public static Bitmap rotate(Bitmap bitmap, float degrees) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degrees);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public Bitmap readBitmapAndScale(String path) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true; //Chỉ đọc thông tin ảnh, không đọc dữ liệu
        BitmapFactory.decodeFile(path, options); //Đọc thông tin ảnh
        options.inSampleSize = 2; //Scale bitmap xuống 2 lần
        options.inJustDecodeBounds = false; //Cho phép đọc dữ liệu ảnh ảnh
        return BitmapFactory.decodeFile(path, options);
    }

    public String getDateNow() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }

    public void showImageTranslation(RoundedImageView view) {
        // Check that the device is running lollipop
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            PreviewImageFragment articleFragment = new PreviewImageFragment();
            view.setTransitionName("ABCDEF");
            fragmentManager.beginTransaction()
                    .addSharedElement(view, ViewCompat.getTransitionName(view))
                    .replace(R.id.frame_chat, articleFragment)
                    .addToBackStack("TAG")
                    .setReorderingAllowed(true) // Need for transition element
                    .commit();
        } else {
            // Code to run on older devices
        }
    }

}
