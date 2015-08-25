package com.fkgfw.proxy.OuterServer;

import com.fkgfw.proxy.*;
import com.fkgfw.proxy.Config.ConfigPojo;
import com.fkgfw.proxy.Secure.TransSecuritySupport;

import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import java.io.*;
import java.net.Socket;
import java.net.SocketException;

import static com.fkgfw.proxy.Utils.*;


public class OuterWorkingTask extends BaseServerTask {

    private Socket targetSocket;
    private TransSecuritySupport mSecurity;
    private ConfigPojo configObj;
    private ADDR_TYPE address_type;

    public OuterWorkingTask(Socket socket, ConfigPojo configObj) {
        this.targetSocket = socket;
        this.mSecurity = new TransSecuritySupport(configObj);
        this.configObj = configObj;
    }

    @Override
    public void run() {
        try {
            startImpl(targetSocket);
        } catch (SocketException e) {
//            System.out.println("connection closed");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("outer close connection");
        }
    }

    private void startImpl(Socket socket) throws IOException, InterruptedException {

        final byte[] buffer = new byte[configObj.getBufferSize()];


        int bufferReadCount = 0;

        final CipherInputStream InnerIN = new CipherInputStream(socket.getInputStream(), mSecurity.getDecryptCipher());
        final CipherOutputStream InnerOut = new CipherOutputStream(socket.getOutputStream(), mSecurity.getEncryptCipher());
        //       final InputStream InnerIN =socket.getInputStream();
        //       final OutputStream InnerOut =socket.getOutputStream();


//readin address request
        bufferReadCount = InnerIN.read(buffer);
        if (bufferReadCount <= 0) {
//            System.out.println("connection stop ...");
            return;
        }

        if (!isByteArrayEqual(REQUEST_HEADER, buffer, 0)) {
            return;
        }
        String request_addr = null;
        int request_port = -1;

        //GET ADDR By diffrent type ,ipv4 or hostname ..ignore ipv6
        if (isByteArrayEqual(ADDR_TYPE_HOSTNAME, buffer, 3)) {
            //hostname typee
//            System.out.println("it's an url request");
            int addrLength = getHostNameLength(buffer, 4);//hex STRING to int
            //substring 左开右闭
            request_addr = byteArray2ASCStr(buffer, 5, addrLength);
            request_port = getPortFromByteArrayAtIPtype(buffer, REQUEST_HEADER_BYTE_OFFSET + addrLength);
            address_type = ADDR_TYPE.HOSTNAME;

        } else if (isByteArrayEqual(ADDR_TYPE_IPV4, buffer, 3)) {
//            System.out.println("it's an ip request");
            request_addr = getIpAddrFromByteArray(buffer, 4);
            request_port = getPortFromByteArrayAtIPtype(buffer, 4 + 4);
            address_type = ADDR_TYPE.IPV4;
        }

        System.out.println(request_addr + ":" + request_port);


        //write back sccuss response
        //todo check whether the proxy you can proxy
        switch (address_type) {
            case HOSTNAME:
            case IPV4:
//                InnerOut.write(RESPONSE_SUCCESS_HEADER);
//                InnerOut.write(buffer,3,bufferReadCount);
                InnerOut.write(byteArrayConcat(RESPONSE_SUCCESS_HEADER, 0, 3, buffer, 3, bufferReadCount));//todo

                //头部+请求4字节后面原样返回。
                break;
            case IPV6:
                //todo ipv6
                break;
        }

        InnerOut.flush();


        final Socket websiteSocket = new Socket(request_addr, request_port);
        final BufferedOutputStream websiteOut = new BufferedOutputStream(websiteSocket.getOutputStream());
        final BufferedInputStream websiteIn = new BufferedInputStream(websiteSocket.getInputStream());


        Thread threadWriteOut = new Thread(new Runnable() {
            @Override
            public void run() {
                forwarding(InnerIN, websiteOut, buffer);
            }
        });


        Thread threadWriteBack = new Thread(new Runnable() {
            @Override
            public void run() {
                byte[] bufferAnother = new byte[configObj.getBufferSize()];
                forwarding(websiteIn, InnerOut, bufferAnother);
            }
        });

        threadWriteOut.start();
        threadWriteBack.start();
//        System.out.println("outer transmission started");

        threadWriteOut.join();
        threadWriteBack.join();
        socket.close();
        websiteSocket.close();
//        System.out.println("Outer connection exit");


    }


}
