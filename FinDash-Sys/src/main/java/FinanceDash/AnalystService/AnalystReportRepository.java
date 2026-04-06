package FinanceDash.AnalystService;

import FinanceDash.UserService.POJO.Role;
import FinanceDash.UserService.POJO.SubscriptionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnalystReportRepository extends JpaRepository<AnalystReport, Long> {
    List<AnalystReport> findByUserId(Long userId);
    List<AnalystReport> findByAnalystId(Long analystId);
}