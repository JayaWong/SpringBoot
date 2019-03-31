package com.jaya.demo;

import java.util.Date;

public class Hello {
	String c = "dfd";
	private String str;
	private Integer number;
	public static void main(String[] args) {
		String cbv = "dfdfd";
		System.out.println("hello world");
	}

	private  void extracted() {
		String a = "ddfdfdf";
		str = "dfdf";
		Date date = new Date();
		number = new Integer(2);
	}

	public String getC() {
		return c;
	}

	public void setC(String c) {
		this.c = c;
	}

	public String getStr() {
		return str;
	}

	public void setStr(String string) {
		this.str = string;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}
}
