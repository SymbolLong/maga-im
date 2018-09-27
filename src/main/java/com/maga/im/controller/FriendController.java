package com.maga.im.controller;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jiguang.common.resp.ResponseWrapper;
import cn.jmessage.api.JMessageClient;
import cn.jmessage.api.common.model.friend.FriendNote;
import cn.jmessage.api.user.UserInfoResult;
import com.maga.im.constant.SystemConstant;
import com.maga.im.entity.ApiResult;
import com.maga.im.util.ApiResultBuilder;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 好友API
 *
 * @author zhangsl 2018/9/27 上午11:21
 */
@RestController
@RequestMapping("/friends")
public class FriendController {

    @Autowired
    private JMessageClient jMessageClient;

    @PostMapping
    @RequestMapping("/add")
    @ApiOperation(value = "添加好友：username， friends 例如：username1|username2,")
    public ApiResult addFriend(String username, String friends) {
        try {
            ResponseWrapper responseWrapper = jMessageClient.addFriends(username, friends.split(SystemConstant.USERS_SPLIT));
            return ApiResultBuilder.success(SystemConstant.OPT_SUCCESS, responseWrapper);
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            e.printStackTrace();
        }
        return ApiResultBuilder.failure(SystemConstant.SYSTEM_EXCEPTION);
    }


    @DeleteMapping
    @RequestMapping("/delete")
    @ApiOperation(value = "删除好友：username， friends 例如：username1|username2,")
    public ApiResult deleteFriends(String username, String friends) {
        try {
            ResponseWrapper responseWrapper = jMessageClient.deleteFriends(username, friends.split(SystemConstant.USERS_SPLIT));
            return ApiResultBuilder.success(SystemConstant.OPT_SUCCESS, responseWrapper);
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            e.printStackTrace();
        }
        return ApiResultBuilder.failure(SystemConstant.SYSTEM_EXCEPTION);
    }

    @PutMapping
    @RequestMapping("/update")
    @ApiOperation(value = "更新好友备注：username,")
    public ApiResult updateFriends(String username, String friend, String noteName, @RequestParam(required = false) String others) {
        try {
            FriendNote.Builder builder = FriendNote.newBuilder();
            builder.setNoteName(noteName);
            builder.setOthers(others);
            builder.setUsername(friend);
            FriendNote[] friendNotes = new FriendNote[1];
            friendNotes[0] = builder.builder();
            ResponseWrapper responseWrapper = jMessageClient.updateFriendsNote(username, friendNotes);
            return ApiResultBuilder.success(SystemConstant.OPT_SUCCESS, responseWrapper);
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            e.printStackTrace();
        }
        return ApiResultBuilder.failure(SystemConstant.SYSTEM_EXCEPTION);
    }

    @GetMapping
    @RequestMapping("/list")
    public ApiResult list(String username) {
        try {
            UserInfoResult[] friendsInfo = jMessageClient.getFriendsInfo(username);
            return ApiResultBuilder.success(SystemConstant.OPT_SUCCESS, friendsInfo);
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            e.printStackTrace();
        }
        return ApiResultBuilder.failure(SystemConstant.SYSTEM_EXCEPTION);
    }
}
