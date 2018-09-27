package com.maga.im.controller;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jiguang.common.resp.ResponseWrapper;
import cn.jmessage.api.JMessageClient;
import cn.jmessage.api.common.model.message.MessageBody;
import cn.jmessage.api.common.model.message.MessagePayload;
import cn.jmessage.api.common.model.message.Notification;
import cn.jmessage.api.message.MessageType;
import cn.jmessage.api.message.SendMessageResult;
import com.maga.im.constant.SystemConstant;
import com.maga.im.entity.ApiResult;
import com.maga.im.util.ApiResultBuilder;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 消息管理API
 *
 * @author zhangsl 2018/9/26 下午10:05
 */
@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private JMessageClient jMessageClient;

    @PostMapping
    @RequestMapping("/send/single/text")
    @ApiOperation(value = "给好友发送文本信息：from 来源，target 目标，text 消息内容")
    public ApiResult sendSingleText(String from, String target, String text) {
        return sendTextMessage(from, target, SystemConstant.IM_SINGLE, text, null, false, false);
    }

    @PostMapping
    @RequestMapping("/send/group/text")
    @ApiOperation(value = "给群组发送文本信息：from 来源，target 目标，text 消息内容")
    public ApiResult sendGroupText(String from, String target, String text) {
        return sendTextMessage(from, target, SystemConstant.IM_GROUP, text, null, false, false);
    }

    @PostMapping
    @RequestMapping("/send/chatroom/text")
    @ApiOperation(value = "给聊天室发送文本信息：from 来源，target 目标，text 消息内容")
    public ApiResult sendChatRoomText(String from, String target, String text) {
        return sendTextMessage(from, target, SystemConstant.IM_CHAT_ROOM, text, null, false, false);
    }

    @PostMapping
    @RequestMapping("/send/single/image")
    @ApiOperation(value = "给好友发送图片信息：from 来源，target 目标，image 消息内容")
    public ApiResult sendSingleImage(String from, String target, @RequestBody JSONObject image) {
        return sendImageMessage(from, target, SystemConstant.IM_SINGLE, image, false, false);
    }

    @PostMapping
    @RequestMapping("/send/group/image")
    @ApiOperation(value = "给群组发送图片信息：from 来源，target 目标，image 消息内容")
    public ApiResult sendGroupImage(String from, String target, @RequestBody JSONObject image) {
        return sendImageMessage(from, target, SystemConstant.IM_GROUP, image, false, false);
    }

    @PostMapping
    @RequestMapping("/send/chatroom/image")
    @ApiOperation(value = "给聊天室发送图片信息：from 来源，target 目标，image 消息内容")
    public ApiResult sendChatRoomImage(String from, String target, @RequestBody JSONObject image) {
        return sendImageMessage(from, target, SystemConstant.IM_CHAT_ROOM, image, false, false);
    }

    @PostMapping
    @RequestMapping("/send/single/voice")
    @ApiOperation(value = "给好友发送语音信息：from 来源，target 目标，voice 消息内容")
    public ApiResult sendSingleVoice(String from, String target, @RequestBody JSONObject voice) {
        return sendVoiceMessage(from, target, SystemConstant.IM_SINGLE, voice, false, false);
    }

    @PostMapping
    @RequestMapping("/send/group/voice")
    @ApiOperation(value = "给群组发送语音信息：from 来源，target 目标，voice 消息内容")
    public ApiResult sendGroupVoice(String from, String target, @RequestBody JSONObject voice) {
        return sendVoiceMessage(from, target, SystemConstant.IM_SINGLE, voice, false, false);
    }

    @PostMapping
    @RequestMapping("/send/chatroom/voice")
    @ApiOperation(value = "给聊天室发送语音信息：from 来源，target 目标，voice 消息内容")
    public ApiResult sendChatRoomVoice(String from, String target, @RequestBody JSONObject voice) {
        return sendVoiceMessage(from, target, SystemConstant.IM_SINGLE, voice, false, false);
    }

    @PostMapping
    @RequestMapping("/retract")
    @ApiOperation(value = "撤回消息，username发送人， msgId 消息id")
    public ApiResult rollback(String username, Long msgId) {
        try {
            ResponseWrapper responseWrapper = jMessageClient.retractMessage(username, msgId);
            return ApiResultBuilder.success(SystemConstant.OPT_SUCCESS, responseWrapper);
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            e.printStackTrace();
        }
        return ApiResultBuilder.failure(SystemConstant.SYSTEM_EXCEPTION);
    }


    private ApiResult sendTextMessage(String from, String target, String targetType, String text, Map<String, String> extras, boolean offline, boolean notification) {
        try {
            MessagePayload.Builder builder = MessagePayload.newBuilder();
            builder.setVersion(1);
            builder.setTargetType(targetType);
            builder.setFromType(SystemConstant.IM_ADMIN);
            builder.setMessageType(MessageType.TEXT);
            builder.setTargetId(target);
            builder.setFromId(from);
            builder.setNoOffline(offline);
            builder.setNoNotification(notification);
            if (notification) {
                Notification.Builder notificationBuilder = Notification.newBuilder();
                notificationBuilder.setTitle("新消息");
                notificationBuilder.setAlert("请查看");
                builder.setNotification(notificationBuilder.build());
            }
            MessageBody.Builder messageBodyBuilder = MessageBody.newBuilder();
            messageBodyBuilder.setText(text);
            if (extras != null && !extras.isEmpty()) {
                messageBodyBuilder.addExtras(extras);
            }
            builder.setMessageBody(messageBodyBuilder.build());

            SendMessageResult sendMessageResult = jMessageClient.sendMessage(builder.build());
            return ApiResultBuilder.success(SystemConstant.OPT_SUCCESS, sendMessageResult);
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            e.printStackTrace();
        }
        return ApiResultBuilder.failure(SystemConstant.SYSTEM_EXCEPTION);
    }

    private ApiResult sendImageMessage(String from, String target, String targetType, JSONObject imageInfo, boolean offline, boolean notification) {
        try {
            MessagePayload.Builder builder = MessagePayload.newBuilder();
            builder.setVersion(1);
            builder.setTargetType(targetType);
            builder.setFromType(SystemConstant.IM_ADMIN);
            builder.setMessageType(MessageType.IMAGE);
            builder.setTargetId(target);
            builder.setFromId(from);
            builder.setNoOffline(offline);
            builder.setNoNotification(notification);
            if (notification) {
                Notification.Builder notificationBuilder = Notification.newBuilder();
                notificationBuilder.setTitle("新消息");
                notificationBuilder.setAlert("请查看");
                builder.setNotification(notificationBuilder.build());
            }
            MessageBody.Builder messageBodyBuilder = MessageBody.newBuilder();
            messageBodyBuilder.setMediaId(imageInfo.getString("media_id"));
            messageBodyBuilder.setMediaCrc32(imageInfo.getLong("media_crc32"));
            messageBodyBuilder.setWidth(imageInfo.getInt("width"));
            messageBodyBuilder.setHeight(imageInfo.getInt("height"));
            messageBodyBuilder.setFormat(imageInfo.getString("format"));
            if (imageInfo.containsKey("hash")) {
                messageBodyBuilder.setHash(imageInfo.getString("hash"));
            }
            messageBodyBuilder.setFsize(imageInfo.getInt("fsize"));
            builder.setMessageBody(messageBodyBuilder.build());

            SendMessageResult sendMessageResult = jMessageClient.sendMessage(builder.build());
            return ApiResultBuilder.success(SystemConstant.OPT_SUCCESS, sendMessageResult);
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            e.printStackTrace();
        }
        return ApiResultBuilder.failure(SystemConstant.SYSTEM_EXCEPTION);
    }

    private ApiResult sendVoiceMessage(String from, String target, String targetType, JSONObject voiceInfo, boolean offline, boolean notification) {
        try {
            MessagePayload.Builder builder = MessagePayload.newBuilder();
            builder.setVersion(1);
            builder.setTargetType(targetType);
            builder.setFromType(SystemConstant.IM_ADMIN);
            builder.setMessageType(MessageType.VOICE);
            builder.setTargetId(target);
            builder.setFromId(from);
            builder.setNoOffline(offline);
            builder.setNoNotification(notification);
            if (notification) {
                Notification.Builder notificationBuilder = Notification.newBuilder();
                notificationBuilder.setTitle("新消息");
                notificationBuilder.setAlert("请查看");
                builder.setNotification(notificationBuilder.build());
            }
            MessageBody.Builder messageBodyBuilder = MessageBody.newBuilder();
            messageBodyBuilder.setMediaId(voiceInfo.getString("media_id"));
            messageBodyBuilder.setMediaCrc32(voiceInfo.getLong("media_crc32"));
            if (voiceInfo.containsKey("hash")) {
                messageBodyBuilder.setHash(voiceInfo.getString("hash"));
            }
            messageBodyBuilder.setDuration(voiceInfo.getInt("duration"));
            messageBodyBuilder.setFsize(voiceInfo.getInt("fsize"));
            builder.setMessageBody(messageBodyBuilder.build());

            SendMessageResult sendMessageResult = jMessageClient.sendMessage(builder.build());
            return ApiResultBuilder.success(SystemConstant.OPT_SUCCESS, sendMessageResult);
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            e.printStackTrace();
        }
        return ApiResultBuilder.failure(SystemConstant.SYSTEM_EXCEPTION);
    }

}

