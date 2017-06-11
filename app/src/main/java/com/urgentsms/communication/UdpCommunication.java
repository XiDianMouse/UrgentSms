package com.urgentsms.communication;

import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * @auther gbh
 * Email:xidian_guobenhao@163.com
 * Created on 2017/6/11.
 */

public class UdpCommunication {
    private SendThread mSendThread;
    private ReceiveThread mReceiveThread;
    private DatagramSocket mDatagramSocket;

    public UdpCommunication(){
        try{
            mDatagramSocket = new DatagramSocket(3000);
        }catch (SocketException e){

        }
        mSendThread = new SendThread(mDatagramSocket);
        mReceiveThread = new ReceiveThread(mDatagramSocket);
        new Thread(mSendThread).start();
		new Thread(mReceiveThread).start();
    }

    public void sendData(String order){
        mSendThread.setSendOrder(order);
    }
}
