package com.company.web.repository;

import com.company.web.model.Company;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CompanyRepository extends BaseMapper<Company> {
    // 这里我们不需要定义任何方法，因为BaseMapper已经提供了基本的CRUD操作
}
