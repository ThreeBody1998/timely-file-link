package com.pojo.custom;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author 灵感大王
 * @Date 2023/11/26
 **/
@Data
@AllArgsConstructor
public class FileVO {
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 文件链接
     */
    private String fileUrl;

}
