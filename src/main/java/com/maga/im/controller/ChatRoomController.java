package com.maga.im.controller;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jiguang.common.resp.ResponseWrapper;
import cn.jmessage.api.JMessageClient;
import cn.jmessage.api.chatroom.ChatRoomListResult;
import cn.jmessage.api.chatroom.ChatRoomMemberList;
import cn.jmessage.api.chatroom.CreateChatRoomResult;
import cn.jmessage.api.common.model.Members;
import cn.jmessage.api.common.model.chatroom.ChatRoomPayload;
import com.maga.im.constant.SystemConstant;
import com.maga.im.entity.ApiResult;
import com.maga.im.util.ApiResultBuilder;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 聊天室API
 *
 * @author zhangsl 2018/9/27 上午11:45
 */
@RestController
@RequestMapping("/chatroom")
public class ChatRoomController {

    @Autowired
    private JMessageClient jMessageClient;


    @PostMapping
    @RequestMapping("/create")
    @ApiOperation(value = "创建聊天室：owner， name， desc， members 例如：username1|username2")
    public ApiResult create(String owner, String name, String desc, String memebrs) {
        try {
            Members.Builder membersBuilder = Members.newBuilder();
            membersBuilder.addMember(memebrs.split(SystemConstant.USERS_SPLIT));
            ChatRoomPayload.Builder builder = ChatRoomPayload.newBuilder();
            builder.setOwnerUsername(owner);
            builder.setName(name);
            builder.setDesc(desc);
            builder.setMembers(membersBuilder.build());
            CreateChatRoomResult chatRoom = jMessageClient.createChatRoom(builder.build());
            return ApiResultBuilder.success(SystemConstant.OPT_SUCCESS, chatRoom);
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            e.printStackTrace();
        }
        return ApiResultBuilder.failure(SystemConstant.SYSTEM_EXCEPTION);
    }

    @DeleteMapping
    @RequestMapping("/delete")
    public ApiResult delete(Long roomId) {
        try {
            ResponseWrapper responseWrapper = jMessageClient.deleteChatRoom(roomId);
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
    @ApiOperation(value = "更新聊天室：owner， name， desc")
    public ApiResult create(Long roomId, @RequestParam(required = false) String owner, @RequestParam(required = false) String name, @RequestParam(required = false) String desc) {
        try {
            ResponseWrapper responseWrapper = jMessageClient.updateChatRoomInfo(roomId, owner, name, desc);
            return ApiResultBuilder.success(SystemConstant.OPT_SUCCESS, responseWrapper);
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            e.printStackTrace();
        }
        return ApiResultBuilder.failure(SystemConstant.SYSTEM_EXCEPTION);
    }

    @GetMapping
    @RequestMapping("/info")
    @ApiOperation(value = "获取聊天室详情： roomIds 例如：roomId1|roomId2")
    public ApiResult getChatRoomInfo(String roomIds) {
        try {
            String[] split = roomIds.split(SystemConstant.USERS_SPLIT);
            long[] ids = new long[split.length];
            for (int i = 0; i < split.length; i++) {
                ids[i] = Long.parseLong(split[i]);
            }
            ChatRoomListResult batchChatRoomInfo = jMessageClient.getBatchChatRoomInfo(ids);
            return ApiResultBuilder.success(SystemConstant.OPT_SUCCESS, batchChatRoomInfo);
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            e.printStackTrace();
        }
        return ApiResultBuilder.failure(SystemConstant.SYSTEM_EXCEPTION);
    }

    @GetMapping
    @RequestMapping("/chatrooms")
    public ApiResult getUserChatRoomInfo(String username) {
        try {
            ChatRoomListResult userChatRoomInfo = jMessageClient.getUserChatRoomInfo(username);
            return ApiResultBuilder.success(SystemConstant.OPT_SUCCESS, userChatRoomInfo);
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            e.printStackTrace();
        }
        return ApiResultBuilder.failure(SystemConstant.SYSTEM_EXCEPTION);
    }

    @GetMapping
    @RequestMapping("/list")
    @ApiOperation(value = "获取所有聊天室：分页参数 page size ")
    public ApiResult list(int page, int size) {
        try {
            ChatRoomListResult chatRoomListResult = jMessageClient.getAppChatRoomInfo((page - 1) * size, size);
            return ApiResultBuilder.success(SystemConstant.QUERY_SUCCESS, chatRoomListResult);
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            e.printStackTrace();
        }
        return ApiResultBuilder.failure(SystemConstant.SYSTEM_EXCEPTION);
    }


    @PutMapping
    @RequestMapping("/silence")
    @ApiOperation(value = "成员禁言：待完成")
    public ApiResult silenceMember(Long roomId, String username, boolean state) {
        int flag = state ? 1 : 0;
        try {
            ResponseWrapper responseWrapper = jMessageClient.updateUserSpeakStatus(roomId, username, flag);
            return ApiResultBuilder.success(SystemConstant.QUERY_SUCCESS, responseWrapper);
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            e.printStackTrace();
        }
        return ApiResultBuilder.failure(SystemConstant.SYSTEM_EXCEPTION);
    }

    @GetMapping
    @RequestMapping("/members")
    @ApiOperation(value = "获取聊天室成员：roomId page size ")
    public ApiResult members(Long roomId, int page, int size) {
        try {
            ChatRoomMemberList chatRoomMembers = jMessageClient.getChatRoomMembers(roomId, (page - 1) * size, size);
            return ApiResultBuilder.success(SystemConstant.QUERY_SUCCESS, chatRoomMembers);
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            e.printStackTrace();
        }
        return ApiResultBuilder.failure(SystemConstant.SYSTEM_EXCEPTION);
    }

    @PutMapping
    @RequestMapping("/add/members")
    @ApiOperation(value = "聊天室添加成员：roomId， members 例如：username1|username2")
    public ApiResult addMember(Long roomId, String members) {
        try {
            ResponseWrapper responseWrapper = jMessageClient.addChatRoomMember(roomId, members.split(SystemConstant.USERS_SPLIT));
            return ApiResultBuilder.success(SystemConstant.QUERY_SUCCESS, responseWrapper);
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            e.printStackTrace();
        }
        return ApiResultBuilder.failure(SystemConstant.SYSTEM_EXCEPTION);
    }

    @DeleteMapping
    @RequestMapping("/remove/member")
    @ApiOperation(value = "聊天室移除成员：roomId， members 例如：username1|username2")
    public ApiResult removeMember(Long roomId, String members) {
        try {
            ResponseWrapper responseWrapper = jMessageClient.removeChatRoomMembers(roomId, members.split(SystemConstant.USERS_SPLIT));
            return ApiResultBuilder.success(SystemConstant.QUERY_SUCCESS, responseWrapper);
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            e.printStackTrace();
        }
        return ApiResultBuilder.failure(SystemConstant.SYSTEM_EXCEPTION);
    }

}
