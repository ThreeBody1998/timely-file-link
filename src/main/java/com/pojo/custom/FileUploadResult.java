package com.pojo.custom;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * description 文件上传结果
 * @author 周建泽
 * @date 2023/11/30
 */
@Data
@Accessors(chain = true)
public class FileUploadResult {
    /**
     * 文件ID
     */
    private String fileId;
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 文件链接
     */
    private String fileUrl;
    /**
     * 文件路径
     */
    private String filePath;
}
