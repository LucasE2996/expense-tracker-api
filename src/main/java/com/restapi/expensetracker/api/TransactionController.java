package com.restapi.expensetracker.api;

import com.restapi.expensetracker.domain.Transaction;
import com.restapi.expensetracker.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/categories/{categoryId}/transactions")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @GetMapping("/{transactionId}")
    public ResponseEntity<Transaction> getTransactionById(
            HttpServletRequest request,
            @PathVariable("categoryId") Integer categoryId,
            @PathVariable("transactionId") Integer transactionId
    ) {
        final int userId = (Integer) request.getAttribute("userId");
        final Transaction transaction = transactionService.fetchTransactionById(userId, categoryId, transactionId);

        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Transaction> addTransaction(
            HttpServletRequest request,
            @PathVariable("categoryId") Integer categoryId,
            @RequestBody Map<String, Object> body
    ) {
        final int userId = (Integer) request.getAttribute("userId");
        final Double amount = Double.valueOf(body.get("amount").toString());
        final String note = (String) body.get("note");
        final Long transactionDate = (Long) body.get("transactionDate");

        final Transaction transaction = transactionService.addTransaction(userId, categoryId, amount, note, transactionDate);

        return new ResponseEntity<>(transaction, HttpStatus.CREATED);
    }
}
