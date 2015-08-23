package com.fkgfw.proxy.InnerServer;

import com.fkgfw.proxy.Config.ConfigManager;
import com.fkgfw.proxy.Config.ConfigPojo;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class InnerServerMain {


    ExecutorService threadPool;

    public InnerServerMain(ConfigPojo configObj) {

        final ServerSocket parentSocket;
        threadPool = Executors.newCachedThreadPool();
        try {
            parentSocket = new ServerSocket(configObj.getInnerServerPort());
            while (true) {
                Socket socket = parentSocket.accept();
                System.out.println("accepted inner connection");
                threadPool.submit(new InnerWorkingTask(socket,configObj));

            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
