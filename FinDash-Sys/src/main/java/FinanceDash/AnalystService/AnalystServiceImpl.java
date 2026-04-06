package FinanceDash.AnalystService;

import FinanceDash.UserService.POJO.Role;
import FinanceDash.UserService.POJO.SubscriptionStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AnalystServiceImpl implements AnalystService {
    @Autowired
    private AnalystReportRepository repository;

    @Override
    public AnalystReport uploadReport(AnalystReport report, Role requestorRole) {
        if (requestorRole != Role.ANALYST) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only Analysts can curate reports.");
        }
        return repository.save(report);
    }

    @Override
    public List<AnalystReport> getMyReports(Long userId, SubscriptionStatus status) {
        if (status != SubscriptionStatus.SUBSCRIBED) {
            throw new ResponseStatusException(HttpStatus.PAYMENT_REQUIRED, "Subscription required for Expert Reports.");
        }
        return repository.findByUserId(userId);
    }

    @Override
    public List<AnalystReport> getReportsByAnalyst(Long analystId, Role requestorRole) {
        if (requestorRole != Role.ANALYST) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        return repository.findByAnalystId(analystId);
    }

    @Override
    public List<AnalystReport> getHistoryForUser(Long targetUserId, Role requestorRole) {
        if (requestorRole != Role.ANALYST) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        return repository.findByUserId(targetUserId);
    }
}