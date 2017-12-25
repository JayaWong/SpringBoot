package com.jaya.demo;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;

public class Test1 {
	public static void main(String[] args) throws IOException {
		ProcessBuilder processBuilder = new ProcessBuilder("java","-version");
		
		Process process = processBuilder.start();
//		processBuilder.
//		OutputStream outputStream = process.getOutputStream();
//		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(outputStream));
//		bw.write("java -version");
//		bw.flush();
		InputStream inputStream = process.getInputStream();
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		byte[] buffer = new byte[100];
		int flag = -1;
		PrintStream out = System.out;
		while ((flag = inputStream.read(buffer)) != -1) {
			out.write(buffer, 0, flag);
			os.write(buffer, 0, flag);
		}
		
	}
}
