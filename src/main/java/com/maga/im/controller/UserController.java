package com.maga.im.controller;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jiguang.common.resp.ResponseWrapper;
import cn.jmessage.api.JMessageClient;
import cn.jmessage.api.common.model.NoDisturbPayload;
import cn.jmessage.api.common.model.RegisterInfo;
import cn.jmessage.api.user.UserInfoResult;
import cn.jmessage.api.user.UserListResult;
import cn.jmessage.api.user.UserStateListResult;
import cn.jmessage.api.user.UserStateResult;
import com.maga.im.constant.SystemConstant;
import com.maga.im.entity.ApiResult;
import com.maga.im.util.ApiResultBuilder;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户API
 *
 * @author zhangsl
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private JMessageClient jMessageClient;

    @PostMapping
    @RequestMapping("/register")
    @ApiOperation(value = "注册用户： username（必填）用户名 开头：字母或者数字 字母、数字、下划线 英文点、减号、@" +
            "password（必填）用户密码。极光IM服务器会MD5加密保存。")
    public ApiResult register(String username, String password) {
        try {
            RegisterInfo[] users = new RegisterInfo[1];
            RegisterInfo.Builder builder = RegisterInfo.newBuilder();
            builder.setUsername(username);
            builder.setPassword(password);
            users[0] = builder.build();
            String result = jMessageClient.registerUsers(users);
            return ApiResultBuilder.success(SystemConstant.OPT_SUCCESS, result);
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            e.printStackTrace();
        }
        return ApiResultBuilder.failure(SystemConstant.SYSTEM_EXCEPTION);
    }

    @GetMapping
    @RequestMapping("/info")
    @ApiOperation(value = "查询用户信息：username")
    public ApiResult getUserInfo(String username) {
        try {
            UserInfoResult userInfo = jMessageClient.getUserInfo(username);
            return ApiResultBuilder.success(SystemConstant.QUERY_SUCCESS, userInfo);
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            e.printStackTrace();
        }
        return ApiResultBuilder.failure(SystemConstant.SYSTEM_EXCEPTION);
    }

    @PutMapping
    @RequestMapping("/update")
    @ApiOperation(value = "更新用户信息：请传入json对象：username(必填),nickname,birthday,signature,gender,region,address,avatar")
    public ApiResult updateUserInfo(@RequestBody JSONObject jsonObject) {
        try {
            String username = jsonObject.getString("username");
            String nickname = jsonObject.getString("nickname");
            String birthday = jsonObject.getString("birthday");
            String signature = jsonObject.getString("signature");
            int gender = jsonObject.getInt("gender");
            String region = jsonObject.getString("region");
            String address = jsonObject.getString("address");
            String avatar = jsonObject.getString("avatar");
            jMessageClient.updateUserInfo(username, nickname, birthday, signature, gender, region, address, avatar);
            return ApiResultBuilder.success(SystemConstant.OPT_SUCCESS);
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            e.printStackTrace();
        }

        return ApiResultBuilder.failure(SystemConstant.SYSTEM_EXCEPTION);
    }

    @GetMapping
    @RequestMapping("/state")
    @ApiOperation(value = "获取用户在线状态：username")
    public ApiResult getUserState(String username) {
        try {
            UserStateResult userStateResult = jMessageClient.getUserState(username);
            return ApiResultBuilder.success(SystemConstant.QUERY_SUCCESS, userStateResult);
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            e.printStackTrace();
        }
        return ApiResultBuilder.failure(SystemConstant.SYSTEM_EXCEPTION);
    }

    @GetMapping
    @RequestMapping("/states")
    @ApiOperation(value = "获取用户列表在线状态：usernames 例如：username1|username2")
    public ApiResult getUserStates(String usernames) {
        try {
            String[] split = usernames.split(SystemConstant.USERS_SPLIT);
            UserStateListResult[] userStateListResults = jMessageClient.getUsersState(split);
            return ApiResultBuilder.success(SystemConstant.QUERY_SUCCESS, userStateListResults);
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            e.printStackTrace();
        }
        return ApiResultBuilder.failure(SystemConstant.SYSTEM_EXCEPTION);
    }

    @PutMapping
    @RequestMapping("/password")
    @ApiOperation(value = "修改密码：username, password")
    public ApiResult changePassword(String username, String password) {
        try {
            jMessageClient.updateUserPassword(username, password);
            return ApiResultBuilder.success(SystemConstant.OPT_SUCCESS);
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            e.printStackTrace();
        }
        return ApiResultBuilder.failure(SystemConstant.SYSTEM_EXCEPTION);
    }

    @DeleteMapping
    @RequestMapping("/delete")
    @ApiOperation(value = "删除用户：username")
    public ApiResult deleteUser(String username) {
        try {
            jMessageClient.deleteUser(username);
            return ApiResultBuilder.success(SystemConstant.OPT_SUCCESS);
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            e.printStackTrace();
        }
        return ApiResultBuilder.failure(SystemConstant.SYSTEM_EXCEPTION);
    }

    @DeleteMapping
    @RequestMapping("/deletes")
    @ApiOperation(value = "批量删除用户：usernames 例如：username1|username2")
    public ApiResult deleteUsers(String usernames) {
        try {
            String[] split = usernames.split(SystemConstant.USERS_SPLIT);
            for (String username : split) {
                jMessageClient.deleteUser(username);
            }
            return ApiResultBuilder.success(SystemConstant.OPT_SUCCESS);
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            e.printStackTrace();
        }
        return ApiResultBuilder.failure(SystemConstant.SYSTEM_EXCEPTION);
    }

    @GetMapping
    @RequestMapping("/users")
    @ApiOperation(value = "分页参数 page size ")
    public ApiResult getUsers(int page, int size) {
        try {
            UserListResult userListResult = jMessageClient.getUserList((page - 1) * size, size);
            return ApiResultBuilder.success(SystemConstant.QUERY_SUCCESS, userListResult);
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            e.printStackTrace();
        }
        return ApiResultBuilder.failure(SystemConstant.SYSTEM_EXCEPTION);
    }

    @PutMapping
    @RequestMapping("/toggle/user")
    @ApiOperation(value = "修改用户状态： username, state: true|false ")
    public ApiResult toggleUser(String username, boolean state) {
        try {
            ResponseWrapper responseWrapper = jMessageClient.forbidUser(username, true);
            return ApiResultBuilder.success(SystemConstant.QUERY_SUCCESS, responseWrapper);
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            e.printStackTrace();
        }
        return ApiResultBuilder.failure(SystemConstant.SYSTEM_EXCEPTION);
    }

    @PutMapping
    @RequestMapping("/addBlackList")
    @ApiOperation(value = "添加黑名单：username , usernames 例如：username1|username2 ")
    public ApiResult addBlackList(String username, String usernames) {
        try {
            String[] split = usernames.split(SystemConstant.USERS_SPLIT);
            ResponseWrapper responseWrapper = jMessageClient.addBlackList(username, split);
            return ApiResultBuilder.success(SystemConstant.OPT_SUCCESS, responseWrapper);
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            e.printStackTrace();
        }
        return ApiResultBuilder.failure(SystemConstant.SYSTEM_EXCEPTION);
    }

    @DeleteMapping
    @RequestMapping("/removeBlackList")
    @ApiOperation(value = "移除黑名单：username , usernames 例如：username1|username2 ")
    public ApiResult removeBlackList(String username, String usernames) {
        try {
            String[] split = usernames.split(SystemConstant.USERS_SPLIT);
            ResponseWrapper responseWrapper = jMessageClient.removeBlacklist(username, split);
            return ApiResultBuilder.success(SystemConstant.OPT_SUCCESS, responseWrapper);
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            e.printStackTrace();
        }
        return ApiResultBuilder.failure(SystemConstant.SYSTEM_EXCEPTION);
    }

    @GetMapping
    @RequestMapping("/blackList")
    @ApiOperation(value = "获取黑名单列表：username")
    public ApiResult getBlackList(String username) {
        try {
            UserInfoResult[] blackList = jMessageClient.getBlackList(username);
            return ApiResultBuilder.success(SystemConstant.OPT_SUCCESS, blackList);
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            e.printStackTrace();
        }
        return ApiResultBuilder.failure(SystemConstant.SYSTEM_EXCEPTION);
    }


    @PostMapping
    @RequestMapping("/single/disturb")
    @ApiOperation(value = "用户免打扰设置：username, addUsernames 例如：username1|username2, removeUsernames 例如：username1|username2")
    public ApiResult setSingleDisturb(String username, String addUsernames, String removeUsernames) {
        try {
            String[] add = addUsernames.split(SystemConstant.USERS_SPLIT);
            String[] remove = removeUsernames.split(SystemConstant.USERS_SPLIT);
            NoDisturbPayload.Builder builder = new NoDisturbPayload.Builder();
            builder.setAddSingleUsers(add);
            builder.setRemoveSingleUsers(remove);
            ResponseWrapper responseWrapper = jMessageClient.setNoDisturb(username, builder.build());
            return ApiResultBuilder.success(SystemConstant.OPT_SUCCESS, responseWrapper);
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            e.printStackTrace();
        }
        return ApiResultBuilder.failure(SystemConstant.SYSTEM_EXCEPTION);
    }

    @PostMapping
    @RequestMapping("/group/disturb")
    @ApiOperation(value = "群聊免打扰设置：username, addGroupIds 例如：id1|id2, removeGroupIds 例如：id1|id2")
    public ApiResult setGroupDisturb(String username, String addGroupIds, String removeGroupIds) {
        try {
            String[] addArray = addGroupIds.split(SystemConstant.USERS_SPLIT);
            String[] removeArray = removeGroupIds.split(SystemConstant.USERS_SPLIT);
            Long[] add = new Long[addArray.length];
            for (int i = 0; i < addArray.length; i++) {
                add[i] = Long.parseLong(addArray[i]);
            }
            Long[] remove = new Long[removeArray.length];
            for (int i = 0; i < removeArray.length; i++) {
                remove[i] = Long.parseLong(removeArray[i]);
            }
            NoDisturbPayload.Builder builder = new NoDisturbPayload.Builder();
            builder.setAddGroupIds(add);
            builder.setRemoveGroupIds(remove);
            ResponseWrapper responseWrapper = jMessageClient.setNoDisturb(username, builder.build());
            return ApiResultBuilder.success(SystemConstant.OPT_SUCCESS, responseWrapper);
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            e.printStackTrace();
        }
        return ApiResultBuilder.failure(SystemConstant.SYSTEM_EXCEPTION);
    }

    @PostMapping
    @RequestMapping("/global/disturb")
    @ApiOperation(value = "全局免打扰设置：username, state:true|false")
    public ApiResult setDisturb(String username, boolean state) {
        try {
            int global = state ? 1 : 0;
            NoDisturbPayload.Builder builder = new NoDisturbPayload.Builder();
            builder.setGlobal(global);
            ResponseWrapper responseWrapper = jMessageClient.setNoDisturb(username, builder.build());
            return ApiResultBuilder.success(SystemConstant.OPT_SUCCESS, responseWrapper);
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            e.printStackTrace();
        }
        return ApiResultBuilder.failure(SystemConstant.SYSTEM_EXCEPTION);
    }


}
