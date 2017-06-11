package com.urgentsms.broadcastreceivers;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.blankj.utilcode.utils.ToastUtils;
import com.urgentsms.daemon.FileUtils;
import com.urgentsms.daemon.NativeRuntime;

import java.util.List;

import static com.urgentsms.app.MyApp.context;

/**
 * @auther gbh
 * Created on 2017/4/9.
 */

public class PhoneStateReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context,Intent intent){
        if(intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)){
            ToastUtils.showShortToast("开机完成,正在开启服务");
            startService();
        }
        else if(intent.getAction().equals(Intent.ACTION_USER_PRESENT)){
            judgeService(context);
        }
    }

    private void judgeService(Context context){
        ActivityManager activityManager = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> runningServiceInfos = activityManager.getRunningServices(100);
        for(int i=0;i<runningServiceInfos.size();i++) {
            ActivityManager.RunningServiceInfo runningServiceInfo = runningServiceInfos.get(i);
            if("com.urgentsms.daemon.HostMonitor".equals(runningServiceInfo.service.getClassName())) {
                ToastUtils.showShortToast("服务已开启");
                return;
            }
        }
        ToastUtils.showShortToast("正在开启服务...");
        startService();
    }

    private void startService(){
        NativeRuntime.getInstance().startService(context.getPackageName()+"/com.urgentsms.daemon.HostMonitor",FileUtils.createRootPath());
    }
}
