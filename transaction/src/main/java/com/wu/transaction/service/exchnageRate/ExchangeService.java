package com.wu.transaction.service.exchnageRate;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Service;


@Service
public class ExchangeService {

    
    public double convertCurrency(String sourceCurrency, String targetCurrency, double amount) throws Exception {
        JSONObject exchangeRates = getExchangeRates();
    
        if (exchangeRates != null) {
            JSONObject rates = exchangeRates.getJSONObject("conversion_rates");
            double sourceRate = rates.getDouble(sourceCurrency);
            double targetRate = rates.getDouble(targetCurrency);
    
            return (amount / sourceRate) * targetRate;
        }
    
        return 0;
    }
    
        public JSONObject getExchangeRates() throws Exception {
        String url = "https://v6.exchangerate-api.com/v6/c2ce17e238d052953dbe890a/latest/USD"; // Replace with the appropriate API endpoint
    
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(url);
    
        HttpResponse response = client.execute(request);
        HttpEntity entity = response.getEntity();
    
        if (entity != null) {
            String jsonString = EntityUtils.toString(entity);
            return new JSONObject(jsonString);
        }
    
        return null;
    }
}
    
    














// @Autowired
    // private CurrencyService currencyService;

    // private final org.slf4j.Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);
    
    // public TransactionAttributes transactionAttributes(String baseCurrencyCode, String targetCurrencyCode, double amount) throws Exception{
    //        JSONObject exchangeRates = getExchangeRates();
    //         logger.info("received conversion request for folloing attribute {}",baseCurrencyCode,targetCurrencyCode,amount);
    //     if (exchangeRates != null) {
    //         JSONObject rates = exchangeRates.getJSONObject("conversion_rates");
    //         double sourceRate = rates.getDouble(baseCurrencyCode);
    //         double targetRate = rates.getDouble(baseCurrencyCode);

    //         Double fee= currencyService.getFeeByCode(baseCurrencyCode);   
    //         Double transactionFee=(amount*fee)/100;
    //         double minFee=currencyService.getMinFeeByCode(baseCurrencyCode);
    //         double maxFee=currencyService.getMaxFeeByCode(baseCurrencyCode);
    //         // double finalFee=0.0;
            
    //         // if(transactionFee<minFee){
    //         //     finalFee=minFee;
    //         // }else if(transactionFee>maxFee){
    //         //     finalFee=maxFee;
    //         // }else if(minFee <= transactionFee && transactionFee <= maxFee){
    //         //     finalFee=transactionFee;
    //         // }
    //         double finalAmountToTransfer= amount-transactionFee;
    //         double convertedAmount=(amount / sourceRate) * targetRate;
    //         double amountCreditedToReceiver=(finalAmountToTransfer/sourceRate)*targetRate;
    //         DecimalFormat decimalFormat = new DecimalFormat("#.####");
    //         double roundedAmount = Double.parseDouble(decimalFormat.format(amountCreditedToReceiver));
    //         TransactionAttributes transactionAttributes=new TransactionAttributes();
    //         transactionAttributes.setConvertedAmount(convertedAmount);
    //         transactionAttributes.setFee(transactionFee);
    //         transactionAttributes.setFinalReceiverAmount(finalAmountToTransfer);
    //         transactionAttributes.setSourceRate(sourceRate);
    //         transactionAttributes.setTargetRate(targetRate);
    //         transactionAttributes.setAmountCreditedToReceiver(roundedAmount);
    //         logger.info("transaction attributes are{}",transactionAttributes);
    //         return transactionAttributes;
    //     }
    //     return null;

    // }
    // public double convertCurrency(String sourceCurrency, String targetCurrency, double amount) throws Exception {
    //     JSONObject exchangeRates = getExchangeRates();
    //     if (exchangeRates != null) {
    //         JSONObject rates = exchangeRates.getJSONObject("conversion_rates");
    //         double sourceRate = rates.getDouble(sourceCurrency);
    //         double targetRate = rates.getDouble(targetCurrency);

    //         Double fee= currencyService.getFeeByCode(sourceCurrency);   
    //         Double transactionFee=(amount*fee)/100;
    //         double minFee=currencyService.getMinFeeByCode(sourceCurrency);
    //         double maxFee=currencyService.getMaxFeeByCode(sourceCurrency);
    //         double finalFee=0.0;
            
    //         if(transactionFee<minFee){
    //             finalFee=minFee;
    //         }else if(transactionFee>maxFee){
    //             finalFee=maxFee;
    //         }else if(minFee <= transactionFee && transactionFee <= maxFee){
    //             finalFee=transactionFee;
    //         }
    //         double finalAmountToTransfer= amount-transactionFee;
    //         double convertedAmountToDeposit = (finalAmountToTransfer / sourceRate) * targetRate;
    //         return convertedAmountToDeposit;
    //     }
       
    //     return 0;
    // }
    
    //     public JSONObject getExchangeRates(String baseCurrencyCode) throws Exception {
    //     String url = "https://v6.exchangerate-api.com/v6/b727d899c9f1e15a4d054c4f/latest/"+baseCurrencyCode; // Replace with the appropriate API endpoint
    
    //     HttpClient client = HttpClientBuilder.create().build();
    //     HttpGet request = new HttpGet(url);
    
    //     HttpResponse response = client.execute(request);
    //     HttpEntity entity = response.getEntity();
    
    //     if (entity != null) {
    //         String jsonString = EntityUtils.toString(entity);
    //         return new JSONObject(jsonString);
    //     }
    
    //     return null;
    // } 
        