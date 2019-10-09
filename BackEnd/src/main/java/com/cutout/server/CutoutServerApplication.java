package com.cutout.server;

import com.cutout.server.utils.Bases;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 2019-09-16
 *
 * @author dimple
 *
 * @SpringBootApplication 标注在某个类上就说明这个类是SpringBoot的主配置类
 *
 * SpringBoot就应该运行这个类的main方法，来启动SpringBoot应用
 */
@SpringBootApplication // 来标注一个主程序类，说明这是一个SpringBoot应用
public class CutoutServerApplication {

    public static void main(String[] args) {
        // Spring应用启动
        SpringApplication.run(CutoutServerApplication.class, args);
    }

}
