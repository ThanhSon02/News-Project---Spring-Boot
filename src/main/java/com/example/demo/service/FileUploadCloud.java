package com.example.demo.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploadCloud {
	String uploadFile(MultipartFile multipartFile) throws IOException;
}
