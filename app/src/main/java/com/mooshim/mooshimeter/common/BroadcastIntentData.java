package com.mooshim.mooshimeter.common;

import android.content.Intent;

import com.mooshim.mooshimeter.interfaces.MooshimeterControlInterface;

/**
 * Created by Workroom on 7/6/2016.   added for broadcast intent. modified 8Feb2017
 */
public class BroadcastIntentData  {
    static boolean CH1isSaved = false;
    static boolean CH2isSaved = false;
    static MeterReading valCH1;
    static MeterReading valCH2;
    //public void run(){}
    public static void broadcastMeterReading(MooshimeterControlInterface.Channel c, MeterReading val) {
        /*  8Feb2017  broadcast the intent containing CH1, CH2, and MATH.  Any and all receivers on the android device
           will receive the intent if the receiver has an action filter of   com.mooshim.mooshimeter.CH1-3
           Broadcast receivers can easily use the data in key:value pair format.
        */

        if (c == MooshimeterControlInterface.Channel.CH1) {
            valCH1 = val;      // save the val of CH1 until all 3 channels are received
            CH1isSaved = true;
        }
        if (c == MooshimeterControlInterface.Channel.CH2) {
            valCH2 = val;      //  save the val of CH2 until all 3 channels are received
            CH2isSaved = true;
        }
        if ((c == MooshimeterControlInterface.Channel.MATH) && (CH1isSaved) && (CH2isSaved)) {  //  we got data for all channels, so broadcast {
            CH1isSaved = false;     // reset the flags
            CH2isSaved = false;
            Intent intent = new Intent();
            intent.putExtra("units1", valCH1.units);  // key, value pair
            intent.putExtra("value1", valCH1.value);
            intent.putExtra("units2", valCH2.units);     // key, value pair
            intent.putExtra("value2", valCH2.value);
            intent.putExtra("units3", val.units);     // key, value pair for MATH
            intent.putExtra("value3", val.value);
            intent.setAction("com.mooshim.mooshimeter.CH");
            try {
                Util.getRootContext().sendBroadcast(intent);  // context must come from an activity or MyApplication
            } catch (Exception e) {
                // placeholder
            }
        }
    }
}