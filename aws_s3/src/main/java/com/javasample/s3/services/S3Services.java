package com.javasample.s3.services;

import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

public interface S3Services {
	 ResponseInputStream<GetObjectResponse> downloadFile(String keyName);
	 PutObjectResponse uploadFile(MultipartFile file);
}
