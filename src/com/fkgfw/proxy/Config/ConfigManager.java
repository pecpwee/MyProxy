package com.fkgfw.proxy.Config;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;

public class ConfigManager {

    public static String CONFIG_NAME = "config.json";


    public ConfigManager() {
    }

    public ConfigPojo getConfig() {

        ConfigPojo config = null;
        File file = new File(CONFIG_NAME);
        Gson gson=new GsonBuilder().setPrettyPrinting().create();
        if (file.exists()) {

            try {
                BufferedReader br = new BufferedReader(new FileReader(CONFIG_NAME));
                config = gson.fromJson(br, ConfigPojo.class);
                br.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        } else {
            //CREATE default config file
            config = new ConfigPojo();
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(CONFIG_NAME));
                bw.write(gson.toJson(config));
                bw.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return config;
    }
}
