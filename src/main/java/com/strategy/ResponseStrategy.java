package com.strategy;

import com.pojo.custom.FileCacheVO;

import javax.servlet.http.HttpServletResponse;

/**
 * description 响应处理策略接口
 * @author 周建泽
 * @date 2023/11/27
 */
public interface ResponseStrategy {
    /**
     * 处理响应
     * @param response  响应
     * @param fileCacheVO   文件缓存VO
     * @return  操作结果
     */
    void handleResponse(HttpServletResponse response, FileCacheVO fileCacheVO);
}
