package com.strategy.impl;

import com.pojo.custom.FileCacheVO;
import com.strategy.ResponseStrategy;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
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
import java.util.Objects;

/**
 * description 下载文件策略处理类
 * @author 周建泽
 * @date 2023/11/27
 */
@Slf4j
@Component
public class DownLoadFileStrategy implements ResponseStrategy {

    /**
     * 最大字节大小
     */
    private static final int MAX_BYTE_SIZE = 4096;

    @Override
    public void handleResponse(HttpServletResponse response, FileCacheVO fileCacheVO) {
        //设置响应头
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileCacheVO.getFileName());
        response.setHeader("pragma", "no-cache");
        response.setHeader("cache-control", "no-cache");
        response.setHeader("expires", "0");
        //以流的形式返回文件
        Path filePath=Paths.get(fileCacheVO.getFilePath());
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
}
