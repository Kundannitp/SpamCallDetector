package com.example.calldetector;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import static android.content.Context.TELEPHONY_SERVICE;
import static android.widget.Toast.LENGTH_LONG;
import static androidx.constraintlayout.widget.Constraints.TAG;

public class CallReceiver extends BroadcastReceiver {

    String inCall;

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_OFFHOOK)){
            showToast(context,"Call started...");
//            inCall = intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
//            String s = findNameByNumber(inCall,context);
////            int n = inCall.length();
////            p = inCall.charAt(n - 1);
////            wasRinging = true;
//            if (s == null)
//                Toast.makeText(context, "IN : " + inCall + " UnknownNumber", LENGTH_LONG).show();
//            else
//                Toast.makeText(context, "IN : " + inCall + s, LENGTH_LONG).show();

        }
        else if(intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_IDLE)){
            showToast(context,"Call ended...");
        }
        else if(intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_RINGING)){
            //showToast(context,"Incoming call...");
            inCall = intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
            String s = findNameByNumber(inCall,context);
//            int n = inCall.length();
//            p = inCall.charAt(n - 1);
//            wasRinging = true;
            if (s == null)
                Toast.makeText(context, "IN : " + inCall + " UnknownNumber", LENGTH_LONG).show();
            else
                Toast.makeText(context, "IN : " + inCall + s, LENGTH_LONG).show();
        }
    }

    void showToast(Context context,String message){
        Toast toast=Toast.makeText(context,message,Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }

    public String findNameByNumber(String num,Context context) {
        String res = null;
        try {
            //Log.d(TAG, "activity: " );
            ContentResolver resolver = context.getApplicationContext().getContentResolver();
            Log.d(TAG, "resolver: " +resolver);
            Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(num));
            Cursor c = resolver.query(uri, new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME}, null, null, null);

            if (c != null) { // cursor not null means number is found contactsTable
                if (c.moveToFirst()) {   // so now find the contact Name
                    res = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

                }
                c.close();
            }
            if (c == null) {
                return null;
            }
        } catch (Exception ex) {
            Toast.makeText(context, ex.toString(), Toast.LENGTH_SHORT).show();
        }
        return res;
    }
}