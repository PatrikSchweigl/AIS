package ais.motorcontroller2;

import android.util.Log;

import com.physicaloid.lib.Physicaloid;

import java.io.UnsupportedEncodingException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by patrik on 17.06.16.
 */
public class ControlManager {

    public static final  String              TAG         = "ControlManager";
    private static       ControlManager      instance    = null;
    private              ConnectionManager   connection;
    private              Physicaloid         physicaloid;
    private final static String              BR          = System.getProperty("line.separator");

    private static final int                 DISP_CHAR   = 0;
    private static final int                 DISP_DEC    = 1;
    private static final int                 DISP_HEX    = 2;

    // Linefeed Code Settings
    private static final int                 LINEFEED_CODE_CR   = 0;
    private static final int                 LINEFEED_CODE_CRLF = 1;
    private static final int                 LINEFEED_CODE_LF   = 2;
    private int                              mReadLinefeedCode  = LINEFEED_CODE_LF;
    private int                              mDisplayType       = DISP_CHAR;
    private              String[]            sensorData         = new String[5];
    public               boolean             isMovingFwd        = false;
    public               boolean             isAllowed          = true;



    public static ControlManager getInstance() {

        if (instance == null)
            instance = new ControlManager();

        return instance;
    }


    private ControlManager(){
        connection = ConnectionManager.getInstance();
        physicaloid = connection.getPhysicaloid();
    }
    /**
     *
     */
    public void driveInOneDirection(){
        driveInOneDirection(Constants.FW_SPEED_10, 0.0);
    }

    public void driveInOneDirection(String speed){
        driveInOneDirection(speed, 0.0);
    }

    public void driveInOneDirection(double length) {
        driveInOneDirection(Constants.FW_SPEED_10, length);
    }

