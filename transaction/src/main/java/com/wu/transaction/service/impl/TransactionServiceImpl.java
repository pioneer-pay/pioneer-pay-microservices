package com.wu.transaction.service.impl;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wu.transaction.entity.Summary;
import com.wu.transaction.entity.Transaction;
import com.wu.transaction.entity.UpdateBalance;
import com.wu.transaction.external.AccountFeignClient;
import com.wu.transaction.payload.ApiResponse;
import com.wu.transaction.repository.TransactionRepository;
import com.wu.transaction.service.TransactionService;
import com.wu.transaction.service.exchnageRate.CurrencyService;
import com.wu.transaction.service.exchnageRate.ExchangeService;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;
    
    @Autowired
    private ExchangeService exchangeService;

    private final AccountFeignClient accountFeignClient;

    public TransactionServiceImpl(AccountFeignClient accountFeignClient) {
        this.accountFeignClient = accountFeignClient;
    }
    @Autowired
    private CurrencyService currencyService;

    private final org.slf4j.Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);

    @Override
    public ApiResponse initiateTransfer(Transaction transaction) {
        transaction.setBaseCurrencyCode(transaction.getBaseCurrencyCode());
        transaction.setTargetCurrencyCode(transaction.getTargetCurrencyCode());
       transaction.setStatus("PENDING");
       transactionRepository.save(transaction);
       logger.info("Transaction Initiated ,Please wait for the successful completion of the transaction.");
       return new ApiResponse("Transaction Initiated..",true);
    }



    @Override
    public ApiResponse completeTransfer(Long transactionId) throws Exception {
        Transaction transaction = transactionRepository.findByTransactionId(transactionId);
        try {
            Double senderBalance = accountFeignClient.getBalance(transaction.getFromAccountId());
            Double receiverBalance = accountFeignClient.getBalance(transaction.getToAccountId());
            Double feeAmount=0.0;

             Double fee = currencyService.getFeeByCode(transaction.getBaseCurrencyCode());
             feeAmount = (transaction.getAmount() * fee) / 100;
            // if(transaction.getAmount()<=50 && transaction.getAmount()>0){
            //     Double fee = currencyService.getMinFeeByCode(transaction.getBaseCurrencyCode());
            //      feeAmount = (transaction.getAmount() * fee) / 100;
            // }
            // else if(transaction.getAmount()>50 && transaction.getAmount()<=100000){
            //     Double fee = currencyService.getFeeByCode(transaction.getBaseCurrencyCode());
            //     feeAmount = (transaction.getAmount() * fee) / 100;
            // }
            // else if(transaction.getAmount()>100000){
            //     Double fee = currencyService.getMaxFeeByCode(transaction.getBaseCurrencyCode());
            //    feeAmount = (transaction.getAmount() * fee) / 100;
            // }
            Double transactionAmountAfterFee = transaction.getAmount() - feeAmount;
            transaction.setCommission(feeAmount);
    
            double convertedAmount = exchangeService.convertCurrency(transaction.getBaseCurrencyCode(), transaction.getTargetCurrencyCode(), transactionAmountAfterFee);
    
            if (senderBalance < transaction.getAmount()) {
                logger.error("Insufficient balance{}");
                transaction.setStatus("FAILED");
                transaction.setDateTime(LocalDateTime.now());
                transactionRepository.save(transaction);
                return new ApiResponse("Insufficient balance", false);
            }
    
            UpdateBalance updateSenderBalance = new UpdateBalance(transaction.getFromAccountId(), senderBalance - transaction.getAmount());
            transaction.setTransferedAmount(convertedAmount);
            UpdateBalance updateReceiverBalance = new UpdateBalance(transaction.getToAccountId(), receiverBalance + convertedAmount);
            accountFeignClient.updateBalance(updateSenderBalance);
            accountFeignClient.updateBalance(updateReceiverBalance);
    
            transaction.setDateTime(LocalDateTime.now());
            transaction.setStatus("SUCCESS");
            transactionRepository.save(transaction);
            logger.info("Transaction successful!!");
            return new ApiResponse("Transaction Successful", true);
        } catch (Exception e) {
            logger.error("An error occurred during the transaction: {}", e.getMessage());
            transaction.setStatus("FAILED");
            return new ApiResponse("An error occurred during the transaction", false);
        }
    }
    
    @Override
    public List<Transaction> getTransactionHistoryByAccountId(String accountId) {
        try {
            List<Transaction> list = transactionRepository.findByFromAccountIdOrToAccountId(accountId, accountId);
            Collections.reverse(list);
            if (list.isEmpty()) {
                logger.info("Transaction history not found");
            }
            logger.info("Transaction History for :{}", accountId);
            return list;
        } catch (Exception e) {
            logger.error("Error occurred while fetching transaction history: {}", e.getMessage());
            return Collections.emptyList(); 
        }
    
    }



    @Override
    public Summary getSummary(String baseCurrencyCode, String targetCurrencyCode, Double amount) {
        Transaction transaction= new Transaction();
        Summary summary=new Summary();
        transaction.setBaseCurrencyCode(baseCurrencyCode);
        summary.setBaseCurrencyCode(baseCurrencyCode);
        transaction.setTargetCurrencyCode(targetCurrencyCode);
        summary.setTargetCurrencyCode(targetCurrencyCode);
        transaction.setAmount(amount);
        summary.setAmount(amount);
        Double fee= currencyService.getFeeByCode(baseCurrencyCode);
        Double commission= amount*fee/100;
        transaction.setCommission(commission);
        summary.setCommission(commission);
        Double transferAmountAfterfee=  transaction.getAmount() - (transaction.getAmount()*fee/100);
        Double receiveAmount=0.0;
        Double rate=0.0;
        try {
            receiveAmount = exchangeService.convertCurrency(transaction.getBaseCurrencyCode(),transaction.getTargetCurrencyCode() , transferAmountAfterfee);
            rate=receiveAmount/transferAmountAfterfee;
        } catch (Exception e) {
            e.printStackTrace();
        }
         transaction.setTransferedAmount(receiveAmount);
         summary.setReceivedMoney(receiveAmount);
         summary.setRate(rate);
         return summary;
    }

 

    

    
}
