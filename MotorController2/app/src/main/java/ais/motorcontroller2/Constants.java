package ais.motorcontroller2;

/**
 * Created by patrik on 17.06.16.
 */
public class Constants {

    public static final String      FW_SPEED_3     = "3";
    public static final String      FW_SPEED_4     = "4";
    public static final String      FW_SPEED_5    = "5";
    public static final String      FW_SPEED_6     = "6";
    public static final String      FW_SPEED_8     = "8";
    public static final String      FW_SPEED_10     = "10";
    public static final String      FW_SPEED_15    = "15";
    public static final String      FW_SPEED_16    = "16";
    public static final String      FW_SPEED_17    = "17";
    public static final String      FW_SPEED_18    = "18";
    public static final String      FW_SPEED_19    = "19";
    public static final String      FW_SPEED_20    = "20";
    public static final String      FW_SPEED_30    = "30";
    public static final String      FW_SPEED_32    = "32";
    public static final String      FW_SPEED_34    = "34";
    public static final String      FW_SPEED_36    = "36";
    public static final String      FW_SPEED_38    = "38";
    public static final String      FW_SPEED_40    = "40";
    public static final String      FW_SPEED_50    = "50";
    public static final String      FW_SPEED_100   = "100";

    public static final String      BW_SPEED_10    = "-10";
    public static final String      BW_SPEED_20    = "-20";
    public static final String      BW_SPEED_50    = "-50";
    public static final String      BW_SPEED_100   = "-100";

    public static final String      BAR_UP         = "1111";
    public static final String      BAR_DOWN       = "1ccc";

    public static final String      S_BAR_UP       = "1111";
    public static final String      S_BAR_STEP1    = "1150";
    public static final String      S_BAR_STEP2    = "11aa";
    public static final String      S_BAR_STEP3    = "1200";
    public static final String      S_BAR_STEP4    = "12aa";
    public static final String      S_BAR_STEP5    = "13aa";
    public static final String      S_BAR_STEP6    = "14aa";
    public static final String      S_BAR_STEP7    = "15aa";
    public static final String      S_BAR_STEP8    = "16aa";
    public static final String      S_BAR_STEP9    = "17aa";
    public static final String      S_BAR_STEP10   = "18aa";
    public static final String      S_BAR_STEP11   = "19aa";
    public static final String      S_BAR_STEP12   = "1aaa";
    public static final String      S_BAR_STEP13   = "1baa";
    public static final String      S_BAR_STEP14   = "1caa";
    public static final String      S_BAR_DOWN     = "1ccc";


    public static final String      SET_VELOCITY   = "i";
    public static final String      STOP           = "s";
    public static final String      SERVO_BAR      = "o";
    public static final String      SENSOR         = "q";
    public static final String      MOTOR_DELTA    = "m";
    public static final String      GET_VELOCITY   = "v";

    public static final double      WHEEL_RADIUS   = 46;
    public static final double      WHEEL_CIRC     = 2*Math.PI * WHEEL_RADIUS;
    public static final double      AXIS_LENGTH    = 188;
    public static final double      AXIS_CIRC      = 2*Math.PI*(AXIS_LENGTH/2);
    public static final double      TICKS_PER_REV  = 2248.46;
    public static final double      TICKS_PER_SEC  = 649; //0x289
    public static final long        TIME_SLEEP     = 100;
    public static final Direction   LEFT           = Direction.LEFT;
    public static final Direction   RIGHT          = Direction.RIGHT;

    public static final long        SLEEP_TIME     = 500;
    public enum Direction{
        LEFT,
        RIGHT
    }
}
