package com.db.trade.model;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

public class TradeStoryDto {
	private String tradeId;
	private int tradeVersion;
	private String counterPartyId;
	private String bookId;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date maturityDate;
	private String expired;

	public String getTradeId() {
		return tradeId;
	}

	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}

	public int getTradeVersion() {
		return tradeVersion;
	}

	public void setTradeVersion(int tradeVersion) {
		this.tradeVersion = tradeVersion;
	}

	public String getCounterPartyId() {
		return counterPartyId;
	}

	public void setCounterPartyId(String counterPartyId) {
		this.counterPartyId = counterPartyId;
	}

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public Date getMaturityDate() {
		return maturityDate;
	}

	public void setMaturityDate(Date maturityDate) {
		this.maturityDate = maturityDate;
	}

	public String getExpired() {
		return expired;
	}

	public void setExpired(String expired) {
		this.expired = expired;
	}
}
