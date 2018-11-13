package com.allenanker.flashnews.service;

import com.alibaba.fastjson.JSONObject;
import com.allenanker.flashnews.controller.NewsController;
import com.allenanker.flashnews.util.FlashNewsUtil;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class QiniuService {

    private static final Logger logger = LoggerFactory.getLogger(NewsController.class);

    public String saveImage(MultipartFile file) throws IOException {
        int dotPos = file.getOriginalFilename().lastIndexOf(".");
        if (dotPos < 0) {
            return null;
        }
        String ext = file.getOriginalFilename().substring(dotPos + 1).toLowerCase();
        if (!FlashNewsUtil.isImageFileAllowed(ext)) {
            return null;
        }

        String fileName = UUID.randomUUID().toString().replace("-", "") + "." + ext;

        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone0());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        String accessKey = "4uyEuvP8g2_iqoyZFdKQ5umqMJdLPduSbonVbfev";
        String secretKey = "gHtwzohBxs_lVZaL0mvQKIEATnp_hIOw9usm4xoG";
        String bucket = "flashnews-upload-image";
        //如果是Windows情况下，格式是 D:\\qiniu\\test.png
        String localFilePath = "/home/qiniu/test.png";

        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(file.getBytes(), fileName, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
            if (response.isOK() && response.isJson()) {
                String key = JSONObject.parseObject(response.bodyString()).get("key").toString();
                return FlashNewsUtil.QINIU_DOMAIN_PREFIX + key;
            } else {
                logger.error("Upload Exception");
                return null;
            }
        } catch (QiniuException ex) {
            logger.error("Upload Exception: " + ex.getMessage());
            return null;
        }
    }
}
