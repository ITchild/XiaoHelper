package com.fei.xiaohelper.mvp.view;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.TextView;

import com.fei.xiaohelper.BaseActivity;
import com.fei.xiaohelper.R;
import com.fei.xiaohelper.di.component.DaggerMainComponent;
import com.fei.xiaohelper.di.moudel.MainMoudel;
import com.fei.xiaohelper.mvp.contract.MainContract;
import com.fei.xiaohelper.mvp.persenter.MainPersenter;
import com.fei.xiaohelper.ui.CompassView;


import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements MainContract.View {

    @Bind(R.id.main_conpass_cv)
    CompassView main_conpass_cv;

    @Bind(R.id.main_speed_tv)
    TextView main_speed_tv;

    @Inject
    MainPersenter mainPersenter;
    /**
     * 需要进行检测的权限数组 这里只列举了几项 小伙伴可以根据自己的项目需求 来添加
     */
    protected String[] needPermissions = {
            Manifest.permission.ACCESS_COARSE_LOCATION,//定位权限
            Manifest.permission.ACCESS_FINE_LOCATION,//定位权限
//            Manifest.permission.WRITE_EXTERNAL_STORAGE,//存储卡写入权限
//            Manifest.permission.READ_EXTERNAL_STORAGE,//存储卡读取权限
//            Manifest.permission.READ_PHONE_STATE//读取手机状态权限
    };
    private static final int PERMISSON_REQUESTCODE = 0;
    /**
     * 判断是否需要检测，防止不停的弹框
     */
    private boolean isNeedCheck = true;

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        mainPersenter.attachView(this);

    }

    @Override
    public void initData() {
        mainPersenter.initSensor(this);
    }


    @Override
    protected void onResume() {
        mainPersenter.register();
        if (isNeedCheck) {
            checkPermissions(needPermissions);
        }
        handler.postDelayed(runnable,3000);
        super.onResume();
    }

    /**
     * 检查权限
     */
    private void checkPermissions(String... permissions) {
        List<String> needRequestPermissonList = findDeniedPermissions(permissions);
        if (null != needRequestPermissonList
                && needRequestPermissonList.size() > 0) {
            ActivityCompat.requestPermissions(this,
                    needRequestPermissonList.toArray(
                            new String[needRequestPermissonList.size()]),
                    PERMISSON_REQUESTCODE);
        }
    }
    /**
     * 获取权限集中需要申请权限的列表
     *
     * @param permissions
     * @return
     * @since 2.5.0
     */
    private List<String> findDeniedPermissions(String[] permissions) {
        List<String> needRequestPermissonList = new ArrayList<String>();
        for (String perm : permissions) {
            if (ContextCompat.checkSelfPermission(this,
                    perm) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.shouldShowRequestPermissionRationale(
                    this, perm)) {
                needRequestPermissonList.add(perm);
            }
        }
        return needRequestPermissonList;
    }
    /**
     * 检测是否有的权限都已经授权
     *
     * @param grantResults
     */
    private boolean verifyPermissions(int[] grantResults) {
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] paramArrayOfInt) {
        if (requestCode == PERMISSON_REQUESTCODE) {
            if (!verifyPermissions(paramArrayOfInt)) {
                showMissingPermissionDialog();
                isNeedCheck = false;
            }else{

            }
        }
    }
    /**
     * 弹出对话框, 提示用户手动授权
     *
     */
    private void showMissingPermissionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("重要通知");
        builder.setMessage("请手动同意获取定位权限");
        // 拒绝授权 退出应用
        builder.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        //同意授权
        builder.setPositiveButton("同意",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startAppSettings();
                    }
                });
        builder.setCancelable(false);
        builder.show();
    }
    /**
     * 启动应用的设置
     *
     */
    private void startAppSettings() {
        Intent intent = new Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }
    @Override
    protected void onPause() {
        mainPersenter.unregister();
        super.onPause();
    }

    @Override
    protected void onStop() {
        mainPersenter.unregister();
        super.onStop();
    }

    @Override
    public void initListener() {

    }

    @Override
    public void setupComponent() {
        DaggerMainComponent.builder().mainMoudel(new MainMoudel(this)).build().Inject(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainPersenter.detachView();
    }

    @Override
    public void getRotem(float rotem) {
        main_conpass_cv.setRotate(rotem);
    }

    @Override
    public void getSpeed(float speed) {
        if (speed < 1) {
            main_speed_tv.setText("_._" + " km/h");
        } else {
            main_speed_tv.setText( Math.round(speed *3.6) + " km/h");
        }
    }


    private Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(runnable,3000);
            mainPersenter.getSpeed(MainActivity.this);
        }
    };

}
