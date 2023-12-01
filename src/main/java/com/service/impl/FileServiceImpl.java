package com.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.entity.File;
import com.pojo.custom.FileUploadResult;
import com.pojo.custom.FileVO;
import com.service.FileService;
import com.mapper.FileMapper;
import com.util.FileUtil;
import com.util.YmlUtil;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class FileServiceImpl extends ServiceImpl<FileMapper, File>
    implements FileService{

    private YmlUtil ymlUtil;
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
        FileUtil.fileUpload(multipartFile,actualFileName,ymlUtil.getUploadPath());
        //插入数据库
        this.save(file);
        FileUploadResult fileUploadResult=new FileUploadResult();
        fileUploadResult.setFileId(file.getId())
                .setFileName(fileName)
                .setFileUrl(ymlUtil.getDownLoadFileUrl()+file.getId())
                .setFilePath(ymlUtil.getUploadPath()+actualFileName);
        return fileUploadResult;
    }
}




