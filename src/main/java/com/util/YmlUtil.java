package com.util;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * description yml工具类
 * @author 周建泽
 * @date 2023/11/27
 */
@Component
@Getter
public class YmlUtil {
    /**
     * 文件路径
     */
    @Value("${file.path}")
    private String filePath;
    /**
     * 端口
     */
    @Value("${server.port}")
    private String serverPort;
    /**
     * 文件url
     */
    @Value("${file.ip}")
    private String ip;
    /**
     * 项目名
     */
    @Value("${server.servlet.context-path}")
    private String contextPath;
    /**
     * 文件上传文件夹
     */
    @Value("${file.upload-file}")
    private String uploadFileFolder;
    /**
     * 文件下载前缀
     */
    @Value("${file.file-download-prefix}")
    private String fileDownloadPrefix;

    /**
     * 获取项目url
     * @return  项目url
     */
    public String getProjectUrl(){
        return ip+":"+serverPort+contextPath+"/";
    }

    public String getDownLoadFileUrl(){
        return getProjectUrl()+fileDownloadPrefix+"/";
    }

    /**
     * 获取文件上传路径
     * @return  文件上传路径
     */
    public String getUploadPath(){
        return filePath+uploadFileFolder+"/";
    }



}
