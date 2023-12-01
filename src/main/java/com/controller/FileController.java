package com.controller;

import com.pojo.custom.*;
import com.service.FileService;
import com.strategy.impl.DownLoadFileStrategy;
import com.strategy.impl.NoFileStrategy;
import com.strategy.ResponseStrategy;
import com.util.CacheManagerUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
     * 上传文件
     *
     * @param uploadFile 上传文件
     * @return 返回临时链接
     */
    @PostMapping("/uploadFile")
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<FileVO> uploadFile(@Validated @NotNull(message = "上传文件不能为空") MultipartFile uploadFile) {
        ResponseResult<FileVO> responseResult = new ResponseResult<>();
        //文件上传
        FileUploadResult fileUploadResult = fileService.uploadFile(uploadFile);
        //加入缓存
        FileCacheVO fileCacheVO = new FileCacheVO(fileUploadResult);
        cacheManagerUtil.put(fileUploadResult.getFileId(), fileCacheVO, CacheManagerUtil.TTL);
        return responseResult.setData(new FileVO(fileUploadResult));
    }

    /**
     * 文件下载
     *
     * @param uuid     文件唯一ID
     * @param response 响应
     */
    @GetMapping("/file/{uuid}")
    public void file(@Validated @PathVariable @NotBlank(message = "文件ID不能为空") String uuid, HttpServletResponse response) {
        //校验
        FileCacheVO fileCacheVO = cacheManagerUtil.get(uuid);
        ResponseStrategy strategy = fileCacheVO == null ? new NoFileStrategy() : new DownLoadFileStrategy();
        strategy.handleResponse(response, fileCacheVO);
    }

}
