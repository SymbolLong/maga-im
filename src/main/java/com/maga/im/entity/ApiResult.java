package com.maga.im.entity;

import net.sf.json.JSONObject;

/**
 * 接口数据实体类
 *
 * @author zhangsl
 * @date 2018-01-24
 */

public class ApiResult {

    private boolean success;
    private String message;
    private JSONObject data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public JSONObject getData() {
        return data;
    }

    public void setData(JSONObject data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
