package com.example.calldetector;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Environment;
import android.provider.ContactsContract;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import java.io.File;

import static android.content.Context.TELEPHONY_SERVICE;
import static android.widget.Toast.LENGTH_LONG;
import static androidx.constraintlayout.widget.Constraints.TAG;

public class CallReceiver extends BroadcastReceiver{

    String inCall;



    MediaRecorder recorder = new MediaRecorder();

    boolean isRecordStarted = false;

    File audioFile;

    int audioSource = MediaRecorder.AudioSource.VOICE_CALL;

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_OFFHOOK)){
            showToast(context,"Call started...");


            /**
             *
             * Media recorder started code starts here...
             *
             */


//            try {
//                startMediaRecorder(audioSource);
//                Log.d("Navin call recorder LOG", "Everything is fine with starting of recorder");
//            }
//            catch (Exception e)
//            {
//                Log.d("Navin call recorder LOG", "Something went wrong while starting recorder");
//            }


            /**
             *
             *
             * Media recorder started code ends here...
             *
             */


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

            isRecordStarted = false;

            //recorder.stop();
        }
        else if(intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_RINGING)){
            showToast(context,"Incoming call...");
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
    }

    void showToast(Context context,String message){
        Toast toast = Toast.makeText(context,message,Toast.LENGTH_LONG);
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


    /**
     *
     * Navin code below...
     *
     */


    private void startMediaRecorder(final int audioSource){


        String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/NavinCallRecorder";

        audioFile = new File(path);
        if (!audioFile.exists()) {
            audioFile.mkdirs();
        }
        File newfile=new File(audioFile,"kkj.txt");

        try {
            newfile.createNewFile();
            Log.d("CreateNewFile", "New file created");
        }
        catch (Exception e)
        {
            Log.d("CreateNewFile", e.getMessage());
        }
//
//        try{
//            recorder.reset();
//            recorder.setAudioSource(audioSource);
//            recorder.setAudioSamplingRate(8000);
//            recorder.setAudioEncodingBitRate(12200);
//            recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
//            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
//            String fileName = audioFile.getAbsolutePath();
//            recorder.setOutputFile(fileName);
//
//            MediaRecorder.OnErrorListener errorListener = new MediaRecorder.OnErrorListener() {
//                public void onError(MediaRecorder arg0, int arg1, int arg2) {
//                    Log.e(TAG, "OnErrorListener " + arg1 + "," + arg2);
//                    //terminateAndEraseFile();
//                }
//            };
//            recorder.setOnErrorListener(errorListener);
//
//            MediaRecorder.OnInfoListener infoListener = new MediaRecorder.OnInfoListener() {
//                public void onInfo(MediaRecorder arg0, int arg1, int arg2) {
//                    Log.e(TAG, "OnInfoListener " + arg1 + "," + arg2);
//                    //terminateAndEraseFile();
//                }
//            };
//            recorder.setOnInfoListener(infoListener);
//
//
//            recorder.prepare();
//            // Sometimes prepare takes some time to complete
//            Thread.sleep(2000);
//            recorder.start();
//            isRecordStarted = true;
//            return true;
//        }catch (Exception e){
//            e.getMessage();
//            return false;
//        }


    }



}