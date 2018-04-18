package com.fei.xiaohelper.di.component;

import com.fei.xiaohelper.di.moudel.MainMoudel;
import com.fei.xiaohelper.mvp.view.MainActivity;

import dagger.Component;

/**
 * Created by fei on 2018/4/18.
 */
@Component(modules = MainMoudel.class)
public interface MainComponent {

    void Inject(MainActivity mainActivity);
}
