package com.locahost.file.upload.pojo;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UploadFile {
    private String name;
    private Long index;
    private Long length;
    private MultipartFile file;
}
