package com.spring.yoon.football.util;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

/*일단 현재 이미지는 한장만 업로드를 해놨다*/
/*이미지 업로더*/
@Component
public class ImageUploader {

    @Value("${file.path}")
    private String path;

    /*이미지 한장 upload*/
    public String imageUpload(Optional<MultipartFile> imageFile){

        String imageName="";
        if(imageFile.isPresent()){
            try{
                UUID uuid = UUID.randomUUID();
                imageName= uuid+imageFile.get().getOriginalFilename();//중복이 되지않게
                Path imagePath = Paths.get(path+imageName);
                Files.write(imagePath,imageFile.get().getBytes());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return imageName;
    }

}
