package com.javasample.s3.controller;

import com.javasample.s3.services.impl.S3ServicesImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("uploadFile")
public class UploadFileController {
    private Logger logger = LoggerFactory.getLogger(UploadFileController.class);
    @Autowired
    private S3ServicesImpl s3Services;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<?> uploadFile(@RequestPart(value = "files") MultipartFile[] srcFiles) throws Exception {
        MultipartFile file = null;
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < srcFiles.length; ++i) {
            file = srcFiles[i];
            if (!file.isEmpty()) {
                s3Services.uploadFile(file);
                map.put("result", "upload success!");
            } else {
                map.put("result", "the file is empty!");
            }
        }
        return ResponseEntity.ok(map);
    }
}
