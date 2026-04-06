package FinanceDash.TransactionService;

import FinanceDash.TransactionService.POJO.Transaction;
import FinanceDash.UserService.POJO.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

    @Service
    public class TransactionServiceImpl implements TransactionService {
        @Autowired
        private TransactionRepository repository;

        @Override
        public Transaction addRecord(Transaction record, Long currentUserId) {
            record.setUserId(currentUserId);
            return repository.save(record);
        }

        @Override
        public void deleteOwnRecord(Long recordId, Long currentUserId) {
            Transaction txn = repository.findById(recordId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

            if (!txn.getUserId().equals(currentUserId)) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access Denied: Not your record");
            }
            repository.delete(txn);
        }

        @Override
        public List<Transaction> getRecords(Long targetUserId, Long currentUserId, Role requestorRole) {
            if (!targetUserId.equals(currentUserId) && requestorRole == Role.VIEWER) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access Denied: You can only view your own records.");
            }
            return repository.findByUserId(targetUserId);
        }
    }

