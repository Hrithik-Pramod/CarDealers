package com.Klaus.be_service.controller;


import com.Klaus.be_service.model.Product;
import com.Klaus.be_service.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/products")
@CrossOrigin
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.findAll();
    }

    @PostMapping(consumes = {"multipart/form-data"})
    public Product createProduct(
            @RequestParam("product") String productJson,
            @RequestParam("image") MultipartFile imageFile) throws IOException {

        // Deserialize the JSON string into a Product object
        ObjectMapper objectMapper = new ObjectMapper();
        Product product = objectMapper.readValue(productJson, Product.class);

        // Convert the image to Base64
        String base64Image = convertToBase64(imageFile);
        String imageType = imageFile.getContentType();

        // Set the image and type in the product
        product.setImage(base64Image);
        product.setType(imageType);
//saveImageToDisk(base64Image,imageType);
        // Save and return the product
        return productService.save(product);
    }

    private String convertToBase64(MultipartFile imageFile) throws IOException {
        byte[] bytes = imageFile.getBytes();
        return Base64.getEncoder().encodeToString(bytes);
    }

//    private void saveImageToDisk(String base64Image, String contentType) throws IOException {
//        // Decode Base64 string to byte array
//        byte[] imageBytes = Base64.getDecoder().decode(base64Image);
//
//        // Determine the file extension from content type
//        String fileExtension = "";
//        if ("image/png".equals(contentType)) {
//            fileExtension = ".png";
//        } else if ("image/jpeg".equals(contentType)) {
//            fileExtension = ".jpg";
//        } else if ("image/gif".equals(contentType)) {
//            fileExtension = ".gif";
//        } else {
//            throw new IllegalArgumentException("Unsupported image type: " + contentType);
//        }
//
//        // Create a directory for uploaded images if it doesn't exist
//        File directory = new File("uploaded-images");
//        if (!directory.exists()) {
//            directory.mkdirs(); // Create the directory
//        }
//
//        // Create a file and save the image
//        File imageFile = new File(directory, System.currentTimeMillis() + fileExtension);
//        try (FileOutputStream fos = new FileOutputStream(imageFile)) {
//            fos.write(imageBytes);
//        }
//    }
}

