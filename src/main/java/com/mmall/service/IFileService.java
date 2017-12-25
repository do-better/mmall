package com.mmall.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by yuanli on 2017/9/13.
 */
public interface IFileService {

    String upload(MultipartFile file, String path);
}
