package FinanceDash.DashboardService;

import FinanceDash.TransactionService.POJO.Category;
import FinanceDash.TransactionService.POJO.Transaction;
import FinanceDash.TransactionService.POJO.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/summary")
    public ResponseEntity<DashboardSummary> getDashboard(@RequestHeader("X-User-Id") Long userId) {
        return ResponseEntity.ok(dashboardService.getSummary(userId));
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Transaction>> getFilteredData(
            @RequestHeader("X-User-Id") Long userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            @RequestParam(required = false) Category category,
            @RequestParam(required = false) TransactionType type) {

        return ResponseEntity.ok(dashboardService.getFilteredTransactions(userId, from, to, category, type));
    }
}