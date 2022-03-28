package ru.voronkovmm.dollar.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * author - Voronkov M.M.
 * @version 1.0
 *
 * Релазиация Feign для API запроса по URL https://api.giphy.com
 */
@FeignClient(value = "feign-gif", url = "${gifUrl}", decode404 = true)
public interface FeignGif {

    @RequestMapping(method = RequestMethod.GET, value = "${gifUrnRich}")
    ResponseEntity<String> getGifRich();

    @RequestMapping(method = RequestMethod.GET, value = "${gifUrnBroke}")
    ResponseEntity<String> getGifBroke();
}
