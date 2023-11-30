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

    @Value("${file.upload-file}")
    private String uploadFileFolder;

    /**
     * 获取项目url
     * @return  项目url
     */
    public String getUrl(){
        return ip+":"+serverPort+"/"+contextPath+"/"+uploadFileFolder+"/";
    }



}
