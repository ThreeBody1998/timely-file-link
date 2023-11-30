package com.strategy.impl;

import com.enums.HttpResultEnum;
import com.pojo.custom.FileCacheVO;
import com.pojo.custom.ResponseResult;
import com.strategy.ResponseStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * description 无文件策略类
 * @author 周建泽
 * @date 2023/11/27
 */
@Slf4j
public class NoFileStrategy implements ResponseStrategy {
    @Override
    public void handleResponse(HttpServletResponse response, FileCacheVO fileCacheVO) {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        String errorJson=new ResponseResult<String>().setHttpResultEnum(HttpResultEnum.SERVER_ERROR).setMsg("文件不存在").toJsonString().toString();
        try {
            response.getWriter().write(errorJson);
        } catch (IOException e) {
            log.error("文件下载失败{}",e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
