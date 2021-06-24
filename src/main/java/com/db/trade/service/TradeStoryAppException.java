package com.db.trade.service;
public class TradeStoryAppException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public TradeStoryAppException(String msg) {
		super(msg);
	}
	public TradeStoryAppException(Throwable cause) {
		super(cause);
	}
	public TradeStoryAppException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
