package com.github.qingying0.im.web.controller;

import com.github.qingying0.im.component.HostHolder;
import com.github.qingying0.im.dto.ResultDTO;
import com.github.qingying0.im.utils.UploadFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private UploadFile uploadFile;

    @PostMapping
    public ResultDTO uploadFile(@RequestParam("file") MultipartFile file, @RequestParam(value = "fileType") Integer fileType) throws IOException {
        if (file.isEmpty()) {
            return ResultDTO.errorOf("文件不存在");
        }
        String fileName;
        if(fileType.equals(0)) {
            fileName = "avatarUrl:" + hostHolder.getUser().getId();
        } else if (fileType.equals(1)) {
            fileName = file.getOriginalFilename();
        } else {
            fileName = file.getOriginalFilename();
        }
        String path = uploadFile.uploadFile(file.getBytes(), fileName);
        return ResultDTO.okOf(path);
    }

    @GetMapping
    public ResultDTO download() {
        return ResultDTO.okOf();
    }
}
