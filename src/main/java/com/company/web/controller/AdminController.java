package com.company.web.controller;

import com.company.web.model.Admin;
import com.company.web.model.Company;
import com.company.web.model.Product;
import com.company.web.repository.AdminRepository;
import com.company.web.service.CompanyService;
import com.company.web.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private CompanyService companyService;

    @Autowired
    private ProductService productService;

    @Value("${image.storage.path}")
    private String imageStoragePath;

    @PostConstruct
    public void init() {
        File uploadDir = new File(imageStoragePath);
        if (!uploadDir.exists()) {
            boolean created = uploadDir.mkdirs();
            logger.info("[初始化] 创建图片存储目录: {}, 结果: {}", imageStoragePath, created);
        }
        logger.info("[初始化] 图片存储路径: {}", imageStoragePath);
    }

    @Autowired
    private Environment env;

    @Autowired
    private AdminRepository adminRepository;

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String userName, @RequestParam String password, HttpSession session) {
        logger.info("Attempting login for user: {}", userName);
        Admin admin = adminRepository.selectOne(new QueryWrapper<Admin>().eq("user_name", userName));
        if (admin != null && admin.getPassword().equals(password)) {
            session.setAttribute("loggedIn", true);
            logger.info("Login successful for user: {}", userName);
            return "redirect:/admin";
        }
        logger.info("Login failed for user: {}", userName);
        return "redirect:/admin/login?error=true";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/admin/login";
    }

    private boolean isLoggedIn(HttpSession session) {
        return session.getAttribute("loggedIn") != null && (Boolean) session.getAttribute("loggedIn");
    }

    @GetMapping
    public String adminPanel(Model model, HttpSession session) {
        if (!isLoggedIn(session)) {
            return "redirect:/admin/login";
        }
        Company company = companyService.getAllCompanies().stream().findFirst().orElse(new Company());
        List<Product> products = productService.getAllProducts();
        model.addAttribute("company", company);
        model.addAttribute("products", products);
        model.addAttribute("newProduct", new Product());
        model.addAttribute("imageStoragePath", imageStoragePath);
        logger.info("Image storage path: {}", imageStoragePath);
        return "admin";
    }

    @GetMapping("/product/edit/{id}")
    public String editProduct(@PathVariable Long id, Model model, HttpSession session) {
        if (!isLoggedIn(session)) {
            return "redirect:/admin/login";
        }
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "edit-product";
    }

    @PostMapping("/save")
    public String saveCompany(@ModelAttribute Company company, HttpSession session) {
        if (!isLoggedIn(session)) {
            return "redirect:/admin/login";
        }
        if (company.getId() == null) {
            companyService.saveCompany(company);
        } else {
            companyService.updateCompany(company);
        }
        return "redirect:/admin";
    }

    @GetMapping("/delete/{id}")
    public String deleteCompany(@PathVariable Long id, HttpSession session) {
        if (!isLoggedIn(session)) {
            return "redirect:/admin/login";
        }
        companyService.deleteCompany(id);
        return "redirect:/admin";
    }

    @PostMapping("/product/save")
    public String saveProduct(
            @RequestParam(value = "id", required = false) Long id,
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("price") BigDecimal price,
            @RequestParam(value = "image", required = false) MultipartFile file,
            HttpSession session) {
        if (!isLoggedIn(session)) {
            return "redirect:/admin/login";
        }

        logger.info("[产品保存] 开始保存产品 - ID: {}, Name: {}, Description: {}, Price: {}, Image: {}",
                    id, name, description, price, file != null ? file.getOriginalFilename() : "null");

        Product product = new Product();
        product.setId(id);
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);

        if (id != null) {
            Product existingProduct = productService.getProductById(id);
            if (existingProduct != null) {
                product.setImagePath(existingProduct.getImagePath());
                logger.info("[产品保存] 更新现有产品，原图片路径: {}", existingProduct.getImagePath());
            }
        }

        if (file != null && !file.isEmpty()) {
            logger.info("[图片上传] 收到图片文件: {}, 大小: {} bytes", file.getOriginalFilename(), file.getSize());

            File uploadDir = new File(imageStoragePath);
            if (!uploadDir.exists()) {
                boolean created = uploadDir.mkdirs();
                logger.info("[图片上传] 创建图片目录: {}, 结果: {}", imageStoragePath, created);
            }
            logger.info("[图片上传] 上传目录是否存在: {}, 是否可写: {}", uploadDir.exists(), uploadDir.canWrite());

            // 若是更新操作且原图片存在，先删除原图片文件
            if (id != null) {
                Product existingProduct = productService.getProductById(id);
                if (existingProduct != null && existingProduct.getImagePath() != null) {
                    File oldImageFile = new File(imageStoragePath, existingProduct.getImagePath());
                    if (oldImageFile.exists() && oldImageFile.isFile()) {
                        boolean deleted = oldImageFile.delete();
                        logger.info("[图片上传] 删除旧图片: {}，结果: {}", oldImageFile.getAbsolutePath(), deleted);
                    } else {
                        logger.warn("[图片上传] 旧图片文件不存在或不是文件: {}", oldImageFile.getAbsolutePath());
                    }
                }
            }

            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            File targetFile = new File(imageStoragePath, fileName);
            logger.info("[图片上传] 目标文件路径: {}", targetFile.getAbsolutePath());

            try {
                file.transferTo(targetFile);
                logger.info("[图片上传] 文件传输完成");
            } catch (IOException e) {
                logger.error("[图片上传] 文件传输失败: {}", e.getMessage(), e);
            }

            logger.info("[图片上传] 图片保存到: {}，存在: {}", targetFile.getAbsolutePath(), targetFile.exists());
            product.setImagePath(fileName);

            // 添加新的日志记录
            logger.info("[图片上传] 文件权限: 可读={}, 可写={}, 可执行={}", targetFile.canRead(), targetFile.canWrite(), targetFile.canExecute());
            logger.info("[图片上传] 父目录权限: 可读={}, 可写={}, 可执行={}", uploadDir.canRead(), uploadDir.canWrite(), uploadDir.canExecute());

            // 检查文件是否真的被写入
            if (targetFile.exists()) {
                logger.info("[图片上传] 文件成功写入，大小: {} bytes", targetFile.length());
            } else {
                logger.error("[图片上传] 文件写入失败，文件不存在");
            }

            // 添加新的日志记录，检查图片是否可以通过 HTTP 访问
            String imageUrl = "/resources/images/products/" + fileName;
            logger.info("[图片上传] 尝试通过 HTTP 访问图片: {}", imageUrl);
        } else {
            logger.info("[图片上传] 没有收到图片文件");
        }

        try {
            productService.saveProduct(product);
            logger.info("[产品保存] 产品保存成功. ID: {}, Name: {}, ImagePath: {}", product.getId(), product.getName(), product.getImagePath());
            
            // 添加新的日志来确认图片压缩
            if (product.getImagePath() != null && !product.getImagePath().isEmpty()) {
                File compressedFile = new File(imageStoragePath, product.getImagePath());
                if (compressedFile.exists()) {
                    long fileSizeKB = compressedFile.length() / 1024;
                    logger.info("[图片压缩] 压缩后的图片大小: {} KB", fileSizeKB);
                    if (fileSizeKB <= 500) {
                        logger.info("[图片压缩] 图片已成功压缩到500KB以下");
                    } else {
                        logger.warn("[图片压缩] 图片压缩后仍超过500KB");
                    }
                } else {
                    logger.warn("[图片压缩] 压缩后的图片文件不存在");
                }
            }
        } catch (Exception e) {
            logger.error("[产品保存] 保存产品时发生错误: {}", e.getMessage(), e);
        }
        return "redirect:/admin";
    }

    @GetMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable Long id, HttpSession session) {
        if (!isLoggedIn(session)) {
            return "redirect:/admin/login";
        }
        productService.deleteProduct(id);
        return "redirect:/admin";
    }
}
