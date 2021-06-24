package com.db.trade.controller;

import java.util.Date;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import com.db.trade.model.TradeStoryResponse;
import com.db.trade.service.TradeStoryAppException;

@RestControllerAdvice
public class TradeStoryExceptionController {
	@ExceptionHandler(value = DataIntegrityViolationException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public TradeStoryResponse exception(DataIntegrityViolationException ex, WebRequest request) {
		TradeStoryResponse message = new TradeStoryResponse(HttpStatus.CONFLICT.value(), new Date(), ex.getMessage(),
				request.getDescription(true));
		return message;
	}

	@ExceptionHandler(value = TradeStoryAppException.class)
	@ResponseStatus(HttpStatus.OK)
	public TradeStoryResponse exception(TradeStoryAppException ex, WebRequest request) {
		TradeStoryResponse message = new TradeStoryResponse(HttpStatus.OK.value(), new Date(), ex.getMessage(),
				request.getDescription(true));
		return message;
	}

	@ExceptionHandler(value = Throwable.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public TradeStoryResponse exception(Throwable ex, WebRequest request) {
		TradeStoryResponse message = new TradeStoryResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), new Date(),
				ex.getMessage(), request.getDescription(true));
		return message;
	}
}
