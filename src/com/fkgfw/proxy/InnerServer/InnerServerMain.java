package com.fkgfw.proxy.InnerServer;

import com.fkgfw.proxy.Config;
import com.fkgfw.proxy.Secure.TransSecuritySupport;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class InnerServerMain {


    ExecutorService threadPool;

    public InnerServerMain() {

        final ServerSocket parentSocket;
        threadPool = Executors.newCachedThreadPool();
        try {
            parentSocket = new ServerSocket(Config.InnerServerPort);

            while (true) {
                Socket socket = parentSocket.accept();
                threadPool.submit(new InnerWorkingTask(socket));
                System.out.println("Inner Server has accept connection");

            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
