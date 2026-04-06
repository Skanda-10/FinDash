package FinanceDash.AnalystService;

import FinanceDash.UserService.POJO.Role;
import FinanceDash.UserService.POJO.SubscriptionStatus;

import java.util.List;

public interface AnalystService {
    AnalystReport uploadReport(AnalystReport report, Role requestorRole);
    List<AnalystReport> getMyReports(Long userId, SubscriptionStatus status);
    List<AnalystReport> getReportsByAnalyst(Long analystId, Role requestorRole);
    List<AnalystReport> getHistoryForUser(Long targetUserId, Role requestorRole);
}
