package com.pojo.custom;

import java.util.Objects;

/**
 * description 操作结果类
 * @author 周建泽
 * @date 2023/7/11
 */
public class OperateResult {
    /**
     * 操作结果
     */
    private boolean result;
    /**
     * 操作信息
     */
    private String message;



    public OperateResult setResult(boolean result) {
        this.result = result;
        this.message=result?"操作成功":"操作失败";
        return this;
    }

    public boolean getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }

    public OperateResult setMessage(String message) {
        this.message = message;
        return this;
    }

    public OperateResult() {
        this.result=true;
        this.message="操作成功";
    }

    public OperateResult(boolean result, String message) {
        this.result = result;
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OperateResult)) return false;

        OperateResult that = (OperateResult) o;

        if (result != that.result) return false;
        return Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        int result1 = (result ? 1 : 0);
        result1 = 31 * result1 + (message != null ? message.hashCode() : 0);
        return result1;
    }
}
