package com.fei.xiaohelper.mvp.model;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.fei.xiaohelper.mvp.view.MainActivity;
import com.fei.xiaohelper.util.LocationUtil;
import com.fei.xiaohelper.util.StringUtils;

import javax.inject.Inject;

/**
 * Created by fei on 2018/4/18.
 */

public class MainModel {

    @Inject
    public MainModel(){

    }

    public float getSpeed(MainActivity mainActivity){
        Location location = LocationUtil.getInstance( mainActivity ).showLocation();
        if (location != null) {
            StringUtils.ShowLog("获取到速度:"+location.getSpeed());
            return location.getSpeed();
        }else{
            StringUtils.ShowLog("没有获取速度");
            return 0f;
        }
    }

}
