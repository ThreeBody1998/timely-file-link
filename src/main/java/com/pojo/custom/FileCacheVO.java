package com.pojo.custom;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * description 文件缓存VO
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
     * 文件链接
     */
    private String fileUrl;

    public FileCacheVO(FileVO fileVO){
        this.fileName = fileVO.getFileName();
    }
}
