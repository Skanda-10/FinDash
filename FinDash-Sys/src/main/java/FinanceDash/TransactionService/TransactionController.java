package FinanceDash.TransactionService;

import FinanceDash.TransactionService.POJO.Transaction;
import FinanceDash.UserService.POJO.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public ResponseEntity<Transaction> addTransaction(
            @RequestBody Transaction transaction,
            @RequestHeader("X-User-Id") Long currentUserId,
            @RequestHeader("X-User-Role") Role role) {

        return ResponseEntity.ok(transactionService.addRecord(transaction, currentUserId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTransaction(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long currentUserId) {

        transactionService.deleteOwnRecord(id, currentUserId);
        return ResponseEntity.ok("Transaction deleted successfully.");
    }

    @GetMapping("/user/{targetUserId}")
    public ResponseEntity<List<Transaction>> getTransactions(
            @PathVariable Long targetUserId,
            @RequestHeader("X-User-Id") Long currentUserId, // New Header
            @RequestHeader("X-User-Role") Role requestorRole) {

        List<Transaction> records = transactionService.getRecords(targetUserId, currentUserId, requestorRole);
        return ResponseEntity.ok(records);
    }
}
