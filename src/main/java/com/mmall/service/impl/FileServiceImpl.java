package com.mmall.service.impl;

import com.google.common.collect.Lists;
import com.mmall.service.IFileService;
import com.mmall.util.FTPUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by yuanli on 2017/9/13.
 */
@Service("iFileService")
public class FileServiceImpl implements IFileService {

    private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    //文件上传配置在dispathcher-servlet.xml中
    public String upload(MultipartFile file, String path) {
        String fileName = file.getOriginalFilename();
        //扩展名
        String fileExtensionName = fileName.substring(fileName.lastIndexOf(".") + 1);
        //使用uuid保证上传文件名的唯一性
        String uploadFileName = UUID.randomUUID().toString() + "." + fileExtensionName;
        logger.info("开始上传文件，上传文件名：{},上传文件路径：{},新文件名：{}",fileName,path,uploadFileName);

        File fileDir = new File(path);
        if(!fileDir.exists()) {
            fileDir.setWritable(true);
            //mkdirs()方法会文件夹一并创建
            fileDir.mkdirs();
        }
        File targetFile = new File(path,uploadFileName);

        try {
            //将字节从此通道的文件传输到给定的可写入字节通道
            file.transferTo(targetFile);
            //文件上传到Tomcat成功了

            // 将targetFile上传到我们的ftp服务器
            //这个list里只有targetFile一个文件
            FTPUtil.uploadFile(Lists.<File>newArrayList(targetFile));
            //上传完成后，删除upload文件夹下的文件
            targetFile.delete();

        } catch (IOException e) {
            logger.error("上传文件异常",e);
            return null;
        }

        return targetFile.getName();
    }
}
