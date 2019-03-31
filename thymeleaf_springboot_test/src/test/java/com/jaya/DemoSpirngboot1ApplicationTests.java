package com.jaya;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoSpirngboot1ApplicationTests {
	@Value("${dev.aaa}")
	private String aaa;
	@Value("${test.bbb}")
	private String bbb;
	@Value("${dev.ccc}")
	private Integer ccc;
	@Test
	public void contextLoads() {
		System.out.println("-=======================================-");
		System.out.println("-=======================================-");
		System.out.println(aaa);
		assertTrue(aaa.equals("dev env"));
		assertFalse(!bbb.equals(aaa));
		assertEquals("heheda",  new Integer(8090) ,ccc);
		System.out.println("-=======================================-");
		System.out.println("-=======================================-");
	}

}
