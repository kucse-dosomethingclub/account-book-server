package com.example.demo.transaction;

import com.example.demo.category.CategoryType;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    public void saveTransaction(TransactionDto.transactionRequest transactionDto, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        Transaction transaction = Transaction.builder()
                .userid(user.getId())
                .amount(transactionDto.amount())
                .transaction_date(OffsetDateTime.parse(transactionDto.transactionDate()))
                .category_id(transactionDto.categoryId())
                .source_id(transactionDto.sourceId())
                .memo(transactionDto.memo())
                .build();

        transactionRepository.addTransaction(transaction);
    }

    public List<TransactionDto.transactionResponse> getTransaction(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        List<Transaction> transactions = transactionRepository.getTransactions(user.getId());

        return transactions.stream()
                .map(t -> new TransactionDto.transactionResponse(
                        t.getId(),
                        t.getAmount(),
                        t.getTransaction_date(),
                        t.getCategory_id(),
                        t.getSource_id(),
                        t.getMemo()
                ))
                .toList();
    }

    public List<TransactionDto.transactionResponse> getCategoryTrans(Long CategoryId, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        List<Transaction> transactions = transactionRepository.getCategoryTrans(CategoryId, user.getId());

        return transactions.stream()
                .map(t -> new TransactionDto.transactionResponse(
                        t.getId(),
                        t.getAmount(),
                        t.getTransaction_date(),
                        t.getCategory_id(),
                        t.getSource_id(),
                        t.getMemo()
                ))
                .toList();
    }

    public List<TransactionDto.transactionResponse> getAmountTrans(CategoryType categoryType, String email) {
        User user =userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        if(categoryType == CategoryType.INCOME){
            List<Transaction> IncomeTransactions = transactionRepository
                    .getIncomeAmountTrans(user.getId());
            return IncomeTransactions.stream()
                    .map(t -> new TransactionDto.transactionResponse(
                            t.getId(),
                            t.getAmount(),
                            t.getTransaction_date(),
                            t.getCategory_id(),
                            t.getSource_id(),
                            t.getMemo()
                    ))
                    .toList();
        }
        else{
            List<Transaction> ExpenseTransactions = transactionRepository
                    .getExpenseAmountTrans(user.getId());
            return ExpenseTransactions.stream()
                    .map(t -> new TransactionDto.transactionResponse(
                            t.getId(),
                            t.getAmount(),
                            t.getTransaction_date(),
                            t.getCategory_id(),
                            t.getSource_id(),
                            t.getMemo()
                    ))
                    .toList();
        }
    }
}