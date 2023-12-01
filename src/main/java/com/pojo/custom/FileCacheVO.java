package com.pojo.custom;

import lombok.Data;

/**
 * description 文件缓存VO
 *
 * @author 周建泽
 * @date 2023/11/27
 */
@Data
public class FileCacheVO {
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 文件存放地址
     */
    private String filePath;

    public FileCacheVO(FileUploadResult fileUploadResult) {
        this.fileName = fileUploadResult.getFileName();
        this.filePath = fileUploadResult.getFilePath();
    }
}
