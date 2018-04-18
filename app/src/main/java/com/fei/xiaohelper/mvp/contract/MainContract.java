package com.fei.xiaohelper.mvp.contract;

import com.fei.xiaohelper.BasePersenter;
import com.fei.xiaohelper.BaseView;
import com.fei.xiaohelper.mvp.view.MainActivity;

/**
 * Created by fei on 2018/4/18.
 */

public abstract class MainContract {

    public interface View extends BaseView{
        void getRotem(float rotem);
        void getSpeed(float speed);
    }

    public interface PerSenter extends BasePersenter<View>{
        void register();
        void unregister();
        void initSensor(MainActivity mainActivity);
        void getSpeed(MainActivity mainActivity);
    }

}
