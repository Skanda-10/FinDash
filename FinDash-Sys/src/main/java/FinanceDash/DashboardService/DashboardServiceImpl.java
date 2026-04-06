package FinanceDash.DashboardService;

import FinanceDash.TransactionService.POJO.*;
import FinanceDash.DashboardService.DashboardSummary;
import FinanceDash.TransactionService.TransactionRepository;
import FinanceDash.UserService.POJO.SubscriptionStatus;
import FinanceDash.UserService.UserRepository;
import FinanceDash.UserService.POJO.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    private TransactionRepository transactionRepo;

    @Autowired
    private UserRepository userRepo;

    @Override
    public DashboardSummary getSummary(Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        List<Transaction> allTxns = transactionRepo.findByUserId(userId);

        double income = allTxns.stream()
                .filter(t -> t.getType() == TransactionType.INCOME)
                .mapToDouble(Transaction::getAmount).sum();

        double expenses = allTxns.stream()
                .filter(t -> t.getType() == TransactionType.EXPENSE)
                .mapToDouble(Transaction::getAmount).sum();

        Map<Category, Double> categoryMap = allTxns.stream()
                .collect(Collectors.groupingBy(Transaction::getCategory,
                        Collectors.summingDouble(Transaction::getAmount)));

        List<Transaction> recent = allTxns.stream()
                .sorted(Comparator.comparing(Transaction::getTimestamp).reversed())
                .limit(5)
                .collect(Collectors.toList());

        DashboardSummary.DashboardSummaryBuilder builder = DashboardSummary.builder()
                .totalIncome(income)
                .totalExpenses(expenses)
                .netBalance(income - expenses)
                .categoryTotals(categoryMap)
                .recentActivity(recent);

        if (user.getSubscriptionStatus() == SubscriptionStatus.SUBSCRIBED) {
            builder.wealthInsight(generatePremiumAnalystReport(income, expenses, categoryMap));
            builder.isCallAnalystEnabled(true);
        } else {
            builder.wealthInsight("Subscribe to Premium to unlock custom Analyst reports and financial course correction.");
            builder.isCallAnalystEnabled(false);
        }

        return builder.build();
    }
    //These report logic to be changed as per Company's Analyst or reports will be directly sent to users mail or provided in FinDash UI
    private String generatePremiumAnalystReport(double income, double expenses, Map<Category, Double> categories) {
        //Sample Logic
        StringBuilder report = new StringBuilder("Analyst Report: ");

        double savingsRate = (income - expenses) / income;
        double diningSpending = categories.getOrDefault(Category.FOOD, 0.0);

        if (savingsRate < 0.2) {
            report.append("Your savings rate is below 20%. Recommended action: Review 'OTHERS' and 'BILLS' categories. ");
        }

        if (diningSpending > (income * 0.15)) {
            report.append("Warning: Food & Dining is exceeding 15% of your income. Suggestion: Cook more at home this month. ");
        }

        if (report.length() < 20) {
            report.append("Your financial health is excellent. Strategy: Maximize investment in S&P 500 Index Funds.");
        }

        return report.toString();
    }

    @Override
    public List<Transaction> getFilteredTransactions(Long userId, LocalDate from, LocalDate to, Category category, TransactionType type) {
        return transactionRepo.findByUserId(userId).stream()
                .filter(t -> !t.getTimestamp().toLocalDate().isBefore(from) &&
                        !t.getTimestamp().toLocalDate().isAfter(to))
                .filter(t -> category == null || t.getCategory() == category) // Optional Category Filter
                .filter(t -> type == null || t.getType() == type)             // Optional Type Filter
                .collect(Collectors.toList());
    }
}