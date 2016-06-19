package ais.motorcontroller2;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.physicaloid.lib.Physicaloid;

public class MainActivity extends Activity {

    private static final int MENU_ID_MAIN           = 1;
    private static final int MENU_ID_PATHS          = 1;
    private static final int MENU_ID_HOMOGRAPHY     = 2;
    private static final int MENU_ID_BALL           = 3;
    private static final int MENU_ID_OBSTACLE       = 4;

    private static final String TAG = MainActivity.class.getSimpleName();
    private static MainActivity instance = null;
    private static long prevNanos;
    private ConnectionManager connection;
    private ControlManager control;
    private PathManager path;
    private SensorManager sensor;
    private ObstacleManager obstacle;
    private OdometryManager odometry;
    private EditText textfieldX;
    private EditText textfieldY;
    private EditText textfieldT;
    Physicaloid mPhysicaloid;

    private static final String ACTION_USB_PERMISSION =
            "ais.motorcontroller2.USB_PERMISSION";


    public static MainActivity getInstance(){
        return instance;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial5);
        instance = this;
        connection = ConnectionManager.getInstance();
        control =  ControlManager.getInstance();
        mPhysicaloid = connection.getPhysicaloid();
        path = PathManager.getInstance();
        sensor = SensorManager.getInstance();
        obstacle = ObstacleManager.getInstance();
        odometry = OdometryManager.getInstance();
        textfieldX = (EditText) findViewById(R.id.fieldX);
        textfieldY = (EditText) findViewById(R.id.fieldY);
        textfieldT = (EditText) findViewById(R.id.fieldTheta);
        initThreads();

        Button buttonMisc = (Button) findViewById(R.id.Misc);
        buttonMisc.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                path.driveXY(Constants.FW_SPEED_10, 500, 1000);
            }

        });

        Button buttonSensor = (Button) findViewById(R.id.SensorData);
        buttonSensor.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                control.getSensorData();
            }

        });

        Button buttonBarUp = (Button) findViewById(R.id.barUp);
        buttonBarUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                control.catcherUpSmooth();
            }

        });

        Button buttonBarDown = (Button) findViewById(R.id.barDown);
        buttonBarDown.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                control.catcherDownSmooth();
            }

        });

        Button buttonPath = (Button) findViewById(R.id.Paths);
        buttonPath.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                path.driveSquarePath(Constants.FW_SPEED_10, 500, 500);
            }

        });

        Button buttonEight = (Button) findViewById(R.id.Homography);
        buttonEight.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                path.driveSmoothEight(Constants.FW_SPEED_38, Constants.FW_SPEED_19);
            }

        });

        Button buttonStop = (Button) findViewById(R.id.buttonStop);
        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                control.stop();
                sensor.extractSensorData(control.getSensorData());
            }
        });

        Button buttonForward = (Button) findViewById(R.id.buttonForward);
        buttonForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                control.driveInOneDirection(Constants.FW_SPEED_10, 500);
            }
        });

        Button buttonBack = (Button) findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                control.driveInOneDirection(Constants.BW_SPEED_10, 500);
            }
        });

        Button buttonLeft = (Button) findViewById(R.id.buttonLeft);
        buttonLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                control.turnLeft(Constants.FW_SPEED_10, 90);
            }
        });
        Button buttonRight = (Button) findViewById(R.id.buttonRight);
        buttonRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                control.turnRight(Constants.FW_SPEED_10, 90);
            }
        });
    }

    public void initThreads(){
        sensor.startSensorThread();
        obstacle.startObstacleThread();
        odometry.startOdometry(control);
    }

    public void writeOdometryToField(RobotPosition pos){
        textfieldX.setText("X:  " + pos.getX());
        textfieldY.setText("Y:  " + pos.getY());
        textfieldT.setText("Angle:  " + pos.getAngle());

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}