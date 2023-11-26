package com.controller;

import com.enums.HttpResultEnum;
import com.pojo.custom.FileVO;
import com.pojo.custom.ResponseResult;
import com.util.CacheManagerUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.core.io.Resource;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * @Author 灵感大王
 * @Date 2023/11/26
 **/
@RestController
@RequestMapping
@AllArgsConstructor
@Slf4j
public class FileController {

    private CacheManagerUtil<String, FileVO> cacheManagerUtil;
    /**
     * 预制链接前缀
     */
    private static final String URL_PREFIX="http://127.0.0.1:8080/timelyFileLink/file/";

    @GetMapping("/generateFileUrl")
    public ResponseResult<String> generateFileUrl(){
        ResponseResult<String> responseResult=new ResponseResult<>();
        //生成唯一性ID
        String uuid= UUID.randomUUID().toString();
        //存入缓存
        FileVO fileVO=new FileVO("test.png","F:\\桌面\\公司项目\\timely-file-link\\src\\main\\resources\\static\\test.png");
        cacheManagerUtil.put(uuid,fileVO,CacheManagerUtil.TTL);
        String url=URL_PREFIX+uuid;
        return responseResult.setData(url);
    }

    @GetMapping("/file/{uuid}")
    public void file(@PathVariable String uuid, HttpServletResponse response) throws MalformedURLException {
        //校验
        FileVO fileVO=cacheManagerUtil.get(uuid);
        if(fileVO==null){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }else {
            //以流的形式返回文件
            String url = fileVO.getFileUrl();
            Path filePath=Paths.get(url);
            Resource resource = new UrlResource(filePath.toUri());
            //设置响应头
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileVO.getFileName());
            //将文件以流的形式写入响应
            try (var inputStream = resource.getInputStream()) {
                var outputStream = response.getOutputStream();
                byte[] buffer = new byte[4096];
                int bytesRead = -1;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            } catch (IOException e) {
                log.error("文件下载失败{}", e.getMessage());
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        }
    }

}
