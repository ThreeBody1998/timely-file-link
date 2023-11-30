package com.service;

import com.entity.File;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pojo.custom.FileUploadResult;
import com.pojo.custom.FileVO;
import org.springframework.web.multipart.MultipartFile;

/**
* @author zhoujianze
* @description 针对表【file】的数据库操作Service
* @createDate 2023-11-30 17:05:47
*/
public interface FileService extends IService<File> {
    /**
     * 上传文件
     * @param multipartFile 上传文件
     * @return  文件信息
     */
    FileUploadResult uploadFile(MultipartFile multipartFile);

}
