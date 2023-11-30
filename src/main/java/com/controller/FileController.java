package com.controller;

import com.enums.HttpResultEnum;
import com.pojo.custom.*;
import com.service.FileService;
import com.strategy.impl.DownLoadFileStrategy;
import com.strategy.impl.NoFileStrategy;
import com.strategy.ResponseStrategy;
import com.strategy.impl.PhotoFileStrategy;
import com.util.CacheManagerUtil;
import com.util.FileUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.net.MalformedURLException;
import java.util.Objects;
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

    private CacheManagerUtil<String, FileCacheVO> cacheManagerUtil;

    private FileService fileService;
    /**
     * 预制链接前缀
     */
    private static final String URL_PREFIX="http://127.0.0.1:8080/timelyFileLink/file/";
    /**
     * 文件保存路径
     */
    private static final String FILE_PATH="/Users/zhoujianze/";
    /**
     * 上传文件保存文件夹
     */
    private static final String UPLOAD_FILE="uploadFile";

    /**
     * 上传文件
     * @param uploadFile    上传文件
     * @return  返回临时链接
     */
    @PostMapping("/uploadFile")
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<FileVO> uploadFile(@Validated @NotNull(message = "上传文件不能为空") MultipartFile uploadFile){
        ResponseResult<FileVO> responseResult=new ResponseResult<>();
        FileUploadResult fileUploadResult=fileService.uploadFile(uploadFile);
//        FileCacheVO fileCacheVO=new FileCacheV O(fileVO);
//        cacheManagerUtil.put(fileVO.getFileId(),fileVO),CacheManagerUtil.TTL);
//        return responseResult.setData(fileVO);
        return null;
    }

    /**
     * 文件下载
     * @param uuid  文件唯一ID
     * @param response  响应
     */
    @GetMapping("/file/{uuid}")
    public void file(@Validated @PathVariable @NotBlank(message = "文件ID不能为空") String uuid, HttpServletResponse response) throws MalformedURLException {
        //校验
        FileCacheVO fileCacheVO=cacheManagerUtil.get(uuid);
        ResponseStrategy strategy=fileCacheVO==null?new NoFileStrategy():new DownLoadFileStrategy();
        strategy.handleResponse(response,fileCacheVO);
    }

    /**
     * 图片反显
     * @param uuid  文件唯一ID
     * @param response  响应
     * @throws MalformedURLException
     */
    @GetMapping("/photo/{uuid}")
    public void photo(@Validated @PathVariable @NotBlank(message = "文件ID不能为空") String uuid, HttpServletResponse response) throws MalformedURLException {
        //校验
        FileCacheVO fileCacheVO=cacheManagerUtil.get(uuid);
        ResponseStrategy strategy=fileCacheVO==null?new NoFileStrategy():new PhotoFileStrategy();
        strategy.handleResponse(response,fileCacheVO);
    }

}
