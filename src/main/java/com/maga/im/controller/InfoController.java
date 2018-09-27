package com.maga.im.controller;

import com.maga.im.constant.SystemConstant;
import com.maga.im.entity.ApiResult;
import com.maga.im.util.ApiResultBuilder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 字段说明信息
 *
 * @author zhangsl 2018/9/27 下午1:56
 */
@RestController
@RequestMapping("/info")
public class InfoController {

    @GetMapping
    @RequestMapping("/base")
    @ApiOperation("基本信息")
    public ApiResult getUserInfo() {
        JSONObject data = new JSONObject();
        data.put("username", "用户登录名");
        data.put("password", "登录密码");
        data.put("appkey", "所属于的应用的appkey");
        data.put("nickname", "用户昵称");
        data.put("birthday", "生日yyyy-MM-dd HH:mm:ss");
        data.put("gender", "性别 0 - 未知， 1 - 男 ，2 - 女");
        data.put("signature", "用户签名");
        data.put("region", "用户所属地区");
        data.put("address", "用户详细地址");
        data.put("ctime", "创建时间");
        data.put("mtime", "最后修改时间");
        data.put("extras", "用户自定义json对象");
        data.put("msg_id", "消息id");
        data.put("msg_ctime", "消息创建时间");
        data.put("login", "是否登录");
        data.put("online", "是否在线");
        data.put("platform", "操作平台 a-Android，i-iOS，j-JS，w-Windows");
        data.put("url", "资源路径");
        data.put("media_id", "媒体id");
        data.put("media_crc32", "媒体crc32校验码");
        data.put("width", "媒体宽度");
        data.put("height", "媒体高度");
        data.put("format", "媒体格式");
        data.put("fsize", "媒体大小");
        data.put("fname", "媒体名称");
        data.put("hash", "媒体哈希值");
        data.put("name", "名称");
        data.put("desc", "描述");
        data.put("description", "描述");
        data.put("owner_username", "群主名称");
        data.put("MaxMemberCount", "群组最大数量，默认500人");
        data.put("max_member_count", "群组最大数量，默认500人");
        data.put("total_member_count", "当前总人数");
        data.put("avatar", "头像");
        data.put("gid", "群组id");
        data.put("flag", "是否群主，是否禁言");
        data.put("status", "状态");
        return ApiResultBuilder.success(SystemConstant.QUERY_SUCCESS, data);
    }

