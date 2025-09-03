package com.company.web.service;

import com.company.web.model.Product;
import com.company.web.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.coobird.thumbnailator.Thumbnails;

import java.io.File;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.nio.file.Files;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> getAllProducts() {
        logger.info("开始查询all products");
        List<Product> all = productRepository.selectList(null);
        logger.info("查询的产品信息为"+all);
        return all;
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.selectById(id);
    }

    @Override
    public void saveProduct(Product product) {
        logger.info("Saving product: {}", product);
        logger.info("Product name: {}", product.getName());
        logger.info("Product description: {}", product.getDescription());
        logger.info("Product price: {}", product.getPrice());
        try {
            if (product.getId() == null) {
                productRepository.insert(product);
            } else {
                updateProduct(product);
            }

            // 压缩图片
            if (product.getImagePath() != null && !product.getImagePath().isEmpty()) {
                String compressedImagePath = compressImage(product.getImagePath());
                product.setImagePath(compressedImagePath);
                updateProduct(product);
            }

            logger.info("Product saved successfully");
        } catch (Exception e) {
            logger.error("Error saving product: {}", e.getMessage(), e);
        }
    }

    private String compressImage(String imagePath) throws IOException {
        File sourceFile = new File("src/main/webapp/resources/images/products/" + imagePath);
        File destFile = new File("src/main/webapp/resources/images/products/compressed_" + imagePath);
        
        double quality = 0.8;
        long maxFileSize = 500 * 1024; // 500KB in bytes
        
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        
        do {
            outputStream.reset();
            Thumbnails.of(sourceFile)
                .size(800, 600)
                .outputQuality(quality)
                .toOutputStream(outputStream);
            
            if (outputStream.size() > maxFileSize) {
                quality -= 0.1;
            }
            
            if (quality < 0.1) {
                logger.warn("无法将图片压缩到500KB以下，使用最小质量");
                break;
            }
        } while (outputStream.size() > maxFileSize);
        
        Files.write(destFile.toPath(), outputStream.toByteArray());
        
        logger.info("Image compressed: {}, Size: {} bytes, Quality: {}", destFile.getName(), outputStream.size(), quality);
        return "compressed_" + imagePath;
    }

    @Override
    public void updateProduct(Product product) {
        productRepository.updateById(product);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
