package com.example.demo.service;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileUploadCloundImpl implements FileUploadCloud{
	@Autowired
	private Cloudinary cloudinary;
    
	@Override
    public String uploadFile(MultipartFile multipartFile) throws IOException {
        return cloudinary.uploader()
                .upload(multipartFile.getBytes(),
                        Map.of("public_id", UUID.randomUUID().toString()))
                .get("url")
                .toString();
    }
}
