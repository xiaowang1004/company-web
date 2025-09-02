package com.company.web.controller;

import com.company.web.model.Company;
import com.company.web.model.Product;
import com.company.web.service.CompanyService;
import com.company.web.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class FrontendController {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public String index(Model model) {
        return products(model);
    }

    @GetMapping("/about")
    public String about(Model model) {
        Company company = companyService.getAllCompanies().stream().findFirst().orElse(new Company());
        model.addAttribute("company", company);
        return "about";
    }

    @GetMapping("/products")
    public String products(Model model) {
        Company company = companyService.getAllCompanies().stream().findFirst().orElse(new Company());
        List<Product> products = productService.getAllProducts();
        model.addAttribute("company", company);
        model.addAttribute("products", products);
        return "products";
    }

    @GetMapping("/contact")
    public String contact(Model model) {
        Company company = companyService.getAllCompanies().stream().findFirst().orElse(new Company());
        model.addAttribute("company", company);
        return "contact";
    }

    @GetMapping("/product/{id}")
    public String productDetail(@PathVariable Long id, Model model) {
        Company company = companyService.getAllCompanies().stream().findFirst().orElse(new Company());
        Product product = productService.getProductById(id);
        model.addAttribute("company", company);
        model.addAttribute("product", product);
        return "product-detail";
    }
}
