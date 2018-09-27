package com.maga.im.controller;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jmessage.api.JMessageClient;
import cn.jmessage.api.user.UserListResult;
import com.maga.im.constant.SystemConstant;
import com.maga.im.entity.ApiResult;
import com.maga.im.util.ApiResultBuilder;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 即时通讯管理员
 *
 * @author zhangsl 2018/9/26 下午5:48
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private JMessageClient jMessageClient;

    @PostMapping
    @RequestMapping("/register")
    @ApiOperation(value = "username（必填）用户名 开头：字母或者数字 字母、数字、下划线 英文点、减号、@" +
            "password（必填）用户密码。极光IM服务器会MD5加密保存。")
    public ApiResult register(@RequestParam String username, String password) {
        try {
            String result = jMessageClient.registerAdmins(username, password);
            return ApiResultBuilder.success(SystemConstant.OPT_SUCCESS, result);
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            e.printStackTrace();
        }
        return ApiResultBuilder.failure(SystemConstant.SYSTEM_EXCEPTION);
    }

    @GetMapping
    @RequestMapping("/list")
    @ApiOperation(value = "管理员列表：分页参数 page size ")
    public ApiResult getAdminList(@RequestParam int page, @RequestParam int size) {
        try {
            UserListResult userListResult = jMessageClient.getAdminListByAppkey((page - 1) * size, size);
            return ApiResultBuilder.success(SystemConstant.QUERY_SUCCESS, userListResult);
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            e.printStackTrace();
        }
        return ApiResultBuilder.failure(SystemConstant.SYSTEM_EXCEPTION);
    }

}
