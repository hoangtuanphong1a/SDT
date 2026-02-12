package sdt.project.module.company.repository;

import sdt.project.module.company.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface CompanyRepository extends JpaRepository<Company, UUID> {
    boolean existsByTaxCode(String taxCode);
    @Query("""
        SELECT u.company 
        FROM User u 
        WHERE u.id = :userId
    """)
    Optional<Company> findCompanyByUserId(UUID userId);

}
