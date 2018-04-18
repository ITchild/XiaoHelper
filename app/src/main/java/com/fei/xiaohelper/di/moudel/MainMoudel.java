package com.fei.xiaohelper.di.moudel;

import com.fei.xiaohelper.mvp.contract.MainContract;

import dagger.Module;

/**
 * Created by fei on 2018/4/18.
 */
@Module
public class MainMoudel {
    private MainContract.View view;

    public MainMoudel(MainContract.View view){
        this.view = view;
    }

}
