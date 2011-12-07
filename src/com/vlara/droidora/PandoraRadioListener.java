package com.vlara.droidora;

import com.vlara.pandora.PandoraRadio;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class PandoraRadioListener extends BroadcastReceiver{ 
    @Override 
    public void onReceive(Context context, Intent intent){

        String action = intent.getAction();
        if( (action.compareTo(Intent.ACTION_HEADSET_PLUG))  == 0)   //if the action match a headset one
        {
            int headSetState = intent.getIntExtra("state", 0);      //get the headset state property
            if( headSetState == 0)        //headset was unplugged & has no microphone
            {
            	if(PandoraRadioService.getInstance(false) != null){
                    PandoraRadioService pandora = PandoraRadioService.getInstance(false);
                    if(pandora.isPlaying()){
                            pandora.pause();
                    }
            }
            }
        } 
    	/*
        if (intent.hasExtra("state")){
                   if (headsetConnected && intent.getIntExtra("state", 0) == 0){
                    headsetConnected = false;
                    if (true){
                        if(PandoraRadioService.getInstance(false) != null){
                                PandoraRadioService pandora = PandoraRadioService.getInstance(false);
                                if(pandora.isPlaying()){
                                        pandora.pause();
                                }
                        }
                    }
                    }
                   }
                   else if (!headsetConnected && intent.getIntExtra("state", 0) == 1){
                    headsetConnected = true;
                   }*/
    }
}
