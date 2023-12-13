package com.bikkadit.electronicstore.services.impl;

import com.bikkadit.electronicstore.exception.BadRequestException;
import com.bikkadit.electronicstore.services.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    private Logger log = LoggerFactory.getLogger(FileServiceImpl.class);

    @Override
    public String uploadFile(MultipartFile file, String path) throws IOException {
        log.info("Request is sending into DAO layer for upload Image ");
        String originalFilename = file.getOriginalFilename();
        log.info("Filename :{}", originalFilename);
        String fileName = UUID.randomUUID().toString();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String filenameWithextension = fileName + extension;
        String fullpathwithfileName = path + filenameWithextension;
        log.info("full image path:{}", fullpathwithfileName);
        if (extension.equalsIgnoreCase(".png") || extension.equalsIgnoreCase(".jpg") || extension.equalsIgnoreCase(".jpeg")) {
            log.info("file extension is : {}", extension);
            File folder = new File(path);
            if (!folder.exists()) {
                folder.mkdirs();
            }
            Files.copy(file.getInputStream(), Paths.get(fullpathwithfileName));
            log.info("Response has  received  from DAO layer for upload Image ");
            return filenameWithextension;
        } else {
            throw new BadRequestException("File with extension " + extension + " is not allowed");
        }


    }

    @Override
    public InputStream getResource(String path, String name) throws FileNotFoundException {
       log.info("Request is sending into DAO layer for get image : {}",name);
        String fullPath = path + File.separator + name;
        InputStream inputStream = new FileInputStream(fullPath);
        log.info("Response has  received  from DAO layer for get Image :{} ",name);
        return inputStream;
    }


}
