package com.maga.im.util;

import com.maga.im.entity.ApiResult;
import net.sf.json.JSONObject;

/**
 * 数据接口返回信息构建
 *
 * @author zhangsl
 * @date 2018-01-24
 */
public class ApiResultBuilder {

    public static ApiResult success(String message) {
        return success(message, null);
    }

    public static ApiResult failure(String message) {
        ApiResult result = new ApiResult();
        result.setSuccess(false);
        result.setMessage(message);
        result.setData(new JSONObject());
        return result;
    }

    public static ApiResult success(String message, Object object) {
        ApiResult result = new ApiResult();
        result.setSuccess(true);
        result.setMessage(message);
        if (object != null) {
            result.setData(JSONObject.fromObject(object));
        } else {
            result.setData(new JSONObject());
        }
        return result;
    }
}
