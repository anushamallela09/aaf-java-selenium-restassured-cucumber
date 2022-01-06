package com.go.rest.utils;

import java.io.FileInputStream;
import java.util.Properties;

public class Config {

    public static Properties properties = null;

    public static Properties init(){

        properties = new Properties();
        try{
            String rootFolderName = System.getProperty("user.dir");
            FileInputStream fis = new FileInputStream( rootFolderName + "/src/test/resources/config.properties");
            properties.load(fis);
            fis.close();
        }catch(Exception e){
            throw new RuntimeException(e);
        }
        return properties;
    }

}
