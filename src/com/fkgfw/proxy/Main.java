package com.fkgfw.proxy;

import com.fkgfw.proxy.InnerServer.InnerServerMain;
import com.fkgfw.proxy.OuterServer.OuterServerMain;
import java.io.IOException;

public class Main {


    public static void main(String[] args) throws IOException {
        // write your code here


        if (args.length == 0) {
            System.out.println("please add parameters,inner or outer ,and with a port number\n");
            System.exit(0);
        } else

        if (args[0].equals( "inner")) {
            new InnerServerMain();
        }
        else
        if (args[0].equals("outer")){
            new OuterServerMain();
        }
        else System.exit(-1);


//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                new OuterServerMain();
//            }
//        }).start();
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                new InnerServerMain();
//            }
//        }).start();


    }
}
