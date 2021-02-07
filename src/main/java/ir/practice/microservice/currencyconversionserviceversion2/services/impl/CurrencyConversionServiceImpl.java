package ir.practice.microservice.currencyconversionserviceversion2.services.impl;

import ir.practice.microservice.currencyconversionserviceversion2.domain.CurrencyConversion;
import ir.practice.microservice.currencyconversionserviceversion2.services.CurrencyConversionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class CurrencyConversionServiceImpl implements CurrencyConversionService {

    @Value("${currency.exchange.service-base-url}")
    private String currencyExchangeEerviceBaseUrl;

    @Value("${currency.exchange-url}")
    private String currencyExchangeUrl;

    @Override
    public CurrencyConversion calculateCurrencyConversion(String from, String to, BigDecimal quantity) {

        Map<String, String> variables = new HashMap<>();
        variables.put("from", from);
        variables.put("to", to);

        String url = currencyExchangeEerviceBaseUrl + currencyExchangeUrl;

        ResponseEntity<CurrencyConversion> response = new RestTemplate().getForEntity(url, CurrencyConversion.class, variables);
        CurrencyConversion currencyConversion = response.getBody();

        return new CurrencyConversion(currencyConversion.getId(), from, to,  quantity,
                currencyConversion.getConversionMultiple(),
                currencyConversion.getConversionMultiple().multiply(quantity), currencyConversion.getEnvironment());

    /*    return new CurrencyConversion(1001L, from, to,  quantity,
                                                           BigDecimal.valueOf(65),
                                                           BigDecimal.valueOf(1300), "8001");*/
    }
}
