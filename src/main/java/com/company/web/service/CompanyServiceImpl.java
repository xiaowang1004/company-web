package com.company.web.service;

import com.company.web.model.Company;
import com.company.web.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public Company saveCompany(Company company) {
        companyRepository.insert(company);
        return company;
    }

    @Override
    public Company getCompanyById(Long id) {
        return companyRepository.selectById(id);
    }

    @Override
    public List<Company> getAllCompanies() {
        return companyRepository.selectList(null);
    }

    @Override
    public Company updateCompany(Company company) {
        companyRepository.updateById(company);
        return company;
    }

    @Override
    public void deleteCompany(Long id) {
        companyRepository.deleteById(id);
    }
}
