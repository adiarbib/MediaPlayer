package com.example.user.mediaplayer;

import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

/**
 * Created by User on 23/11/2016.
 */
public class CustomPhoneStateListener extends PhoneStateListener
{
    private Context context;

    public CustomPhoneStateListener(Context context) {
        this.context = context;
    }

    @Override
    public void onCallStateChanged(int state, String incomingNumber) {
        super.onCallStateChanged(state, incomingNumber);
        switch (state)
        {
            case TelephonyManager.CALL_STATE_RINGING:
                //pause music from service
                serviceAction(MediaService.ACTION_STATE_PAUSE);
                break;
            //outgoing call finished
            case TelephonyManager.CALL_STATE_IDLE:
                serviceAction(MediaService.ACTION_STATE_PLAY);
                break;
            //outgoing call starting
            case TelephonyManager.CALL_STATE_OFFHOOK:
                serviceAction(MediaService.ACTION_STATE_PAUSE);
                break;
        }
    }

    private void serviceAction(String state) {
        Intent intent = new Intent(this.context, MediaService.class);
        intent.setAction(state);
        this.context.startService(intent);
    }

}
