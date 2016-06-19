package ais.motorcontroller2;

import android.content.Context;
import android.util.Log;

import ais.motorcontroller2.Exception.ConnectionException;
import com.physicaloid.lib.Physicaloid;
import com.physicaloid.lib.usb.driver.uart.UartConfig;

import ais.motorcontroller2.Exception.ConnectionException;

/**
 * Created by patrik on 17.06.16.
 */
public class ConnectionManager {

    public static final String              TAG             = "ConnectionManager";
    private static      ConnectionManager   instance        = null;
    private             Physicaloid         physicaloid;
    private             UartConfig          uartConfig;
    private             MainActivity        activity;
    private final       int                 BAUDRATE        = 115200;

    public static ConnectionManager getInstance(){

        if( instance == null)
            try {
                instance = new ConnectionManager();
            } catch (ConnectionException e) {
                Log.i(TAG, "Cannot connect");
            }

        return instance;
    }

    private ConnectionManager() throws ConnectionException{

        activity = MainActivity.getInstance();
        physicaloid = new Physicaloid(activity);
        uartConfig = new UartConfig(BAUDRATE, UartConfig.DATA_BITS8, UartConfig.STOP_BITS1, UartConfig.PARITY_NONE, false, false);

        if(!physicaloid.open(uartConfig))
            throw new ConnectionException("Could not connect");
    }

    public Physicaloid getPhysicaloid(){
        return physicaloid;
    }

    /*public Physicaloid setPhysicaloid(Context context) throws ConnectionException{
        physicaloid = new Physicaloid(context);
        if(!physicaloid.open(uartConfig))
            throw new ConnectionException("could not connect");

        return physicaloid;
    }*/

}
