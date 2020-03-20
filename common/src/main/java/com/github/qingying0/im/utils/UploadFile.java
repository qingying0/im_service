package com.github.qingying0.im.utils;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.util.Date;

@Component
public class UploadFile {

    //阿里云API的内或外网域名
    @Value("${oss.endpoint}")
    private String ENDPOINT;
    //阿里云API的密钥Access Key ID
    @Value("${oss.accessKey}")
    private String ACCESS_KEY_ID ;
    //阿里云API的密钥Access Key Secret
    @Value("${oss.accessKeySecret}")
    private String ACCESS_KEY_SECRET ;
    //阿里云API的bucket名称
    @Value("${oss.backetName}")
    private String BACKET_NAME;

    //上传图片文件，返回地址
    public String uploadFile(byte[] content,String filename) throws IOException {

        OSSClient client= new OSSClient(ENDPOINT,ACCESS_KEY_ID, ACCESS_KEY_SECRET);
        MultipartFile file = new MockMultipartFile(filename,content);
        String fileUrl;
        //创建上传object的metadata
        ObjectMetadata metadata=new ObjectMetadata();
        metadata.setContentLength(file.getInputStream().available());

        metadata.setContentEncoding("utf-8");
        metadata.setContentType(file.getContentType());
        metadata.setContentDisposition("inline");
        //上传文件
        PutObjectResult putresult=client.putObject(BACKET_NAME,filename, file.getInputStream(),metadata);

        Date expiration = new Date(new Date().getTime() + 3600 * 1000 * 24 * 365);

        URL url=client.generatePresignedUrl(BACKET_NAME, filename, expiration);

        fileUrl = url.toString();

        client.shutdown();

        return fileUrl;
    }
}
