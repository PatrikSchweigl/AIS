package ais.motorcontroller2;

import android.graphics.Path;

import com.physicaloid.lib.Physicaloid;

/**
 * Created by patrik on 18.06.16.
 */
public class PathManager {

    public static final     String              TAG         = "PathManager";
    private static          PathManager         instance    = null;
    private                 Physicaloid         physicaloid;
    private                 ConnectionManager   connection;
    private                 ControlManager      control;

    public static PathManager getInstance(){

        if(instance == null)
            instance = new PathManager();

        return instance;
    }

    private PathManager(){
        connection = ConnectionManager.getInstance();
        control = ControlManager.getInstance();
        physicaloid = connection.getPhysicaloid();
    }

    public void driveSquarePath(int length){
        driveSquarePath(Constants.FW_SPEED_10, length);
    }

    public void driveSquarePath(String speed, int length){
        driveSquarePath(speed, length, length);
    }

    public void driveSquarePath(String speed, int length1, int length2){
        try {
            long timeDriveLength1 = (long)(((Constants.TICKS_PER_REV * (length1/Constants.WHEEL_CIRC) ) / Constants.TICKS_PER_SEC) * 1000);
            long timeDriveLength2 = (long)(((Constants.TICKS_PER_REV * (length2/Constants.WHEEL_CIRC) ) / Constants.TICKS_PER_SEC) * 1000);
            long timeDriveDegree = (long)(((Constants.TICKS_PER_REV * ((Constants.AXIS_CIRC / (360 / 90))/Constants.WHEEL_CIRC) ) / Constants.TICKS_PER_SEC) * 1000);
            if(control.isAllowed) {
                control.driveInOneDirection(speed, length1);
                Thread.sleep(timeDriveLength1);
            }
            if(control.isAllowed) {
                control.turnLeft(speed, 90);
                Thread.sleep(timeDriveDegree);
            }
            if(control.isAllowed) {
                control.driveInOneDirection(speed, length2);
                Thread.sleep(timeDriveLength2);
            }
            if(control.isAllowed) {
                control.turnRight(speed, 90);
                Thread.sleep(timeDriveDegree);
            }
            if(control.isAllowed) {
                control.driveInOneDirection(speed, length1);
                Thread.sleep(timeDriveLength1);
            }
            if(control.isAllowed) {
                control.turnRight(speed, 90);
                Thread.sleep(timeDriveDegree);
            }
            if(control.isAllowed) {
                control.driveInOneDirection(speed, length2);
                Thread.sleep(timeDriveLength2);
            }
            if(control.isAllowed) {
                control.turnRight(speed, 90);
                Thread.sleep(timeDriveDegree);
            }
            if(control.isAllowed) {
                control.driveInOneDirection(speed, length1);
                Thread.sleep(timeDriveLength1);
            }
            if(control.isAllowed) {
                control.turnRight(speed, 90);
                Thread.sleep(timeDriveDegree);
            }
            if(control.isAllowed) {
                control.driveInOneDirection(speed, length2);
                Thread.sleep(timeDriveLength2);
            }
            if(control.isAllowed) {
                control.turnLeft(speed, 90);
                Thread.sleep(timeDriveDegree);
            }
            if(control.isAllowed) {
                control.driveInOneDirection(speed, length1);
                Thread.sleep(timeDriveLength1);
            }
            if(control.isAllowed) {
                control.turnLeft(speed, 90);
                Thread.sleep(timeDriveDegree);
            }
            if(control.isAllowed) {
                control.driveInOneDirection(speed, length2);
                Thread.sleep(timeDriveLength2);
            }
            if(control.isAllowed) {
                control.turnLeft(speed, 90);
                Thread.sleep(timeDriveDegree);
            }
            control.stop();

        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void driveSmoothEight(String speed1, String speed2)
    {
        try {
            long timeDriveDegree = (long)(((Constants.TICKS_PER_REV * ((Constants.AXIS_CIRC / (360 / 180))/Constants.WHEEL_CIRC) ) / Constants.TICKS_PER_SEC) * 1000);
            if(control.isAllowed) {
                control.setSpeedForBoth(speed1, speed2);
                Thread.sleep(timeDriveDegree);
            }
            if(control.isAllowed) {
                control.setSpeedForBoth(speed2, speed1);
                Thread.sleep(timeDriveDegree);
            }
            if(control.isAllowed) {
                control.setSpeedForBoth(speed2, speed1);
                Thread.sleep(timeDriveDegree);
            }
            if(control.isAllowed) {
                control.setSpeedForBoth(speed1, speed2);
                Thread.sleep(timeDriveDegree);
            }
            control.stop();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void driveXY(String speed, double x, double y){
        double angle = Math.toDegrees(Math.atan(x/y));
        double length = x/Math.sin(Math.toRadians(angle));
        long timeDriveLength = (long)(((Constants.TICKS_PER_REV * (length/Constants.WHEEL_CIRC) ) / Constants.TICKS_PER_SEC) * 1000);
        long timeDriveDegree = (long)(((Constants.TICKS_PER_REV * ((Constants.AXIS_CIRC / (360 / angle))/Constants.WHEEL_CIRC) ) / Constants.TICKS_PER_SEC) * 1000);
        try {
            if (control.isAllowed) {
                control.turnRight(Constants.FW_SPEED_10, angle);
                Thread.sleep(timeDriveDegree);
            }
            if(control.isAllowed){
                control.driveInOneDirection(length);
                Thread.sleep(timeDriveLength);
            }
            control.stop();
        }
        catch (Exception e){

        }
    }
}

