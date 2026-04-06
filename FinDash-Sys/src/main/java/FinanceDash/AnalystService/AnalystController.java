package FinanceDash.AnalystService;

import FinanceDash.UserService.POJO.Role;
import FinanceDash.UserService.POJO.SubscriptionStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/analyst")
public class AnalystController {

    @Autowired
    private AnalystService analystService;

    @PostMapping("/report")
    public ResponseEntity<AnalystReport> uploadReport(
            @RequestBody AnalystReport report,
            @RequestHeader("X-User-Role") Role role) {
        return ResponseEntity.ok(analystService.uploadReport(report, role));
    }

    @GetMapping("/my-work")
    public ResponseEntity<List<AnalystReport>> getAnalystWorkload(
            @RequestHeader("X-Analyst-Id") Long analystId,
            @RequestHeader("X-User-Role") Role role) {
        return ResponseEntity.ok(analystService.getReportsByAnalyst(analystId, role));
    }

    @GetMapping("/my-suggestions")
    public ResponseEntity<List<AnalystReport>> getUserSuggestions(
            @RequestHeader("X-User-Id") Long userId,
            @RequestHeader("X-Subscription") SubscriptionStatus status) {
        return ResponseEntity.ok(analystService.getMyReports(userId, status));
    }

    @GetMapping("/user-history/{targetUserId}")
    public ResponseEntity<List<AnalystReport>> getUserHistory(
            @PathVariable Long targetUserId,
            @RequestHeader("X-User-Role") Role role) {
        return ResponseEntity.ok(analystService.getHistoryForUser(targetUserId, role));
    }
}