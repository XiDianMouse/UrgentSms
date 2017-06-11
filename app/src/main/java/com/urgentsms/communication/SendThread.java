package com.urgentsms.communication;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * @auther gbh
 * Email:xidian_guobenhao@163.com
 * Created on 2017/6/11.
 */

public class SendThread implements Runnable{
    private DatagramSocket mDatagramSocket;
    private boolean runFlag;
    private boolean sendFlag;
    private String sendOrder;
    private String addrUdp;
    private int portUdp;

    public SendThread(DatagramSocket datagramSocket){
        mDatagramSocket = datagramSocket;
        runFlag = true;
        sendFlag = false;
        addrUdp = "255.255.255.255";
        portUdp = 4972;
    }

    public void stopSendThread(){
        runFlag = false;
    }

    public void setSendOrder(String order){
        sendOrder = order;
        sendFlag = true;
    }

    @Override
    public void run(){
        while(runFlag){
            if(sendFlag){
                byte[] sendByte = sendOrder.getBytes();
                try
                {
                    DatagramPacket sendPacket = new DatagramPacket(sendByte,sendByte.length, InetAddress.getByName(addrUdp),portUdp);
                    mDatagramSocket.send(sendPacket);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                sendFlag = false;
            }
        }
    }
}
