package FinanceDash.TransactionService;

import FinanceDash.TransactionService.POJO.Transaction;
import FinanceDash.UserService.POJO.Role;

import java.util.List;

public interface TransactionService {
    Transaction addRecord(Transaction record, Long currentUserId);
    void deleteOwnRecord(Long recordId, Long currentUserId);
    List<Transaction> getRecords(Long targetUserId, Long currentUserId, Role requestorRole);
}
