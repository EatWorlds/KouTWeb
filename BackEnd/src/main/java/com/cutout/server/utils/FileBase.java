package com.cutout.server.utils;

import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class FileBase {

    /**
     * 判断文件目录是否存在，不存在，创建目录
     * @Title: isFileExist
     * @Description: TODO
     * @param: @param path	路径
     * @return: void
     * @throws
     */
    public void isFileExist(String path){
        File dir = new File(path);
        if (!dir.exists()){
            dir.mkdirs();
        }
    }
}
