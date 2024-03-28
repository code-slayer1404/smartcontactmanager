package com.pranshu.smartcontactmanager.service;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.pranshu.smartcontactmanager.entity.Contact;

@ExtendWith(MockitoExtension.class)
public class FileServiceTest {

    @Mock
    private FileServices fileServices;
    
    @Test
    public void test() throws IOException{
        Contact contact = new Contact();
        contact.setCid(1);
        contact.setName("test");
        contact.setPhone("123456");

        MultipartFile multipartFile = new MockMultipartFile("test_file","file content".getBytes());

        System.out.println(multipartFile.getOriginalFilename());

        fileServices.upload(contact, multipartFile);

        verify(fileServices, times(1)).upload(eq(contact), any(MultipartFile.class));


    }
    
}
