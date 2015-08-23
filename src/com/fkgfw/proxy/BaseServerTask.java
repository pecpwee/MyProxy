package com.fkgfw.proxy;

import com.fkgfw.proxy.Config.ConfigPojo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import static com.fkgfw.proxy.Utils.byteArray2HexString;



public abstract class BaseServerTask implements Runnable {
    public static String SHAKE_RECEIVE = "050100";
    public static String SHAKE_SEND = "0500";

    public static String REQUEST_HEADER = "050100";
    public static int REQUEST_HEADER_BYTE_OFFSET = 5;
    public static int REQUEST_HEADER_HEX_STR_OFFSET = REQUEST_HEADER_BYTE_OFFSET * 2;

    public static String RESPONSE_SUCCESS_HEADER = "050000";
    public static String RESPONSE_FAILED_HEADER = "0501";

    public static String ADDR_TYPE_IPV4 = "01";
    public static String ADDR_TYPE_HOSTNAME = "03";
    public static String ADDR_TYPE_IPV6 = "05";


    public enum ADDR_TYPE {
        IPV4, HOSTNAME, IPV6
    }

    public void forwarding(InputStream in, OutputStream out,byte[] buffer) {
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
