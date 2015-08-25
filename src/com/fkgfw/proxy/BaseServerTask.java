package com.fkgfw.proxy;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;



public abstract class BaseServerTask implements Runnable {
    public static byte[] SHAKE_RECEIVE = {0x05, 0x01, 0x00};
    public static byte[] SHAKE_SEND = {0x05, 0x00};
    public static byte[] REQUEST_HEADER = {0x05, 0x01, 0x00};
    public static int REQUEST_HEADER_BYTE_OFFSET = 5;
    public static byte[] RESPONSE_SUCCESS_HEADER = {0x05, 0x00, 0x00};
    public static byte[] RESPONSE_FAILED_HEADER = {0x05, 0x01};
    public static byte[] ADDR_TYPE_IPV4 = {0x01};
    public static byte[] ADDR_TYPE_HOSTNAME = {0x03};
    public static byte[] ADDR_TYPE_IPV6 = {0x05};


    public enum ADDR_TYPE {
        IPV4, HOSTNAME, IPV6
    }

    public void forwarding(InputStream in, OutputStream out, byte[] buffer) {
        int receiveCount;
        try {
            while ((receiveCount = in.read(buffer)) != -1) {
                if (receiveCount > 0) {
                    out.write(buffer, 0, receiveCount);
                    out.flush();
//                    System.out.println("transmit:" + byteArray2HexString(buffer, 20));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
