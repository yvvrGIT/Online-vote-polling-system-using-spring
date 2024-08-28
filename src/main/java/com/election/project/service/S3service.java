package com.election.project.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class S3service {
    @Value("${aws.s3.bucket}")
    private String bucketName;

    private AmazonS3 amazonS3;

    public S3service(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    public String uploadFile(MultipartFile photo, String name) {
        String fileName = photo.getOriginalFilename();
        String fileExtension = fileName.substring(fileName.lastIndexOf("."));
        String key = "public/party/" + name + fileExtension;
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(photo.getSize());
        try {
            PutObjectRequest request = new PutObjectRequest(bucketName, key, photo.getInputStream(), metadata);
            // Set the access control list to allow public read access
            request.setCannedAcl(CannedAccessControlList.PublicRead);
            amazonS3.putObject(request);
            String fileUrl = amazonS3.getUrl(bucketName, key).toString();
            return fileUrl;
        } catch(IOException e) {
            e.printStackTrace();
            return "Upload error";
        }
    }
}
