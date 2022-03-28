package ru.voronkovmm.dollar;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import ru.voronkovmm.dollar.controller.MyController;
import ru.voronkovmm.dollar.feign.FeignCurrency;
import ru.voronkovmm.dollar.feign.FeignGif;
import ru.voronkovmm.dollar.pojo.JustTestClass;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Properties;

/**
 * Это тестовый класс
 *
 * author: Voronkov M.M.
 * @version 1.0
 */
@SpringBootTest
class DollarApplicationTests {

	@Autowired
	FeignGif feignGif;
	@Autowired
	FeignCurrency feignCurrency;
	@Autowired
	MyController myController;
	@Autowired
	JustTestClass justTestClass;
	@Autowired
	Properties properties;

	/**
	 * Очищает поля объекта {@param justTestClass} после каждого test-метода
	 */
	@AfterEach
	void clean() {
		justTestClass.setGifTag("");
		justTestClass.setCurrencyToday(0.0);
		justTestClass.setCurrencyYesterday(0.0);
	}

	@Test
	void contextLoads() {
	}

	/**
	 * Тестирует контроллер {@link MyController}
	 */
	@Test
	void testController() {
		ResponseEntity<String> responseStatus200 = myController.getGif();

		int status200 = responseStatus200.getStatusCodeValue();
		String body = responseStatus200.getBody();

		Assertions.assertEquals(200, status200);
		Assertions.assertTrue(body.startsWith("https://"), body);
	}

	/**
	 * Тестирует FeignGif && FeignCurrency {@link FeignGif}
	 */
	@Test
	void testFeignGifAndCurrencyGif() {
		ResponseEntity<String> gifBroke = feignGif.getGifBroke();
		ResponseEntity<String> gifRich = feignGif.getGifRich();

		Assertions.assertEquals(gifBroke.getStatusCodeValue(), 200);
		Assertions.assertEquals(gifRich.getStatusCodeValue(), 200);

		ResponseEntity<String> today = feignCurrency.getCurrencyToday();
		ResponseEntity<String> yesterday = feignCurrency.getCurrencyYesterday(LocalDate.now().minusDays(1).toString());

		Assertions.assertEquals(today.getStatusCodeValue(), 200);
		Assertions.assertEquals(yesterday.getStatusCodeValue(), 200);

		myController.getGif();
		System.out.println(justTestClass);
		int compare = Double.compare(justTestClass.getCurrencyToday(), justTestClass.getCurrencyYesterday());

		if (compare >= 0)
			Assertions.assertEquals("rich", justTestClass.getGifTag());
		else
			Assertions.assertEquals("broke", justTestClass.getGifTag());
	}

}
