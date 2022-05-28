package com.tulingxueyuan.mall.service.impl;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import com.tulingxueyuan.mall.dto.OssPolicyResult;
import com.tulingxueyuan.mall.service.OssService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * oss上传管理Service实现类
 * Created on 2018/5/17.
 */
@Service
@Slf4j
public class OssServiceImpl implements OssService {

    @Value("${aliyun.oss.policy.expire}")
    private int ALIYUN_OSS_EXPIRE;   // 签名的有效期
    @Value("${aliyun.oss.maxSize}")
    private int ALIYUN_OSS_MAX_SIZE;  //上传问文件大小
    //    @Value("${aliyun.oss.callback}") 回调
    //    private String ALIYUN_OSS_CALLBACK;
    @Value("${aliyun.oss.bucketName}")
    private String ALIYUN_OSS_BUCKET_NAME;  // oss存储行间的名称
    @Value("${aliyun.oss.endpoint}")
    private String ALIYUN_OSS_ENDPOINT;   //地域名称
    @Value("${aliyun.oss.dir.prefix}")
    private String ALIYUN_OSS_DIR_PREFIX;  // 上传文件夹路径前缀

    @Autowired
    private OSSClient ossClient;

    /**
     * 签名生成
     */
    @Override
    public OssPolicyResult policy() {
        OssPolicyResult result = new OssPolicyResult();
        //  用时间生成存储目录名
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        //加入到目录中 dir + sdf.format(new Date())
        String dir = ALIYUN_OSS_DIR_PREFIX+sdf.format(new Date());
        // 设置 签名有效期
        long expireEndTime = System.currentTimeMillis() + ALIYUN_OSS_EXPIRE * 1000;
        Date expiration = new Date(expireEndTime);
        // 文件大小
        long maxSize = ALIYUN_OSS_MAX_SIZE * 1024 * 1024;

        // 提交节点  host地址 格式为 https://bucketname.endpoint
        String action = "https://" + ALIYUN_OSS_BUCKET_NAME + "." + ALIYUN_OSS_ENDPOINT;
        try {
            PolicyConditions policyConds = new PolicyConditions();
            policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, maxSize);
            policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);
            String postPolicy = ossClient.generatePostPolicy(expiration, policyConds);
            byte[] binaryData = postPolicy.getBytes("utf-8");
            String policy = BinaryUtil.toBase64String(binaryData);
            String signature = ossClient.calculatePostSignature(postPolicy);
            // 返回结果
            result.setAccessKeyId(ossClient.getCredentialsProvider().getCredentials().getAccessKeyId());
            result.setPolicy(policy);
            result.setSignature(signature);
            result.setDir(dir);
            result.setHost(action);
        } catch (Exception e) {
            log.error("签名生成失败", e);
        }
        return result;
    }



}