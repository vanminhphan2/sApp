package com.team.s.sapp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
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
import com.team.s.sapp.api.ApiClient;
import com.team.s.sapp.api.RegisterApi;
import com.team.s.sapp.dialog.BackDialog;
import com.team.s.sapp.dialog.DialogListener;
import com.team.s.sapp.dialog.LoadingDialog;
import com.team.s.sapp.fragment.account.EditProfileFragment;
import com.team.s.sapp.fragment.login.LoginFragment;
import com.team.s.sapp.fragment.main.MainFragment;
import com.team.s.sapp.fragment.main.chat.ChatBoxFragment;
import com.team.s.sapp.fragment.utility.GalleryFragment;
import com.team.s.sapp.fragment.utility.PreviewImageFragment;
import com.team.s.sapp.inf.MyCallBackLayout;
import com.team.s.sapp.inf.MyOnClickItem;
import com.team.s.sapp.model.Box;
import com.team.s.sapp.model.Message;
import com.team.s.sapp.model.Profile;
import com.team.s.sapp.model.Result;
import com.team.s.sapp.util.AnimateUtils;
import com.team.s.sapp.util.Constants;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.team.s.sapp.util.AppUtil.hideKeyboard;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    static public int WIDTHDEVICE = 0;

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
    private boolean checkLogin = false;


    final public Handler mHandler = new Handler();

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
        if (isLogged()) {
            loadingDialog.show();
            checkLogin = true;
            String phone = getPhoneLogged();
            String password = getPassLogged();
            login(phone, password);

        } else {
            checkLogin = false;
            replaceLoginFragment();
        }
//        replaceChatFragment();

    }

    public void showLoadingDialog() {
        if (loadingDialog != null && !loadingDialog.isShowing()) {
            loadingDialog.show();
        }
    }

    public void hideLoadingDialog() {
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

    public void verifySuccess(String phone) {

        replaceEditProfileFragment(phone);
        removeLoginFragment();
    }

    public void finishRegister(Profile profile) {

        user = profile;
        setStatusUser(profile.getId(), profile.isOnline(), profile.isLogin());
        replaceMainFragment(user);
        removeEditProfileFragment();
    }

    public void setStatusUser(int id, boolean isLogin, boolean isOnline) {
        String userId = String.valueOf(id);
        userRef.child(userId).child("Login").setValue(isLogin);
        userRef.child(userId).child("Online").setValue(isOnline);
    }

    public void loginSuccess(Profile profile) {

        user = profile;
        setStatusUser(profile.getId(), profile.isOnline(), profile.isLogin());
        replaceMainFragment(user);
    }

    //Create a string with number param was input
    public static String getRandomString(final int sizeOfRandomString) {
        final Random random = new Random();
        final StringBuilder sb = new StringBuilder(sizeOfRandomString);
        for (int i = 0; i < sizeOfRandomString; ++i)
            sb.append(Constants.ALLOWED_CHARACTERS.charAt(random.nextInt(Constants.ALLOWED_CHARACTERS.length())));
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
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, Constants.PERMISION_READ_EXTERNAL_STORAGE);

            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case Constants.PERMISION_READ_EXTERNAL_STORAGE: {
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

    private void replaceLoginFragment() {

        loginFragment = new LoginFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_login, loginFragment)
                .commitAllowingStateLoss();
    }

    public void removeFragment(final Fragment fragment) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                getSupportFragmentManager().beginTransaction().remove(fragment).commitAllowingStateLoss();
            }
        });
    }

    @Override
    public void onBackPressed() {
        hideLoadingDialog();

        Fragment fragmentLogin = getSupportFragmentManager().findFragmentById(R.id.frame_login);
        if (fragmentLogin != null && fragmentLogin instanceof LoginFragment) {
            ((LoginFragment) fragmentLogin).pressKeyBack();
            return;
        }

        Fragment fragmentMain = getSupportFragmentManager().findFragmentById(R.id.frame_main);
        if (fragmentMain != null && fragmentMain instanceof MainFragment) {
            ((MainFragment) fragmentMain).pressKeyBack();
            return;
        }

        super.onBackPressed();
    }

    public void exitApp() {

        BackDialog backDialog = new BackDialog(this, Constants.EXIT_APP_CONFIRM);
        backDialog.setDialogListener(new DialogListener() {
            @Override
            public void onClickYes() {

                finish();
            }
        });
        backDialog.show();
    }

    @Override
    public void onStart() {
        if (user != null)
            setStatusUser(user.getId(), true, true);
        Log.e(TAG, "onStart:111");
        super.onStart();
    }

    @Override
    public void onResume() {
        if (user != null)
            setStatusUser(user.getId(), true, true);
        Log.e(TAG, "onResume:222");
        super.onResume();
    }

    @Override
    public void onPause() {
        if (user != null)
            setStatusUser(user.getId(), true, false);
        Log.e(TAG, "onPause:333");
        super.onPause();
    }

    @Override
    public void onStop() {
        if (user != null)
            setStatusUser(user.getId(), true, false);
        Log.e(TAG, "onStop:444");
        super.onStop();
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy:5555");
        if (user != null)
            setStatusUser(user.getId(), false, false);
        super.onDestroy();
    }

    public void login(String phone, String password) {

        RegisterApi apiClient = ApiClient.getClient().create(RegisterApi.class);
        Call<Result> call = apiClient.login(phone.trim(), password.trim());
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {

                Log.e("Rio result login", response.body().getStatus().toString());
                if (response.body().getStatus().equals("success")) {
                    if (!checkLogin)
                        Toast.makeText(getApplicationContext(), "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                    Profile user = response.body().getUserById();
                    Log.e("Rio result login", response.body().getUserById() + "");
                    hideKeyboard(mainActivity);
                    user.setLogin(true);
                    user.setOnline(true);
                    saveLogin(phone, password);
                    loginSuccess(user);
                } else if (response.body().getStatus().equals("incorrect phone")) {
                    hideLoadingDialog();
                    if (!checkLogin)
                        Toast.makeText(getApplicationContext(), "Số điện thoại này chưa đăng kí!", Toast.LENGTH_SHORT).show();
                } else if (response.body().getStatus().equals("incorrect password")) {
                    hideLoadingDialog();
                    if (!checkLogin)
                        Toast.makeText(getApplicationContext(), "Sai mật khẩu!", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Log.e("Rio login error", t.toString());
                mainActivity.hideLoadingDialog();
                Toast.makeText(getApplicationContext(), "lỗi kết nối server!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isLogged() {
        SharedPreferences sharedPreferences = getSharedPreferences("LOGIN", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("IS_LOGIN", false);
    }

    private String getPhoneLogged() {
        SharedPreferences sharedPreferences = getSharedPreferences("LOGIN", Context.MODE_PRIVATE);
        return sharedPreferences.getString("PHONE_LOGIN", "");
    }

    private String getPassLogged() {
        SharedPreferences sharedPreferences = getSharedPreferences("LOGIN", Context.MODE_PRIVATE);
        return sharedPreferences.getString("PASSWORD_LOGIN", "");
    }

    public void saveLogin(String phone, String pass) {
        SharedPreferences sharedPreferences = getSharedPreferences("LOGIN", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("IS_LOGIN", true);
        editor.putString("PHONE_LOGIN", phone);
        editor.putString("PASSWORD_LOGIN", pass);
        editor.apply();
    }
}
