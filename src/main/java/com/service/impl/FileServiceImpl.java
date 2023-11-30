package com.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.entity.File;
import com.pojo.custom.FileUploadResult;
import com.pojo.custom.FileVO;
import com.service.FileService;
import com.mapper.FileMapper;
import com.util.FileUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;
import java.util.UUID;

/**
* @author zhoujianze
* @description 针对表【file】的数据库操作Service实现
* @createDate 2023-11-30 17:05:47
*/
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, File>
    implements FileService{
    /**
     * 文件保存路径
     */
    private static final String FILE_PATH="/Users/test/";
    /**
     * 上传文件保存文件夹
     */
    private static final String UPLOAD_FILE="uploadFile";

    @Override
    public FileUploadResult uploadFile(MultipartFile multipartFile) {
        String fileName = multipartFile.getOriginalFilename();
        String suffix = Objects.requireNonNull(fileName).substring(fileName.lastIndexOf("."));
        File file=new File(fileName,UPLOAD_FILE,suffix);
        //存储文件
        String actualFileName= file.getId()+suffix;
        FileUtil.fileUpload(multipartFile,actualFileName,FILE_PATH+UPLOAD_FILE);
        //插入数据库
        this.save(file);
        FileUploadResult fileUploadResult=new FileUploadResult();
        fileUploadResult.setFileName(fileName)
                .setFilePath(FILE_PATH+UPLOAD_FILE+"/"+actualFileName)
//                .setFileUrl();
        return null;
    }
}




