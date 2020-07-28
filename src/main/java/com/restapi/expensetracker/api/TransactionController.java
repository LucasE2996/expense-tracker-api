package com.restapi.expensetracker.api;

import com.google.common.collect.ImmutableMap;
import com.restapi.expensetracker.Constants;
import com.restapi.expensetracker.domain.Transaction;
import com.restapi.expensetracker.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/categories/{categoryId}/transactions")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @GetMapping("")
    public ResponseEntity<List<Transaction>> getAllTransactions(
            HttpServletRequest request,
            @PathVariable("categoryId") Integer categoryId
    ) {
        final int userId = (Integer) request.getAttribute(Constants.USER_ID);
        List<Transaction> transactions = transactionService.fetchAllTransactions(userId, categoryId);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<Transaction> getTransactionById(
            HttpServletRequest request,
            @PathVariable("categoryId") Integer categoryId,
            @PathVariable("transactionId") Integer transactionId
    ) {
        final int userId = (Integer) request.getAttribute(Constants.USER_ID);
        final Transaction transaction = transactionService.fetchTransactionById(userId, categoryId, transactionId);

        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Transaction> addTransaction(
            HttpServletRequest request,
            @PathVariable("categoryId") Integer categoryId,
            @RequestBody Map<String, Object> body
    ) {
        final int userId = (Integer) request.getAttribute(Constants.USER_ID);
        final Double amount = Double.valueOf(body.get("amount").toString());
        final String note = (String) body.get("note");
        final Long transactionDate = (Long) body.get("transactionDate");

        final Transaction transaction = transactionService.addTransaction(userId, categoryId, amount, note, transactionDate);

        return new ResponseEntity<>(transaction, HttpStatus.CREATED);
    }

    @PostMapping("/{transactionId}")
    public ResponseEntity<Map<String, Boolean>> updateTransaction(
            HttpServletRequest request,
            @PathVariable("categoryId") Integer categoryId,
            @PathVariable("transactionId") Integer transactionId,
            @RequestBody Transaction transaction
    ) {
        final int userId = (Integer) request.getAttribute("userId");
        transactionService.updateTransaction(userId, categoryId, transactionId, transaction);
        return new ResponseEntity<>(ImmutableMap.of("success", true), HttpStatus.OK);
    }

    @DeleteMapping("/{transactionId}")
    public ResponseEntity<Map<String, Boolean>> deleteTransaction(
            HttpServletRequest request,
            @PathVariable("categoryId") Integer categoryId,
            @PathVariable("transactionId") Integer transactionId
    ) {
        final int userId = (Integer) request.getAttribute("userId");
        transactionService.removeTransaction(userId, categoryId, transactionId);
        return new ResponseEntity<>(ImmutableMap.of("success", true), HttpStatus.OK);
    }
}
