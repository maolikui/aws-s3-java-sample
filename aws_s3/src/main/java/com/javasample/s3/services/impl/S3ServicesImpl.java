package com.javasample.s3.services.impl;

import com.javasample.s3.services.S3Services;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

@Service
public class S3ServicesImpl implements S3Services {
    private Logger logger = LoggerFactory.getLogger(S3ServicesImpl.class);
    @Value("${aws.s3.bucket}")
    private String bucketName;
    @Value("${aws.s3.filename}")
    private String fileName;
    @Autowired
    private S3Client s3Client;

    @Override
    public ResponseInputStream<GetObjectResponse> downloadFile(String keyName) {
        ResponseInputStream<GetObjectResponse> responseInputStream = null;
        try {
            logger.info("---------------- START DOWNLOAD FILE ----------------");
            logger.info("Downloading to bucket '" + bucketName);
            responseInputStream = s3Client.getObject(GetObjectRequest.builder().bucket(bucketName).key(keyName).build());
            logger.info("===================== Download File - Done! =====================");
        } catch (Exception e) {
            logger.info("Exception e:" + e.toString());
        }
        return responseInputStream;
    }

    @Override
    public PutObjectResponse uploadFile(MultipartFile file) {
        PutObjectResponse putObjectResponse = null;
        try {
            logger.info("---------------- START UPLOAD FILE ----------------");
            logger.info("Uploading to bucket '" + bucketName);
            putObjectResponse = s3Client.putObject(PutObjectRequest.builder().bucket(bucketName).key(fileName).build(), RequestBody.fromBytes(file.getBytes()));
            logger.info("===================== Upload File - Done! =====================");
        } catch (Exception e) {
            logger.info("Exception e:" + e.toString());
        }
        return putObjectResponse;
    }

}
