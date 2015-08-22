package com.fkgfw.proxy;


public class Config {
    public static String OuterServerIP = "127.0.0.1";
    public static int OuterServerPort = 2333;

    public static int InnerServerPort = 1080;

    public static String ENCRYPT_SEED = "admin";

    public static int BufferSize = 11024;

    public enum RunningModeEnum {
        innerServer,OuterServer
    }
//    public static String ENCRYPT_WAY=" ";

}
