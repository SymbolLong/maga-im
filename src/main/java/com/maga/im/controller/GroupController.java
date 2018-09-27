package com.maga.im.controller;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jiguang.common.resp.ResponseWrapper;
import cn.jmessage.api.JMessageClient;
import cn.jmessage.api.common.model.group.GroupShieldPayload;
import cn.jmessage.api.group.CreateGroupResult;
import cn.jmessage.api.group.GroupInfoResult;
import cn.jmessage.api.group.GroupListResult;
import cn.jmessage.api.group.MemberListResult;
import cn.jmessage.api.user.UserGroupsResult;
import com.maga.im.constant.SystemConstant;
import com.maga.im.entity.ApiResult;
import com.maga.im.util.ApiResultBuilder;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 群组控制API
 *
 * @author zhangsl 2018/9/27 上午10:39
 */
@RestController
@RequestMapping("/group")
public class GroupController {

    @Autowired
    private JMessageClient jMessageClient;

    @PostMapping
    @RequestMapping("/create")
    @ApiOperation(value = "创建群组：owner 群主， groupName 群名称，desc 群描述，avatar 群组头像，上传接口所获得的media_id, " +
            "flag 1 - 私有群（默认）2 - 公开群  members 例如：username1|username2 ")
    public ApiResult create(String owner, String groupName, @RequestParam(required = false) String desc, @RequestParam(required = false) String avatar, @RequestParam(required = false, defaultValue = "1") int flag, String members) {
        try {
            CreateGroupResult groupResult = jMessageClient.createGroup(owner, groupName, desc, avatar, flag, members.split(SystemConstant.USERS_SPLIT));
            return ApiResultBuilder.success(SystemConstant.OPT_SUCCESS, groupResult);
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            e.printStackTrace();
        }
        return ApiResultBuilder.failure(SystemConstant.SYSTEM_EXCEPTION);
    }

    @GetMapping
    @RequestMapping("/info")
    @ApiOperation(value = "获取群聊信息： groupId 群组id")
    public ApiResult getGroupInfo(Long groupId) {
        try {
            GroupInfoResult groupInfo = jMessageClient.getGroupInfo(groupId);
            return ApiResultBuilder.success(SystemConstant.OPT_SUCCESS, groupInfo);
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            e.printStackTrace();
        }
        return ApiResultBuilder.failure(SystemConstant.SYSTEM_EXCEPTION);
    }

    @PutMapping
    @RequestMapping("/update")
    @ApiOperation(value = "修改群聊信息： groupId 群组id, groupName 群组名称， groupDesc 群组描述，avatar 群组头像上传接口所获得的media_id")
    public ApiResult update(Long groupId, @RequestParam(required = false) String groupName, @RequestParam(required = false) String groupDesc, @RequestParam(required = false) String avatar) {
        try {
            jMessageClient.updateGroupInfo(groupId, groupName, groupDesc, avatar);
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
    @ApiOperation(value = "解散群组：groupId 群组id")
    public ApiResult delete(Long groupId) {
        try {
            jMessageClient.deleteGroup(groupId);
            return ApiResultBuilder.success(SystemConstant.OPT_SUCCESS);
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            e.printStackTrace();
        }
        return ApiResultBuilder.failure(SystemConstant.SYSTEM_EXCEPTION);
    }

    @PostMapping
    @RequestMapping("/update/member")
    @ApiOperation(value = "修改群组成员：groupId 群组id, addMembers 例如：username1|username2, removeMembers 例如：username1|username2")
    public ApiResult updateMember(Long groupId, String addMembers, String removeMembers) {
        try {
            jMessageClient.addOrRemoveMembers(groupId, addMembers.split(SystemConstant.USERS_SPLIT), removeMembers.split(SystemConstant.USERS_SPLIT));
            return ApiResultBuilder.success(SystemConstant.OPT_SUCCESS);
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            e.printStackTrace();
        }
        return ApiResultBuilder.failure(SystemConstant.SYSTEM_EXCEPTION);
    }

    @GetMapping
    @RequestMapping("/members")
    @ApiOperation(value = "获取群组成员：groupId 群组id")
    public ApiResult getMembers(Long groupId) {
        try {
            MemberListResult groupMembers = jMessageClient.getGroupMembers(groupId);
            return ApiResultBuilder.success(SystemConstant.OPT_SUCCESS, groupMembers);
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            e.printStackTrace();
        }
        return ApiResultBuilder.failure(SystemConstant.SYSTEM_EXCEPTION);
    }

    @GetMapping
    @RequestMapping("/groups")
    @ApiOperation(value = "获取用户群组列表：username")
    public ApiResult getGroups(String username) {
        try {
            UserGroupsResult groupList = jMessageClient.getGroupListByUser(username);
            return ApiResultBuilder.success(SystemConstant.OPT_SUCCESS, groupList);
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            e.printStackTrace();
        }
        return ApiResultBuilder.failure(SystemConstant.SYSTEM_EXCEPTION);
    }

    @GetMapping
    @RequestMapping("/all")
    @ApiOperation(value = "获取所有群组：分页参数 page size ")
    public ApiResult getAllGroup(int page, int size) {
        try {
            GroupListResult groupList = jMessageClient.getGroupListByAppkey((page - 1) * size, size);
            return ApiResultBuilder.success(SystemConstant.OPT_SUCCESS, groupList);
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            e.printStackTrace();
        }
        return ApiResultBuilder.failure(SystemConstant.SYSTEM_EXCEPTION);
    }

    @PostMapping
    @RequestMapping("/shield")
    @ApiOperation(value = "屏蔽群组：username，addGroupIds 例如：id1|id2, removeGroupIds 例如：id1|id2 ")
    public ApiResult shieldGroup(String username, String addGroupIds, String removeGroupIds) {
        String[] addArray = addGroupIds.split(SystemConstant.USERS_SPLIT);
        String[] removeArray = removeGroupIds.split(SystemConstant.USERS_SPLIT);
        List<Long> add = new ArrayList<>();
        for (int i = 0; i < addArray.length; i++) {
            add.add(Long.parseLong(addArray[i]));
        }
        List<Long> remove = new ArrayList<>();
        for (int i = 0; i < removeArray.length; i++) {
            remove.add(Long.parseLong(removeArray[i]));
        }
        GroupShieldPayload.Builder builder = GroupShieldPayload.newBuilder();
        builder.setAddGroupShield(add);
        builder.setRemoveGroupShield(remove);
        try {
            ResponseWrapper responseWrapper = jMessageClient.setGroupShield(builder.build(), username);
            return ApiResultBuilder.success(SystemConstant.OPT_SUCCESS, responseWrapper);
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            e.printStackTrace();
        }
        return ApiResultBuilder.failure(SystemConstant.SYSTEM_EXCEPTION);
    }

    @PutMapping
    @RequestMapping("/silence")
    @ApiOperation(value = "群成员禁言：待完成")
    public ApiResult silenceMember() {
        return ApiResultBuilder.failure("群成员禁言：待完成");
    }

    @PutMapping
    @RequestMapping("/changeOwner")
    @ApiOperation(value = "移交群主：待完成")
    public ApiResult changeOwner(Long groupId, String owner) {
        return ApiResultBuilder.failure("移交群主：待完成");
    }


}
