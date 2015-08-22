package com.fkgfw.proxy.InnerServer;

import com.fkgfw.proxy.*;
import com.fkgfw.proxy.Secure.TransSecuritySupport;

import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;


public class InnerWorkingTask extends BaseServerTask {

    private ADDR_TYPE address_type;
    private Socket targetSocket;
    private TransSecuritySupport mSecurity;

    public InnerWorkingTask(Socket socket) {
        this.targetSocket = socket;
        this.mSecurity =new TransSecuritySupport();
    }

    @Override
    public void run() {

        try {
            startImpl(targetSocket);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void startImpl(Socket socket) throws IOException, InterruptedException {
        final byte[] buffer = new byte[Config.BufferSize];


        final InputStream localin = socket.getInputStream();
        final OutputStream localOut = socket.getOutputStream();

        Socket OuterSocket = new Socket(Config.OuterServerIP, Config.OuterServerPort);

        final CipherOutputStream OuterOUT = new CipherOutputStream(OuterSocket.getOutputStream(), mSecurity.getEncryptCipher());
        final CipherInputStream OuterIN = new CipherInputStream(OuterSocket.getInputStream(), mSecurity.getDecryptCipher());
        //TODO
//        final OutputStream OuterOUT=OuterSocket.getOutputStream();
//        final InputStream OuterIN=OuterSocket.getInputStream();


        Thread tSendOuterServer = new Thread(new Runnable() {
            @Override
            public void run() {
                forwarding(localin, OuterOUT, buffer);
            }
        });


        Thread tGetFromOuterServer = new Thread(new Runnable() {
            @Override
            public void run() {
                final byte[] bufferAnother = new byte[Config.BufferSize];
                forwarding(OuterIN, localOut, bufferAnother);

            }
        });

        tSendOuterServer.start();
        tGetFromOuterServer.start();

        tSendOuterServer.join();
        tGetFromOuterServer.join();
        System.out.println("Inner connection exit");
    }


}
