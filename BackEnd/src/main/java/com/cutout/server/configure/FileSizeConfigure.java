package com.cutout.server.configure;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;

@Configuration
public class FileSizeConfigure {

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();

        // 单个文件最大根据配置
        factory.setMaxFileSize(DataSize.ofMegabytes(10L));
        // 设置总上传数据总大小
        factory.setMaxRequestSize(DataSize.ofGigabytes(10L));
        return factory.createMultipartConfig();
    }
}
