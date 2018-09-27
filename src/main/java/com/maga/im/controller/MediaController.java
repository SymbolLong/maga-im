package com.maga.im.controller;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jmessage.api.JMessageClient;
import cn.jmessage.api.resource.DownloadResult;
import cn.jmessage.api.resource.UploadResult;
import com.maga.im.constant.SystemConstant;
import com.maga.im.entity.ApiResult;
import com.maga.im.util.ApiResultBuilder;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * 媒体API
 *
 * @author zhangsl 2018/9/27 上午10:26
 */
@RestController
@RequestMapping("/media")
public class MediaController {

    @Autowired
    private JMessageClient jMessageClient;

    @GetMapping
    @RequestMapping("/download")
    public ApiResult download(String mediaId) {
        try {
            DownloadResult downloadResult = jMessageClient.downloadFile(mediaId);
            return ApiResultBuilder.success(SystemConstant.OPT_SUCCESS, downloadResult);
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            e.printStackTrace();
        }
        return ApiResultBuilder.failure(SystemConstant.SYSTEM_EXCEPTION);
    }

    @PostMapping
    @RequestMapping("/upload")
    public ApiResult upload(@RequestParam MultipartFile file) {
        try {
            String fileName = file.getName();
            String suffix = fileName.substring(fileName.lastIndexOf("."));
            File tmp = new File("/tmp/" + UUID.randomUUID() + suffix);
            file.transferTo(tmp);
            UploadResult uploadResult = jMessageClient.uploadFile(tmp.getAbsolutePath());
            return ApiResultBuilder.success(SystemConstant.OPT_SUCCESS, uploadResult);
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ApiResultBuilder.failure(SystemConstant.SYSTEM_EXCEPTION);
    }

}
