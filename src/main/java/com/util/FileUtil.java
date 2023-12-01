package com.util;

import com.enums.HttpResultEnum;
import com.pojo.custom.OperateResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * description 文件工具类
 * @author 周建泽
 * @date 2023/11/27
 */
@Slf4j
public class FileUtil {

    /**
     * 文件上传
     * @param uploadFile    文件
     * @param fileName  文件名
     * @param saveFolderPath    保存文件夹路径
     * @return  操作结果
     */
    public static OperateResult fileUpload(MultipartFile uploadFile, String fileName, String saveFolderPath){
        OperateResult operateResult=new OperateResult();
        File folderPath = new File(saveFolderPath);
        //创建文件夹
        if(!folderPath.exists()){
            boolean flag=folderPath.mkdirs();
            if(!flag){
                return operateResult.setResult(false).setMessage("文件夹创建失败");
            }
        }
        //存储文件
        try (InputStream inputStream = new ByteArrayInputStream(uploadFile.getBytes())) {
            FileOutputStream fileOutputStream = new FileOutputStream(saveFolderPath + fileName);
            byte[] buffer = new byte[1024];
            while (inputStream.read(buffer) != -1) {
                fileOutputStream.write(buffer);
            }
            fileOutputStream.close();
        } catch (IOException e) {
            log.error("文件上传失败{}", e.getMessage());
            return operateResult.setResult(false).setMessage("文件读写失败");
        }
        return operateResult;
    }
}
