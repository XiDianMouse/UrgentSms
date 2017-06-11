package com.urgentsms.broadcastreceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

import com.blankj.utilcode.utils.ToastUtils;
import com.urgentsms.communication.UdpCommunication;

import static com.urgentsms.daemon.HostMonitor.ACTION_SYSTEM_MSG;

/**
 * @auther gbh
 * Email:xidian_guobenhao@163.com
 * Created on 2017/6/11.
 */

public class SmsReceiver extends BroadcastReceiver{
    private UdpCommunication mUdpCommunication;

    public SmsReceiver(UdpCommunication udpCommunication){
        mUdpCommunication = udpCommunication;
    }

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

    private void msgReceiveHandle(SmsMessage msg){
        String telNum = msg.getDisplayOriginatingAddress();
        String context = msg.getDisplayMessageBody();
        ToastUtils.showShortToast(telNum+"   "+context);
        mUdpCommunication.sendData(telNum+"   "+context);
    }
}
