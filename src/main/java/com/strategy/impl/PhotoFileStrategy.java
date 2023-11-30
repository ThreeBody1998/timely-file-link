package com.strategy.impl;

import com.pojo.custom.FileCacheVO;
import com.strategy.ResponseStrategy;
import com.util.YmlUtil;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * description 图片处理策略接口
 * @author 周建泽
 * @date 2023/11/27
 */
@Component
@Slf4j
public class PhotoFileStrategy implements ResponseStrategy {

    @Autowired
    private YmlUtil ymlUtil;
    /**
     * 最大字节大小
     */
    private static final int MAX_BYTE_SIZE = 4096;

    @Override
    public void handleResponse(HttpServletResponse response, FileCacheVO fileCacheVO) {
        //设置响应头
        response.setContentType(getContentTypeByFileName(fileCacheVO.getFileName()));
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileCacheVO.getFileName());
        //以流的形式返回文件
        Path filePath=Paths.get(ymlUtil.getFilePath()+fileCacheVO.getFileUrl());
        try {
            Resource resource = new UrlResource(filePath.toUri());
            InputStream inputStream = Objects.requireNonNull(resource).getInputStream();
            var outputStream = response.getOutputStream();
            byte[] buffer = new byte[MAX_BYTE_SIZE];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.close();
        } catch (MalformedURLException e) {
            log.error("文件下载失败{}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (IOException e) {
            log.error("文件IO异常{}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 根据文件名获取contentType
     * @param fileName  文件名
     * @return  contentType
     */
    private String getContentTypeByFileName(String fileName){
        Map<String,String> contentType=new HashMap<>();
        contentType.put("jpg",MediaType.IMAGE_JPEG_VALUE);
        contentType.put("png",MediaType.IMAGE_PNG_VALUE);
        contentType.put("gif",MediaType.IMAGE_GIF_VALUE);
        String suffix=fileName.substring(fileName.lastIndexOf(".")+1);
        return contentType.get(suffix);
    }

}
