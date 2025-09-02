package com.company.web.service;

import com.company.web.model.Product;
import com.company.web.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
            productRepository.insert(product);
            logger.info("Product saved successfully");
        } catch (Exception e) {
            logger.error("Error saving product: {}", e.getMessage(), e);
        }
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
