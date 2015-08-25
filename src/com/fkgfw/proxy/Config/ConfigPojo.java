package com.fkgfw.proxy.Config;

/**
 * Created by Salmon on 2015/8/22.
 */
public class ConfigPojo {

    private boolean DEBUG = true;
    private RUNModeEnum RUN_MODE = RUNModeEnum.inner;
    private String OuterServerIP = "127.0.0.1";
    private int OuterServerPort = 2333;
    private int InnerServerPort = 1080;

    private String ENCRYPT_SEED = "admin";

    private int BufferSize = 11024;

    public ThreadPoolModeEnum ThreadPoolMode = ThreadPoolModeEnum.Cached;

    private int MaxFixedThreadPoolSize = 1024;



    public RUNModeEnum getRUN_MODE() {
        return RUN_MODE;
    }

    public void setRUN_MODE(RUNModeEnum RUN_MODE) {
        this.RUN_MODE = RUN_MODE;
    }

    public String getOuterServerIP() {
        return OuterServerIP;
    }

    public void setOuterServerIP(String outerServerIP) {
        OuterServerIP = outerServerIP;
    }

    public int getOuterServerPort() {
        return OuterServerPort;
    }

    public void setOuterServerPort(int outerServerPort) {
        OuterServerPort = outerServerPort;
    }

    public int getInnerServerPort() {
        return InnerServerPort;
    }

    public void setInnerServerPort(int innerServerPort) {
        InnerServerPort = innerServerPort;
    }

    public String getENCRYPT_SEED() {
        return ENCRYPT_SEED;
    }

    public void setENCRYPT_SEED(String ENCRYPT_SEED) {
        this.ENCRYPT_SEED = ENCRYPT_SEED;
    }

    public int getBufferSize() {
        return BufferSize;
    }

    public void setBufferSize(int bufferSize) {
        BufferSize = bufferSize;
    }

    public ThreadPoolModeEnum getThreadPoolMode() {
        return ThreadPoolMode;
    }

    public void setThreadPoolMode(ThreadPoolModeEnum threadPoolMode) {
        this.ThreadPoolMode = threadPoolMode;
    }

    public int getMaxFixedThreadPoolSize() {
        return MaxFixedThreadPoolSize;
    }

    public void setMaxFixedThreadPoolSize(int maxFixedThreadPoolSize) {
        MaxFixedThreadPoolSize = maxFixedThreadPoolSize;
    }

    public boolean isDEBUG() {
        return DEBUG;
    }

    public void setDEBUG(boolean DEBUG) {
        this.DEBUG = DEBUG;
    }


    public enum RUNModeEnum {
        inner, outer
    }


    public enum ThreadPoolModeEnum {
        Cached, Fixed
    }


    //getter and setter methods

    @Override
    public String toString() {
        return "ConfigPojo{" +
                "RUN_MODE=" + RUN_MODE +
                ", OuterServerIP='" + OuterServerIP + '\'' +
                ", OuterServerPort=" + OuterServerPort +
                ", InnerServerPort=" + InnerServerPort +
                ", ENCRYPT_SEED='" + ENCRYPT_SEED + '\'' +
                ", BufferSize=" + BufferSize +
                ", ThreadPoolMode=" + ThreadPoolMode +
                ", MaxFixedThreadPoolSize=" + MaxFixedThreadPoolSize +
                ", DEBUG=" + DEBUG +
                '}';
    }
}
