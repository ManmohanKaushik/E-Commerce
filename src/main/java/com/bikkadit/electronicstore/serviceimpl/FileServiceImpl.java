package com.bikkadit.electronicstore.serviceimpl;

import com.bikkadit.electronicstore.exception.BadRequestException;
import com.bikkadit.electronicstore.services.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

public class FileServiceImpl implements FileService
{
private Logger log= LoggerFactory.getLogger(FileServiceImpl.class);
    @Override
    public String uploadFile(MultipartFile file, String path) throws IOException {
        String originalFilename = file.getOriginalFilename();
        log.info("Filename :{}",originalFilename);
        String fileName= UUID.randomUUID().toString();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String filenameWithextension= fileName+extension;
        String fullpathwithfileName=path+filenameWithextension;
        if(extension.equalsIgnoreCase(".png")||extension.equalsIgnoreCase(".jpg")||extension.equalsIgnoreCase(".pdf")){
            File folder= new File(path);
            if (!folder.exists()){
                folder.mkdirs();
                Files.copy(file.getInputStream(), Paths.get(fullpathwithfileName));
                return filenameWithextension;
            }
        }else {
            throw new BadRequestException("File with extension "+extension+" not allowed");
        }


        return originalFilename;
    }

    @Override
    public InputStream getResource(String path, String name) throws FileNotFoundException {
       String fullPath=path+File.separator+name;
       InputStream inputStream=new FileInputStream(fullPath);
        return inputStream;
    }


}
