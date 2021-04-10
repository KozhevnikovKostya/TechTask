package com.kozhevnikov.TechTask;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

@SpringBootTest
class TechTaskApplicationTests {

	@Test
	void contextLoads() {
		BigDecimal val = new BigDecimal("200.20");
		BigDecimal ss = new BigDecimal("200.768");
		System.out.println(val.subtract(ss).setScale(2, RoundingMode.HALF_DOWN));

	}

}
