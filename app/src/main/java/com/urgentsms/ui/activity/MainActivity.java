package com.urgentsms.ui.activity;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.blankj.utilcode.utils.ToastUtils;
import com.urgentsms.R;
import com.urgentsms.daemon.FileUtils;
import com.urgentsms.daemon.NativeRuntime;

import java.util.List;

import static com.urgentsms.app.MyApp.context;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initService();
        judgeService(this.getApplication());
    }

    private void initService(){
        String executable = "libhelper.so";
        String aliasfile = "helper";
        String parafind = "/data/data/" + getPackageName() + "/" + aliasfile;
        String retx = "false";
        NativeRuntime.getInstance().RunExecutable(getPackageName(), executable, aliasfile, getPackageName() + "/com.urgentsms.daemon.HostMonitor");
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
