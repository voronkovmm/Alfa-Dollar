package ru.voronkovmm.dollar.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.voronkovmm.dollar.feign.FeignCurrency;
import ru.voronkovmm.dollar.feign.FeignGif;
import ru.voronkovmm.dollar.pojo.JustTestClass;

import java.time.LocalDate;

/**
 * Контроллер выводит gif в зависимости от разницы курса к доллару текущего дня ко вчерашнему
 *
 * author: Voronkov M.M.
 * @version 1.0
 */
@Controller
public class MyController {

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    JustTestClass justTestClass;
    @Autowired
    private FeignGif feignGif;
    @Autowired
    private FeignCurrency feignCurrency;

    @Value("${currency}")
    private String nameCurrency;

    /**
     * По адресу localhost:8080 возвращает gif
     *
     * @return возвращает URI gif
     */
    @GetMapping
    public ResponseEntity<String> getGif() {
        return new ResponseEntity<>(gif(), HttpStatus.OK);
    }

    /**
     * {@link #currencyCompare()} - сравнивает курсы валют и возвращает соответствующий тег для API запроса на получение URI gif
     * {@link #getUriGif(String)} - в зависимости от тэга посылает API запрос на получение URI gif
     *
     * @return - возвращает URI gif для {@link MyController}
     */
    private String gif() {
        String tag = currencyCompare();

        return getUriGif(tag);
    }

    /**
     * Метод сравнивает курсы валют за {@param today} и {@param yesterday} и возвращает соответствующий тег
     *
     * @return возвращает тэг в зависимости от разницы курса
     */
    private String currencyCompare() {
        LocalDate localDate = LocalDate.now().minusDays(1);
        String currencyYesterday = feignCurrency.getCurrencyYesterday(localDate.toString()).getBody();
        String currencyToday = feignCurrency.getCurrencyToday().getBody();

        Integer result = null;
        try {
            double today = objectMapper.readTree(currencyToday).get("rates").get(nameCurrency).asDouble();
            double yesterday = objectMapper.readTree(currencyYesterday).get("rates").get(nameCurrency).asDouble();

            justTestClass.setCurrencyToday(today);
            justTestClass.setCurrencyYesterday(yesterday);

            result = Double.compare(today, yesterday);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return result >= 0 ? "rich" : "broke";
    }

    /**
     * Метод в зависимости от содержания {@param tag} делает api запрос на получение URI gif
     *
     * @param tag - тэг 'rich || broke'
     * @return - возвращает URI на нужный gif для контроллера {@link #getGif()}
     */
    private String getUriGif(String tag) {

        justTestClass.setGifTag(tag);

        String request;
        if (tag.equals("rich"))
            request = feignGif.getGifRich().getBody();
        else
            request = feignGif.getGifBroke().getBody();

        String uriGif = null;
        try {
            JsonNode jsonNode = objectMapper.readTree(request);
            if (jsonNode == null)
                return "Oops no route and no API found with those values";
            else
                uriGif = objectMapper.readTree(request).get("data").get("images").get("fixed_width_still").get("url").asText();

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return uriGif;
    }
}
