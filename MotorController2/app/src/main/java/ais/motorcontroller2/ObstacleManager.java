package ais.motorcontroller2;

import android.widget.EditText;

/**
 * Created by patrik on 18.06.16.
 */
public class ObstacleManager {

    public static   String          TAG         = "ObstacleManager";
    private static  ObstacleManager instance    = null;
    private         SensorManager   sensor;
    private         ControlManager  control;
    private         Thread          obstacleThread;

    public static  ObstacleManager getInstance(){

        if(instance == null)
            instance = new ObstacleManager();

        return instance;
    }

    private ObstacleManager(){
        sensor = SensorManager.getInstance();
        control = ControlManager.getInstance();
    }

    public boolean checkObstacke(){

        int valueLeftSensor = sensor.getLeftSensorData();
        int valueRightSensor = sensor.getRightSensorData();
        int valueMidSensor = sensor.getMiddleSensorData();

        if(valueLeftSensor > 1100 || valueRightSensor > 1100)
            return true;

        return false;
    }


    public void initObstacleThread(){
        obstacleThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    if(checkObstacke() && control.isMovingFwd) {
                        control.stop();
                        control.isAllowed = false;
                    }
                    else
                        control.isAllowed = true;
                    try {
                        Thread.sleep(Constants.SLEEP_TIME);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }

    public void startObstacleThread(){
        initObstacleThread();
        obstacleThread.start();
    }
}
