package FinanceDash.DashboardService;

import FinanceDash.TransactionService.POJO.Category;
import FinanceDash.TransactionService.POJO.Transaction;
import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class DashboardSummary {
    private Double totalIncome;
    private Double totalExpenses;
    private Double netBalance;
    private Map<Category, Double> categoryTotals;
    private List<Transaction> recentActivity;
    private String wealthInsight;
    private boolean isCallAnalystEnabled;
}