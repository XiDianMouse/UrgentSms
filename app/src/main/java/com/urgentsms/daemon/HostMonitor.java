package com.urgentsms.daemon;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

import com.blankj.utilcode.utils.ToastUtils;
import com.urgentsms.broadcastreceivers.SmsReceiver;
import com.urgentsms.communication.UdpCommunication;

/**
 * @auther gbh
 * Created on 2017/4/9.
 */

public class HostMonitor extends Service{
    public static final String ACTION_SYSTEM_MSG = "android.provider.Telephony.SMS_RECEIVED";
    private UdpCommunication mUdpCommunication;
    private SmsReceiver mSmsReceiver;

    @Override
    public void onCreate(){
        super.onCreate();
        mUdpCommunication = new UdpCommunication();
        mSmsReceiver = new SmsReceiver(mUdpCommunication);
        registerReceiver(mSmsReceiver,myIntentFilter());
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

    private IntentFilter myIntentFilter(){
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.setPriority(Integer.MAX_VALUE);
        intentFilter.addAction(ACTION_SYSTEM_MSG);

        return intentFilter;
    }
}
