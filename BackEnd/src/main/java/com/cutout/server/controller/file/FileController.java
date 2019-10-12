package com.cutout.server.controller.file;

import com.alibaba.fastjson.JSON;
import com.cutout.server.configure.exception.MessageException;
import com.cutout.server.configure.message.MessageCodeStorage;
import com.cutout.server.constant.ConstantConfigure;
import com.cutout.server.domain.bean.response.ResponseBean;
import com.cutout.server.utils.Bases;
import com.cutout.server.utils.FileBase;
import com.cutout.server.utils.ResponseHelperUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件上传
 */
@RestController
@RequestMapping("/v1")
public class FileController {

    private Logger logger = LoggerFactory.getLogger(FileController.class);

    @Value("${file.upload.path}")
    private String path;

    @Autowired
    private ResponseHelperUtil responseHelperUtil;

    @Autowired
    private MessageCodeStorage messageCodeStorage;

    @Autowired
    private FileBase fileBase;

    @Autowired
    private Bases bases;

    @RequestMapping(value = "/file", method = RequestMethod.POST)
    public ResponseBean fileUpload(@RequestParam("file") MultipartFile files, HttpServletRequest request) {
        String message = messageCodeStorage.success_code;
        Map<String,String> paths = null;
        try {
            Map<String,Object> attribute = (Map<String, Object>) request.getAttribute(ConstantConfigure.USER_ATTRIBUTE_KEY);
            if (attribute == null) {
                throw new MessageException(messageCodeStorage.user_upload_img_failed);
            }

            if (StringUtils.isEmpty(files)) {
                throw new MessageException(messageCodeStorage.user_upload_file_empty);
            }

            String email = (String)attribute.get("email");

            paths = new HashMap<>();
            String fileName = files.getOriginalFilename();

            // 按日期新建文件夹
            String imgPath = path + "/" + bases.transferLongToDate("yyyyMMdd",System.currentTimeMillis()) + "/";
            // 判断是否有文件夹路径，没有需要创建
            fileBase.isFileExist(imgPath);

            // 组装新的文件名  文件夹路径+邮箱地址+时间+随机数+文件名
            String filePath = imgPath +"/"+email + bases.getSystemSeconds() + bases.getRandom(10000,99999) + fileName;

            File localFile = new File(filePath);
            files.transferTo(localFile);
            paths.put("path",localFile.getAbsolutePath());
            paths.put("email",email);

        } catch (MessageException messageException) {
            message = messageException.getMessage();
        } catch (IOException ioException) {
            logger.error("IOException",ioException);
        } catch (Exception e) {
            logger.error("Exception",e);
        }

        return responseHelperUtil.returnMessage(message,paths);
    }

    /**
     * 创建多级目录文件
     *
     * @param path 文件路径
     * @throws IOException
     */
    private void createFile(String path) throws IOException {
        if (StringUtils.isEmpty(path)) {
            File file = new File(path);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            file.createNewFile();
        }
    }

}
