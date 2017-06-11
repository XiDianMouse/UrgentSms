package com.urgentsms.daemon;

import android.os.Environment;

import com.urgentsms.app.MyApp;


/**
 * @auther gbh
 * Created on 2017/4/9.
 */

public class FileUtils {
    //根缓存目录
    private static String cacheRootPath = "";
    //SD卡是否可用
    public static boolean isSdCardAvailable(){
        return Environment.getExternalStorageDirectory().equals(Environment.MEDIA_MOUNTED);
    }
    //创建根缓存目录
    public static String createRootPath(){
        if(isSdCardAvailable()){
            cacheRootPath = MyApp.context.getExternalCacheDir().getPath();
        }
        else{
            cacheRootPath = MyApp.context.getCacheDir().getPath();
        }
        return cacheRootPath;
    }
}
