package com.droid.smsblocker;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.os.Bundle;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;
import android.util.Log;

public class MainActivity_SMSBlocker extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {

	    //---get the SMS message passed in---
	    Bundle bundle = intent.getExtras();   
	    SmsMessage[] msgs = null;
	    String from = "";            
	    if (bundle != null)
	    {
	        //---retrieve the SMS message received---
	        Object[] pdus = (Object[]) bundle.get("pdus");
	        msgs = new SmsMessage[pdus.length];            
	        for (int i=0; i<msgs.length; i++){
	            msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);                
	            from = msgs[i].getOriginatingAddress();
	        }
	        Log.d("message From", from);
			Pattern p = Pattern.compile("LM",Pattern.MULTILINE | Pattern.DOTALL);
			Matcher m = p.matcher(from);

			if(m.find()){
	    	    //this stops notifications to others
	    	    this.abortBroadcast();
	    	    Log.d("message aborted", from);
	        }
	    }
	}
}