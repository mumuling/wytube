package com.cqxb.yecall.bean;

public class Test {
	public String success;
	public String code;
	public data data;

	public class data{
		public paginator paginator;
	}
	
	public class paginator{
		public String limit;
	}
}
