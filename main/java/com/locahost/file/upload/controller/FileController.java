package com.locahost.file.upload.controller;

import com.locahost.file.upload.pojo.UploadFile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

@RestController
@RequestMapping("/file")
public class FileController {

    private final String FILE_PATH = "D:\\Projects\\manage_exercise_projects\\segmented_file_upload\\segmented_file_upload\\src\\main\\resources\\static\\file\\";

    @PostMapping("/upload")
    public void upload(UploadFile file){
        //取出上传的文件
        MultipartFile uploadFile = file.getFile();
        File saveFile = new File(FILE_PATH + file.getName()); //将上传文件的名称与保留文件路径拼接成一个文件路径
        try {
            if (!saveFile.exists() && file.getIndex() == 0) {//文件不存在，并且 文件的索引不为0 代这个文件是新上传的文件，需要创建新的文件来保存
                //将文件转移到指定文件中保存
                uploadFile.transferTo(saveFile);
            } else { //文件非第一次上传，即本地保存有该文件的一部分数据
                RandomAccessFile randomAccessFile = new RandomAccessFile(saveFile, "rw");
                //从上次保存数据的位置开始写入数据
                randomAccessFile.seek(file.getIndex() * file.getLength());
                byte[] data = uploadFile.getBytes();
                randomAccessFile.write(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
