package com.fei.xiaohelper.mvp.persenter;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.support.annotation.NonNull;

import com.fei.xiaohelper.mvp.contract.MainContract;
import com.fei.xiaohelper.mvp.model.MainModel;
import com.fei.xiaohelper.mvp.view.MainActivity;

import javax.inject.Inject;

import static android.content.Context.SENSOR_SERVICE;
import static com.fei.xiaohelper.R.id.main_conpass_cv;

/**
 * Created by fei on 2018/4/18.
 */

public class MainPersenter implements MainContract.PerSenter,SensorEventListener {
    @Inject
    MainModel mainModel;

    private MainContract.View view;


    private Sensor sensor;
    private SensorManager sensorManager;
    private float currentDegree = 0f;
    @Inject
    public MainPersenter(){

    }

    @Override
    public void register() {
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void unregister() {
        sensorManager.unregisterListener(this);
    }

    @Override
    public void initSensor(MainActivity mainActivity) {
        sensorManager = (SensorManager) mainActivity.getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        int sensorType = event.sensor.getType();
        switch (sensorType) {
            case Sensor.TYPE_ORIENTATION:
                float degree = event.values[0];
                if (Math.abs(currentDegree - degree) > 1) {
                    view.getRotem(degree);
                    currentDegree = degree;
                }
                break;

            default:
                break;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    @Override
    public void getSpeed(MainActivity mainActivity) {
        view.getSpeed(mainModel.getSpeed(mainActivity));
    }

    @Override
    public void attachView(@NonNull MainContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        view = null;
    }
}
