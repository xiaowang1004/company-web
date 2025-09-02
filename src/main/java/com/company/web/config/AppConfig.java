package com.company.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;

@Configuration
@EnableWebMvc
@EnableTransactionManagement
@MapperScan("com.company.web.repository")
@ComponentScan(basePackages = "com.company.web")
@PropertySource("classpath:database.properties")
public class AppConfig implements WebMvcConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(AppConfig.class);

    private final Environment env;
    private final ServletContext servletContext;

    public AppConfig(Environment env, ServletContext servletContext) {
        this.env = env;
        this.servletContext = servletContext;
        logger.info("AppConfig initialized");
    }

    @PostConstruct
    public void init() {
        logger.info("AppConfig PostConstruct method called");
        logger.info("Loaded properties:");
        logger.info("jdbc.driverClassName: {}", env.getProperty("jdbc.driverClassName"));
        logger.info("jdbc.url: {}", env.getProperty("jdbc.url"));
        logger.info("jdbc.username: {}", env.getProperty("jdbc.username"));
        logger.info("hibernate.dialect: {}", env.getProperty("hibernate.dialect"));
        logger.info("hibernate.hbm2ddl.auto: {}", env.getProperty("hibernate.hbm2ddl.auto"));
    }

@Override
public void addResourceHandlers(org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry registry) {
    String realResourcesPath = servletContext.getRealPath("/resources/");
    logger.info("[静态资源映射] 真实物理路径: {}", realResourcesPath);
    registry.addResourceHandler("/resources/**").addResourceLocations("file:" + realResourcesPath);

    // 添加图片存储路径的映射
    String imageStoragePath = env.getProperty("image.storage.path");
    logger.info("[图片资源映射] 真实物理路径: {}", imageStoragePath);
    registry.addResourceHandler("/images/**").addResourceLocations("file:" + imageStoragePath);
}


    @Bean
    public DataSource dataSource() {
        logger.info("Configuring DataSource");
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
        dataSource.setUrl(env.getProperty("jdbc.url"));
        dataSource.setUsername(env.getProperty("jdbc.username"));
        dataSource.setPassword(env.getProperty("jdbc.password"));
        logger.info("DataSource configured successfully");
        try {
            logger.info("Testing database connection...");
            dataSource.getConnection().close();
            logger.info("Database connection test successful");
        } catch (Exception e) {
            logger.error("Error testing database connection", e);
        }
        return dataSource;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        logger.info("Configuring SqlSessionFactory");
        MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSource);
        sqlSessionFactory.setTypeAliasesPackage("com.company.web.model");
        logger.info("SqlSessionFactory configured successfully");
        return sqlSessionFactory.getObject();
    }

    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        logger.info("Configuring TransactionManager");
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public InternalResourceViewResolver viewResolver() {
        logger.info("Configuring ViewResolver");
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");
        logger.info("ViewResolver configured successfully");
        return viewResolver;
    }

    @Bean
    public CommonsMultipartResolver multipartResolver() {
        logger.info("Configuring MultipartResolver");
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setDefaultEncoding("UTF-8");
        resolver.setMaxUploadSize(5242880); // 5MB
        logger.info("MultipartResolver configured successfully");
        return resolver;
    }
}
