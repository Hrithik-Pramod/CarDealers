package com.Klaus.be_service.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/images")
public class ImageController {

    @GetMapping("/{imageName}")
    public ResponseEntity<byte[]> getImage(@PathVariable String imageName) throws IOException {
        // Path to the image in the resources/images folder
        ClassPathResource imgFile = new ClassPathResource("images/" + imageName);

        // Determine the file extension to set the content type accordingly
        String fileExtension = imageName.substring(imageName.lastIndexOf('.') + 1).toLowerCase();
        MediaType mediaType;

        switch (fileExtension) {
            case "png":
                mediaType = MediaType.IMAGE_PNG;
                break;
            case "jpg":
            case "jpeg":
                mediaType = MediaType.IMAGE_JPEG;
                break;
            case "gif":
                mediaType = MediaType.IMAGE_GIF;
                break;
            default:
                // Return 415 Unsupported Media Type if format is not supported
                return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).build();
        }

        // Read the image into a byte array
        byte[] imageBytes = Files.readAllBytes(Paths.get(imgFile.getURI()));

        // Return the image with the correct content type
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + imageName + "\"")
                .contentType(mediaType)
                .body(imageBytes);
    }
}
