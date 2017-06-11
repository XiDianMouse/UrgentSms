package com.urgentsms.communication;

import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * @auther gbh
 * Email:xidian_guobenhao@163.com
 * Created on 2017/6/11.
 */

public class ReceiveThread implements Runnable{
    private DatagramSocket mDatagramSocket;
    private byte[] revBuf;
    private boolean runFlag;

    public ReceiveThread(DatagramSocket datagramSocket){
        mDatagramSocket = datagramSocket;
        revBuf = new byte[1024];
        runFlag = true;
    }

    public void stopReceiveThread(){
        runFlag = false;
    }

    @Override
    public void run(){
        while(runFlag){
            try
            {
                DatagramPacket receivePacket = new DatagramPacket(revBuf,revBuf.length);
                mDatagramSocket.receive(receivePacket);
                String revData = getHexString(receivePacket);
                Log.e(null,"接收数据为:"+revData);
            }
            catch (IOException e)
            {
                Log.e(null,"socket连接已断开");
            }
        }
    }

    //将byte[]数组转换为16进制字符串
    private String getHexString(DatagramPacket inPacket)
    {
        String result = "";
        for(int i=0;i<inPacket.getLength();i++)
        {
            result+=Integer.toString((revBuf[i]&0xff)+0x100,16).substring(1);
        }
        return result;
    }
}
