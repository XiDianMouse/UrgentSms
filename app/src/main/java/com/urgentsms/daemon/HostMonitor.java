package com.urgentsms.daemon;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.blankj.utilcode.utils.ToastUtils;

/**
 * @auther gbh
 * Created on 2017/4/9.
 */

public class HostMonitor extends Service{
    @Override
    public void onCreate(){
        super.onCreate();
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
}
