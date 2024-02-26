package com.wu.transaction.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wu.transaction.entity.Summary;
import com.wu.transaction.entity.Transaction;
import com.wu.transaction.payload.ApiResponse;
import com.wu.transaction.service.TransactionService;
import com.wu.transaction.service.exchnageRate.CurrencyService;
import com.wu.transaction.service.exchnageRate.ExchangeService;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;
    
    @Autowired
    private ExchangeService exchangeService;
    
    @Autowired
    private CurrencyService currencyService;

    //get fee from json file
    @GetMapping("/currency/{code}")
    public double getFeeByCode(@PathVariable String code) {
        return currencyService.getFeeByCode(code);
    }

    //convert amount from one currency to another
    @GetMapping("/convert/{baseCurrencyCode}/{targetCurrencyCode}/{amount}")
    public double convertCurrencyCode( @PathVariable String baseCurrencyCode, @PathVariable String targetCurrencyCode,@PathVariable double amount) throws Exception {
        return exchangeService.convertCurrency(baseCurrencyCode,targetCurrencyCode,amount);
    }
    

    //transfer money from one country to another(initiate)
    @PostMapping("/initiate")
    public ResponseEntity<ApiResponse>initiateTransaction(@RequestBody Transaction transaction){
        return ResponseEntity.status(HttpStatus.OK).body(transactionService.initiateTransfer(transaction));
    }

    //get histroy for perticular user
    @GetMapping("/history/{accountId}")
    public ResponseEntity<List<Transaction>> showTransactionHistory(@PathVariable String accountId){
        return ResponseEntity.status(HttpStatus.OK).body(transactionService.getTransactionHistoryByAccountId(accountId));
    }

    //summary of transaction
    @GetMapping("/summary/{baseCurrencyCode}/{targetCurrencyCode}/{amount}")
    public ResponseEntity<Summary> showSummary(@PathVariable String baseCurrencyCode,@PathVariable String targetCurrencyCode,@PathVariable Double amount)
    {
        return ResponseEntity.status(HttpStatus.OK).body(transactionService.getSummary(baseCurrencyCode, targetCurrencyCode, amount));
    }
}
