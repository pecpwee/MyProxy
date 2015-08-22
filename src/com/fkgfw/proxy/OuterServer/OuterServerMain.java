package com.fkgfw.proxy.OuterServer;

import com.fkgfw.proxy.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class OuterServerMain {


    ExecutorService threadPool;

    public OuterServerMain() {

        final ServerSocket parentSocket;
        threadPool = Executors.newCachedThreadPool();
        try {
            parentSocket = new ServerSocket(Config.OuterServerPort);

            while (true) {
                Socket socket = parentSocket.accept();
                threadPool.submit(new OuterWorkingTask(socket));
                System.out.println("Outer Server has accept connection");

            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}

