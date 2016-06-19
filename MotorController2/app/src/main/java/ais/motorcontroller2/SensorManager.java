package ais.motorcontroller2;

import android.util.Log;

/**
 * Created by patrik on 18.06.16.
 */
public class SensorManager {

    public static  String           TAG      = "SensorManager";
    private static SensorManager    instance = null;
    private        ControlManager   control;

    private        int              leftSensorData;
    private        int              rightSensorData;
    private        int              middleSensorData;
    private        String[]         sensorData          = new String[5];
    private        Thread           sensorThread;

    public static SensorManager getInstance(){

        if(instance == null)
            instance = new SensorManager();

        return instance;
    }

    private SensorManager(){
        control = ControlManager.getInstance();
    }

    public void extractSensorData(String data){
        if(data.length() < 30)
            return;

        int readEndOfLine = 0;
        int i = 0;
        for(i = 0; i < data.length() && readEndOfLine < 2; ++i) {
            if (data.charAt(i) == '\n' && readEndOfLine < 2) {
                readEndOfLine++;
            }
        }
        sensorData[0] = new String(data.substring(i, i + 4));
        sensorData[1] = new String(data.substring(i + 5, i + 9));
        sensorData[2] = new String(data.substring(i + 10, i + 14));
        sensorData[3] = new String(data.substring(i + 15, i + 19));
        sensorData[4] = new String(data.substring(i + 20, i + 24));
        leftSensorData = Integer.parseInt(sensorData[2], 16);
        rightSensorData = Integer.parseInt(sensorData[4], 16);
        middleSensorData = Integer.parseInt(sensorData[3], 16);
        Log.i(TAG, "left    " + leftSensorData);
        Log.i(TAG, "middle    " + middleSensorData);
        Log.i(TAG, "right    " + rightSensorData);
    }

    public void initSensorThread(){
        sensorThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    extractSensorData(control.getSensorData());
                    try {
                        Thread.sleep(Constants.SLEEP_TIME);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void startSensorThread(){
        initSensorThread();
        sensorThread.start();
    }

    public int getLeftSensorData(){
        return leftSensorData;
    }

    public int getRightSensorData(){
        return rightSensorData;
    }

    public int getMiddleSensorData(){
        return middleSensorData;
    }
}
