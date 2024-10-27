package com.Klaus.be_service.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping("/images")
@CrossOrigin
public class ImageController {

    // Load image path from application properties
    @Value("${image.folder.path}")
    private String imageFolderPath;

    @GetMapping("/{imageName}")
    public ResponseEntity<byte[]> getImage(@PathVariable String imageName) throws IOException {
        // Construct the path to the image file
        Path imgPath = Path.of(imageFolderPath, imageName);

        // Determine the content type based on the file extension
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
            default:
                mediaType = MediaType.APPLICATION_OCTET_STREAM;
                break;
        }

        // Read the file as a byte array
        byte[] imageBytes = Files.readAllBytes(imgPath);

        return ResponseEntity.ok()
                .contentType(mediaType)
                .body(imageBytes);
    }
}
