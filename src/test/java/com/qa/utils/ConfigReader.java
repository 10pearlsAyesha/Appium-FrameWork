package com.qa.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private final Properties properties;

    public ConfigReader(){
        BufferedReader reader;
        String propertyFilePath = "configuration/Config.properties";
        try {
            reader = new BufferedReader(new FileReader(propertyFilePath));
            properties = new Properties();
            try{
                properties.load(reader);
                reader.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }catch (FileNotFoundException e){
            e.printStackTrace();
            throw new RuntimeException("Configuration.properties not found at " + propertyFilePath);
        }
    }

    public String getAutomationName(){
        String androidAutomationName = properties.getProperty("androidAutomationName");
        if (androidAutomationName != null) return  androidAutomationName;
        else throw new RuntimeException("androidPlatformName not specified in the Configuration.properties file");
    }

    public String getAppPackageName(){
        String androidAppPackage = properties.getProperty("androidAppPackage");
        if (androidAppPackage != null) return  androidAppPackage;
        else throw new RuntimeException("androidAppPackage not specified in the Configuration.properties file");
    }

    public String getAppActivity(){
        String androidAppActivity = properties.getProperty("androidAppActivity");
        if (androidAppActivity != null) return  androidAppActivity;
        else throw new RuntimeException("androidAppActivity not specified in the Configuration.properties file");
    }

    public String getCommandTimeoutValue(){
        String androidCommandTimeoutValue = properties.getProperty("androidCommandTimeoutValue");
        if (androidCommandTimeoutValue != null) return  androidCommandTimeoutValue;
        else throw new RuntimeException("androidCommandTimeoutValue not specified in the Configuration.properties file");
    }

    public String getAPKPath(){
        String androidApkPath = properties.getProperty("androidApkPath");
        if (androidApkPath != null) return  androidApkPath;
        else throw new RuntimeException("androidApkPath not specified in the Configuration.properties file");
    }

    public String getNoReset(){
        String androidNoReset = properties.getProperty("androidNoReset");
        if (androidNoReset != null) return  androidNoReset;
        else throw new RuntimeException("androidNoReset not specified in the Configuration.properties file");
    }

    public String getAppiumServerEndPoint(){
        String androidAppiumServerEndpointURL = properties.getProperty("androidAppiumServerEndpointURL");
        if (androidAppiumServerEndpointURL != null) return  androidAppiumServerEndpointURL;
        else throw new RuntimeException("androidAppiumServerEndpointURL not specified in the Configuration.properties file");
    }
}
