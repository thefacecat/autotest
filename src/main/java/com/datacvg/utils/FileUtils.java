package com.datacvg.utils;


import com.datacvg.config.Config;

import java.util.ResourceBundle;

public class FileUtils {

    //获取excel驱动文件目录
    public static void getFilePath(){
        Config.bundle = ResourceBundle.getBundle("exceldriver");
        Config.filepath = Config.bundle.getString("filepath");
//        System.out.println(Config.filepath);
    }
}
