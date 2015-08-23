package com.fkgfw.proxy;

import com.fkgfw.proxy.Config.ConfigManager;
import com.fkgfw.proxy.Config.ConfigPojo;
import com.fkgfw.proxy.InnerServer.InnerServerMain;
import com.fkgfw.proxy.OuterServer.OuterServerMain;

import java.io.IOException;

public class Main {


    public static void main(String[] args) throws IOException {
        // write your code here

        ConfigManager c = new ConfigManager();
        final ConfigPojo configObj = c.getConfig();
        System.out.println(configObj.toString());

        if (configObj.isDEBUG() == true) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    new OuterServerMain(configObj);
                }
            }).start();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    new InnerServerMain(configObj);
                }
            }).start();

        } else {
            if (configObj.getRUN_MODE() == ConfigPojo.RUNModeEnum.inner){
                new InnerServerMain(configObj);
            }
            else {
                new OuterServerMain(configObj);
            }

        }

    }
}