    @GetMapping
    @RequestMapping("/codes")
    @ApiOperation("响应错误码")
    public ApiResult getCodeInfo() {
        JSONObject data = new JSONObject();
        data.put("898000", "Server internal error 内部错误");
        data.put("898001", "User exist 用户已存在");
        data.put("898002", "No such user 用户不存在");
        data.put("898003", "Parameter invalid! 请求参数不合法");
        data.put("898004", "Password error 更新密码操作，用户密码错误");
        data.put("898006", "Group id invalid Group id不存在");
        data.put("898007", "Missing authen info 校验信息为空");
        data.put("898008", "Basic authentication failed. 校验失败");
        data.put("898009", "Appkey not exists appkey不存在");
        data.put("898010", "Token expired API请求 token 过期。正常情况下SDK会自动重新获取 token。");
        data.put("898011", "no auth to query other appkey's user or appkey no exist 查询的appkey不具备跨应用权限 或者appkey不存在");
        data.put("898030", "Server response time out, please try again later 系统繁忙，稍后重试");
        data.put("899000", "Server internal error 系统内部错误");
        data.put("899001", "User exist 用户已存在");
        data.put("899002", "No such user 用户不存在");
        data.put("899003", "parameter invalid 参数错误，Request Body参数不符合要求;resend 值不符合要求;用户名或者密码不合法;群组Group id不合法");
        data.put("899006", "Group id invalid Group id 不存在");
        data.put("899007", "Missing authen info 校验信息为空");
        data.put("899008", "Basic authentication failed 校验失败");
        data.put("899009", "Appkey not exit Appkey不存在");
        data.put("899011", "Repeat to add the members 重复添加群成员");
        data.put("899012", "No enough space for members 没有足够位置添加此次请求的成员");
        data.put("899013", "User list is bigger than 500 注册列表大于500，批量注册最大长度为500");
        data.put("899014", "User list is bigger than 500 添加操作操作成功 remove操作有username不存在讨论组中 remove失败");
        data.put("899015", "User 's group are full can not continue 用户加入讨论组达到上限");
        data.put("899016", "No authority to send message 用户没有管理员权限发送信息");
        data.put("899017", "There are usernames exist in blacklist 用户已经被添加进黑名单");
        data.put("899018", "Admin can not be added into blacklist 管理员不能被添加进黑名单");
        data.put("899019", "Here are usernames not exist in blacklist 删除目标黑名单用户不存在黑名单中");
        data.put("899020", "no auth to operating other appkey 跨应用失败");
        data.put("899021", "should use cross app api 查询失败 应该使用跨应用api");
        data.put("899043", "duplicate add user 已经设置此用户为消息免打扰，重复设置错误");
        data.put("899044", "user is not exist in setting 取消消息免打扰用户时，该用户不存在当前设置中");
        data.put("899045", "group is not exist 设置群组消息免打扰时，群组不存在该系统中");
        data.put("899046", "user is not in group 设置群组消息免打扰时，设置的群组，用户不在该群组中");
        data.put("899047", "duplicate add group 已经设置此群组为消息免打扰，重复设置错误");
        data.put("899048", "already open global 已经设置全局为消息免打扰，重复设置错误");
        data.put("899049", "group is not exist in setting 取消消息免打扰群组时，该群组不存在当前设置中");
        data.put("899050", "already close global 已经设置全局为消息免打扰，重复设置错误");
        data.put("899070", " 添加的好友已经存在好友列表中");
        data.put("899071", " 更新的好友不存在好友列表中");
        data.put("899030", "Server response time out, please try again later 系统繁忙，稍后重试");
        data.put("899081", "room id no exist 聊天室ID不存在");
        data.put("899082", "user not in room 用户不在聊天室中");
        data.put("800003", "appkey not exist appkey未注册");
        data.put("800005", "user not exist 用户ID未注册（appkey无该UID）");
        data.put("800006", "user not exist 用户ID不存在（数据库中无该UID）");
        data.put("800008", "invalid request 请求类型无法识别");
        data.put("800009", "system error 服务器系统错误");
        data.put("800012", "user never login 发起的用户处于登出状态，账号注册以后从未登录过，需要先登录");
        data.put("800013", "user logout 发起的用户处于登出状态，请求的用户已经登出，需要先登录");
        data.put("800014", "appkey not match 发起的用户appkey与目标不匹配");
        data.put("800016", "device not match 发起的用户设备不匹配,当前请求的设备与上次登录的设备不匹配导致，需要先登录");
        data.put("800017", "beyond the frequency limit 发送请求频率超过系统限制");
        data.put("800018", "group id not exist 群组ID不存在");
        data.put("800019", "req user not in group 请求用户不在群组中");
        data.put("800020", "request user no permission 请求用户无操作权限");
        data.put("801003", "user not exist 登录的用户名未注册，登录失败");
        data.put("801004", "invalid password 登录的用户密码错误，登录失败");
        data.put("801005", "invalid device 登录的用户设备有误，登录失败");
        data.put("801006", "user disabled 登录的用户被禁用，登录失败");
        data.put("801007", "multi channel online error 多通道同时登录错误，登录失败");
        data.put("802002", "username not match 登出用户名和登录用户名不匹配，登出失败");
        data.put("803001", "system error 发送消息失败，状态存储异常");
        data.put("803002", "system error 发送消息失败，系统网络异常");
        data.put("803003", "target user not exist 发送消息失败，目标用户未注册或不存在");
        data.put("803004", "target group not exist 发送消息失败，目标讨论组不存在");
        data.put("803005", "user not in group 发送消息失败，发起者不在目标讨论组中");
        data.put("803006", "user not permitted 发送消息失败，发起者权限不够或者类别不匹配");
        data.put("803007", "length of message exceed limit 发送消息失败，消息长度超过限制");
        data.put("803008", "user in blacklist 发送消息失败，发送者已被接收者拉入黑名单，仅限单聊");
        data.put("803009", "the message contains sensitive word: the word 发送消息失败，消息内容包含敏感词汇：具体敏感词");
        data.put("803010", "beyond the frequency limit 发送消息失败，发送频率超过系统限制，无法发送，客户端限制每分钟60条");
        data.put("803011", "msg content json error 发送消息失败，消息格式配置错误");
        data.put("803012", "can not chat while silent 发送消息失败，请求用户被管理员禁言");
        data.put("805001", "target user not exist 目标用户不存在");
        data.put("805002", "already is friend 添加好友失败：双方已经是好友");
        data.put("805003", "user not friend 删除好友失败：目标用户并不是自己的好友");
        data.put("805004", "invalid friend memo 修改好友备注失败：备注内容无效，无法修改");
        data.put("805006", "invitation event is not valid 添加好友失败：邀请事件无效");
        data.put("808001", "group name invalid 创建群组时群名为空，创建群组失败");
        data.put("808002", "user not permitted to create group 用户无创建群组权限，创建群组失败");
        data.put("808003", "amount of group exceed limit 用户拥有的群组数量已达上限,无法再创建");
        data.put("808004", "length of group name exceed limit 群组名长度超出上限，创建群组失败");
        data.put("808005", "length of group desc exceed limit 群组描述长度超出上限，创建群组失败");
        data.put("810002", "add member list is null 添加的成员列表为空");
        data.put("810005", "have member not register 添加的成员列表中包含未注册成员");
        data.put("810007", "repeated added member 添加的成员列表中有成员重复添加");
        data.put("810008", "amount of member exceed group limit 添加的成员数量超出群组拥有的最大成员数上限");
        data.put("810009", "amount of group exceed member limit 添加的成员列表中有成员拥有的群组数量已达上限");
        data.put("811002", "del member list is null 删除的成员列表为空");
        data.put("811005", "have member not register 删除的成员列表中有成员未注册");
        data.put("811006", "member of group not permitted deleted 删除的成员列表中有成员该用户没有权限进行删除");
        data.put("811007", "repeated deleted member 删除的成员列表中有成员重复删除");
        data.put("811008", "have member not in group 删除的成员列表中有成员不在该群组中");
        data.put("812003", "length of group name exceed limit 群组名超出长度上限");
        data.put("812004", "length of group desc exceed limit 群组描述超出上限");
        data.put("818001", "zero member 用户添加黑名单时，成员列表为空，添加失败");
        data.put("818002", "member not exist 用户添加黑名单时，成员列表中有成员不存在，添加失败");
        data.put("818003", "member not permitted added 用户添加黑名单时，成员列表中有成员不能被添加，添加失败");
        data.put("819001", "zero member 用户移除好友出黑名单时，成员列表为空，操作失败");
        data.put("819002", "member not exist 用户删除黑名单时，成员列表中有成员不存在，删除失败");
        data.put("831001", "member already set 用户添加成员消息免打扰时，该成员已处于免打扰状态");
        data.put("832001", "member never set 用户删除成员消息免打扰时，该成员不处于免打扰状态");
        data.put("833001", "group not exist 用户添加群组消息免打扰时，该群组不存在");
        data.put("833002", "user not in group 用户添加群组消息免打扰时，用户不存在该群组中");
        data.put("833003", "group already set 用户添加群组消息免打扰时，该群组已处于免打扰状态");
        data.put("834001", "group never set 用户删除群组消息免打扰时，该群组不处于免打扰状态");
        data.put("835001", "already set 用户添加全局消息免打扰时，该用户已处于全局免打扰状态");
        data.put("836001", "never set 用户删除全局消息免打扰时，该用户不处于全局免打扰状态");
        data.put("842001", "group not exist 用户添加群组消息屏蔽时，该群组不存在");
        data.put("842002", "user not in group 用户添加群组消息屏蔽时，用户不在该群组中");
        data.put("842003", "group already set 用户添加群组消息屏蔽时，该群组已处于消息屏蔽状态");
        data.put("843001", "group never set 用户删除群组消息屏蔽时，该群组不处于消息屏蔽状态");
        data.put("847001", "user not in chatroom 发送聊天室消息失败，发起者不在该聊天室中");
        data.put("847002", "user baned to post 发送聊天室消息失败，发起者在该聊天室中被禁言");
        data.put("847003", "chatroom not exist 发送聊天室消息失败，该聊天室不存在");
        data.put("847004", "length of chatroom message exceed limit 发送聊天室消息失败，消息长度超过限制");
        data.put("847005", "chatroom msg content json error 发送聊天室消息失败，消息内容格式错误");
        data.put("850001", "chatroom not exist 删除不存在的聊天室");
        data.put("851001", "repeated invit chatroom member 邀请成员到聊天室时，邀请的成员列表中有重复的成员，邀请失败");
        data.put("851002", "invit member not exist 邀请成员到聊天室时，邀请的成员列表中有未注册成员，邀请失败");
        data.put("851003", "member has in the chatroom 邀请或加入到聊天室时，邀请或加入的成员已在聊天室中，邀请或加入失败");
        data.put("851004", "chatroom not exist 邀请或加入不存在的聊天室");
        data.put("851005", "zero member 邀请成员到聊天室时，邀请的成员列表为空，邀请成员失败");
        data.put("851006", "amount of member exceed chatroom limit 邀请或加入聊天时，邀请的人员数量超过聊天室剩余加入的人员数量");
        data.put("852001", "user not in chatroom 踢出或退出聊天室时，该用户其实并不在该聊天室中，踢出或退出聊天室失败");
        data.put("852002", "chatroom not exist 踢出或退出不存在的聊天室");
        data.put("852003", "zero member 踢出成员到聊天室时，踢出的成员列表为空，踢出成员失败");
        data.put("852004", "owner can not leave chatroom 踢出或退出聊天室时，存在owner用户退出聊天室");
        data.put("853001", "chatroom not exist 更新不存在的聊天室信息");
        data.put("853002", "owner not in chatroom 更新聊天室owner时，新的owner并不在该聊天室中");
        data.put("855001", "out of time 消息撤回失败，超出撤回时间");
        data.put("855002", "request user is not message sender 消息撤回失败，请求撤回方不是消息发送方");
        data.put("855003", "request message not exist 消息撤回失败，请求撤回消息不存在");
        data.put("855004", "message already retract 消息撤回失败，该消息已经撤回");
        data.put("856001", "this request already process 审批失效，该添加成员邀请已经被处理");
        data.put("856002", "invalid request data 请求数据无效");
        data.put("857001", "target group not exist 目标群组不存在");
        data.put("857002", "target not online 目标不在线");
        data.put("857003", "target user not exist 目标用户不存在");
        data.put("857004", "length of trans cmd exceed limit 透传消息长度超过限制");
        data.put("857005", "user not in group 请求用户不在群组中");
        data.put("857006", "target can't be self 目标不能为自己");
        data.put("859001", "user already in the group 用户已经在目标群组中");
        data.put("859002", "group type not support 目标群组类型不支持申请入群");
        data.put("765001", "target not in group 目标用户不在群组中");
        data.put("765002", "request user no permission 请求用户无操作权限");
        return ApiResultBuilder.success(SystemConstant.QUERY_SUCCESS, data);
    }

    @GetMapping
    @RequestMapping("/list")
    @ApiOperation(value = "列表类响应数据")
    public ApiResult getListInfo() {
        JSONObject data = new JSONObject();
        data.put("total", "总记录数");
        data.put("start", "起始记录");
        data.put("count", "查看记录数");
        data.put("data", "响应数据数组");
        return ApiResultBuilder.success(SystemConstant.QUERY_SUCCESS, data);
    }


}
