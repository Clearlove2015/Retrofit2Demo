package com.odbpo.fenggou.retrofit2demo.ui;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.odbpo.fenggou.retrofit2demo.R;
import com.odbpo.fenggou.retrofit2demo.bean.LoginBean;
import com.odbpo.fenggou.retrofit2demo.net.common.AbsAPICallback;
import com.odbpo.fenggou.retrofit2demo.net.common.netutil.RetrofitUtil;
import com.odbpo.fenggou.retrofit2demo.net.use.CustomerUse;
import com.odbpo.fenggou.retrofit2demo.net.use.LoginUse;
import com.odbpo.fenggou.retrofit2demo.net.use.StoreBannerUse;
import com.odbpo.fenggou.retrofit2demo.net.use.UploadImgUse;
import com.odbpo.fenggou.retrofit2demo.util.AppToast;
import com.odbpo.fenggou.retrofit2demo.util.Global;
import com.odbpo.fenggou.retrofit2demo.util.ShareKey;
import com.odbpo.fenggou.retrofit2demo.util.SpUtil;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MultipartBody;
import rx.functions.Action1;

public class MainActivity extends RxAppCompatActivity {

    @Bind(R.id.btn_login)
    Button btnLogin;
    @Bind(R.id.btn_common)
    Button btnCommon;
    @Bind(R.id.btn_store)
    Button btnStore;
    @Bind(R.id.tv_content)
    TextView tvContent;
    @Bind(R.id.btn_upload_img)
    Button btnUploadImg;
    @Bind(R.id.progressbar)
    ProgressBar progressbar;

    private static final int IMAGE = 1;
    private List<File> imgList = new ArrayList<>();

    private LoginUse loginUse = new LoginUse();
    private CustomerUse customerUse = new CustomerUse();
    private UploadImgUse uploadImgUse = new UploadImgUse();
    private StoreBannerUse storeBannerUse = new StoreBannerUse();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        requestPermissions();
    }

    /**
     * 权限请求(记得在Manifest中添加相应权限)
     */
    public void requestPermissions() {
        new RxPermissions(this)
                .request(Manifest.permission.CAMERA,
                        Manifest.permission.CALL_PHONE,
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (aBoolean) {
                            //当所有权限都允许之后，返回true
                            Log.i("permissions", "aBoolean = " + aBoolean);
                        } else {
                            //只要有一个权限禁止，返回false，
                            //下一次申请只申请没通过申请的权限
                            Log.i("permissions", "aBoolean = " + aBoolean);
                        }
                    }
                });
    }

    @OnClick({R.id.btn_login, R.id.btn_common, R.id.btn_upload_img, R.id.btn_store})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                login();
                break;
            case R.id.btn_common:
                getCustomerInfo();
                break;
            case R.id.btn_upload_img:
                openPhoto();
                break;
            case R.id.btn_store:
                getStoreBanner();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE && resultCode == RESULT_OK) {
            Uri selectedImage = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(selectedImage, filePathColumns, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            String imagePath = c.getString(columnIndex);
            System.out.println("img_path:" + imagePath);
            imgList.add(new File(imagePath));
            c.close();

            upload_img();
        }
    }

    //调用相册
    public void openPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, IMAGE);
    }

    public void login() {
        progressbar.setVisibility(View.VISIBLE);
        WeakHashMap<String, String> map = new WeakHashMap<>();
        map.put("user", "17351466656");
        map.put("password", "12345678");
        loginUse.setFormParams(map);
        loginUse.execute(this).subscribe(new AbsAPICallback<String>() {
            @Override
            protected void onDone(String s) {
                LoginBean bean = new Gson().fromJson(s, LoginBean.class);
                if (bean.getCode().equals(Global.CODE_SUCCESS)) {
                    AppToast.show("登录成功");
                    SpUtil.getInstance().writeStr(ShareKey.TOKEN, bean.getData().getToken());
                } else {
                    AppToast.show(bean.getMessage());
                }
            }

            @Override
            public void onCompleted() {
                super.onCompleted();
                progressbar.setVisibility(View.GONE);
            }
        });
    }

    public void getCustomerInfo() {
        progressbar.setVisibility(View.VISIBLE);
        customerUse.execute(this).subscribe(new AbsAPICallback<String>() {
            @Override
            protected void onDone(String s) {
                tvContent.setText(s);
            }

            @Override
            public void onCompleted() {
                super.onCompleted();
                progressbar.setVisibility(View.GONE);
            }
        });
    }

    public void upload_img() {
        progressbar.setVisibility(View.VISIBLE);
        List<MultipartBody.Part> parts = RetrofitUtil.filesToMultipartBodyParts(imgList);
        uploadImgUse.setMulitParams(parts);
        uploadImgUse.execute(this).subscribe(new AbsAPICallback<String>() {
            @Override
            protected void onDone(String s) {
                tvContent.setText(s);
            }

            @Override
            public void onCompleted() {
                super.onCompleted();
                progressbar.setVisibility(View.GONE);
            }
        });
    }

    public void getStoreBanner() {
        progressbar.setVisibility(View.VISIBLE);
        storeBannerUse.execute(this).subscribe(new AbsAPICallback<String>() {
            @Override
            protected void onDone(String s) {
                tvContent.setText(s);
            }

            @Override
            public void onCompleted() {
                super.onCompleted();
                progressbar.setVisibility(View.GONE);
            }
        });
    }

}
