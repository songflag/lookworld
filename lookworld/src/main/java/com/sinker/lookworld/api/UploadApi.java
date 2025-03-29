package com.sinker.lookworld.api;


import com.sinker.lookworld.util.JsonResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * BelongsProject: lookworld
 * BelongsPackage: com.sinker.lookworld.api
 * Author: sinker
 * CreateTime: 2025-03-07  22:49
 * Description: 我亦无他,唯手熟耳
 * Version: 1.0
 */

@RestController
@RequestMapping("/api/upload")
public class UploadApi {
    @Value("${file.upload.pic}")
    private String basUrl;

    @PostMapping
    public ResponseEntity<JsonResult> uploadPic(@RequestBody MultipartFile file, String type) throws IOException {
        if(type.isEmpty()){
            type = "undefined";
        }
        File dir = new File(basUrl+File.separator+type);
        if (!dir.exists()){
            boolean is =  dir.mkdirs();
            if (!is){
                return ResponseEntity.badRequest().body(JsonResult.fail("无法创建文件"));
            }
        }
        String fileName = file.getOriginalFilename();
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        fileName = fileName.substring(0,fileName.indexOf("."))+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss")) +fileType;

        File desc = new File(dir.getCanonicalPath()+File.separator+fileName);
        file.transferTo(desc);
        return ResponseEntity.ok(JsonResult.success("成功",type+"/"+fileName));
    }
}
