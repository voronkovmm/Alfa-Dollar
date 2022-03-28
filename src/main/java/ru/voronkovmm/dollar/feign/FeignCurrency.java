package ru.voronkovmm.dollar.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * author - Voronkov M.M.
 * @version 1.0
 *
 * Релазиация Feign для API запросов по URL https://openexchangerates.org
 */
@FeignClient(value = "feign-currency", url = "${currencyUrl}")
public interface FeignCurrency {

    @RequestMapping(method = RequestMethod.GET, value = "/${currencyTodayUrn}{$currency}")
    ResponseEntity<String> getCurrencyToday();

    @RequestMapping(method = RequestMethod.GET, value = "/api/historical/{date}.json?app_id=ef419d4c53cd42f59011d721a5c373c8&symbols={$currency}")
    ResponseEntity<String> getCurrencyYesterday(@RequestParam("date") String date);
}