    public void driveInOneDirection(String speed, double length) {
        if(speed.startsWith("-"))
            isMovingFwd = false;
        else
            isMovingFwd = true;
        sendToRobo(speed + "\r");
        this.readBytes();
        sendToRobo(speed + "\r");
        this.readBytes();
        sendToRobo(Constants.SET_VELOCITY + "\r");
        this.readBytes();

        if (length > 0.5){

            long timeToStop = (long) (((Constants.TICKS_PER_REV * (length / Constants.WHEEL_CIRC)) / Constants.TICKS_PER_SEC) * 1000);
            Log.i(TAG, "Time to stop: " + timeToStop);
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Log.i(TAG, "Stopping turn");
                    stop();
                    readBytes();
                }
            }, timeToStop);
        }
    }

    public void setSpeedForBoth(String speed1, String speed2){
        if(speed1.startsWith("-") || speed2.startsWith("-"))
            isMovingFwd = false;
        else
            isMovingFwd = true;
        sendToRobo(speed1 + "\r");
        this.readBytes();
        sendToRobo(speed2 + "\r");
        this.readBytes();
        sendToRobo(Constants.SET_VELOCITY + "\r");
        this.readBytes();
    }


    public void turnLeft(String speed, double angle){
        turn(speed, Constants.Direction.LEFT, angle);
    }

    public void turnRight( String speed, double angle){
        turn(speed, Constants.Direction.RIGHT, angle);
    }

    public void turn(Constants.Direction direction, double angle){
        turn(Constants.FW_SPEED_10, direction, angle );
    }

    public void turn(String speed, Constants.Direction direction, double angle) {

        isMovingFwd = false;
        if(direction == Constants.Direction.RIGHT) {
            sendToRobo(speed + "\r");
            this.readBytes();
            sendToRobo("-" + speed + "\r");
            this.readBytes();
            sendToRobo(Constants.SET_VELOCITY + "\r");
            this.readBytes();
        }
        else{
            sendToRobo("-" + speed + "\r");
            this.readBytes();
            sendToRobo(speed + "\r");
            this.readBytes();
            sendToRobo(Constants.SET_VELOCITY + "\r");
            this.readBytes();
        }

        double length = Constants.AXIS_CIRC / (360 / angle);

        long timeToStop = (long)(((Constants.TICKS_PER_REV * (length/Constants.WHEEL_CIRC) ) / Constants.TICKS_PER_SEC) * 1000);
//        long timeToStop = (long) dTimeToStop;
//        Log.i(TAG, "Time to stop: " + timeToStop + " d:" + dTimeToStop);
        Log.i(TAG, "Time to stop: " + timeToStop);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Log.i(TAG, "Stopping turn");
                stop();
                readBytes();
            }
        }, timeToStop);
    }

    public void catcherUp(){
        setCatcherLevel(Constants.BAR_UP);
    }

    public void catcherDown(){
        setCatcherLevel(Constants.BAR_DOWN);
    }

    public void setCatcherLevel(String level){
        sendToRobo(level + "\r");
        readBytes();
        sendToRobo(Constants.SERVO_BAR + "\r");
    }

    public void catcherDownSmooth(){
        try{
            sendToRobo(Constants.S_BAR_STEP1 + "\r");
            Thread.sleep(10);
            sendToRobo( Constants.SERVO_BAR + "\r");
            readBytes();
            sendToRobo(Constants.S_BAR_STEP2 + "\r");
            Thread.sleep(10);
            sendToRobo( Constants.SERVO_BAR + "\r");
            readBytes();
            sendToRobo(Constants.S_BAR_STEP3 + "\r");
            Thread.sleep(10);
            sendToRobo( Constants.SERVO_BAR + "\r");
            readBytes();
            sendToRobo(Constants.S_BAR_STEP4 + "\r");
            Thread.sleep(10);
            sendToRobo( Constants.SERVO_BAR + "\r");
            readBytes();
            sendToRobo(Constants.S_BAR_STEP5 + "\r");
            Thread.sleep(10);
            sendToRobo( Constants.SERVO_BAR + "\r");
            readBytes();
            sendToRobo(Constants.S_BAR_STEP6 + "\r");
            Thread.sleep(10);
            sendToRobo( Constants.SERVO_BAR + "\r");
            readBytes();
            sendToRobo(Constants.S_BAR_STEP7 + "\r");
            Thread.sleep(10);
            sendToRobo( Constants.SERVO_BAR + "\r");
            readBytes();
            sendToRobo(Constants.S_BAR_STEP8 + "\r");
            Thread.sleep(10);
            sendToRobo( Constants.SERVO_BAR + "\r");
            readBytes();
            sendToRobo(Constants.S_BAR_STEP9 + "\r");
            Thread.sleep(10);
            sendToRobo( Constants.SERVO_BAR + "\r");
            readBytes();
            sendToRobo(Constants.S_BAR_STEP10 + "\r");
            Thread.sleep(10);
            sendToRobo( Constants.SERVO_BAR + "\r");
            readBytes();
            sendToRobo(Constants.S_BAR_STEP11 + "\r");
            Thread.sleep(10);
            sendToRobo( Constants.SERVO_BAR + "\r");
            readBytes();
            sendToRobo(Constants.S_BAR_STEP12 + "\r");
            Thread.sleep(10);
            sendToRobo( Constants.SERVO_BAR + "\r");
            readBytes();
            sendToRobo(Constants.S_BAR_STEP13 + "\r");
            Thread.sleep(10);
            sendToRobo( Constants.SERVO_BAR + "\r");
            readBytes();
            sendToRobo(Constants.S_BAR_STEP14 + "\r");
            Thread.sleep(10);
            sendToRobo( Constants.SERVO_BAR + "\r");
            readBytes();
            sendToRobo(Constants.S_BAR_DOWN + "\r");
            Thread.sleep(10);
            sendToRobo( Constants.SERVO_BAR + "\r");
            readBytes();
        }
        catch (InterruptedException e)
        {
        }
    }

    public void catcherUpSmooth(){
        try{
            sendToRobo(Constants.S_BAR_DOWN + "\r");
            Thread.sleep(10);
            sendToRobo( Constants.SERVO_BAR + "\r");
            readBytes();
            sendToRobo(Constants.S_BAR_STEP14 + "\r");
            Thread.sleep(10);
            sendToRobo( Constants.SERVO_BAR + "\r");
            readBytes();
            sendToRobo(Constants.S_BAR_STEP13 + "\r");
            Thread.sleep(10);
            sendToRobo( Constants.SERVO_BAR + "\r");
            readBytes();
            sendToRobo(Constants.S_BAR_STEP12 + "\r");
            Thread.sleep(10);
            sendToRobo( Constants.SERVO_BAR + "\r");
            readBytes();
            sendToRobo(Constants.S_BAR_STEP11 + "\r");
            Thread.sleep(10);
            sendToRobo( Constants.SERVO_BAR + "\r");
            readBytes();
            sendToRobo(Constants.S_BAR_STEP10 + "\r");
            Thread.sleep(10);
            sendToRobo( Constants.SERVO_BAR + "\r");
            readBytes();
            sendToRobo(Constants.S_BAR_STEP9 + "\r");
            Thread.sleep(10);
            sendToRobo( Constants.SERVO_BAR + "\r");
            readBytes();
            sendToRobo(Constants.S_BAR_STEP8 + "\r");
            Thread.sleep(10);
            sendToRobo( Constants.SERVO_BAR + "\r");
            readBytes();
            sendToRobo(Constants.S_BAR_STEP7 + "\r");
            Thread.sleep(10);
            sendToRobo( Constants.SERVO_BAR + "\r");
            readBytes();
            sendToRobo(Constants.S_BAR_STEP6 + "\r");
            Thread.sleep(10);
            sendToRobo( Constants.SERVO_BAR + "\r");
            readBytes();
            sendToRobo(Constants.S_BAR_STEP5 + "\r");
            Thread.sleep(10);
            sendToRobo( Constants.SERVO_BAR + "\r");
            readBytes();
            sendToRobo(Constants.S_BAR_STEP4 + "\r");
            Thread.sleep(10);
            sendToRobo( Constants.SERVO_BAR + "\r");
            readBytes();
            sendToRobo(Constants.S_BAR_STEP3 + "\r");
            Thread.sleep(10);
            sendToRobo( Constants.SERVO_BAR + "\r");
            readBytes();
            sendToRobo(Constants.S_BAR_STEP2 + "\r");
            Thread.sleep(10);
            sendToRobo( Constants.SERVO_BAR + "\r");
            readBytes();
            sendToRobo(Constants.S_BAR_UP + "\r");
            Thread.sleep(10);
            sendToRobo( Constants.SERVO_BAR + "\r");
            readBytes();
        }
        catch (InterruptedException e)
        {
        }
    }

    public String getSensorData(){
        sendToRobo(Constants.SENSOR + "\r");
        Log.i(TAG,"read sensor data");
        return readBytes();
    }


    public void sendBytesToRobo(byte[] buf) {
        if(physicaloid.isOpened()) {
            Log.i(TAG, "Write: Str:");
            for (byte b : buf) {
                try {
                    Log.i(TAG, new String(buf, "US-ASCII"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            if (buf.length > 0) {
                physicaloid.write(buf, buf.length);
            }
            try {
                Thread.sleep(100);
//                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }


    public void sendToRobo(String str) {
        if (str.length() > 0) {
            byte[] buf;

            try {
                buf = str.getBytes("US-ASCII");
                sendBytesToRobo(buf);

            } catch (UnsupportedEncodingException e) {
                Log.i(TAG, e.getMessage());
            }

        }
    }


    public String readBytes() {
        byte[] buf = new byte[64];
        int readSize = physicaloid.read(buf);
        Log.i(TAG, "readsize:   " + readSize);
        Log.i(TAG, "ReadBytes:");
        for (int i=0; i< readSize; i++) {
            Log.i(TAG, Integer.toHexString(buf[i]));
        }
        String readStr = setSerialDataToTextView(0, buf, readSize, "", "");
        Log.i(TAG, "ReadStr: " + readStr);
        return readStr;
    }

    public void stop() {
        Log.i(TAG, "Stop");
        this.sendToRobo(Constants.STOP + "\r");
        readBytes();
    }

    boolean lastDataIs0x0D = false;

    public String setSerialDataToTextView(int disp, byte[] rbuf, int len, String sCr, String sLf) {
        int tmpbuf;
        StringBuilder mText = new StringBuilder();
        for (int i = 0; i < len; ++i) {
            Log.d(TAG, "Read  Data[" + i + "] : " + rbuf[i]);

            if ((mReadLinefeedCode == LINEFEED_CODE_CR) && (rbuf[i] == 0x0D)) {
                mText.append(sCr);
                mText.append(BR);
            } else if ((mReadLinefeedCode == LINEFEED_CODE_LF) && (rbuf[i] == 0x0A)) {
                mText.append(sLf);
                mText.append(BR);
            } else if ((mReadLinefeedCode == LINEFEED_CODE_CRLF) && (rbuf[i] == 0x0D)
                    && (rbuf[i + 1] == 0x0A)) {
                mText.append(sCr);
                if (disp != DISP_CHAR) {
                    mText.append(" ");
                }
                mText.append(sLf);
                mText.append(BR);
                ++i;
            } else if ((mReadLinefeedCode == LINEFEED_CODE_CRLF) && (rbuf[i] == 0x0D)) {
                // case of rbuf[last] == 0x0D and rbuf[0] == 0x0A
                mText.append(sCr);
                lastDataIs0x0D = true;
            } else if (lastDataIs0x0D && (rbuf[0] == 0x0A)) {
                if (disp != DISP_CHAR) {
                    mText.append(" ");
                }
                mText.append(sLf);
                mText.append(BR);
                lastDataIs0x0D = false;
            } else if (lastDataIs0x0D && (i != 0)) {
                // only disable flag
                lastDataIs0x0D = false;
                --i;
            } else {
                switch (disp) {
                    case DISP_CHAR:
                        mText.append((char) rbuf[i]);
                        break;
                    case DISP_DEC:
                        tmpbuf = rbuf[i];
                        if (tmpbuf < 0) {
                            tmpbuf += 256;
                        }
                        mText.append(String.format("%1$03d", tmpbuf));
                        mText.append(" ");
                        break;
                    case DISP_HEX:
                        mText.append(IntToHex2((int) rbuf[i]));
                        mText.append(" ");
                        break;
                    default:
                        break;
                }
            }
        }
        return mText.toString();
    }

    private String IntToHex2(int Value) {
        char HEX2[] = {
                Character.forDigit((Value >> 4) & 0x0F, 16),
                Character.forDigit(Value & 0x0F, 16)
        };
        String Hex2Str = new String(HEX2);
        return Hex2Str;
    }

}
