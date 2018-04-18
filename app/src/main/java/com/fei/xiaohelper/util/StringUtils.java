package com.fei.xiaohelper.util;

import android.util.Log;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.fei.xiaohelper.R;
import com.fei.xiaohelper.XiaoHelperApplication;

/**
 * Created by fei on 2018/3/29.
 */

public class StringUtils {

    public static void ShowLog(String msg){
        if(XiaoHelperApplication.getmIntent().isApkDebugable()){
            Log.i("fei_One",msg);
        }
    }

    /**
     * 普通的Toast
     * @param flag
     */
    public static void showToast(String flag){
        Toast.makeText(XiaoHelperApplication.getmIntent(),flag,Toast.LENGTH_SHORT).show();
    }
    /**
     * 中部的Toast
     * @param flag
     */
    public static void showCenterToast(String flag){
        Toast toast = Toast.makeText(XiaoHelperApplication.getmIntent(),flag,Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }

    /**
     * 带图片的Toast
     * @param flag
     */
    public static void showImageToast(String flag){
        Toast toast = Toast.makeText(XiaoHelperApplication.getmIntent(),flag,Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        LinearLayout toastView = (LinearLayout) toast.getView();
        ImageView imageCodeProject = new ImageView(XiaoHelperApplication.getmIntent());
        imageCodeProject.setImageResource(R.mipmap.ic_launcher);
        toastView.addView(imageCodeProject, 0);
        toast.show();
    }

}
