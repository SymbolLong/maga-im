package com.maga.im.controller;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jiguang.common.resp.ResponseWrapper;
import cn.jmessage.api.JMessageClient;
import cn.jmessage.api.sensitiveword.SensitiveWordListResult;
import cn.jmessage.api.sensitiveword.SensitiveWordStatusResult;
import com.maga.im.constant.SystemConstant;
import com.maga.im.entity.ApiResult;
import com.maga.im.util.ApiResultBuilder;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 敏感词API
 *
 * @author zhangsl 2018/9/27 上午11:33
 */
@RestController
@RequestMapping("/sensitive")
public class SensitiveController {

    @Autowired
    private JMessageClient jMessageClient;


    @PostMapping
    @RequestMapping("/create")
    @ApiOperation(value = "添加敏感词：words 例如：word1|word2")
    public ApiResult create(String words) {
        try {
            ResponseWrapper responseWrapper = jMessageClient.addSensitiveWords(words.split(SystemConstant.USERS_SPLIT));
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
    @ApiOperation(value = "修改敏感词：newWord, oldWord")
    public ApiResult update(String newWord, String oldWord) {
        try {
            ResponseWrapper responseWrapper = jMessageClient.updateSensitiveWord(newWord, oldWord);
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
    @ApiOperation(value = "删除敏感词：word")
    public ApiResult delete(String word) {
        try {
            ResponseWrapper responseWrapper = jMessageClient.deleteSensitiveWord(word);
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
    @ApiOperation(value = "分页参数 page size ")
    public ApiResult list(int page, int size) {
        try {
            SensitiveWordListResult sensitiveWordList = jMessageClient.getSensitiveWordList((page - 1) * size, size);
            return ApiResultBuilder.success(SystemConstant.QUERY_SUCCESS, sensitiveWordList);
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            e.printStackTrace();
        }
        return ApiResultBuilder.failure(SystemConstant.SYSTEM_EXCEPTION);
    }

    @PutMapping
    @RequestMapping("/set/status")
    @ApiOperation(value = "设置敏感词状态：state true|false")
    public ApiResult setStatus(boolean state) {
        try {
            int status = state ? 1 : 0;
            ResponseWrapper responseWrapper = jMessageClient.updateSensitiveWordStatus(status);
            return ApiResultBuilder.success(SystemConstant.OPT_SUCCESS, responseWrapper);
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            e.printStackTrace();
        }
        return ApiResultBuilder.failure(SystemConstant.SYSTEM_EXCEPTION);
    }

    @GetMapping
    @RequestMapping("/status")
    public ApiResult getStatus() {
        try {
            SensitiveWordStatusResult sensitiveWordStatus = jMessageClient.getSensitiveWordStatus();
            return ApiResultBuilder.success(SystemConstant.OPT_SUCCESS, sensitiveWordStatus);
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            e.printStackTrace();
        }
        return ApiResultBuilder.failure(SystemConstant.SYSTEM_EXCEPTION);
    }
}
