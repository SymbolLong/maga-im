package com.maga.im.controller;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jmessage.api.JMessageClient;
import cn.jmessage.api.message.MessageListResult;
import cn.jmessage.api.reportv2.GroupStatListResult;
import cn.jmessage.api.reportv2.MessageStatListResult;
import cn.jmessage.api.reportv2.UserStatListResult;
import com.maga.im.constant.SystemConstant;
import com.maga.im.entity.ApiResult;
import com.maga.im.util.ApiResultBuilder;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 统计API
 *
 * @author zhangsl 2018/9/27 下午12:21
 */
@RestController
@RequestMapping("/report")
public class ReportController {

    @Autowired
    private JMessageClient jMessageClient;

    @GetMapping
    @RequestMapping("/messages")
    @ApiOperation(value = "获取消息历史：count 每次查询的总条数 一次最多1000，起止时间 start end yyyy-MM-dd HH:mm:ss ")
    public ApiResult getMessages(int count, String start, String end) {
        try {
            MessageListResult messageListResult = jMessageClient.v2GetMessageList(count, start, end);
            return ApiResultBuilder.success(SystemConstant.QUERY_SUCCESS, messageListResult);
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            e.printStackTrace();
        }
        return ApiResultBuilder.failure(SystemConstant.SYSTEM_EXCEPTION);

    }

    @GetMapping
    @RequestMapping("/cursor/messages")
    @ApiOperation(value = "获取消息历史：游标 cursor")
    public ApiResult getMessages(String cursor) {
        try {
            MessageListResult messageListResult = jMessageClient.v2GetMessageListByCursor(cursor);
            return ApiResultBuilder.success(SystemConstant.QUERY_SUCCESS, messageListResult);
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            e.printStackTrace();
        }
        return ApiResultBuilder.failure(SystemConstant.SYSTEM_EXCEPTION);
    }

    @GetMapping
    @RequestMapping("/user")
    @ApiOperation(value = "获取用户消息历史：username, count 每次查询的总条数 一次最多1000，起止时间 start end yyyy-MM-dd HH:mm:ss ")
    public ApiResult getUserMessages(String username, int count, String start, String end) {
        try {
            MessageListResult messageListResult = jMessageClient.v2GetUserMessages(username, count, start, end);
            return ApiResultBuilder.success(SystemConstant.QUERY_SUCCESS, messageListResult);
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            e.printStackTrace();
        }
        return ApiResultBuilder.failure(SystemConstant.SYSTEM_EXCEPTION);

    }

    @GetMapping
    @RequestMapping("/cursor/user")
    @ApiOperation(value = "获取用户消息历史：username 游标 cursor")
    public ApiResult getUserMessages(String username, String cursor) {
        try {
            MessageListResult messageListResult = jMessageClient.v2GetUserMessagesByCursor(username, cursor);
            return ApiResultBuilder.success(SystemConstant.QUERY_SUCCESS, messageListResult);
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            e.printStackTrace();
        }
        return ApiResultBuilder.failure(SystemConstant.SYSTEM_EXCEPTION);
    }

    @GetMapping
    @RequestMapping("/group")
    @ApiOperation(value = "获取群组消息历史：groupId, count 每次查询的总条数 一次最多1000，起止时间 start end yyyy-MM-dd HH:mm:ss ")
    public ApiResult getGroupMessages(Long groupId, int count, String start, String end) {
        return ApiResultBuilder.failure("待完成");
    }

    @GetMapping
    @RequestMapping("/cursor/group")
    @ApiOperation(value = "获取群组消息历史：groupId 游标 cursor")
    public ApiResult getGroupMessages(Long groupId, String cursor) {
        return ApiResultBuilder.failure("待完成");
    }

    @GetMapping
    @RequestMapping("/chatroom")
    @ApiOperation(value = "获取群组消息历史：groupId, count 每次查询的总条数 一次最多1000，起止时间 start end yyyy-MM-dd HH:mm:ss ")
    public ApiResult getChatRoomMessages(Long roomId, int count, String start, String end) {
        return ApiResultBuilder.failure("待完成");
    }

    @GetMapping
    @RequestMapping("/cursor/chatroom")
    @ApiOperation(value = "获取群组消息历史：groupId 游标 cursor")
    public ApiResult getChatRoomMessages(Long roomId, String cursor) {
        return ApiResultBuilder.failure("待完成");
    }

    /**
     * VIP 拥有以下权限
     */

    @GetMapping
    @RequestMapping("/users")
    @ApiOperation(value = "用户统计：start 开始时间 格式为yyyy-MM-dd, duration:天数 最大为60天")
    public ApiResult getUsers(String start, int duration) {
        try {
            UserStatListResult userStatistic = jMessageClient.getUserStatistic(start, duration);
            return ApiResultBuilder.success(SystemConstant.QUERY_SUCCESS, userStatistic);
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            e.printStackTrace();
        }
        return ApiResultBuilder.failure(SystemConstant.SYSTEM_EXCEPTION);
    }

    @GetMapping
    @RequestMapping("/messages")
    @ApiOperation(value = "消息统计：timeUnit 时间单位HOUR DAY MONTH start 开始时间 格式为yyyy-MM-dd, duration:天数 最大为60天")
    public ApiResult getMessages(String timeUnit, String start, int duration) {
        try {
            MessageStatListResult messageStatistic = jMessageClient.getMessageStatistic(timeUnit, start, duration);
            return ApiResultBuilder.success(SystemConstant.QUERY_SUCCESS, messageStatistic);
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            e.printStackTrace();
        }
        return ApiResultBuilder.failure(SystemConstant.SYSTEM_EXCEPTION);
    }

    @GetMapping
    @RequestMapping("/groups")
    @ApiOperation(value = "群组统计：start 开始时间 格式为yyyy-MM-dd, duration:天数 最大为60天")
    public ApiResult getGroups(String start, int duration) {
        try {
            GroupStatListResult groupStatistic = jMessageClient.getGroupStatistic(start, duration);
            return ApiResultBuilder.success(SystemConstant.QUERY_SUCCESS, groupStatistic);
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            e.printStackTrace();
        }
        return ApiResultBuilder.failure(SystemConstant.SYSTEM_EXCEPTION);
    }

}
