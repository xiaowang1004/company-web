package com.company.web.service;

import com.company.web.model.Company;
import java.util.List;

public interface CompanyService {
    Company saveCompany(Company company);
    Company getCompanyById(Long id);
    List<Company> getAllCompanies();
    Company updateCompany(Company company);
    void deleteCompany(Long id);
}
