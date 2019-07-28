package com.wl.Tangpoetry.config;


import lombok.Data;
import org.apache.xpath.operations.Bool;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/*
配置url,用户，密码等
 */
@Data
public class ConfigPropertise {
    private String pachongBase;
    private String pachongPath;
    private boolean pachongDetail;

    private String dbUsername;
    private String dbPassword;
    private String  dbUrl;
    private  String dbDriverClass;

    private  boolean enableConsole;

    public ConfigPropertise(){
        //从外部，外部文件加载
        InputStream inputStream=ConfigPropertise.class.getClassLoader ()
                .getResourceAsStream ("config.propertise");
        Properties p=new Properties ();
        try {
            p.load (inputStream);
            System.out.println (p);
        } catch (IOException e) {
            e.printStackTrace ();
        }

        //怎mo读呢
        this.pachongBase=String.valueOf (p.get ("pachong.base"));
        this.pachongPath=String.valueOf (p.get ("pachong.path"));
        this.pachongDetail=Boolean.parseBoolean (String.valueOf (p.get ("pachong.detail")));

        this.dbUsername=String.valueOf (p.get ("db.username"));
        this.dbPassword=String.valueOf (p.get ("db.password"));
        this.dbUrl=String.valueOf (p.get ("db.url"));
        this.dbDriverClass=String.valueOf (p.get ("db.driver_class"));

        this.enableConsole=Boolean.valueOf (String.valueOf (p.getProperty ("config.enable_Console","false")));

    }

//    public static void main(String[] args) {
//        new ConfigPropertise ();
//    }
}
