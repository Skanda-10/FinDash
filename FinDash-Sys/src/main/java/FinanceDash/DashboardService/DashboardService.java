package FinanceDash.DashboardService;

import FinanceDash.TransactionService.POJO.Category;
import FinanceDash.TransactionService.POJO.Transaction;
import FinanceDash.TransactionService.POJO.TransactionType;

import java.time.LocalDate;
import java.util.List;

public interface DashboardService {
    DashboardSummary getSummary(Long userId);
    List<Transaction> getFilteredTransactions(Long userId, LocalDate from, LocalDate to, Category category, TransactionType type);
}