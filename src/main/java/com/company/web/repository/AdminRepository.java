package com.company.web.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.company.web.model.Admin;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminRepository extends BaseMapper<Admin> {
}
