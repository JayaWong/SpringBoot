package com.jaya.demo;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.jaya.DemoSpirngboot1Application;

import junit.framework.Assert;
@RunWith(value=SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=DemoSpirngboot1Application.class)
@WebAppConfiguration
public class Test1 {
	@Test
	public void fun() {
		Assert.assertTrue("ok",false);
		Assert.assertFalse("no ok",false);
	}
	public static void main(String[] args) throws IOException {
		ProcessBuilder processBuilder = new ProcessBuilder("java","Hello");
		processBuilder.directory(new File("D:\\"));
		Map<String, String> environment = processBuilder.environment();
		System.out.println(environment.get("CLASSPATH"));
		environment.put("CLASSPATH", environment.get("CLASSPATH")+"D:\\;");
		Process process = processBuilder.start();
//		processBuilder.
//		OutputStream outputStream = process.getOutputStream();
//		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(outputStream));
//		bw.write("java -version");
//		bw.flush();
		InputStream inputStream = process.getInputStream();
		InputStream errorStream = process.getErrorStream();
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		byte[] buffer = new byte[100];
		int flag = -1;
		PrintStream out = System.out;
		while ((flag = inputStream.read(buffer)) != -1) {
			out.write(buffer, 0, flag);
			os.write(buffer, 0, flag);
		}
		out.write("===========\n".getBytes());
		while ((flag = errorStream.read(buffer)) != -1) {
			out.write(buffer, 0, flag);
			os.write(buffer, 0, flag);
		}
		
	}
}
