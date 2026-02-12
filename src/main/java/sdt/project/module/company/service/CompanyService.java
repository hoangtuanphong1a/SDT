package sdt.project.module.company.service;

import sdt.project.common.exception.NotFoundException;
import sdt.project.module.company.dto.request.UpdateCompanyRequest;
import sdt.project.module.company.dto.response.CompanyResponse;
import sdt.project.module.company.entity.Company;
import sdt.project.module.company.mapper.CompanyMapper;
import sdt.project.module.company.repository.CompanyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class CompanyService {
    private final CompanyRepository companyRepo;
    private final CompanyMapper companyMapper;

    public CompanyService(CompanyRepository companyRepo, CompanyMapper companyMapper) {
        this.companyRepo = companyRepo;
        this.companyMapper = companyMapper;
    }

    public CompanyResponse getMyCompany(UUID userId) {

        Company company = companyRepo.findCompanyByUserId(userId)
                .orElseThrow(() ->
                        new NotFoundException("Company not found"));

        return companyMapper.toResponse(company);
    }

    public void updateMyCompany(UUID userId, UpdateCompanyRequest req) {

        Company company = companyRepo.findCompanyByUserId(userId)
                .orElseThrow(() ->
                        new NotFoundException("Company not found"));

        companyMapper.update(req, company);
    }
}
