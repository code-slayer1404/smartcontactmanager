package com.pranshu.smartcontactmanager.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.pranshu.smartcontactmanager.entity.Contact;

@Service
public class FileServices {
    // Method to save the file on disk
    static final Path dir = Paths.get("src/main/resources/static/img/uploaded-images/").toAbsolutePath();

    public void upload(Contact contact, MultipartFile file) throws IOException {
        if (file.isEmpty())
            throw new IllegalArgumentException("Failed to upload empty file");
        try {
            InputStream inputStream = file.getInputStream();
            Path upload_path = dir.resolve(file.getOriginalFilename());
            Files.copy(inputStream, upload_path, StandardCopyOption.REPLACE_EXISTING);
            contact.setImage(file.getOriginalFilename());
        } catch (Exception e) {
            // contact.setImage("default.jpg"); handled in controller itself
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        // for testing
        System.out.println(dir);
    }
}
