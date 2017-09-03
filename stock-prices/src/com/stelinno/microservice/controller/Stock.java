package com.stelinno.microservice.controller;

import java.util.Date;

public class Stock {
	private String symbol;
	private String name;
	private double quotePrice;
	private Date quoteTime;
	private String exchange;
	
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getQuotePrice() {
		return quotePrice;
	}
	public void setQuotePrice(double quotePrice) {
		this.quotePrice = quotePrice;
	}
	public Date getQuoteTime() {
		return quoteTime;
	}
	public void setQuoteTime(Date quoteTime) {
		this.quoteTime = quoteTime;
	}
	public String getExchange() {
		return exchange;
	}
	public void setExchange(String exchange) {
		this.exchange = exchange;
	}
}
