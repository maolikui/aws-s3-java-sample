package com.javasample.s3.controller;

import com.javasample.s3.services.impl.S3ServicesImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("downloadFile")
public class DownloadFileController {
    private Logger logger = LoggerFactory.getLogger(UploadFileController.class);
    @Autowired
    private S3ServicesImpl s3Services;

    @RequestMapping(value = "/{empnum}/{serialnumber}", method = RequestMethod.GET)
    @ResponseBody
    public void downloadFile(@PathVariable(value = "empnum") String empnum, @PathVariable(value = "serialnumber") String serialnumber, HttpServletResponse response) throws Exception {
        ResponseInputStream<GetObjectResponse> responseInputStream = s3Services.downloadFile("/" + empnum + "/" + serialnumber + "/photo.zip");
        String fileName = empnum + "_" + serialnumber + "_photo.zip";
        response.setHeader("content-type", "application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        response.setContentType("application/octet-stream");
        ServletOutputStream sos = null;
        try {
            byte[] buffer = new byte[1024];
            sos = response.getOutputStream();
            int i = responseInputStream.read(buffer);
            while (i != -1) {
                sos.write(buffer, 0, i);
                i = responseInputStream.read(buffer);
            }
            sos.flush();
        } catch (Exception e) {
            logger.info("downloadFile exception:" + e.toString());
        } finally {
            if (responseInputStream != null) {
                try {
                    responseInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (sos != null) {
                try {
                    sos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
