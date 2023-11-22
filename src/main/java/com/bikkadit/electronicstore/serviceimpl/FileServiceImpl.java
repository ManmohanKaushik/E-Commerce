package com.bikkadit.electronicstore.serviceimpl;

import com.bikkadit.electronicstore.services.FileService;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public class FileServiceImpl implements FileService
{

    @Override
    public String uploadFile(MultipartFile file, String path) {
        return null;
    }

    @Override
    public InputStream getResource(String path, String name) {
        return null;
    }


}
