package com.urgentsms.daemon;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.SmsMessage;

import com.blankj.utilcode.utils.ToastUtils;
import com.urgentsms.communication.UdpCommunication;

/**
 * @auther gbh
 * Created on 2017/4/9.
 */

public class HostMonitor extends Service{
    public static final String ACTION_SYSTEM_MSG = "android.provider.Telephony.SMS_RECEIVED";
    private UdpCommunication mUdpCommunication;

    @Override
    public void onCreate(){
        super.onCreate();
        registerReceiver(mBroadcastReceiver,myIntentFilter());
        mUdpCommunication = new UdpCommunication();
        ToastUtils.showShortToast("服务已开启成功");
    }

    @Override
    public int onStartCommand(Intent intent,int flags,int startId){
        return super.onStartCommand(intent,flags,startId);
    }

    @Override
    public IBinder onBind(Intent arg0){
        return null;
    }

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(ACTION_SYSTEM_MSG)){
                Bundle bundle = intent.getExtras();
                if(bundle!=null){
                    Object[] pdus = (Object[])bundle.get("pdus");
                    int length = pdus.length;
                    SmsMessage[] message = new SmsMessage[length];
                    for(int i=0;i<length;i++){
                        message[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                    }
                    msgReceiveHandle(message[0]);
                }
            }
        }
    };

    private IntentFilter myIntentFilter(){
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.setPriority(Integer.MAX_VALUE);
        intentFilter.addAction(ACTION_SYSTEM_MSG);

        return intentFilter;
    }

    private void msgReceiveHandle(SmsMessage msg){
        String telNum = msg.getDisplayOriginatingAddress();
        String context = msg.getDisplayMessageBody();
        ToastUtils.showShortToast(telNum+"   "+context);
        mUdpCommunication.sendData(telNum+"   "+context);
    }

}
